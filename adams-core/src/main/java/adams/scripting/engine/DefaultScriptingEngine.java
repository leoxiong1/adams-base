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
 * DefaultScriptingEngine.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.scripting.engine;

import adams.env.Environment;
import adams.scripting.command.CommandUtils;
import adams.scripting.command.RemoteCommand;
import adams.scripting.command.RemoteCommandWithResponse;
import gnu.trove.list.array.TByteArrayList;

import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Default implementation of scripting engine for remote commands.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultScriptingEngine
  extends AbstractScriptingEngine {

  private static final long serialVersionUID = -3763240773922918567L;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Default implementation of scripting engine for remote commands.";
  }

  /**
   * Handles the client connection.
   *
   * @param client	the connection to handle
   */
  @Override
  protected void handleClient(Socket client) {
    InputStream		in;
    int			b;
    TByteArrayList 	bytes;
    String		data;
    RemoteCommand	cmd;

    // read data
    bytes = new TByteArrayList();
    try {
      in = client.getInputStream();
      while ((b = in.read()) != -1)
      	bytes.add((byte) b);
      client.close();
    }
    catch (Exception e) {
      getLogger().log(Level.SEVERE, "Failed to process client connection!", e);
      return;
    }
    if (bytes.isEmpty()) {
      getLogger().warning("No data received, ignoring connection!");
      return;
    }

    // instantiate command
    data = new String(bytes.toArray());
    cmd  = CommandUtils.parse(this, data);

    if (!m_PermissionHandler.permitted(cmd)) {
      m_RequestHandler.requestRejected(cmd, "Not permitted!");
      return;
    }

    // handle command
    if (cmd != null) {
      cmd.setApplicationContext(getApplicationContext());
      if (cmd.isRequest()) {
	cmd.handleRequest(this, m_RequestHandler);
      }
      else {
	if (cmd instanceof RemoteCommandWithResponse)
	  ((RemoteCommandWithResponse) cmd).handleResponse(this, m_ResponseHandler);
	else
	  getResponseHandler().responseFailed(cmd, "Command does not support response handling!");
      }
    }
  }

  /**
   * Starts the scripting engine from commandline.
   *
   * @param args  	additional options for the scripting engine
   */
  public static void main(String[] args) {
    runScriptingEngine(Environment.class, DefaultScriptingEngine.class, args);
  }
}
