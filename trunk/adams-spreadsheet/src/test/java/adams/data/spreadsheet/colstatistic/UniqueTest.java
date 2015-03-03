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
 * UniqueTest.java
 * Copyright (C) 2013-2014 University of Waikato, Hamilton, New Zealand
 */
package adams.data.spreadsheet.colstatistic;

/**
 * Tests the Unique statistic generator.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class UniqueTest
  extends AbstractColumnStatisticTestCase {

  /**
   * Initializes the test.
   * 
   * @param name	the name of the test
   */
  public UniqueTest(String name) {
    super(name);
  }
  
  /**
   * Returns the filenames (without path) of the input data files to use
   * in the regression test.
   *
   * @return		the filenames
   */
  @Override
  protected String[] getRegressionInputFiles() {
    return new String[]{
	"labor.csv",
	"labor.csv",
	"labor.csv",
    };
  }

  /**
   * Returns the setups to use in the regression test.
   *
   * @return		the setups
   */
  @Override
  protected AbstractColumnStatistic[] getRegressionSetups() {
    Unique[]	result;
    
    result    = new Unique[3];
    result[0] = new Unique();
    result[1] = new Unique();
    result[2] = new Unique();
    
    return result;
  }

  @Override
  protected int[] getRegressionColumns() {
    return new int[]{
	0,
	1,
	16,
    };
  }
}
