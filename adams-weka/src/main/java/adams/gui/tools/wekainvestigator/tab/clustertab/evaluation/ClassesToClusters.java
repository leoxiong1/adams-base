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
 * ClassesToClusters.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.gui.tools.wekainvestigator.tab.clustertab.evaluation;

import adams.core.option.OptionUtils;
import adams.gui.core.AbstractNamedHistoryPanel;
import adams.gui.core.ParameterPanel;
import adams.gui.tools.wekainvestigator.data.DataContainer;
import adams.gui.tools.wekainvestigator.tab.clustertab.ResultItem;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Tries to map the clusters of the built clusterer to the class labels in
 * the dataset.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ClassesToClusters
  extends AbstractClustererEvaluation {

  private static final long serialVersionUID = -4460266467650893551L;

  /** the panel with the parameters. */
  protected ParameterPanel m_PanelParameters;

  /** the train set. */
  protected JComboBox<String> m_ComboBoxTrain;

  /** the test set. */
  protected JComboBox<String> m_ComboBoxTest;

  /** the datasets model. */
  protected DefaultComboBoxModel<String> m_ModelDatasets;

  /** the class attribute. */
  protected JComboBox<String> m_ComboBoxClass;

  /** the class attribute model. */
  protected DefaultComboBoxModel<String> m_ModelClass;

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    m_PanelParameters = new ParameterPanel();
    m_PanelOptions.add(m_PanelParameters, BorderLayout.CENTER);

    m_ModelDatasets = new DefaultComboBoxModel<>();

    // train
    m_ComboBoxTrain = new JComboBox<>(m_ModelDatasets);
    m_ComboBoxTrain.addActionListener((ActionEvent e) -> update());
    m_PanelParameters.addParameter("Train", m_ComboBoxTrain);

    // test
    m_ComboBoxTest = new JComboBox<>(m_ModelDatasets);
    m_ComboBoxTest.addActionListener((ActionEvent e) -> update());
    m_PanelParameters.addParameter("Test", m_ComboBoxTest);

    // class
    m_ComboBoxClass = new JComboBox<>();
    m_ComboBoxClass.addActionListener((ActionEvent e) -> getOwner().updateButtons());
    m_PanelParameters.addParameter("Class", m_ComboBoxClass);
  }

  /**
   * Returns the name of the evaluation (displayed in combobox).
   *
   * @return		the name
   */
  @Override
  public String getName() {
    return "Classes to clusters";
  }

  /**
   * Tests whether the clusterer can be evaluated.
   *
   * @return		null if successful, otherwise error message
   */
  public String canEvaluate(Clusterer clusterer) {
    Instances 		train;
    Instances 		test;
    Capabilities	caps;

    if (m_ComboBoxTrain.getSelectedIndex() == -1)
      return "No training data available!";
    if (m_ComboBoxTest.getSelectedIndex() == -1)
      return "No test data available!";

    if (m_ComboBoxClass.getSelectedIndex() < 1)
      return "No class attribute set!";

    train = getOwner().getData().get(m_ComboBoxTrain.getSelectedIndex()).getData();
    if (train.classIndex() == -1) {
      caps = clusterer.getCapabilities();
      if (!caps.test(train)) {
	if (caps.getFailReason() != null)
	  return caps.getFailReason().getMessage();
	else
	  return "Clusterer cannot handle training data!";
      }
    }

    test = getOwner().getData().get(m_ComboBoxTest.getSelectedIndex()).getData();
    if (test.classIndex() == -1) {
      caps = clusterer.getCapabilities();
      if (!caps.test(test)) {
	if (caps.getFailReason() != null)
	  return caps.getFailReason().getMessage();
	else
	  return "Clusterer cannot handle test data!";
      }
    }

    if (!train.equalHeaders(test))
      return train.equalHeadersMsg(test);

    return null;
  }

  /**
   * Returns the number of classes in the test set.
   *
   * @return		the number of classes, -1 in case of error
   */
  protected int numClasses() {
    int		result;
    Instances	data;
    String	classAtt;
    Attribute 	att;

    result   = -1;
    classAtt = "" + m_ComboBoxClass.getSelectedItem();
    data     = getOwner().getData().get(m_ComboBoxTest.getSelectedIndex()).getData();
    att = data.attribute(classAtt);
    if (att != null)
      result = att.numValues();

    return result;
  }

  /**
   * Returns the index of the selected class attribute in the provided dataset.
   *
   * @param data	the dataset to get the class index for
   * @return		the class index, -1 if failed to locate attribute
   */
  protected int classIndex(Instances data) {
    String	classAtt;
    Attribute 	att;

    classAtt = "" + m_ComboBoxClass.getSelectedItem();

    data = new Instances(data);
    if (data.classIndex() > -1)
      data.setClassIndex(-1);
    att = data.attribute(classAtt);
    if (att != null)
      return att.index();
    else
      return -1;
  }

  /**
   * Removes the class attribute from the dataset (if present).
   *
   * @param data	the dataset to process
   * @return		the clean dataset
   */
  protected Instances removeClassAttribute(Instances data) {
    int		classIndex;

    classIndex = classIndex(data);
    data       = new Instances(data);
    if (data.classIndex() > -1)
      data.setClassIndex(-1);
    if (classIndex > -1)
      data.deleteAttributeAt(classIndex);

    return data;
  }

  /**
   * Returns a "confusion" style matrix of classes to clusters assignments
   *
   * @param counts the counts of classes for each cluster
   * @param clusterTotals total number of examples in each cluster
   * @param inst the training instances (with class)
   * @return the "confusion" style matrix as string
   * @throws Exception if matrix can't be generated
   */
  protected String toMatrixString(int numClusters, int[][] counts, int[] clusterTotals, Instances inst) throws Exception {
    StringBuilder result = new StringBuilder("Classes to clusters\n==================\n\n");

    int maxval = 0;
    for (int i = 0; i < numClusters; i++) {
      for (int j = 0; j < counts[i].length; j++) {
        if (counts[i][j] > maxval) {
          maxval = counts[i][j];
        }
      }
    }

    int Cwidth =
      1 + Math.max((int) (Math.log(maxval) / Math.log(10)),
        (int) (Math.log(numClusters) / Math.log(10)));

    result.append("\n");

    for (int i = 0; i < numClusters; i++) {
      if (clusterTotals[i] > 0) {
        result.append(" ").append(Utils.doubleToString(i, Cwidth, 0));
      }
    }
    result.append("  <-- assigned to cluster\n");

    for (int i = 0; i < counts[0].length; i++) {

      for (int j = 0; j < numClusters; j++) {
        if (clusterTotals[j] > 0) {
          result.append(" ").append(Utils.doubleToString(counts[j][i], Cwidth, 0));
        }
      }
      result.append(" | ").append(inst.classAttribute().value(i)).append("\n");
    }

    return result.toString();
  }

  /**
   * Evaluates the clusterer and returns the generated evaluation object.
   *
   * @param history	the history to add the result to
   * @return		the generate history item
   * @throws Exception	if evaluation fails
   */
  @Override
  public ResultItem evaluate(Clusterer clusterer, AbstractNamedHistoryPanel<ResultItem> history) throws Exception {
    ClusterEvaluation 		eval;
    Instances			data;
    Instances			train;
    Instances			test;
    String			msg;
    int				i;
    Instance			instance;
    double[]			m_clusterAssignments;
    int 			numClusters;
    int[][] 			counts;
    int[] 			clusterTotals;
    double[] 			best;
    double[] 			current;
    String 			matrix;

    if ((msg = canEvaluate(clusterer)) != null)
      throw new IllegalArgumentException("Cannot evaluate clusterer!\n" + msg);

    // train
    data  = getOwner().getData().get(m_ComboBoxTrain.getSelectedIndex()).getData();
    train = removeClassAttribute(data);
    clusterer = (Clusterer) OptionUtils.shallowCopy(clusterer);
    getOwner().logMessage("Building clusterer on '" + data.relationName() + "' without class attribute using " + OptionUtils.getCommandLine(clusterer));
    clusterer.buildClusterer(train);

    // test
    data = getOwner().getData().get(m_ComboBoxTest.getSelectedIndex()).getData();
    test = removeClassAttribute(data);
    getOwner().logMessage("Testing clusterer on '" + data.relationName() + "' without class attribute using " + OptionUtils.getCommandLine(clusterer));
    eval = new ClusterEvaluation();
    eval.setClusterer(clusterer);
    eval.evaluateClusterer(test);

    // classes to clusters
    getOwner().logMessage("Determining classes to clusters mapping on '" + data.relationName() + "' for " + OptionUtils.getCommandLine(clusterer));
    numClusters   = clusterer.numberOfClusters();
    counts        = new int[numClusters][numClasses()];
    clusterTotals = new int[numClusters];
    best          = new double[numClusters + 1];
    current       = new double[numClusters + 1];

    m_clusterAssignments = eval.getClusterAssignments();
    for (i = 0; i < data.numInstances(); i++) {
      instance = data.instance(i);
      if (m_clusterAssignments[i] >= 0) {
        counts[(int) m_clusterAssignments[i]][(int) instance.value(classIndex(data))]++;
        clusterTotals[(int) m_clusterAssignments[i]]++;
      }
    }

    best[numClusters] = Double.MAX_VALUE;
    ClusterEvaluation.mapClasses(numClusters, 0, counts, clusterTotals, current, best, 0);

    matrix = toMatrixString(numClusters, counts, clusterTotals, new Instances(data, 0));

    // history
    return addToHistory(history, new ResultItem(eval, "Classes to clusters", matrix, clusterer, new Instances(train, 0)));
  }

  /**
   * Updates the settings panel.
   */
  @Override
  public void update() {
    List<String>	datasets;
    int 		indexTrain;
    int 		indexTest;
    DataContainer	cont;
    Instances		data;
    int			i;

    if (getOwner() == null)
      return;
    if (getOwner().getOwner() == null)
      return;

    datasets   = generateDatasetList();
    indexTrain = indexOfDataset((String) m_ComboBoxTrain.getSelectedItem());
    indexTest  = indexOfDataset((String) m_ComboBoxTest.getSelectedItem());
    if (hasDataChanged(datasets, m_ModelDatasets)) {
      m_ModelDatasets = new DefaultComboBoxModel<>(datasets.toArray(new String[datasets.size()]));
      // train
      m_ComboBoxTrain.setModel(m_ModelDatasets);
      if ((indexTrain == -1) && (m_ModelDatasets.getSize() > 0))
	m_ComboBoxTrain.setSelectedIndex(0);
      else if (indexTrain > -1)
	m_ComboBoxTrain.setSelectedIndex(indexTrain);
      // test
      m_ComboBoxTest.setModel(m_ModelDatasets);
      if ((indexTest == -1) && (m_ModelDatasets.getSize() > 0))
	m_ComboBoxTest.setSelectedIndex(0);
      else if (indexTest > -1)
	m_ComboBoxTest.setSelectedIndex(indexTest);
    }

    m_ModelClass = new DefaultComboBoxModel<>();
    m_ModelClass.addElement("");
    if (m_ComboBoxTest.getSelectedIndex() != -1) {
      cont = getOwner().getData().get(m_ComboBoxTest.getSelectedIndex());
      data = cont.getData();
      for (i = 0; i < data.numAttributes(); i++)
	m_ModelClass.addElement(data.attribute(i).name());
    }
    m_ComboBoxClass.setModel(m_ModelClass);

    getOwner().updateButtons();
  }
}