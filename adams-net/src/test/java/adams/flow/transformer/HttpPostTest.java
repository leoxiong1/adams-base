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
 * HttpPostTest.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.transformer;

import junit.framework.Test;
import junit.framework.TestSuite;
import adams.env.Environment;
import adams.flow.AbstractOnlineFlowTest;
import adams.flow.control.Flow;
import adams.flow.core.AbstractActor;

/**
 * Test for HttpPost actor.
 * <br><br>
 * NB: Dummy test.
 *
 * @author fracpete
 * @version $Revision: 7457 $
 */
public class HttpPostTest
  extends AbstractOnlineFlowTest {

  /**
   * Initializes the test.
   *
   * @param name	the name of the test
   */
  public HttpPostTest(String name) {
    super(name);
  }

  /**
   *
   * Returns a test suite.
   *
   * @return		the test suite
   */
  public static Test suite() {
    return new TestSuite(HttpPostTest.class);
  }

  /**
   * Used to create an instance of a specific actor.
   *
   * @return a suitably configured <code>AbstractActor</code> value
   */
  @Override
  public AbstractActor getActor() {
    return new Flow();
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

