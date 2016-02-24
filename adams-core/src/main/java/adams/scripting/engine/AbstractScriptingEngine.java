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
 * AbstractScriptingEngine.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.scripting.engine;

import adams.core.StoppableWithFeedback;
import adams.core.Utils;
import adams.core.logging.LoggingHelper;
import adams.core.logging.LoggingSupporter;
import adams.core.management.ProcessUtils;
import adams.core.option.AbstractOptionConsumer;
import adams.core.option.AbstractOptionHandler;
import adams.core.option.ArrayConsumer;
import adams.core.option.OptionUtils;
import adams.env.Environment;
import adams.gui.application.AbstractApplicationFrame;
import adams.gui.scripting.ScriptingEngine;
import adams.multiprocess.CallableWithResult;
import adams.scripting.permissionhandler.AllowAll;
import adams.scripting.permissionhandler.PermissionHandler;
import adams.scripting.requesthandler.RequestHandler;
import adams.scripting.responsehandler.ResponseHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ancestor of scripting engine for remote commands.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractScriptingEngine
  extends AbstractOptionHandler
  implements RemoteScriptingEngine, StoppableWithFeedback {

  private static final long serialVersionUID = -3763240773922918567L;

  /** the application context. */
  protected AbstractApplicationFrame m_ApplicationContext;

  /** the port to listen on. */
  protected int m_Port;

  /** the permission handler. */
  protected PermissionHandler m_PermissionHandler;

  /** the request handler. */
  protected RequestHandler m_RequestHandler;

  /** the response handler to use. */
  protected ResponseHandler m_ResponseHandler;

  /** the timeout for the socket. */
  protected int m_Timeout;

  /** the number of concurrent jobs to allow. */
  protected int m_MaxConcurrentJobs;

  /** whether the engine is paused. */
  protected boolean m_Paused;

  /** whether the engine is stopped. */
  protected boolean m_Stopped;

  /** for accepting connections. */
  protected transient ServerSocket m_Server;

  /** the executor service to use for parallel execution. */
  protected ExecutorService m_Executor;

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
      "port", "port",
      12345, 1, 65535);

    m_OptionManager.add(
      "permission-handler", "permissionHandler",
      getDefaultPermissionHandler());

    m_OptionManager.add(
      "request-handler", "requestHandler",
      getDefaultRequestHandler());

    m_OptionManager.add(
      "response-handler", "responseHandler",
      getDefaultResponseHandler());

    m_OptionManager.add(
      "timeout", "timeout",
      3000, 100, null);

    m_OptionManager.add(
      "max-concurrent-jobs", "maxConcurrentJobs",
      1, 1, null);
  }

  /**
   * Sets the port to listen on.
   *
   * @param value	the port to listen on
   */
  @Override
  public void setPort(int value) {
    if (getOptionManager().isValid("port", value)) {
      m_Port = value;
      reset();
    }
  }

  /**
   * Returns the port to listen on.
   *
   * @return		the port listening on
   */
  @Override
  public int getPort() {
    return m_Port;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  @Override
  public String portTipText() {
    return "The port to listen on for remote connections.";
  }

  /**
   * Returns the default permission handler.
   *
   * @return		the default
   */
  protected PermissionHandler getDefaultPermissionHandler() {
    return new AllowAll();
  }

  /**
   * Sets the permission handler to use.
   *
   * @param value	the permission handler
   */
  public void setPermissionHandler(PermissionHandler value) {
    m_PermissionHandler = value;
    reset();
  }

  /**
   * Returns the permission handler in use.
   *
   * @return		the permission handler
   */
  public PermissionHandler getPermissionHandler() {
    return m_PermissionHandler;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String permissionHandlerTipText() {
    return "The handler that determines what request can be executed.";
  }

  /**
   * Returns the default request handler.
   *
   * @return		the default
   */
  protected RequestHandler getDefaultRequestHandler() {
    return new adams.scripting.requesthandler.LoggingHandler();
  }

  /**
   * Sets the request handler to use.
   *
   * @param value	the request handler
   */
  public void setRequestHandler(RequestHandler value) {
    m_RequestHandler = value;
    reset();
  }

  /**
   * Returns the request handler in use.
   *
   * @return		the request handler
   */
  public RequestHandler getRequestHandler() {
    return m_RequestHandler;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String requestHandlerTipText() {
    return "The request handler for acting on rejected, failed and successful requests.";
  }

  /**
   * Returns the default request handler.
   *
   * @return		the default
   */
  protected ResponseHandler getDefaultResponseHandler() {
    return new adams.scripting.responsehandler.LoggingHandler();
  }

  /**
   * Sets the response listener to use.
   *
   * @param value	the response listener
   */
  public void setResponseHandler(ResponseHandler value) {
    m_ResponseHandler = value;
    reset();
  }

  /**
   * Returns the response listener in use.
   *
   * @return		the response listener
   */
  public ResponseHandler getResponseHandler() {
    return m_ResponseHandler;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String responseHandlerTipText() {
    return "The handler for acting on successful and failed responses.";
  }

  /**
   * Sets the timeout in milli-second to wait for new connections.
   *
   * @param value	the timeout in msec
   */
  public void setTimeout(int value) {
    if (getOptionManager().isValid("timeout", value)) {
      m_Timeout = value;
      reset();
    }
  }

  /**
   * Returns the timeout in milli-second to wait for new connections.
   *
   * @return		the timeout in msec
   */
  public int getTimeout() {
    return m_Timeout;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String timeoutTipText() {
    return "The timeout in milli-second for waiting on new client connections.";
  }

  /**
   * Sets the maximum number of concurrent jobs to execute.
   *
   * @param value	the number of jobs
   */
  public void setMaxConcurrentJobs(int value) {
    if (getOptionManager().isValid("maxConcurrentJobs", value)) {
      m_MaxConcurrentJobs = value;
      reset();
    }
  }

  /**
   * Returns the maximum number of concurrent jobs to execute.
   *
   * @return		the number of jobs
   */
  public int getMaxConcurrentJobs() {
    return m_MaxConcurrentJobs;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String maxConcurrentJobsTipText() {
    return "The maximum number of concurrent jobs to execute.";
  }

  /**
   * Sets the application context.
   *
   * @param value	the context
   */
  public void setApplicationContext(AbstractApplicationFrame value) {
    m_ApplicationContext = value;
  }

  /**
   * Returns the application context.
   *
   * @return		the context, null if none set
   */
  public AbstractApplicationFrame getApplicationContext() {
    return m_ApplicationContext;
  }

  /**
   * Queues the job in the execution pipeline.
   *
   * @param job		the job to queue
   */
  public void queueJob(CallableWithResult<String> job) {
    m_Executor.submit(job);
  }

  /**
   * Handles the client connection.
   *
   * @param client	the connection to handle
   */
  protected abstract void handleClient(Socket client);

  /**
   * Executes the scripting engine.
   *
   * @return		error message in case of failure to start up or run,
   * 			otherwise null
   */
  @Override
  public String execute() {
    String		result;
    Socket		client;

    result    = null;
    m_Paused  = false;
    m_Stopped = false;

    // connect to port
    try {
      m_Server = new ServerSocket(m_Port);
      m_Server.setSoTimeout(m_Timeout);
    }
    catch (Exception e) {
      result   = Utils.handleException(this, "Failed to set up server socket!", e);
      m_Server = null;
    }

    // wait for connections
    if (m_Server != null) {
      // start up job queue
      m_Executor = Executors.newFixedThreadPool(m_MaxConcurrentJobs);

      while (!m_Stopped) {
	while (m_Paused && !m_Stopped) {
	  Utils.wait(this, this, 1000, 50);
	}

	try {
	  client = m_Server.accept();
	  if (client != null) {
	    handleClient(client);
	  }
	}
	catch (SocketTimeoutException t) {
	  // ignored
	}
	catch (Exception e) {
          if ((m_Server != null) && !m_Server.isClosed())
            Utils.handleException(this, "Failed to accept connection!", e);
	}
      }
    }

    closeSocket();

    if (!m_Executor.isTerminated()) {
      getLogger().info("Shutting down job queue...");
      m_Executor.shutdown();
      while (!m_Executor.isTerminated())
	Utils.wait(this, 1000, 100);
      getLogger().info("Job queue shut down");
    }

    return result;
  }

  /**
   * Pauses the execution.
   */
  @Override
  public void pauseExecution() {
    m_Paused = true;
  }

  /**
   * Returns whether the object is currently paused.
   *
   * @return		true if object is paused
   */
  @Override
  public boolean isPaused() {
    return m_Paused;
  }

  /**
   * Resumes the execution.
   */
  @Override
  public void resumeExecution() {
    m_Paused = false;
  }

  /**
   * Closes the server socket if necessary.
   */
  protected void closeSocket() {
    if (m_Server != null) {
      try {
	m_Server.close();
      }
      catch (Exception e) {
	Utils.handleException(this, "Failed to close server socket!", e);
      }
      m_Server = null;
    }
  }

  /**
   * Stops the execution.
   */
  @Override
  public void stopExecution() {
    m_Stopped = true;
    m_Paused  = false;
    m_Executor.shutdownNow();
    closeSocket();
  }

  /**
   * Whether the execution has been stopped.
   *
   * @return		true if stopped
   */
  public boolean isStopped() {
    return m_Stopped;
  }

  /**
   * Runs the engine from the commandline.
   *
   * @param env		the environment class to use
   * @param engine		the engine class
   * @param options	the commandline options
   * @return		the instantiated frame, null in case of an error or
   * 			invocation of help
   */
  public static RemoteScriptingEngine runScriptingEngine(Class env, Class engine, String[] options) {
    RemoteScriptingEngine	result;
    String			msg;

    Environment.setEnvironmentClass(env);
    LoggingHelper.useHandlerFromOptions(options);

    try {
      if (OptionUtils.helpRequested(options)) {
	System.out.println("Help requested...\n");
	result = forName(engine.getName(), new String[0]);
	System.out.print("\n" + OptionUtils.list(result));
	LoggingHelper.outputHandlerOption();
	ScriptingEngine.stopAllEngines();
	result = null;
      }
      else {
	result = forName(engine.getName(), options);
	if (result instanceof LoggingSupporter)
	  ((LoggingSupporter) result).getLogger().info("PID: " + ProcessUtils.getVirtualMachinePID());
	msg = result.execute();
	if (msg != null) {
	  if (result instanceof LoggingSupporter)
	    ((LoggingSupporter) result).getLogger().severe(msg);
	  else
	    System.err.println(msg);
	}
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      result = null;
    }

    return result;
  }

  /**
   * Instantiates the engine with the given options.
   *
   * @param classname	the classname of the engine to instantiate
   * @param options	the options for the engine
   * @return		the instantiated engine or null if an error occurred
   */
  public static RemoteScriptingEngine forName(String classname, String[] options) {
    RemoteScriptingEngine	result;

    try {
      result = (RemoteScriptingEngine) OptionUtils.forName(RemoteScriptingEngine.class, classname, options);
    }
    catch (Exception e) {
      e.printStackTrace();
      result = null;
    }

    return result;
  }

  /**
   * Instantiates the engine from the given commandline
   * (i.e., classname and optional options).
   *
   * @param cmdline	the classname (and optional options) of the
   * 			engine to instantiate
   * @return		the instantiated engine
   * 			or null if an error occurred
   */
  public static RemoteScriptingEngine forCommandLine(String cmdline) {
    return (RemoteScriptingEngine) AbstractOptionConsumer.fromString(ArrayConsumer.class, cmdline);
  }
}
