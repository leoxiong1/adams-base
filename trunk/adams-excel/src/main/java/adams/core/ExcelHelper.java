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
 * ExcelHelper.java
 * Copyright (C) 2013 University of Waikato, Hamilton, New Zealand
 */
package adams.core;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import adams.data.spreadsheet.SpreadSheet;

/**
 * Helper class for Excel-related stuff.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ExcelHelper {

  /**
   * Returns row/column index based on the provided position string (e.g., A12).
   *
   * @param position	the position string to parse
   * @return		the array with row and column index (0-based indices)
   * @throws Exception	in case of an invalid position string
   */
  public static int[] getCellLocation(String position) throws Exception {
    int[]	result;
    
    result = SpreadSheet.getCellLocation(position);
    result[0]++;
    
    return result;
  }

  /**
   * Returns the position letter(s) of the column.
   *
   * @param col		the column index of the cell (0-based)
   * @return		the position string
   */
  public static String getColumnPosition(int col) {
    return SpreadSheet.getColumnPosition(col);
  }

  /**
   * Returns the position of the cell. A position is a combination of a number
   * of letters (for the column) and number (for the row).
   *
   * @param row		the row index of the cell (0-based)
   * @param col		the column index of the cell (0-based)
   * @return		the position string or null if not found
   */
  public static String getCellPosition(int row, int col) {
    String	result;

    result = getColumnPosition(col);

    if ((row == -1) || (col == -1))
      return result;

    result += (row + 1);

    return result;
  }
  
  /**
   * Creates a cellstyle for a date cell with the specified format.
   * 
   * @param wb		the workbook to use this format for
   * @param format	the format to use
   * @return		the generated style
   */
  public static CellStyle getDateCellStyle(Workbook wb, String format) {
    CellStyle 	result;
    
    result = wb.createCellStyle();
    result.setDataFormat(wb.createDataFormat().getFormat(format));
    
    return result;
  }
}
