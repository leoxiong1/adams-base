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
 * SpreadSheetUtils.java
 * Copyright (C) 2013-2015 University of Waikato, Hamilton, New Zealand
 */
package adams.data.spreadsheet;

import adams.core.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class for spreadsheet related functionality.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class SpreadSheetUtils {
  
  /**
   * Attempts to split a string, using the specified delimiter character.
   * A delimiter gets ignored if inside double quotes.
   * 
   * @param s		the string to split
   * @param delimiter	the delimiting character
   * @return		the parts (single array element if no range)
   */
  public static String[] split(String s, char delimiter) {
    return split(s, delimiter, false);
  }
  
  /**
   * Attempts to split a string, using the specified delimiter character.
   * A delimiter gets ignored if inside double quotes.
   * 
   * @param s		the string to split
   * @param delimiter	the delimiting character
   * @param unquote	whether to remove double quotes
   * @return		the parts (single array element if no range)
   */
  public static String[] split(String s, char delimiter, boolean unquote) {
    List<String>	result;
    int			i;
    StringBuilder	current;
    boolean		escaped;
    char		c;
    
    result = new ArrayList<String>();
    
    current = new StringBuilder();
    escaped = false;
    for (i = 0; i < s.length(); i++) {
      c = s.charAt(i);
      if (c == '"') {
	escaped = !escaped;
	current.append(c);
      }
      else if (c == delimiter) {
	if (escaped) {
	  current.append(c);
	}
	else {
	  if (unquote)
	    result.add(Utils.unDoubleQuote(current.toString()));
	  else
	    result.add(current.toString());
	  current.delete(0, current.length());
	}
      }
      else {
	current.append(c);
      }
    }
    
    // add last string
    if (current.length() > 0) {
      if (unquote)
	result.add(Utils.unDoubleQuote(current.toString()));
      else
	result.add(current.toString());
    }
    
    return result.toArray(new String[result.size()]);
  }

  /**
   * Creates list of column header names. Either using the comma-separated
   * list or, if that is empty, a made up list using "Col-" plus the index.
   *
   * @param numCols         the number of column headers to generate
   * @param customCols      the comma-separated list of custom headers, can be empty
   * @return                the generated list of headers
   */
  public static List<String> createHeader(int numCols, String customCols) {
    List<String>      result;

    result     = new ArrayList<>();
    customCols = customCols.trim();

    if (!customCols.isEmpty())
      result.addAll(Arrays.asList(customCols.split(",")));

    while (result.size() < numCols)
      result.add("Col-" + (result.size() + 1));

    while (result.size() > numCols)
      result.remove(result.size() - 1);

    return result;
  }
}
