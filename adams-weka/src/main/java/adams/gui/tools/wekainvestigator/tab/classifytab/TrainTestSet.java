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
 * TrainTestSet.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.gui.tools.wekainvestigator.tab.classifytab;

import adams.core.option.OptionUtils;
import adams.gui.core.ParameterPanel;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Uses dedicated train/test sets.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class TrainTestSet
  extends AbstractClassifierEvaluation {

  private static final long serialVersionUID = -4460266467650893551L;

  /** the panel with the parameters. */
  protected ParameterPanel m_PanelParameters;

  /** the train set. */
  protected JComboBox<String> m_ComboBoxTrain;

  /** the test set. */
  protected JComboBox<String> m_ComboBoxTest;

  /** the datasets model. */
  protected DefaultComboBoxModel<String> m_ModelDatasets;

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    m_PanelParameters = new ParameterPanel();
    m_PanelOptions.add(m_PanelParameters, BorderLayout.CENTER);

    m_ModelDatasets = new DefaultComboBoxModel<>();

    // Train
    m_ComboBoxTrain = new JComboBox<>(m_ModelDatasets);
    m_ComboBoxTrain.addActionListener((ActionEvent e) -> update());
    m_PanelParameters.addParameter("Train", m_ComboBoxTrain);

    // Test
    m_ComboBoxTest = new JComboBox<>(m_ModelDatasets);
    m_ComboBoxTest.addActionListener((ActionEvent e) -> update());
    m_PanelParameters.addParameter("Test", m_ComboBoxTest);
  }

  /**
   * Returns the name of the evaluation (displayed in combobox).
   *
   * @return		the name
   */
  @Override
  public String getName() {
    return "Train/test set";
  }

  /**
   * Tests whether the classifier can be evaluated.
   *
   * @return		true if possible
   */
  public boolean canEvaluate(Classifier classifier) {
    Instances train;
    Instances test;

    if (m_ComboBoxTrain.getSelectedIndex() == -1)
      return false;
    if (m_ComboBoxTest.getSelectedIndex() == -1)
      return false;

    train = getOwner().getData().get(m_ComboBoxTrain.getSelectedIndex()).getData();
    if (!classifier.getCapabilities().test(train))
      return false;

    test = getOwner().getData().get(m_ComboBoxTest.getSelectedIndex()).getData();
    if (!classifier.getCapabilities().test(test))
      return false;

    if (!train.equalHeaders(test))
      return false;

    return true;
  }

  /**
   * Evaluates the classifier and returns the generated evaluation object.
   *
   * @return		the evaluation
   * @throws Exception	if evaluation fails
   */
  @Override
  public Evaluation evaluate(Classifier classifier) throws Exception {
    Evaluation			result;
    Instances			train;
    Instances			test;

    if (!canEvaluate(classifier))
      throw new IllegalArgumentException("Cannot evaluate classifier!");

    train = getOwner().getData().get(m_ComboBoxTrain.getSelectedIndex()).getData();
    test = getOwner().getData().get(m_ComboBoxTest.getSelectedIndex()).getData();
    classifier = (Classifier) OptionUtils.shallowCopy(classifier);
    classifier.buildClassifier(train);
    result = new Evaluation(train);
    result.evaluateModel(classifier, test);

    return result;
  }

  /**
   * Updates the settings panel.
   */
  @Override
  public void update() {
    List<String>	datasets;
    int			index;

    if (getOwner() == null)
      return;
    if (getOwner().getOwner() == null)
      return;

    datasets = generateDatasetList();
    
    // train
    index = indexOfDataset((String) m_ComboBoxTrain.getSelectedItem());
    m_ModelDatasets = new DefaultComboBoxModel<>(datasets.toArray(new String[datasets.size()]));
    m_ComboBoxTrain.setModel(m_ModelDatasets);
    if ((index == -1) && (m_ModelDatasets.getSize() > 0))
      m_ComboBoxTrain.setSelectedIndex(0);
    else if (index > -1)
      m_ComboBoxTrain.setSelectedIndex(index);
    
    // test
    index = indexOfDataset((String) m_ComboBoxTest.getSelectedItem());
    m_ModelDatasets = new DefaultComboBoxModel<>(datasets.toArray(new String[datasets.size()]));
    m_ComboBoxTest.setModel(m_ModelDatasets);
    if ((index == -1) && (m_ModelDatasets.getSize() > 0))
      m_ComboBoxTest.setSelectedIndex(0);
    else if (index > -1)
      m_ComboBoxTest.setSelectedIndex(index);

    getOwner().updateButtons();
  }
}
