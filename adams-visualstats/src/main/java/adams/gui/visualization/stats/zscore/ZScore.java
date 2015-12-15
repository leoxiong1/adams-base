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
 * ZScore.java
 * Copyright (C) 2011-2015 University of Waikato, Hamilton, New Zealand
 */

package adams.gui.visualization.stats.zscore;

import adams.core.Index;
import adams.core.base.BaseRegExp;
import adams.data.instance.Instance;
import adams.gui.core.BaseDialog;
import adams.gui.core.BaseSplitPane;
import adams.gui.core.GUIHelper;
import adams.gui.core.ParameterPanel;
import adams.gui.goe.GenericArrayEditorPanel;
import adams.gui.goe.GenericObjectEditorPanel;
import adams.gui.visualization.core.PaintablePanel;
import adams.gui.visualization.core.PlotPanel;
import adams.gui.visualization.core.PopupMenuCustomizer;
import adams.gui.visualization.core.plot.TipTextCustomizer;
import adams.gui.visualization.instance.InstanceContainer;
import adams.gui.visualization.instance.InstanceExplorer;
import adams.gui.visualization.stats.core.IndexSet;
import adams.gui.visualization.stats.paintlet.AbstractZScorePaintlet;
import adams.gui.visualization.stats.paintlet.ZScoreCircle;
import weka.core.Attribute;
import weka.core.Instances;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create a paintable panel displaying a z score plot panel as well
 * being able to choose attribute, paintlet and overlays.
 *
 * @author msf8
 * @version $Revision$
 */
