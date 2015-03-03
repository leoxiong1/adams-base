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
 * SpreadSheetColumnsByNameTest.java
 * Copyright (C) 2011 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.transformer;

import junit.framework.Test;
import junit.framework.TestSuite;
import adams.core.option.AbstractArgumentOption;
import adams.env.Environment;
import adams.flow.AbstractFlowTest;
import adams.flow.control.Flow;
import adams.flow.core.AbstractActor;
import adams.test.TmpFile;

/**
 * Test for SpreadSheetColumnsByName actor.
 *
 * @author fracpete
 * @author adams.core.option.FlowJUnitTestProducer (code generator)
 * @version $Revision$
 */
public class SpreadSheetColumnsByNameTest
  extends AbstractFlowTest {

  /**
   * Initializes the test.
   *
   * @param name	the name of the test
   */
  public SpreadSheetColumnsByNameTest(String name) {
    super(name);
  }

  /**
   * Called by JUnit before each test method.
   *
   * @throws Exception 	if an error occurs.
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();

    m_TestHelper.copyResourceToTmp("iris.csv");
    m_TestHelper.deleteFileFromTmp("dumpfile.txt");
  }

  /**
   * Called by JUnit after each test method.
   *
   * @throws Exception	if tear-down fails
   */
  @Override
  protected void tearDown() throws Exception {
    m_TestHelper.deleteFileFromTmp("iris.csv");
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
    return new TestSuite(SpreadSheetColumnsByNameTest.class);
  }

  /**
   * Used to create an instance of a specific actor.
   *
   * @return a suitably configured <code>AbstractActor</code> value
   */
  @Override
  public AbstractActor getActor() {
    AbstractArgumentOption    argOption;

    Flow flow = new Flow();

    try {
      argOption = (AbstractArgumentOption) flow.getOptionManager().findByProperty("actors");
      adams.flow.core.AbstractActor[] tmp1 = new adams.flow.core.AbstractActor[3];
      adams.flow.source.FileSupplier tmp2 = new adams.flow.source.FileSupplier();
      argOption = (AbstractArgumentOption) tmp2.getOptionManager().findByProperty("files");
      tmp2.setFiles(new adams.core.io.PlaceholderFile[]{(adams.core.io.PlaceholderFile) argOption.valueOf("${TMP}/iris.csv")});

      tmp1[0] = tmp2;
      adams.flow.transformer.SpreadSheetFileReader tmp4 = new adams.flow.transformer.SpreadSheetFileReader();
      argOption = (AbstractArgumentOption) tmp4.getOptionManager().findByProperty("reader");
      adams.data.io.input.CsvSpreadSheetReader tmp6 = new adams.data.io.input.CsvSpreadSheetReader();
      tmp4.setReader(tmp6);

      tmp1[1] = tmp4;
      adams.flow.control.Branch tmp7 = new adams.flow.control.Branch();
      argOption = (AbstractArgumentOption) tmp7.getOptionManager().findByProperty("branches");
      adams.flow.core.AbstractActor[] tmp8 = new adams.flow.core.AbstractActor[2];
      adams.flow.control.Sequence tmp9 = new adams.flow.control.Sequence();
      argOption = (AbstractArgumentOption) tmp9.getOptionManager().findByProperty("name");
      tmp9.setName((java.lang.String) argOption.valueOf("not inverted"));

      argOption = (AbstractArgumentOption) tmp9.getOptionManager().findByProperty("actors");
      adams.flow.core.AbstractActor[] tmp11 = new adams.flow.core.AbstractActor[2];
      adams.flow.transformer.SpreadSheetColumnsByName tmp12 = new adams.flow.transformer.SpreadSheetColumnsByName();
      argOption = (AbstractArgumentOption) tmp12.getOptionManager().findByProperty("regExp");
      tmp12.setRegExp((adams.core.base.BaseRegExp) argOption.valueOf("(sepal.*|class)"));

      tmp11[0] = tmp12;
      adams.flow.sink.DumpFile tmp14 = new adams.flow.sink.DumpFile();
      argOption = (AbstractArgumentOption) tmp14.getOptionManager().findByProperty("outputFile");
      tmp14.setOutputFile((adams.core.io.PlaceholderFile) argOption.valueOf("${TMP}/dumpfile.txt"));

      tmp14.setAppend(true);

      tmp11[1] = tmp14;
      tmp9.setActors(tmp11);

      tmp8[0] = tmp9;
      adams.flow.control.Sequence tmp16 = new adams.flow.control.Sequence();
      argOption = (AbstractArgumentOption) tmp16.getOptionManager().findByProperty("name");
      tmp16.setName((java.lang.String) argOption.valueOf("inverted"));

      argOption = (AbstractArgumentOption) tmp16.getOptionManager().findByProperty("actors");
      adams.flow.core.AbstractActor[] tmp18 = new adams.flow.core.AbstractActor[2];
      adams.flow.transformer.SpreadSheetColumnsByName tmp19 = new adams.flow.transformer.SpreadSheetColumnsByName();
      argOption = (AbstractArgumentOption) tmp19.getOptionManager().findByProperty("regExp");
      tmp19.setRegExp((adams.core.base.BaseRegExp) argOption.valueOf("(sepal.*|class)"));

      tmp19.setInvertMatching(true);

      tmp18[0] = tmp19;
      adams.flow.sink.DumpFile tmp21 = new adams.flow.sink.DumpFile();
      argOption = (AbstractArgumentOption) tmp21.getOptionManager().findByProperty("outputFile");
      tmp21.setOutputFile((adams.core.io.PlaceholderFile) argOption.valueOf("${TMP}/dumpfile.txt"));

      tmp21.setAppend(true);

      tmp18[1] = tmp21;
      tmp16.setActors(tmp18);

      tmp8[1] = tmp16;
      tmp7.setBranches(tmp8);

      argOption = (AbstractArgumentOption) tmp7.getOptionManager().findByProperty("numThreads");
      tmp7.setNumThreads((Integer) argOption.valueOf("0"));

      tmp1[2] = tmp7;
      flow.setActors(tmp1);

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

