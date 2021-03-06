/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ClassLister.java
 * Copyright (C) 2007-2018 University of Waikato, Hamilton, New Zealand
 */

package adams.core;

import adams.core.base.BaseRegExp;
import adams.core.option.OptionUtils;
import adams.env.ClassListerBlacklistDefinition;
import adams.env.ClassListerDefinition;
import adams.env.Environment;
import adams.flow.core.Compatibility;
import nz.ac.waikato.cms.locator.ClassLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Determines the classnames of superclasses that are to be displayed in
 * the GUI for instance.
 * <br><br>
 * <b>IMPORTANT NOTE:</b> Due to <a href="http://geekexplains.blogspot.com/2008/07/what-is-reentrant-synchronization-in.html" target="_blank">reentrant threads</a>,
 * the <code>getSingleton()</code> method is not allowed to be called from
 * <code>static {...}</code> blocks in classes that are managed by the
 * ClassLister class (and therefore the ClassLocator class). Since the
 * ClassLocator class loads classes into the JVM, the <code>static {...}</code>
 * block of these classes gets executed and the ClassLister gets initialized
 * once again. In this case, the previous singleton will most likely get
 * overwritten.
 * <br><br>
 * Calling the main method of this class allows listing of classes for
 * all or a specific superclass. Examples:
 * <pre>
 * - List all:
 *   adams.core.ClassLister -allow-empty
 * - List all actors:
 *   adams.core.ClassLister -super adams.flow.core.AbstractActor
 * - List only transformers:
 *   adams.core.ClassLister -super adams.flow.core.AbstractActor -match ".*\.transformer\..*"
 * </pre>
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 * @see #main(String[])
 */
public class ClassLister
  extends nz.ac.waikato.cms.locator.ClassLister {

  /** for serialization. */
  private static final long serialVersionUID = 8482163084925911272L;

  /** the name of the props file. */
  public final static String FILENAME = "ClassLister.props";

  /** the name of the props file. */
  public final static String BLACKLIST = "ClassLister.blacklist";

  /** the singleton. */
  protected static ClassLister m_Singleton;

  /**
   * Initializes the classlister.
   */
  protected ClassLister() {
    super();
    setPackages(Environment.getInstance().read(ClassListerDefinition.KEY));
    setBlacklist(Environment.getInstance().read(ClassListerBlacklistDefinition.KEY));
    initialize();
  }

  /**
   * Returns all the classes of the specified superclass (abstract class or
   * interface), but restricts it further to the specified class.
   *
   * @param superclass	abstract class or interface to get classes for
   * @param restriction	the interface that the classes must implement
   * @return		the class subset
   */
  public Class[] getClasses(Class superclass, Class restriction) {
    return getClasses(superclass, new Class[]{restriction});
  }

  /**
   * Returns all the classes of the specified superclass (abstract class or
   * interface), but restricts it further to the specified classes.
   *
   * @param superclass	abstract class or interface to get classes for
   * @param restriction	the interfaces that the classes must implement
   * @return		the class subset
   */
  public Class[] getClasses(Class superclass, Class[] restriction) {
    List<Class> 	result;
    Class[]		classes;
    Compatibility	comp;

    result  = new ArrayList<>();
    classes = getClasses(superclass);
    comp    = new Compatibility();
    for (Class cls: classes) {
      if (comp.isCompatible(new Class[]{cls}, restriction))
        result.add(cls);
    }

    return result.toArray(new Class[0]);
  }

  /**
   * For returning a list of all classes.
   *
   * @param managed	whether to restrict to managed classes
   * @return		the list of class names
   */
  public List<String> getAllClassnames(boolean managed) {
    List<String> 	result;
    int			i;
    String		name;
    Iterator<String> 	iter;

    result = new ArrayList<>();
    if (!managed) {
      // all classes
      iter = ClassLocator.getSingleton().getCache().packages();
      while (iter.hasNext()) {
	for (String cls: ClassLocator.getSingleton().getCache().getClassnames(iter.next())) {
	  if (cls.contains("$"))
	    continue;
	  result.add(cls);
	}
      }
    }
    else {
      // only managed classes
      for (String supercls : ClassLister.getSingleton().getSuperclasses()) {
	for (Class cls : ClassLister.getSingleton().getClasses(supercls))
	  result.add(cls.getName());
      }
    }
    Collections.sort(result);
    i = 0;
    name = "";
    while (i < result.size()) {
      if (!name.equals(result.get(i))) {
	name = result.get(i);
	i++;
      }
      else {
	result.remove(i);
      }
    }

    return result;
  }

  /**
   * Returns the singleton instance of the class lister.
   *
   * @return		the singleton
   */
  public static synchronized ClassLister getSingleton() {
    if (m_Singleton == null)
      m_Singleton = new ClassLister();

    return m_Singleton;
  }

  /**
   * Outputs a list of available conversions.
   *
   * @param args	the commandline options: [-env classname] [-super classname] [-match regexp]
   * @throws Exception	if invalid environment class or invalid regular expression
   */
  public static void main(String[] args) throws Exception {
    if (OptionUtils.helpRequested(args)) {
      System.out.println();
      System.out.println("Usage: " + ClassLister.class.getName() + " [-env <classname>] [-super <classname>] [-match <regexp>] [-allow-empty]");
      System.out.println();
      return;
    }

    // environment
    String env = OptionUtils.getOption(args, "-env");
    if (env == null)
      env = Environment.class.getName();
    Class cls = Class.forName(env);
    Environment.setEnvironmentClass(cls);

    // match
    String match = OptionUtils.getOption(args, "-match");
    if (match == null)
      match = BaseRegExp.MATCH_ALL;
    BaseRegExp regexp = new BaseRegExp(match);

    // allow empty class hierarchies?
    boolean allowEmpty = OptionUtils.hasFlag(args, "-allow-empty");

    // superclass
    String[] superclasses;
    String sclass = OptionUtils.getOption(args, "-super");
    if (sclass == null)
      superclasses = getSingleton().getSuperclasses();
    else
      superclasses = new String[]{sclass};

    // list them
    for (String superclass: superclasses) {
      cls = Class.forName(superclass);
      Class[] classes = getSingleton().getClasses(cls);
      if ((classes.length > 0) || allowEmpty) {
        System.out.println("--> " + superclass);
        for (Class c: classes) {
          if (regexp.isMatch(c.getName()))
            System.out.println(c.getName());
        }
        System.out.println();
      }
    }
  }
}
