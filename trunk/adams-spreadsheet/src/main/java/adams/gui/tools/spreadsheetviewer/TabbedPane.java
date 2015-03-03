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
 * TabbedPane.java
 * Copyright (C) 2009-2014 University of Waikato, Hamilton, New Zealand
 */
package adams.gui.tools.spreadsheetviewer;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import adams.core.CleanUpHandler;
import adams.data.spreadsheet.SpreadSheet;
import adams.gui.core.DragAndDropTabbedPane;
import adams.gui.core.SpreadSheetTable;
import adams.gui.tools.SpreadSheetViewerPanel;

/**
 * A specialized tabbed pane with a few methods for easier access.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class TabbedPane
  extends DragAndDropTabbedPane {

  /** for serialization. */
  private static final long serialVersionUID = -2048229771213837710L;

  /** prefix for new titles. */
  public final static String PREFIX_TITLE = "new";

  /** the owning viewer. */
  protected SpreadSheetViewerPanel m_Owner;
  
  /** the number of decimals to display. */
  protected int m_NumDecimals;

  /** the custom background color for negative values (null if none set). */
  protected Color m_BackgroundNegative;

  /** the custom background color for positive values (null if none set). */
  protected Color m_BackgroundPositive;

  /**
   * Initializes the tabbed pane.
   * 
   * @param owner	the owning viewer
   */
  public TabbedPane(SpreadSheetViewerPanel owner) {
    super();
    setOwner(owner);

    getModel().addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
	tabSelected(e);
      }
    });
  }
  
  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_NumDecimals        = -1;
    m_BackgroundNegative = null;
    m_BackgroundPositive = null;
  }
  
  /**
   * Sets the owning viewer.
   * 
   * @param value	the owner
   */
  public void setOwner(SpreadSheetViewerPanel value) {
    m_Owner = value;
  }
  
  /**
   * Returns the owning viewer.
   * 
   * @return		the owner, null if none set
   */
  public SpreadSheetViewerPanel getOwner() {
    return m_Owner;
  }

  /**
   * Hook method that gets executed after a tab was successfully removed with
   * a middle mouse button click.
   * <p/>
   * Default implementation calls cleanUp() method of {@link CleanUpHandler} 
   * instances.
   * 
   * @param index	the original index
   * @param comp	the component that was removed
   */
  @Override
  protected void afterTabClosedWithMiddleMouseButton(int index, Component comp) {
    super.afterTabClosedWithMiddleMouseButton(index, comp);
    if (getOwner() != null)
      getOwner().updateMenu();
  }

  /**
   * Returns the number of panels in the tabbed pane.
   *
   * @return		the number of panels
   */
  public int getPanelCount() {
    return getTabCount();
  }

  /**
   * Returns all the image panels.
   *
   * @return		the image panels
   */
  public SpreadSheetPanel[] getAllPanels() {
    SpreadSheetPanel[]	result;
    int			i;
    
    result = new SpreadSheetPanel[getTabCount()];
    for (i = 0; i < getTabCount(); i++)
      result[i] = getPanelAt(i);
    
    return result;
  }

  /**
   * Returns the panel at the specified position.
   *
   * @param index	the tab index of the table
   * @return		the panel, null if not available or invalid index
   */
  public SpreadSheetPanel getPanelAt(int index) {
    SpreadSheetPanel	result;

    if ((index < 0) || (index >= getTabCount()))
      return null;
    
    result = (SpreadSheetPanel) getComponentAt(index);

    return result;
  }
  
  /**
   * Returns the table at the specified position.
   *
   * @param index	the tab index of the table
   * @return		the table, null if not available or invalid index
   */
  public SpreadSheetTable getTableAt(int index) {
    SpreadSheetTable	result;
    SpreadSheetPanel	panel;

    result = null;
    panel  = getPanelAt(index);
    if (panel != null)
      result = panel.getTable();

    return result;
  }
  
  /**
   * Returns the table at the specified position.
   *
   * @param index	the tab index of the table
   * @return		the table
   */
  public SpreadSheet getSheetAt(int index) {
    SpreadSheet		result;
    SpreadSheetTable	table;
    
    result = null;
    table  = getTableAt(index);
    if (table == null)
      return result;

    result = table.toSpreadSheet();

    return result;
  }

  /**
   * Sets the number of decimals to use.
   *
   * @param index	the tab index
   * @param numDec	the number of decimals to use
   */
  public void setNumDecimalsAt(int index, int numDec) {
    getTableAt(index).setNumDecimals(numDec);
  }

  /**
   * returns the number of decimals in use.
   *
   * @param index	the tab index
   * @return		the number of decimals in use
   */
  public int getNumDecimalsAt(int index) {
    return getTableAt(index).getNumDecimals();
  }

  /**
   * Sets the number of decimals to use for all tables.
   *
   * @param numDec	the number of decimals to use
   */
  public void setNumDecimals(int numDec) {
    int	i;

    m_NumDecimals = numDec;
    for (i = 0; i < getTabCount(); i++)
      setNumDecimalsAt(i, numDec);
  }
  
  /**
   * Returns the currently set number of decimals.
   * 
   * @return		the number of decimals to use
   */
  public int getNumDecimals() {
    return m_NumDecimals;
  }

  /**
   * Sets the background color to use negative values.
   *
   * @param index	the tab index
   * @param color	the color to use
   */
  public void setNegativeBackgroundAt(int index, Color color) {
    getTableAt(index).setNegativeBackground(color);
  }

  /**
   * Returns the background color in use for negative values.
   *
   * @param index	the tab index
   * @return		the color to use, null if none set
   */
  public Color getNegativeBackgroundAt(int index) {
    return getTableAt(index).getNegativeBackground();
  }

  /**
   * Sets the background color to use for negative values.
   *
   * @param color	the color to use, null to unset
   */
  public void setNegativeBackground(Color color) {
    int	i;

    m_BackgroundNegative = color;
    for (i = 0; i < getTabCount(); i++)
      setNegativeBackgroundAt(i, color);
  }
  
  /**
   * Returns the current background color used for negative values.
   * 
   * @return		the color to use, null if not set
   */
  public Color getNegativeBackground() {
    return m_BackgroundNegative;
  }

  /**
   * Sets the background color to use positive values.
   *
   * @param index	the tab index
   * @param color	the color to use
   */
  public void setPositiveBackgroundAt(int index, Color color) {
    getTableAt(index).setPositiveBackground(color);
  }

  /**
   * Returns the background color in use for positive values.
   *
   * @param index	the tab index
   * @return		the color to use, null if none set
   */
  public Color getPositiveBackgroundAt(int index) {
    return getTableAt(index).getPositiveBackground();
  }

  /**
   * Sets the background color to use for positive values.
   *
   * @param color	the color to use, null to unset
   */
  public void setPositiveBackground(Color color) {
    int	i;

    m_BackgroundPositive = color;
    for (i = 0; i < getTabCount(); i++)
      setPositiveBackgroundAt(i, color);
  }
  
  /**
   * Returns the current background color used for positive values.
   * 
   * @return		the color to use, null if not set
   */
  public Color getPositiveBackground() {
    return m_BackgroundPositive;
  }

  /**
   * Sets whether to show the formulas.
   *
   * @param index	the tab index
   * @param value	whether to show the formulas
   */
  public void setShowFormulasAt(int index, boolean value) {
    getTableAt(index).setShowFormulas(value);
  }

  /**
   * Returns whether to show the formulas.
   *
   * @param index	the tab index
   * @return		whether to show the formulas
   */
  public boolean getShowFormulas(int index) {
    return getTableAt(index).getShowFormulas();
  }

  /**
   * Sets whether to show the formulas.
   *
   * @param value	whether to show the formulas
   */
  public void setShowFormulas(boolean value) {
    int	i;

    for (i = 0; i < getTabCount(); i++)
      setShowFormulasAt(i, value);
  }

  /**
   * Returns the currently selected panel.
   *
   * @return		the current panel, null if not available
   */
  public SpreadSheetPanel getCurrentPanel() {
    return getPanelAt(getSelectedIndex());
  }

  /**
   * Returns the currently selected table.
   * 
   * @return		the table, null if none available
   */
  public SpreadSheetTable getCurrentTable() {
    return getTableAt(getSelectedIndex());
  }

  /**
   * Returns the currently selected sheet.
   * 
   * @return		the sheet, null if none available
   */
  public SpreadSheet getCurrentSheet() {
    return getSheetAt(getSelectedIndex());
  }
  
  /**
   * Adds the sheet.
   * 
   * @param file	the file this sheet is from
   * @param sheet	the sheet to add
   * @return		the create panel
   */
  public SpreadSheetPanel addTab(File file, SpreadSheet sheet) {
    return addTab(createTabTitle(file, sheet), sheet);
  }
  
  /**
   * Adds the sheet.
   * 
   * @param title	the title for the tab
   * @param sheet	the sheet to add
   * @return		the create panel
   */
  public SpreadSheetPanel addTab(String title, SpreadSheet sheet) {
    SpreadSheetPanel	result;
    
    result = new SpreadSheetPanel(this);
    result.setNumDecimals(m_NumDecimals);
    result.setNegativeBackground(m_BackgroundNegative);
    result.setPositiveBackground(m_BackgroundPositive);
    result.setSheet(sheet);
    addTab(title, result);
    setSelectedIndex(getTabCount() - 1);
    
    return result;
  }
  
  /**
   * Creates a tab title.
   * 
   * @param file	the file name to use
   * @param sheet	the sheet loaded from the file
   * @return		the generated filename
   */
  public String createTabTitle(File file, SpreadSheet sheet) {
    return file.getName() + (sheet.getName() != null ? "/" + sheet.getName() : "");
  }
  
  /**
   * Returns all the tab titles.
   * 
   * @return		the titles
   */
  public List<String> getTabTitles() {
    ArrayList<String>	result;
    int			i;
    
    result = new ArrayList<String>();
    
    for (i = 0; i < getTabCount(); i++)
      result.add(getTitleAt(i));
    
    return result;
  }
  
  /**
   * Returns a new title (does not reserve it).
   */
  public String newTitle() {
    String		result;
    int			i;
    HashSet<String>	titles;
    
    titles = new HashSet<String>(getTabTitles());
    i      = 0;
    do {
      i++;
      result = PREFIX_TITLE + i;
    }
    while (titles.contains(result));
    
    return result;
  }

  /**
   * Gets called when a tab gets selected.
   * 
   * @param e		the event that triggered the action
   */
  protected void tabSelected(ChangeEvent e) {
    // actor tabs
    if (getPanelCount() == 0)
      m_Owner.getViewerTabs().notifyTabs(
	  null,
	  new int[0]);
    else
      m_Owner.getViewerTabs().notifyTabs(
	  m_Owner.getCurrentPanel(),
	  m_Owner.getCurrentPanel().getTable().getSelectedRows());
  }
}