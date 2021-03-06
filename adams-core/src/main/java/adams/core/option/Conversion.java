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

/**
 * Conversion.java
 * Copyright (C) 2010-2016 University of Waikato, Hamilton, New Zealand
 */
package adams.core.option;

import adams.core.Properties;
import adams.env.ConversionDefinition;
import adams.env.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Helper class that converts classnames from options on the fly into the
 * most up-to-date format.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Conversion {

  /** the name of the props file. */
  public final static String FILENAME = "Conversion.props";

  /** the key that lists the classname mapping keys. */
  public final static String KEY_RENAME = "Rename";

  /** the key that lists the partial renaming keys. */
  public final static String KEY_PARTIAL_RENAME = "PartialRename";

  /** the key that lists the keys for option renaming. */
  public final static String KEY_RENAME_OPTION = "RenameOption";

  /** the key that lists the keys for property renaming. */
  public final static String KEY_RENAME_PROPERTY = "RenameProperty";

  /** the properties. */
  protected Properties m_Properties;

  /** the mapping (exact classname replacement). */
  protected HashMap<String,String> m_Mapping;

  /** the partial mapping order. */
  protected List<String> m_MappingPartialOrder;

  /** the mapping (partial renaming). */
  protected HashMap<String,String> m_MappingPartial;

  /** the mapping (option renaming: class - [-old, -new]). */
  protected HashMap<String,HashMap<String,String>> m_MappingOption;

  /** the mapping (property renaming: class - [oldProp, newProp]). */
  protected HashMap<String,HashMap<String,String>> m_MappingProperty;

  /** the singleton. */
  protected static Conversion m_Singleton;

  /**
   * Initializes the conversions.
   */
  protected Conversion() {
    super();
    initMappings();
  }

  /**
   * Returns the properties that define the editor.
   *
   * @return		the properties
   */
  protected synchronized Properties getProperties() {
    if (m_Properties == null)
      m_Properties = Environment.getInstance().read(ConversionDefinition.KEY);

    return m_Properties;
  }

  /**
   * Initializes the mapping.
   */
  protected synchronized void initMappings() {
    Properties		props;
    String[]		keys;
    int			i;
    String[]		parts;

    if (m_Mapping == null) {
      props     = getProperties();

      // exact mappings
      keys      = props.getPath(KEY_RENAME, "").split(",");
      m_Mapping = new HashMap<>();
      if ((keys.length >= 1) && (keys[0].length() != 0)) {
	for (i = 0; i < keys.length; i++) {
	  if (!props.hasKey(keys[i])) {
	    System.err.println("Rename mapping '" + keys[i] + "' not found - skipped!");
	    continue;
	  }

	  parts = props.getPath(keys[i], "").split("-");
	  if (parts.length != 2) {
	    System.err.println(
		"Wrong format for rename mapping ('" + keys[i] + "'): "
		+ props.getProperty(keys[i]));
	    continue;
	  }

	  m_Mapping.put(parts[0], parts[1]);
	}
      }

      // partial renaming
      keys                  = props.getPath(KEY_PARTIAL_RENAME, "").split(",");
      m_MappingPartialOrder = new ArrayList<>();
      m_MappingPartial      = new HashMap<>();
      if ((keys.length >= 1) && (keys[0].length() != 0)) {
	for (i = 0; i < keys.length; i++) {
	  if (!props.hasKey(keys[i])) {
	    System.err.println("Partial renaming '" + keys[i] + "' not found - skipped!");
	    continue;
	  }

	  parts = props.getPath(keys[i], "").split("\t");
	  if (parts.length != 2) {
	    System.err.println(
		"Wrong format for partial renaming ('" + keys[i] + "'): "
		+ props.getProperty(keys[i]));
	    continue;
	  }

	  m_MappingPartialOrder.add(parts[0]);
	  m_MappingPartial.put(parts[0], parts[1]);
	}
      }

      // option renaming
      keys            = props.getPath(KEY_RENAME_OPTION, "").split(",");
      m_MappingOption = new HashMap<>();
      if ((keys.length >= 1) && (keys[0].length() != 0)) {
	for (i = 0; i < keys.length; i++) {
	  if (!props.hasKey(keys[i])) {
	    System.err.println("Option renaming '" + keys[i] + "' not found - skipped!");
	    continue;
	  }

	  parts = props.getPath(keys[i], "").split("#");
	  if (parts.length != 3) {
	    System.err.println(
		"Wrong format for option renaming ('" + keys[i] + "'): "
		+ props.getProperty(keys[i]));
	    continue;
	  }

	  if (!m_MappingOption.containsKey(parts[0]))
	    m_MappingOption.put(parts[0], new HashMap<>());
	  m_MappingOption.get(parts[0]).put(parts[1], parts[2]);
	}
      }

      // property renaming
      keys              = props.getPath(KEY_RENAME_PROPERTY, "").split(",");
      m_MappingProperty = new HashMap<>();
      if ((keys.length >= 1) && (keys[0].length() != 0)) {
	for (i = 0; i < keys.length; i++) {
	  if (!props.hasKey(keys[i])) {
	    System.err.println("Property renaming '" + keys[i] + "' not found - skipped!");
	    continue;
	  }

	  parts = props.getPath(keys[i], "").split("#");
	  if (parts.length != 3) {
	    System.err.println(
		"Wrong format for property renaming ('" + keys[i] + "'): "
		+ props.getProperty(keys[i]));
	    continue;
	  }

	  if (!m_MappingProperty.containsKey(parts[0]))
	    m_MappingProperty.put(parts[0], new HashMap<>());
	  m_MappingProperty.get(parts[0]).put(parts[1], parts[2]);
	}
      }
    }
  }

  /**
   * Renames a classname if necessary.
   *
   * @param classname	the classname to process
   * @return		the potentially updated classname
   */
  public String rename(String classname) {
    String	result;

    result = classname;

    // replace classname
    while (m_Mapping.containsKey(result))
      result = m_Mapping.get(result);

    // partial renaming
    for (String key: m_MappingPartialOrder)
      result = result.replace(key, m_MappingPartial.get(key));

    return result;
  }

  /**
   * Renames an option (eg -blah to -bloerk) if necessary.
   *
   * @param classname	the classname to process
   * @param option	the option to process (with or without leading dash)
   * @return		the potentially updated option
   */
  public String renameOption(String classname, String option) {
    String	result;
    boolean	dash;

    result = option;
    dash   = option.startsWith("-");

    if (dash)
      option = option.substring(1);
    if (m_MappingOption.containsKey(classname) && m_MappingOption.get(classname).containsKey(option)) {
      if (dash)
	result = "-" + m_MappingOption.get(classname).get(option);
      else
	result = m_MappingOption.get(classname).get(option);
    }
    
    return result;
  }

  /**
   * Renames a property (eg blahProp to bloerkProp) if necessary.
   *
   * @param classname	the classname to process
   * @param property	the property to process
   * @return		the potentially updated option
   */
  public String renameProperty(String classname, String property) {
    String	result;

    result = property;

    if (m_MappingProperty.containsKey(classname) && m_MappingProperty.get(classname).containsKey(property))
      result = m_MappingProperty.get(classname).get(property);
    
    return result;
  }

  /**
   * Returns the singleton.
   *
   * @return		the singleton
   */
  public static synchronized Conversion getSingleton() {
    if (m_Singleton == null)
      m_Singleton = new Conversion();
    return m_Singleton;
  }
}
