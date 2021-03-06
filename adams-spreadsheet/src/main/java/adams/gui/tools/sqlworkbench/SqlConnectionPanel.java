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
 * SqlConnectionPanel.java
 * Copyright (C) 2018-2019 University of Waikato, Hamilton, NZ
 */

package adams.gui.tools.sqlworkbench;

import adams.core.Constants;
import adams.core.Utils;
import adams.core.base.BasePassword;
import adams.core.logging.LoggingLevel;
import adams.db.AbstractDatabaseConnection;
import adams.db.DatabaseConnection;
import adams.db.DatabaseConnectionProvider;
import adams.gui.core.BaseButton;
import adams.gui.core.BaseCheckBox;
import adams.gui.core.BaseComboBox;
import adams.gui.core.BasePanel;
import adams.gui.core.BaseTextField;
import adams.gui.core.GUIHelper;
import adams.gui.core.ParameterPanel;
import adams.gui.dialog.ApprovalDialog;
import adams.gui.dialog.DatabaseConnectionDialog;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;

/**
 * Panel for database connection.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class SqlConnectionPanel
  extends BasePanel
  implements DatabaseConnectionProvider {

  private static final long serialVersionUID = 8008764357731780782L;

  /** the label for the showing the active connection. */
  protected JLabel m_LabelConnection;

  /** the button for opening the connection dialog. */
  protected BaseButton m_ButtonConnection;

  /** the current connection. */
  protected AbstractDatabaseConnection m_DatabaseConnection;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_DatabaseConnection = adams.db.DatabaseConnection.getSingleton();
  }

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    setLayout(new BorderLayout());

    m_LabelConnection = new JLabel();
    add(m_LabelConnection, BorderLayout.CENTER);

    m_ButtonConnection = new BaseButton("...");
    m_ButtonConnection.setToolTipText("Opens dialog to select database connection");
    m_ButtonConnection.addActionListener((ActionEvent e) -> selectConnection());
    add(m_ButtonConnection, BorderLayout.EAST);
  }

  /**
   * Finishes the initialization.
   */
  @Override
  protected void finishInit() {
    super.finishInit();
    updateConnection();
  }

  /**
   * Allows the user to select a connection.
   */
  protected void selectConnection() {
    ApprovalDialog  		dialog;
    ParameterPanel 		panelParameters;
    BaseTextField 		textURL;
    BaseTextField 		textUser;
    JPasswordField 		textPassword;
    BaseCheckBox 		checkBoxShowPassword;
    BaseComboBox<LoggingLevel> 	comboBoxLoggingLevel;
    String			error;

    panelParameters = new ParameterPanel();

    textURL = new BaseTextField(20);
    textURL.setText(getDatabaseConnection().getURL());
    panelParameters.addParameter("_URL", textURL);

    textUser = new BaseTextField(20);
    textUser.setText(getDatabaseConnection().getUser());
    panelParameters.addParameter("U_ser", textUser);

    textPassword = new JPasswordField(20);
    textPassword.setText(getDatabaseConnection().getPassword().getValue());
    textPassword.setEchoChar(Constants.PASSWORD_CHAR);
    panelParameters.addParameter("_Password", textPassword);

    checkBoxShowPassword = new BaseCheckBox();
    checkBoxShowPassword.setSelected(false);
    checkBoxShowPassword.addActionListener((ActionEvent e) ->
      textPassword.setEchoChar(checkBoxShowPassword.isSelected() ? (char) 0 : Constants.PASSWORD_CHAR));
    panelParameters.addParameter("Sho_w password", checkBoxShowPassword);

    comboBoxLoggingLevel = new BaseComboBox<>(LoggingLevel.values());
    comboBoxLoggingLevel.setSelectedItem(getDatabaseConnection().getLoggingLevel());
    panelParameters.addParameter("_Logging level", comboBoxLoggingLevel);

    if (getParentDialog() != null)
      dialog = new ApprovalDialog(getParentDialog(), ModalityType.DOCUMENT_MODAL);
    else
      dialog = new ApprovalDialog(getParentFrame(), true);
    dialog.setTitle("Connect to database");
    dialog.setDefaultCloseOperation(DatabaseConnectionDialog.DISPOSE_ON_CLOSE);
    dialog.getContentPane().add(panelParameters, BorderLayout.CENTER);
    dialog.setApproveCaption("Connect");
    dialog.setApproveVisible(true);
    dialog.setCancelVisible(true);
    dialog.setDiscardVisible(false);
    dialog.pack();
    dialog.setLocationRelativeTo(getParent());
    dialog.setVisible(true);
    if (dialog.getOption() != ApprovalDialog.APPROVE_OPTION)
      return;

    m_DatabaseConnection = DatabaseConnection.getSingleton(textURL.getText(), textUser.getText(), new BasePassword(textPassword.getText()));
    m_DatabaseConnection.setLoggingLevel(comboBoxLoggingLevel.getSelectedItem());
    error = null;
    if (!m_DatabaseConnection.isConnected()) {
      try {
	m_DatabaseConnection.connect();
	if (!m_DatabaseConnection.isConnected())
	  error = "Failed to connect to: " + textURL.getText();
      }
      catch (Exception e) {
        error = Utils.handleException(m_DatabaseConnection, "Failed to connect to: " + textURL.getText(), e);
      }
    }
    if (error != null)
      GUIHelper.showErrorMessage(this, error);

    updateConnection();
  }

  /**
   * Updates the displayed connection.
   */
  protected void updateConnection() {
    m_LabelConnection.setText(
      (m_DatabaseConnection.getUser().isEmpty() ? "" : m_DatabaseConnection.getUser() + "@")
      + m_DatabaseConnection.getURL());
  }

  /**
   * Returns the currently used database connection object, can be null.
   *
   * @return		the current object
   */
  public AbstractDatabaseConnection getDatabaseConnection() {
    return m_DatabaseConnection;
  }

  /**
   * Sets the enabled state.
   *
   * @param value	true if enabled
   */
  public void setEnabled(boolean value) {
    super.setEnabled(value);
    m_ButtonConnection.setEnabled(value);
  }
}
