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
 * IntegerRangeTest.java
 * Copyright (C) 2015 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.source;

import adams.core.option.AbstractArgumentOption;
import adams.env.Environment;
import adams.flow.AbstractFlowTest;
import adams.flow.control.Flow;
import adams.flow.core.Actor;
import adams.test.TmpFile;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test for IntegerRange actor.
 *
 * @author fracpete
 * @author adams.core.option.FlowJUnitTestProducer (code generator)
 * @version $Revision$
 */
public class IntegerRangeTest
  extends AbstractFlowTest {

  /**
   * Initializes the test.
   *
   * @param name	the name of the test
   */
  public IntegerRangeTest(String name) {
    super(name);
  }

  /**
   * Called by JUnit before each test method.
   *
   * @throws Exception 	if an error occurs.
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    m_TestHelper.deleteFileFromTmp("dumpfile.txt");
  }

  /**
   * Called by JUnit after each test method.
   *
   * @throws Exception	if tear-down fails
   */
  protected void tearDown() throws Exception {
    m_TestHelper.deleteFileFromTmp("dumpfile.txt");
    
    super.tearDown();
  }

  /**
   * Performs a regression test, comparing against previously generated output.
   */
  public void testRegression() {
    performRegressionTest(
        new TmpFile[]{
          new TmpFile("dumpfile.txt")
        });
  }

  /**
   * 
   * Returns a test suite.
   *
   * @return		the test suite
   */
  public static Test suite() {
    return new TestSuite(IntegerRangeTest.class);
  }

  /**
   * Used to create an instance of a specific actor.
   *
   * @return a suitably configured <code>Actor</code> value
   */
  public Actor getActor() {
    AbstractArgumentOption    argOption;
    
    Flow flow = new Flow();
    
    try {
      argOption = (AbstractArgumentOption) flow.getOptionManager().findByProperty("actors");
      adams.flow.core.Actor[] actors1 = new adams.flow.core.Actor[2];

      // Flow.IntegerRange
      adams.flow.source.IntegerRange integerrange2 = new adams.flow.source.IntegerRange();
      argOption = (AbstractArgumentOption) integerrange2.getOptionManager().findByProperty("range");
      integerrange2.setRange((adams.core.Range) argOption.valueOf("2,7-10,18-32"));
      actors1[0] = integerrange2;

      // Flow.DumpFile
      adams.flow.sink.DumpFile dumpfile4 = new adams.flow.sink.DumpFile();
      argOption = (AbstractArgumentOption) dumpfile4.getOptionManager().findByProperty("outputFile");
      dumpfile4.setOutputFile((adams.core.io.PlaceholderFile) argOption.valueOf("${TMP}/dumpfile.txt"));
      dumpfile4.setAppend(true);

      actors1[1] = dumpfile4;
      flow.setActors(actors1);

      argOption = (AbstractArgumentOption) flow.getOptionManager().findByProperty("flowExecutionListener");
      adams.flow.execution.NullListener nulllistener7 = new adams.flow.execution.NullListener();
      flow.setFlowExecutionListener(nulllistener7);

    }
    catch (Exception e) {
      fail("Failed to set up actor: " + e);
    }
    
    return flow;
  }

  /**
   * Runs the test from commandline.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    Environment.setEnvironmentClass(adams.env.Environment.class);
    runTest(suite());
  }
}

