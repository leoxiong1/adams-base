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
 * VersusOrder.java
 * Copyright (C) 2011-2012 University of Waikato, Hamilton, New Zealand
 */

package adams.gui.visualization.stats.fourinone;

import java.awt.BorderLayout;
import java.awt.Graphics;

import weka.core.Instances;
import adams.data.statistics.StatUtils;
import adams.gui.visualization.core.AxisPanel;
import adams.gui.visualization.core.PaintablePanel;
import adams.gui.visualization.core.PlotPanel;
import adams.gui.visualization.core.plot.Axis;
import adams.gui.visualization.stats.paintlet.VsOrderPaintlet;

/**
 * Class that creates a versus order plot with the residuals
 * on the y axis and the position in the data on the x axis.
 *
 * @author msf8
 * @version $Revision$
 */
public class VersusOrder
extends PaintablePanel{

  /** for serialization */
  private static final long serialVersionUID = 6182760237927361108L;

  /** Instances containing the data */
  protected Instances m_Instances;

  /**Panel for displaying the data */
  protected VersusOrderPanel m_Plot;

  /**Paintlet for plotting the data */
  protected VsOrderPaintlet m_Val;

  /**Options for the vsorder plot */
  protected VersusOrderOptions m_VsOrderOptions;

  /** index of the residuals attribute within the instances */
  protected int m_Index;

  /**
   * For initializing the GUI.
   */
  @Override
  protected void initGUI() {
    super.initGUI();
    setLayout(new BorderLayout());
    m_Plot = new VersusOrderPanel();
    m_Plot.addPaintListener(this);
    add(m_Plot, BorderLayout.CENTER);
    m_Val = new VsOrderPaintlet();
    m_Val.setPanel(this);
  }

  /**
   * Set the options for the versus order
   * @param val			VersusOrderoptions object containing all the options for this versus order plot
   */
  public void setOptions(VersusOrderOptions val) {
    m_VsOrderOptions = val;
    m_VsOrderOptions.getAxisX().configure(m_Plot, Axis.BOTTOM);
    m_VsOrderOptions.getAxisY().configure(m_Plot, Axis.LEFT);
    removePaintlet(m_Val);
    m_Val = (VsOrderPaintlet) m_VsOrderOptions.getPaintlet().shallowCopy(true);
    m_Val.setPanel(this);
  }
  
  /**
   * Returns the plot panel of the panel, null if no panel present.
   *
   * @return		the plot panel
   */
  @Override
  public PlotPanel getPlot() {
    return m_Plot;
  }

  /**
   * Prepares the update, i.e., calculations etc.
   */
  @Override
  public void prepareUpdate() {
    if (m_Instances != null) {
      m_Val.setInstances(m_Instances);
      m_Val.setIndex(m_Index);
      m_Val.setRepaintOnChange(true);
      
      AxisPanel axisBottom = getPlot().getAxis(Axis.BOTTOM);
      AxisPanel axisLeft = getPlot().getAxis(Axis.LEFT);

      double[] residuals = m_Instances.attributeToDoubleArray(m_Index);
      double minY = StatUtils.min(residuals);
      double maxY = StatUtils.max(residuals);
      axisBottom.setMinimum(0);
      axisBottom.setMaximum(residuals.length -1);
      axisLeft.setMinimum(minY);
      axisLeft.setMaximum(maxY);
    }
  }

  /**
   * Returns true if the paintlets can be executed.
   *
   * @param g		the graphics context
   * @return		true if painting can go ahead
   */
  @Override
  protected boolean canPaint(Graphics g) {
    return (m_Plot != null) && (m_Instances != null);
  }

  /**
   * Get the instances for this versus order
   * @return			Instances for this plot
   */
  public Instances getInstances() {
    return m_Instances;
  }

  /**
   * Set the instances for this versus order
   * @param inst				Instances for this plot
   */
  public void setInstances(Instances inst) {
    m_Instances = inst;
  }

  /**
   * Set the index of the residuals attribute within the instances
   * @param val
   */
  public void setIndex(int val) {
    m_Index = val;
  }
}