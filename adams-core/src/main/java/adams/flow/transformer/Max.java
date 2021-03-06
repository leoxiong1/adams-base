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
 * Max.java
 * Copyright (C) 2009-2017 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.transformer;

import adams.core.QuickInfoHelper;
import adams.core.Utils;
import adams.data.statistics.StatUtils;
import adams.flow.core.Token;

/**
 <!-- globalinfo-start -->
 * Returns the maximum value from a double&#47;int array or the index of the maximum value.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- flow-summary-start -->
 * Input&#47;output:<br>
 * - accepts:<br>
 * &nbsp;&nbsp;&nbsp;java.lang.Integer[]<br>
 * &nbsp;&nbsp;&nbsp;java.lang.Double[]<br>
 * &nbsp;&nbsp;&nbsp;int[]<br>
 * &nbsp;&nbsp;&nbsp;double[]<br>
 * - generates:<br>
 * &nbsp;&nbsp;&nbsp;java.lang.Integer<br>
 * &nbsp;&nbsp;&nbsp;java.lang.Double<br>
 * <br><br>
 <!-- flow-summary-end -->
 *
 <!-- options-start -->
 * <pre>-logging-level &lt;OFF|SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST&gt; (property: loggingLevel)
 * &nbsp;&nbsp;&nbsp;The logging level for outputting errors and debugging output.
 * &nbsp;&nbsp;&nbsp;default: WARNING
 * </pre>
 * 
 * <pre>-name &lt;java.lang.String&gt; (property: name)
 * &nbsp;&nbsp;&nbsp;The name of the actor.
 * &nbsp;&nbsp;&nbsp;default: Max
 * </pre>
 * 
 * <pre>-annotation &lt;adams.core.base.BaseAnnotation&gt; (property: annotations)
 * &nbsp;&nbsp;&nbsp;The annotations to attach to this actor.
 * &nbsp;&nbsp;&nbsp;default: 
 * </pre>
 * 
 * <pre>-skip &lt;boolean&gt; (property: skip)
 * &nbsp;&nbsp;&nbsp;If set to true, transformation is skipped and the input token is just forwarded 
 * &nbsp;&nbsp;&nbsp;as it is.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-stop-flow-on-error &lt;boolean&gt; (property: stopFlowOnError)
 * &nbsp;&nbsp;&nbsp;If set to true, the flow execution at this level gets stopped in case this 
 * &nbsp;&nbsp;&nbsp;actor encounters an error; the error gets propagated; useful for critical 
 * &nbsp;&nbsp;&nbsp;actors.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-silent &lt;boolean&gt; (property: silent)
 * &nbsp;&nbsp;&nbsp;If enabled, then no errors are output in the console; Note: the enclosing 
 * &nbsp;&nbsp;&nbsp;actor handler must have this enabled as well.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-index &lt;boolean&gt; (property: returnIndex)
 * &nbsp;&nbsp;&nbsp;If set to true, then the index of the maximum is returned instead of the 
 * &nbsp;&nbsp;&nbsp;value.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-use-one-based-index &lt;boolean&gt; (property: useOneBasedIndex)
 * &nbsp;&nbsp;&nbsp;If enabled, 1-based indices are returned instead of 0-based ones.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Max
  extends AbstractTransformer {

  /** for serialization. */
  private static final long serialVersionUID = 2007764064808349672L;

  /** whether to return the index instead of the value. */
  protected boolean m_ReturnIndex;

  /** whether to return 1-based indices. */
  protected boolean m_UseOneBasedIndex;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Returns the maximum value from a double/int array or the index of the maximum value.";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "index", "returnIndex",
	    false);

    m_OptionManager.add(
	    "use-one-based-index", "useOneBasedIndex",
	    false);
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    String	result;

    result  = QuickInfoHelper.toString(this, "returnIndex", (m_ReturnIndex ? "Index" : "Value"));
    result += QuickInfoHelper.toString(this, "useOneBasedIndex", m_UseOneBasedIndex, "one-based index", ", ");

    return result;
  }

  /**
   * Sets whether to return the value or the index.
   *
   * @param value	true if to return the index, false to return value
   */
  public void setReturnIndex(boolean value) {
    m_ReturnIndex = value;
    reset();
  }

  /**
   * Returns whether to return the value or the index.
   *
   * @return		true the index is returned, false if the value is returned
   */
  public boolean getReturnIndex() {
    return m_ReturnIndex;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String returnIndexTipText() {
    return "If set to true, then the index of the maximum is returned instead of the value.";
  }

  /**
   * Sets whether to return 1-based indices or 0-based ones.
   *
   * @param value	true if to return 1-based indices
   */
  public void setUseOneBasedIndex(boolean value) {
    m_UseOneBasedIndex = value;
    reset();
  }

  /**
   * Returns whether to return 1-based indices or 0-based ones.
   *
   * @return		true if to return 1-based indices
   */
  public boolean getUseOneBasedIndex() {
    return m_UseOneBasedIndex;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String useOneBasedIndexTipText() {
    return "If enabled, 1-based indices are returned instead of 0-based ones.";
  }

  /**
   * Returns the class that the consumer accepts.
   *
   * @return		<!-- flow-accepts-start -->java.lang.Integer[].class, java.lang.Double[].class, int[].class, double[].class<!-- flow-accepts-end -->
   */
  public Class[] accepts() {
    return new Class[]{Integer[].class, Double[].class, int[].class, double[].class};
  }

  /**
   * Returns the class of objects that it generates.
   *
   * @return		<!-- flow-generates-start -->java.lang.Integer.class, java.lang.Double.class<!-- flow-generates-end -->
   */
  public Class[] generates() {
    return new Class[]{Integer.class, Double.class};
  }

  /**
   * Executes the flow item.
   *
   * @return		null if everything is fine, otherwise error message
   */
  @Override
  protected String doExecute() {
    String	result;
    Double[]	doublesO;
    double[]	doublesP;
    Integer[]	integersO;
    int[]	integersP;
    int		inc;

    result = null;

    inc = (m_UseOneBasedIndex ? 1 : 0);
    try {
      if (m_InputToken.getPayload() instanceof Double[]) {
	doublesO = (Double[]) m_InputToken.getPayload();
	if (m_ReturnIndex)
	  m_OutputToken = new Token(StatUtils.maxIndex(doublesO) + inc);
	else
	  m_OutputToken = new Token((Double) (StatUtils.max(doublesO)));
      }
      else if (m_InputToken.getPayload() instanceof double[]) {
	doublesP = (double[]) m_InputToken.getPayload();
	if (m_ReturnIndex)
	  m_OutputToken = new Token(StatUtils.maxIndex(doublesP) + inc);
	else
	  m_OutputToken = new Token((Double) (StatUtils.max(doublesP)));
      }
      else if (m_InputToken.getPayload() instanceof Integer[]) {
	integersO = (Integer[]) m_InputToken.getPayload();
	if (m_ReturnIndex)
	  m_OutputToken = new Token(StatUtils.maxIndex(integersO) + inc);
	else
	  m_OutputToken = new Token((Integer) (StatUtils.max(integersO)));
      }
      else if (m_InputToken.getPayload() instanceof int[]) {
	integersP = (int[]) m_InputToken.getPayload();
	if (m_ReturnIndex)
	  m_OutputToken = new Token(StatUtils.maxIndex(integersP) + inc);
	else
	  m_OutputToken = new Token((Integer) (StatUtils.max(integersP)));
      }
      else {
	result = "Unhandled class: " + Utils.classToString(m_InputToken.getPayload());
      }
    }
    catch (Exception e) {
      m_OutputToken = null;
      result = handleException("Failed to determing maximum: ", e);
    }

    return result;
  }
}
