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
 * AbstractExcelSpreadSheetReader.java
 * Copyright (C) 2010-2015 University of Waikato, Hamilton, New Zealand
 */
package adams.data.io.input;

import adams.core.Range;

/**
 * Ancestor for special Excel readers.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractExcelSpreadSheetReader
  extends AbstractMultiSheetSpreadSheetReaderWithMissingValueSupport
  implements NoHeaderSpreadSheetReader {

  /** for serialization. */
  private static final long serialVersionUID = 4755872204697328246L;

  /** whether to automatically extend the header if rows have more cells than header. */
  protected boolean m_AutoExtendHeader;

  /** the range of columns to force to be text. */
  protected Range m_TextColumns;

  /** whether the file has a header or not. */
  protected boolean m_NoHeader;

  /** the comma-separated list of column header names. */
  protected String m_CustomColumnHeaders;

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
      "no-auto-extend-header", "autoExtendHeader",
      true);

    m_OptionManager.add(
      "text-columns", "textColumns",
      new Range());

    m_OptionManager.add(
      "no-header", "noHeader",
      false);

    m_OptionManager.add(
      "custom-column-headers", "customColumnHeaders",
      "");
  }

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_TextColumns = new Range();
  }

  /**
   * Returns the default string for missing values.
   * 
   * @return		the default
   */
  @Override
  protected String getDefaultMissingValue() {
    return "";
  }

  /**
   * Sets whether to extend the header if rows have more cells than the header.
   *
   * @param value	if true then the header gets extended if necessary
   */
  public void setAutoExtendHeader(boolean value) {
    m_AutoExtendHeader = value;
    reset();
  }

  /**
   * Returns whether to extend the header if rows have more cells than the header.
   *
   * @return		true if the header gets extended if necessary
   */
  public boolean getAutoExtendHeader() {
    return m_AutoExtendHeader;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   *         		displaying in the explorer/experimenter gui
   */
  public String autoExtendHeaderTipText() {
    return "If enabled, the header gets automatically extended if rows have more cells than the header.";
  }

  /**
   * Sets the range of columns to treat as text.
   *
   * @param value	the range of columns
   */
  public void setTextColumns(Range value) {
    m_TextColumns = value;
    reset();
  }

  /**
   * Returns the range of columns to treat as text.
   *
   * @return		the range of columns
   */
  public Range getTextColumns() {
    return m_TextColumns;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   *         		displaying in the explorer/experimenter gui
   */
  public String textColumnsTipText() {
    return "The range of columns to treat as text.";
  }

  /**
   * Sets whether the file contains a header row or not.
   *
   * @param value	true if no header row available
   */
  public void setNoHeader(boolean value) {
    m_NoHeader = value;
    reset();
  }

  /**
   * Returns whether the file contains a header row or not.
   *
   * @return		true if no header row available
   */
  public boolean getNoHeader() {
    return m_NoHeader;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String noHeaderTipText() {
    return "If enabled, all rows get added as data rows and a dummy header will get inserted.";
  }

  /**
   * Sets the custom headers to use.
   *
   * @param value	the comma-separated list
   */
  public void setCustomColumnHeaders(String value) {
    m_CustomColumnHeaders = value;
    reset();
  }

  /**
   * Returns whether the file contains a header row or not.
   *
   * @return		the comma-separated list
   */
  public String getCustomColumnHeaders() {
    return m_CustomColumnHeaders;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String customColumnHeadersTipText() {
    return "The custom headers to use for the columns instead (comma-separated list); ignored if empty.";
  }
}
