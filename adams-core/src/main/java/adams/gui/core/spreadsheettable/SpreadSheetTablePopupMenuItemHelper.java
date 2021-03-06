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
 * SpreadSheetTablePopupMenuItemHelper.java
 * Copyright (C) 2015-2017 University of Waikato, Hamilton, NZ
 */

package adams.gui.core.spreadsheettable;

import adams.core.ClassLister;
import adams.data.spreadsheet.SpreadSheet;
import adams.gui.core.ConsolePanel;
import adams.gui.core.GUIHelper;
import adams.gui.core.SpreadSheetTable;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class for constructing popup menus for the SpreadSheetTable.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class SpreadSheetTablePopupMenuItemHelper {

  /**
   * Returns a sorted list of popup menu items for the specified superclass.
   *
   * @param cls		the superclass
   * @return		the list
   */
  protected static List<SpreadSheetTablePopupMenuItem> getItems(Class cls) {
    List<SpreadSheetTablePopupMenuItem>		result;
    String[]					classes;

    result = new ArrayList<>();
    classes = ClassLister.getSingleton().getClassnames(cls);
    for (String c : classes) {
      try {
	result.add((SpreadSheetTablePopupMenuItem) Class.forName(c).newInstance());
      }
      catch (Exception e) {
	ConsolePanel.getSingleton().append("Failed to instantiate SpreadSheetTable menu item: " + c, e);
      }
    }

    if (result.size() > 1)
      Collections.sort(result);

    return result;
  }

  /**
   * Adds the appropriate action to the menuitem.
   *
   * @param table	the table this menu is for
   * @param isRow	whether this is for a row or a column
   * @param actRow	the current actual row
   * @param selRow	the current selected row
   * @param column	the current column
   * @param sheet	the spreadsheet to use
   * @param menuitem	the menuitem to add the action to
   * @param item	the menu item scheme
   */
  protected static void addAction(final SpreadSheetTable table, final SpreadSheet sheet, boolean isRow, final int actRow, final int selRow, final int column, final JMenuItem menuitem, final SpreadSheetTablePopupMenuItem item) {
    if (isRow) {
      if (item instanceof PlotRow) {
	menuitem.addActionListener(new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
	    ((PlotRow) item).plotRow(table, sheet, actRow, selRow);
	  }
	});
      }
      else if (item instanceof ProcessRow) {
	menuitem.addActionListener(new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
	    ((ProcessRow) item).processRow(table, sheet, actRow, selRow);
	  }
	});
      }
      else if (item instanceof ProcessCell) {
	menuitem.addActionListener(new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
	    ((ProcessCell) item).processCell(table, sheet, actRow, selRow, column);
	  }
	});
      }
    }
    else {
      if (item instanceof PlotColumn) {
	menuitem.addActionListener(new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
	    ((PlotColumn) item).plotColumn(table, sheet, column);
	  }
	});
      }
      else if (item instanceof ProcessColumn) {
	menuitem.addActionListener(new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
	    ((ProcessColumn) item).processColumn(table, sheet, column);
	  }
	});
      }
    }
  }

  /**
   * Adds the available menu items to the menu.
   *
   * @param table	the table this menu is for
   * @param isRow	whether this is for a row or a column
   * @param actRow	the current actual row
   * @param selRow	the current selected row
   * @param column	the current column
   * @param menu	the menu to add the items to
   * @param items	the available schemes
   */
  protected static void addToPopupMenu(SpreadSheetTable table, boolean isRow, int actRow, int selRow, int column, JPopupMenu menu, List<SpreadSheetTablePopupMenuItem> items) {
    JMenuItem		menuitem;
    SpreadSheet		sheet;

    if (items.size() == 0)
      return;

    sheet = table.toSpreadSheet();

    if (menu.getComponent(menu.getComponentCount() - 1) instanceof JMenuItem)
      menu.addSeparator();
    for (SpreadSheetTablePopupMenuItem item: items) {
      menuitem = new JMenuItem(item.getMenuItem());
      if (item.getIconName() != null)
        menuitem.setIcon(GUIHelper.getIcon(item.getIconName()));
      addAction(table, sheet, isRow, actRow, selRow, column, menuitem, item);
      menu.add(menuitem);
    }
  }

  /**
   * Adds the available menu items to the menu.
   *
   * @param table	the table this menu is for
   * @param menu	the menu to add the items to
   * @param isRow	whether this is for a row or a column
   * @param actRow	the current actual row
   * @param selRow	the current selected row
   * @param column	the current column
   */
  public static void addToPopupMenu(SpreadSheetTable table, JPopupMenu menu, boolean isRow, int actRow, int selRow, int column) {
    menu.addSeparator();
    if (isRow) {
      addToPopupMenu(table, true, actRow, selRow, column, menu, getItems(PlotRow.class));
      addToPopupMenu(table, true, actRow, selRow, column, menu, getItems(ProcessRow.class));
      addToPopupMenu(table, true, actRow, selRow, column, menu, getItems(ProcessCell.class));
    }
    else {
      addToPopupMenu(table, false, actRow, selRow, column, menu, getItems(PlotColumn.class));
      addToPopupMenu(table, false, actRow, selRow, column, menu, getItems(ProcessColumn.class));
    }
  }

  /**
   * Adds the available menu items to the menu for processing selected rows.
   *
   * @param table	the table this menu is for
   * @param menu	the menu to add the items to
   * @param actRows	the current actual row
   * @param selRows	the current selected row
   * @see		ProcessSelectedRows
   */
  public static void addProcessSelectedRowsAction(SpreadSheetTable table, SpreadSheet sheet, JPopupMenu menu, int[] actRows, int[] selRows, SpreadSheetTablePopupMenuItem item) {
    JMenuItem		menuitem;
    ProcessSelectedRows	proc;
    boolean		enabled;

    proc = (ProcessSelectedRows) item;
    menuitem = new JMenuItem(item.getMenuItem());
    if (item.getIconName() != null)
      menuitem.setIcon(GUIHelper.getIcon(item.getIconName()));
    enabled = (selRows.length >= proc.minNumRows());
    if (proc.maxNumRows() > -1)
      enabled = enabled && (selRows.length <= proc.maxNumRows());
    menuitem.setEnabled(enabled);
    menuitem.addActionListener((ActionEvent e) -> {
      proc.processSelectedRows(table, sheet, actRows, selRows);
    });
    menu.add(menuitem);
  }

  /**
   * Adds the available menu items to the menu for processing selected rows.
   *
   * @param table	the table this menu is for
   * @param menu	the menu to add the items to
   * @param actRows	the current actual row
   * @param selRows	the current selected row
   * @see		ProcessSelectedRows
   */
  public static void addProcessSelectedRowsToPopupMenu(SpreadSheetTable table, JPopupMenu menu, int[] actRows, int[] selRows, List<SpreadSheetTablePopupMenuItem>items) {
    SpreadSheet		sheet;

    if (items.size() == 0)
      return;

    sheet = table.toSpreadSheet();

    if (menu.getComponent(menu.getComponentCount() - 1) instanceof JMenuItem)
      menu.addSeparator();
    for (SpreadSheetTablePopupMenuItem item: items)
      addProcessSelectedRowsAction(table, sheet, menu, actRows, selRows, item);
  }
}
