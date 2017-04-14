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
 * SetVariable.java
 * Copyright (C) 2009-2017 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.standalone;

import adams.core.QuickInfoHelper;
import adams.core.VariableName;
import adams.core.VariableUpdater;
import adams.core.base.BaseText;

import java.util.ArrayList;
import java.util.List;

/**
 <!-- globalinfo-start -->
 * Sets the value of a variable.<br>
 * Optionally, the specified value (or incoming value) can be expanded, in case it is made up of variables itself.<br>
 * It is also possible to override the variable value with the value obtained from an environment variable.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- flow-summary-start -->
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
 * &nbsp;&nbsp;&nbsp;default: SetVariable
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
 * <pre>-var-name &lt;adams.core.VariableName&gt; (property: variableName)
 * &nbsp;&nbsp;&nbsp;The name of the variable to update.
 * &nbsp;&nbsp;&nbsp;default: variable
 * </pre>
 * 
 * <pre>-var-value &lt;adams.core.base.BaseText&gt; (property: variableValue)
 * &nbsp;&nbsp;&nbsp;The value for the variable to use.
 * &nbsp;&nbsp;&nbsp;default: value
 * </pre>
 * 
 * <pre>-expand-value &lt;boolean&gt; (property: expandValue)
 * &nbsp;&nbsp;&nbsp;If enabled, the value gets expanded first in case it is made up of variables 
 * &nbsp;&nbsp;&nbsp;itself.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-override-with-env-var &lt;boolean&gt; (property: overrideWithEnvVar)
 * &nbsp;&nbsp;&nbsp;If enabled, the value gets overriden by the value obtained from the specified 
 * &nbsp;&nbsp;&nbsp;environment variable.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-env-variable &lt;java.lang.String&gt; (property: envVariable)
 * &nbsp;&nbsp;&nbsp;The name of the environment variable to use for overriding the variable 
 * &nbsp;&nbsp;&nbsp;value.
 * &nbsp;&nbsp;&nbsp;default: 
 * </pre>
 * 
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class SetVariable
  extends AbstractStandalone 
  implements VariableUpdater {

  /** for serialization. */
  private static final long serialVersionUID = -3383735680425581504L;

  /** the name of the variable. */
  protected VariableName m_VariableName;

  /** the value of the variable. */
  protected BaseText m_VariableValue;
  
  /** whether to expand the value. */
  protected boolean m_ExpandValue;

  /** whether to override using an environment variable. */
  protected boolean m_OverrideWithEnvVar;

  /** the environment variable to use. */
  protected String m_EnvVariable;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return
      "Sets the value of a variable.\n"
        + "Optionally, the specified value (or incoming value) can be expanded, "
        + "in case it is made up of variables itself.\n"
        + "It is also possible to override the variable value with the value "
        + "obtained from an environment variable.";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "var-name", "variableName",
	    new VariableName());

    m_OptionManager.add(
	    "var-value", "variableValue",
	    new BaseText("value"));

    m_OptionManager.add(
	    "expand-value", "expandValue",
	    false);

    m_OptionManager.add(
	    "override-with-env-var", "overrideWithEnvVar",
	    false);

    m_OptionManager.add(
	    "env-variable", "envVariable",
	    "");
  }

  /**
   * Sets the name of the variable to update.
   *
   * @param value	the name
   */
  public void setVariableName(VariableName value) {
    m_VariableName = value;
    reset();
  }

  /**
   * Returns the name of the variable to update.
   *
   * @return		the name
   */
  public VariableName getVariableName() {
    return m_VariableName;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String variableNameTipText() {
    return "The name of the variable to update.";
  }

  /**
   * Sets the value of the variable to update.
   *
   * @param value	the value
   */
  public void setVariableValue(BaseText value) {
    m_VariableValue = value;
    reset();
  }

  /**
   * Returns the value of the variable to update.
   *
   * @return		the value
   */
  public BaseText getVariableValue() {
    return m_VariableValue;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String variableValueTipText() {
    return "The value for the variable to use.";
  }

  /**
   * Sets whether to expand the value before settting it
   * (eg if it is made up of variables itself).
   *
   * @param value	true if to expand
   */
  public void setExpandValue(boolean value) {
    m_ExpandValue = value;
    reset();
  }

  /**
   * Returns whether the value gets expanded before setting it 
   * (eg if it is made up of variables itself).
   *
   * @return		true if expanded
   */
  public boolean getExpandValue() {
    return m_ExpandValue;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String expandValueTipText() {
    return "If enabled, the value gets expanded first in case it is made up of variables itself.";
  }

  /**
   * Sets whether to override the value with the one obtained from the
   * specified environment variable.
   *
   * @param value	true if to override
   */
  public void setOverrideWithEnvVar(boolean value) {
    m_OverrideWithEnvVar = value;
    reset();
  }

  /**
   * Returns whether to override the value with the one obtained from the
   * specified environment variable.
   *
   * @return		true if to override
   */
  public boolean getOverrideWithEnvVar() {
    return m_OverrideWithEnvVar;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String overrideWithEnvVarTipText() {
    return "If enabled, the value gets overriden by the value obtained from the specified environment variable.";
  }

  /**
   * Sets the name of the environment variable to use.
   *
   * @param value	the name
   */
  public void setEnvVariable(String value) {
    m_EnvVariable = value;
    reset();
  }

  /**
   * Returns the name of the environment variable to use.
   *
   * @return		the name
   */
  public String getEnvVariable() {
    return m_EnvVariable;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String envVariableTipText() {
    return "The name of the environment variable to use for overriding the variable value.";
  }

  /**
   * Returns whether variables are being updated.
   * 
   * @return		true if variables are updated
   */
  public boolean isUpdatingVariables() {
    return !getSkip();
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    String		variable;
    String		result;
    String		value;
    List<String>	options;

    variable = QuickInfoHelper.getVariable(this, "variableName");
    if (variable != null)
      result = variable;
    else
      result = m_VariableName.paddedValue();

    if (m_OverrideWithEnvVar && !m_EnvVariable.isEmpty()) {
      result += QuickInfoHelper.toString(this, "envVariable", m_EnvVariable, ", use env var: ");
    }
    else {
      value = QuickInfoHelper.toString(this, "variableValue", m_VariableValue.getValue(), " = ");
      if (value != null)
	result += value;

      // further options
      options = new ArrayList<>();
      QuickInfoHelper.add(options, QuickInfoHelper.toString(this, "expandValue", m_ExpandValue, "expand"));
      result += QuickInfoHelper.flatten(options);
    }

    return result;
  }

  /**
   * Executes the flow item.
   *
   * @return		null if everything is fine, otherwise error message
   */
  @Override
  protected String doExecute() {
    String	result;
    String	value;

    result = null;

    value = null;
    if (m_OverrideWithEnvVar) {
      if (m_EnvVariable.isEmpty()) {
	getLogger().warning("No environment variable specified!");
      }
      else {
	value = System.getenv(m_EnvVariable);
	if (value == null)
	  getLogger().warning("Environment variable '" + m_EnvVariable + "' not set?");
      }
    }
    if (value == null) {
      value = m_VariableValue.getValue();
      if (m_ExpandValue)
	value = getVariables().expand(value);
    }
    
    getVariables().set(m_VariableName.getValue(), value);
    if (isLoggingEnabled())
      getLogger().info("Setting variable '" + m_VariableName + "': " + value);

    return result;
  }
}
