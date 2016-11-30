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
 * SimplePlot.java
 * Copyright (C) 2015-2016 University of Waikato, Hamilton, NZ
 */

package adams.gui.visualization.instances.instancestable;

import adams.core.Utils;
import adams.core.option.AbstractOptionHandler;
import adams.flow.control.Flow;
import adams.flow.control.StorageName;
import adams.flow.core.Actor;
import adams.flow.source.StorageValue;
import adams.flow.transformer.ArrayToSequence;
import adams.flow.transformer.MakePlotContainer;
import adams.gui.core.BaseFrame;
import adams.gui.core.GUIHelper;
import adams.gui.goe.GenericObjectEditorDialog;
import adams.gui.visualization.instances.InstancesTable;
import weka.core.Instances;

import javax.swing.SwingWorker;
import java.awt.Dialog.ModalityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Allows to perform a simple plot of a column or row.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class SimplePlot
  extends AbstractOptionHandler
  implements PlotColumn, PlotRow {

  private static final long serialVersionUID = -5624002368001818142L;

  /** the maximum of data points to plot. */
  public final static int MAX_POINTS = 1000;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Allows to generate a simple plot from a instances row or column";
  }

  /**
   * Returns the name for the menu item.
   *
   * @return            the name
   */
  @Override
  public String getMenuItem() {
    return "Simple plot...";
  }

  /**
   * Returns the name of the icon.
   *
   * @return            the name, null if none available
   */
  public String getIconName() {
    return "plot.gif";
  }

  /**
   * For sorting the menu items.
   *
   * @param o       the other item
   * @return        -1 if less than, 0 if equal, +1 if larger than this
   *                menu item name
   */
  @Override
  public int compareTo(InstancesTablePopupMenuItem o) {
    return getMenuItem().compareTo(o.getMenuItem());
  }

  /**
   * Allows the user to generate a plot from either a row or a column.
   *
   * @param data	the instances to use
   * @param isColumn	whether the to use column or row
   * @param index	the index of the row/column
   */
  protected void plot(final InstancesTable table, final Instances data, final boolean isColumn, int index) {
    final List<Double> 		list;
    List<Double> 		tmp;
    GenericObjectEditorDialog 	setup;
    int				i;
    final String		title;
    SwingWorker 		worker;
    adams.flow.sink.SimplePlot	last;
    int				numPoints;
    String			newPoints;
    int				col;
    int				row;
    Object			value;

    numPoints = isColumn ? data.numInstances() : data.numAttributes();
    if (numPoints > MAX_POINTS) {
      newPoints = GUIHelper.showInputDialog(null, "More than " + MAX_POINTS + " data points to plot - enter sample size:", "" + numPoints);
      if (newPoints == null)
	return;
      if (!Utils.isInteger(newPoints))
	return;
      numPoints = Integer.parseInt(newPoints);
    }
    else {
      numPoints = -1;
    }

    // let user customize plot
    if (GUIHelper.getParentDialog(table) != null)
      setup = new GenericObjectEditorDialog(GUIHelper.getParentDialog(table), ModalityType.DOCUMENT_MODAL);
    else
      setup = new GenericObjectEditorDialog(GUIHelper.getParentFrame(table), true);
    setup.setDefaultCloseOperation(GenericObjectEditorDialog.DISPOSE_ON_CLOSE);
    setup.getGOEEditor().setClassType(Actor.class);
    setup.getGOEEditor().setCanChangeClassInDialog(false);
    last = (adams.flow.sink.SimplePlot) table.getLastSetup(getClass(), true, !isColumn);
    if (last == null)
      last = new adams.flow.sink.SimplePlot();
    setup.setCurrent(last);
    setup.setLocationRelativeTo(GUIHelper.getParentComponent(table));
    setup.setVisible(true);
    if (setup.getResult() != GenericObjectEditorDialog.APPROVE_OPTION)
      return;
    last = (adams.flow.sink.SimplePlot) setup.getCurrent();
    table.addLastSetup(getClass(), true, !isColumn, last);

    // get data from instances
    tmp = new ArrayList<>();
    if (isColumn) {
      col = index + 1;
      for (i = 0; i < table.getRowCount(); i++) {
	value = table.getValueAt(i, col);
	if ((value != null) && (Utils.isDouble(value.toString())))
	  tmp.add(Utils.toDouble(value.toString()));
      }
    }
    else {
      row = index;
      for (i = 0; i < data.numAttributes(); i++) {
	if (data.attribute(i).isNumeric() && !data.instance(row).isMissing(i))
	  tmp.add(data.instance(row).value(i));
      }
    }

    if (numPoints > -1) {
      Collections.shuffle(tmp, new Random(1));
      list = tmp.subList(0, numPoints);
    }
    else {
      list = tmp;
    }

    // generate plot
    if (isColumn)
      title = "Column " + (index + 1) + "/" + data.attribute(index).name();
    else
      title = "Row " + (index + 1);

    worker = new SwingWorker() {
      @Override
      protected Object doInBackground() throws Exception {
	Flow flow = new Flow();
	flow.setDefaultCloseOperation(BaseFrame.DISPOSE_ON_CLOSE);

	StorageValue sv = new StorageValue();
	sv.setStorageName(new StorageName("values"));
	flow.add(sv);

	ArrayToSequence a2s = new ArrayToSequence();
	flow.add(a2s);

	MakePlotContainer mpc = new MakePlotContainer();
	mpc.setPlotName(title);
	flow.add(mpc);

        Object last = table.getLastSetup(SimplePlot.this.getClass(), true, !isColumn);
	adams.flow.sink.SimplePlot plot = (adams.flow.sink.SimplePlot) ((adams.flow.sink.SimplePlot) last).shallowCopy();
	plot.setShortTitle(true);
	plot.setShowSidePanel(false);
	plot.setName(title);
        plot.setX(-2);
        plot.setY(-2);
	flow.add(plot);

	flow.setUp();
	flow.getStorage().put(new StorageName("values"), list.toArray(new Double[list.size()]));
	flow.execute();
	flow.wrapUp();
	return null;
      }
    };
    worker.execute();
  }

  /**
   * Plots the specified column.
   *
   * @param table	the source table
   * @param data	the instances to use as basis
   * @param column	the column in the instances
   * @return		true if successful
   */
  @Override
  public boolean plotColumn(InstancesTable table, Instances data, int column) {
    plot(table, data, true, column);
    return true;
  }

  /**
   * Plots the specified row.
   *
   * @param table	the source table
   * @param data	the instances to use as basis
   * @param row	        the row in the instances
   * @return		true if successful
   */
  @Override
  public boolean plotRow(InstancesTable table, Instances data, int row) {
    plot(table, data, false, row);
    return true;
  }
}