public class ZScore
  extends PaintablePanel
  implements TipTextCustomizer, PopupMenuCustomizer {

  /** for serialization */
  private static final long serialVersionUID = 1398942330181177958L;

  /** z score plot to be displayed */
  protected ZScorePanel m_Plot;

  /**Instances to be displayed */
  protected Instances m_Instances;

  /**Paintlet used for drawing the data */
  protected AbstractZScorePaintlet m_Val;

  /**Default paintlet for the GOE panel */
  protected AbstractZScorePaintlet m_Def;

  /**overlays to be displayed */
  protected AbstractZScoreOverlay[] m_Over;

  /**default overlays for the GAE */
  protected AbstractZScoreOverlay[] m_Default;

  /**Index of the attribute to be plotted */
  protected int m_Index;

  /**Model for the attribute combo box*/
  protected DefaultComboBoxModel m_ComboBox;

  /**combo box for attribute selection */
  protected JComboBox m_Att;

  /** For displaying a generic object editor for choosing the paintlet */
  protected GenericObjectEditorPanel m_PanelPaintlet;

  /**For displaying a generic array editor for choosing overlays */
  protected GenericArrayEditorPanel m_PanelOverlay;

  /**Panel for displaying the key */
  protected JPanel m_Key;

  /**String for regular expression */
  protected BaseRegExp m_AttReg;

  /** Index for choosing attribute */
  protected Index m_Indx;

  /**Hit detector for tip text and pop up menu */
  protected ZScoreHitDetector m_Detect;

  /**Current displayexplorers containing instance explorers being displayed */
  protected ArrayList<DisplayExplorer> dialogs;

  /**number of instance explorer panels displayed */
  protected int m_NumDialogs;

  /** the split pane. */
  protected BaseSplitPane m_SplitPane;

  @Override
  protected void initialize() {
    super.initialize();
    m_Index = 0;
    m_NumDialogs = 0;
    m_AttReg = new BaseRegExp();
    m_Indx = new Index();
  }

  /**
   * set the instances to be used when drawing the z score graph
   * @param inst
   */
  public void setInstances(Instances inst) {
    m_Instances = inst;
  }

  /**
   * Get the instances being used by the z score plot
   * @return				instances used
   */
  public Instances getInstances() {
    return m_Instances;
  }

  @Override
  public PlotPanel getPlot() {
    return m_Plot;
  }

  @Override
  public void prepareUpdate() {
    if(m_Instances != null) {
      for(int i = 0; i< m_Over.length; i++) {
	if(m_Over[i].getPaintlet() != null) {
	  m_Over[i].getPaintlet().parameters(m_Instances, m_Index);
	  if(m_Over[i].getPaintlet().getCalculated() == false) {
	    m_Over[i].getPaintlet().calculate();
	  }
	}
      }
    }
  }

  @Override
  protected boolean canPaint(Graphics g) {
    if(m_Plot != null)
      return true;
    else
      return false;
  }

  /**
   * called by the class that creates this z score plot
   * called after the fields have all been set
   */
  public void reset() {
    //add the attributes to the combo box
    for(int i = 0; i< m_Instances.numAttributes(); i++) {
      m_ComboBox.addElement(m_Instances.attribute(i).name());
    }

    int temp = -1;
    temp = IndexSet.getIndex(m_AttReg, m_Indx, m_Instances, temp);
    if(temp == -1) {
      temp = 0;
      System.err.println("changed to 0");
    }

    if(m_Over == null) {
      m_Over = new AbstractZScoreOverlay[]{};
    }
    m_Att.setSelectedIndex(m_Index);
    m_Val.setPanel(this);
    m_Val.setInstances(m_Instances);
    change();
  }

  /**
   * called when new overlays have been chosen
   */
  private void changeOverlay() {
    removeOverlays();
    int len = ((AbstractZScoreOverlay[]) m_PanelOverlay.getCurrent()).length;
    m_Over = new AbstractZScoreOverlay[len];
    for(int i = 0; i < len; i++) {
      m_Over[i] = ((AbstractZScoreOverlay[]) m_PanelOverlay.getCurrent())[i].shallowCopy(true);
    }
    for(int i = 0; i< m_Over.length; i++) {
      AbstractZScoreOverlay temp = m_Over[i];
      temp.setInstances(m_Instances);
      temp.setParent(this);
      temp.setUp();
    }
    //update the key
    changeKey();
    repaint();
  }

  /**
   * remove existing overlays and their paintlets
   */
  public void removeOverlays() {
    if(m_Over != null) {
      for(int i = 0; i< m_Over.length; i++) {
	if(m_Over[i].getPaintlet() != null)
	  removePaintlet(m_Over[i].getPaintlet());
      }
      m_Over = null;
    }
  }

  /**
   * called when the paintlet used is changed
   */
  protected void changePaintlet() {
    removePaintlet(m_Val);
    m_Val = (AbstractZScorePaintlet)m_PanelPaintlet.getCurrent();
    m_Val.setPanel(this);
    m_Val.setIndex(m_Index);
    m_Val.setInstances(m_Instances);
    change();
  }

  @Override
  protected void initGUI() {
    super.initGUI();
    setLayout(new BorderLayout());

    //plot panel for displaying data
    m_Plot = new ZScorePanel();
    m_Plot.addPaintListener(this);

    ParameterPanel optionPanel = new ParameterPanel();
    m_SplitPane = new BaseSplitPane(BaseSplitPane.HORIZONTAL_SPLIT);
    m_SplitPane.setLeftComponent(m_Plot);
    JPanel hold = new JPanel(new BorderLayout());
    hold.add(optionPanel, BorderLayout.NORTH);
    m_SplitPane.setRightComponent(hold);
    m_SplitPane.setResizeWeight(1.0);
    m_SplitPane.setOneTouchExpandable(true);
    add(m_SplitPane, BorderLayout.CENTER);
    hold.setPreferredSize(new Dimension(500,0));

    m_ComboBox = new DefaultComboBoxModel();
    m_Att = new JComboBox(m_ComboBox);
    AttListener listen = new AttListener(this);
    m_Att.addItemListener(listen);

    if(m_Val == null) {
      m_Def = new ZScoreCircle();
      m_Val = new ZScoreCircle();
    }
    else
      m_Def = m_Val;
    //GOE panel for choosing paintlet
    m_PanelPaintlet = new GenericObjectEditorPanel(AbstractZScorePaintlet.class, m_Def, true);
    m_PanelPaintlet.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
	changePaintlet();
      }
    });

    m_Default = new AbstractZScoreOverlay[]{};
    //GAE panel for choosing overlays
    m_PanelOverlay = new GenericArrayEditorPanel(m_Default);
    changeOverlay();
    m_PanelOverlay.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
	changeOverlay();
      }});

    //Panel to hold the key
    JPanel east = new JPanel(new BorderLayout());
    east.setPreferredSize(new Dimension(100, 50));
    JLabel title = new JLabel("key");
    title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    JPanel titleHolder = new JPanel();
    titleHolder.add(title);

    m_Key = new JPanel();
    m_Key.setBackground(Color.WHITE);
    m_Key.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    m_Key.setLayout(new BoxLayout(m_Key, BoxLayout.Y_AXIS));
    JPanel keyHold = new JPanel();
    m_Key.setPreferredSize(new Dimension(100,200));
    keyHold.add(m_Key);
    JPanel south = new JPanel(new BorderLayout());
    south.add(titleHolder, BorderLayout.NORTH);
    south.add(keyHold, BorderLayout.SOUTH);
    east.add(south, BorderLayout.SOUTH);
    JCheckBox grid = new JCheckBox();

    grid.addItemListener(new gridListener());

    changeKey();

    optionPanel.addParameter("attribute", m_Att);
    optionPanel.addParameter("Paintlet", m_PanelPaintlet);
    optionPanel.addParameter("Overlays", m_PanelOverlay);
    optionPanel.addParameter("Add grid", grid);

    hold.add(east, BorderLayout.CENTER);

    m_Detect = new ZScoreHitDetector(this);
    //set tiptext customizer and popup menu customizer for
    //the mouse events
    getPlot().setTipTextCustomizer(this);
    getPlot().setPopupMenuCustomizer(this);
  }

  /**
   * Listener for the grid check box
   * @author msf8
   *
   */
  public class gridListener implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
      //need to change this to use the existing grid
      JCheckBox check = (JCheckBox)e.getSource();
      if(check.isSelected()) {
	m_Plot.getLeft().setShowGridLines(true);
	m_Plot.getBottom().setShowGridLines(true);
	m_Plot.setGridColor(Color.LIGHT_GRAY);
      }
      else {
	m_Plot.getLeft().setShowGridLines(false);
	m_Plot.getBottom().setShowGridLines(false);
      }
      m_Plot.repaint();
    }
  }

  /**
   * Updates the key when the overlays are changed
   */
  private void changeKey() {
    //remove any existing buttons
    if(m_Key != null)  {
      m_Key.removeAll();
      int len = m_Over.length;
      for(int i = 0; i< len; i++) {
	JButton temp = new JButton(m_Over[i].shortName());
	//make it not look like a button
	temp.setBorder(null);
	temp.setFocusPainted(false);
	temp.setMargin(new Insets(0, 0, 0, 0));
	temp.setContentAreaFilled(false);
	temp.setBorderPainted(false);
	temp.setOpaque(false);
	temp.setForeground(m_Over[i].getColor());
	m_Key.add(temp);
	temp.addActionListener(new pushButton());
      }
      repaint();
      revalidate();
    }
  }

  /**
   * Listener for when the user clicks on an overlay in the key
   * @author msf8
   *
   */
  public class pushButton implements ActionListener {

    public void actionPerformed(ActionEvent arg0) {
      //overlay pressed
      JButton push = (JButton)arg0.getSource();
      int index =0 ;
      //find position of overlay
      for(int i = 0; i< m_Over.length; i++) {
	if(m_Over[i].shortName().equals(push.getText())) {
	  index = i;
	  break;
	}
      }
      //Color chosen
      Color newCol = JColorChooser.showDialog(ZScore.this, "Choose overlay color", m_Over[index].getColor());
      //If a color has been chosen
      if(newCol != null) {
	//change color of the overlay in the GAE and thus also the paintlet color
	AbstractZScoreOverlay[] temp = (AbstractZScoreOverlay[])m_PanelOverlay.getCurrent();
	temp[index].setColor(newCol);
	m_PanelOverlay.setCurrent(temp);
	//change color of the overlay actually drawn
	m_Over[index].setColor(newCol);
	//changes color of the items in the key
	changeKey();
      }
    }
  }

  /**
   * Listener for when the attribute combobox selection changes
   * @author msf8
   *
   */
  protected class AttListener implements ItemListener {
    ZScore m_parent;
    public AttListener(ZScore parent) {
      m_parent = parent;
    }
    public void itemStateChanged(ItemEvent arg0) {
      Instances inst = m_parent.getInstances();
      if(arg0.getStateChange() == ItemEvent.SELECTED) {
	String item =(String)arg0.getItem();
	Attribute chose = inst.attribute(item);
	//Finds the position of the attribute chosen
	for(int i = 0; i< inst.numAttributes(); i++) {
	  if(inst.attribute(i) == chose) {
	    m_Index = i;
	    for(int t = 0; t< m_Over.length; t++) {
	      m_Over[t].getPaintlet().setCalculated(false);
	    }
	    change();
	    break;
	  }
	}
      }
    }
  }

  /**
   * prepare for displaying
   */
  public void change() {
    if(m_Instances != null) {
      m_Plot.setInstances(m_Instances);
      m_Plot.setIndex(m_Index);
      m_Plot.reset();
      m_Val.setIndex(m_Index);
      m_Val.setInstances(m_Instances);
      update();
      validate();
      repaint();
    }
  }

  /**
   * get the index of the attribute being displayed
   * @return			index of attribute
   */
  public int getIndex() {
    return m_Index;
  }

  /**
   * set the overlays to be displayed on the z score
   * @param val				Array of overlays
   */
  public void setOverlays(AbstractZScoreOverlay[] val) {
    AbstractZScoreOverlay[]	overlays;
    int				i;
    
    overlays = new AbstractZScoreOverlay[val.length];
    for (i = 0; i < val.length; i++)
      overlays[i] = val[i].shallowCopy();
    m_PanelOverlay.setCurrent(overlays);
    changeOverlay();
  }

  /**
   * Set the paintlet to be used when plotting the data
   * @param val				Paintlet for plotting
   */
  public void setPaintlet(AbstractZScorePaintlet val) {
    m_PanelPaintlet.setCurrent(val.shallowCopy());
    removePaintlet(m_Val);
    m_Val = val;
  }

  /**
   * Set the regular expression used when choosing the attribute
   * @param val			String for regular expression
   */
  public void setAttReg(BaseRegExp val) {
    m_AttReg = val;
  }

  /**
   * Set the index to use when setting the attribute using an index
   * @param val			index for choosing attribute
   */
  public void setAttindex(Index val) {
    m_Indx = val;
  }

  public String processTipText(PlotPanel panel, Point mouse, String tiptext) {

    MouseEvent event;
    String hit;
    String result = "";

    event  = new MouseEvent(
	getPlot().getContent(),
	MouseEvent.MOUSE_MOVED,
	new Date().getTime(),
	0,
	(int) mouse.getX(),
	(int) mouse.getY(),
	0,
	false);

    hit = (String)m_Detect.detect(event);
    //if over a data point
    if(hit != null)
      result += hit;
    //returns the string to display as a tip text, has data
    //point and value
    return result;
  }

  public void customizePopupMenu(MouseEvent e, JPopupMenu menu) {
    //If list of instances explorers hasn't been created
    if(dialogs == null) {
      dialogs = new ArrayList<DisplayExplorer>();
    }
    JMenuItem diffPanel;
    JMenuItem view;
    //if on a point
    if(m_Detect.detect(e) != null) {
      String info = (String)m_Detect.detect(e);
      //position of instance in data
      String text = info.substring(info.indexOf(":") +1,info.indexOf(","));
      int pos = Integer.parseInt(text);
      final Instance inst = new Instance();
      inst.setID((pos + 1) + ". " + m_Instances.relationName());
      inst.set(m_Instances.instance(pos));

      //new item in the popup menu
      if(dialogs.size() == 0) {
        view = new JMenuItem();
        view.setIcon(GUIHelper.getEmptyIcon());
        view.setText("View instance...");
        view.addActionListener((ActionEvent arg0) -> {
          //show all the data for the instance
          //number to call this instances explorer, keeps incrementing
          m_NumDialogs ++;
          final DisplayExplorer temp = new DisplayExplorer();
          temp.setName("Explorer (" + m_NumDialogs + ")");
          temp.setTitle("Explorer (" + m_NumDialogs + ")");
          InstanceExplorer explore = new InstanceExplorer();
          //custom close operation
          temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
              dialogs.remove(temp);
              temp.dispose();
            }
          });
          temp.setDefaultCloseOperation(BaseDialog.DO_NOTHING_ON_CLOSE);
          explore = new adams.gui.visualization.instance.InstanceExplorer();
          List<InstanceContainer> data = new ArrayList<InstanceContainer>();
          data.add(explore.getContainerManager().newContainer(inst));
          //add the instance to the container manager
          explore.getContainerManager().addAll(data);
          //add instance explorer to the displayexplorer object
          temp.add(explore);
          temp.setPreferredSize(new Dimension(900,500));
          temp.pack();
          temp.setVisible(true);
          //add to listt of display explorers
          dialogs.add(temp);
          temp.setExplore(explore);
	});
	menu.addSeparator();
	menu.add(view);
      }
      //existing windows
      else {
	menu.addSeparator();
	//if more than one panel to choose from
	if(dialogs.size()>1) {
	  //create a menuitem with a submenu
	  JMenu samePanel = new JMenu();
          samePanel.setIcon(GUIHelper.getEmptyIcon());
	  samePanel.setToolTipText("Display this instance on an existing instance explorer");
	  samePanel.setText("Display on existing panel");
	  menu.add(samePanel);
	  //add an option for each of the existing panels
	  for(int i = 0; i< dialogs.size(); i++) {
	    JMenuItem sub = new JMenuItem();
	    sub.setText(dialogs.get(i).getName());
	    final int y = i;
	    sub.addActionListener((ActionEvent ae) -> {
              final DisplayExplorer temp = dialogs.get(y);
              InstanceExplorer explore = temp.getExplore();
              List<InstanceContainer> data = new ArrayList<InstanceContainer>();
              data.add(explore.getContainerManager().newContainer(inst));
              //add the instance to the container manager
              explore.getContainerManager().addAll(data);
              temp.repaint();
	    });
	    samePanel.add(sub);
	  }
	}
	//only one existing panel
	else {
	  JMenuItem samePanel = new JMenuItem();
          samePanel.setIcon(GUIHelper.getEmptyIcon());
	  samePanel.setToolTipText("Display this instance on the existing instance explorer");
	  samePanel.setText("Display on existing panel");
	  menu.add(samePanel);
	  samePanel.addActionListener((ActionEvent arg0) -> {
            final DisplayExplorer temp = dialogs.get(0);
            //existing instance explorer
            InstanceExplorer explore = temp.getExplore();
            List<InstanceContainer> data = new ArrayList<InstanceContainer>();
            data.add(explore.getContainerManager().newContainer(inst));
            //add the instance to the container manager
            explore.getContainerManager().addAll(data);
            temp.repaint();
          });
	}
	diffPanel = new JMenuItem();
        diffPanel.setIcon(GUIHelper.getEmptyIcon());
	diffPanel.setText("Display on a new panel");
	menu.add(diffPanel);
        diffPanel.addActionListener((ActionEvent arg0) -> {
          m_NumDialogs ++;
          final DisplayExplorer temp = new DisplayExplorer();
          temp.setName("Explorer (" + m_NumDialogs + ")");
          temp.setTitle("Explorer (" + m_NumDialogs + ")");
          InstanceExplorer explore = new InstanceExplorer();
          //custom closing event
          temp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
              dialogs.remove(temp);
              temp.dispose();
            }
          });
          temp.setDefaultCloseOperation(BaseDialog.DO_NOTHING_ON_CLOSE);
          explore = new adams.gui.visualization.instance.InstanceExplorer();
          List<InstanceContainer> data = new ArrayList<InstanceContainer>();
          data.add(explore.getContainerManager().newContainer(inst));
          //add the instance to the container manager
          explore.getContainerManager().addAll(data);
          temp.add(explore);
          temp.setPreferredSize(new Dimension(900,500));
          temp.pack();
          temp.setVisible(true);
          dialogs.add(temp);
          temp.setExplore(explore);
	});
      }
    }
  }

  /**
   * Sets whether to hide/show the options panel.
   *
   * @param value	if true then the options are visible
   */
  public void setOptionsVisible(boolean value) {
    m_SplitPane.setRightComponentHidden(!value);
  }

  /**
   * Returns whether to hide/show the options panel.
   *
   * @return	true if the options are visible
   */
  public boolean getOptionsVisible() {
    return !m_SplitPane.isRightComponentHidden();
  }
}