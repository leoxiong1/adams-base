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
 * EmailFileWriter.java
 * Copyright (C) 2013 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.sink;

import java.util.Arrays;

import adams.core.QuickInfoHelper;
import adams.core.io.PlaceholderFile;
import adams.data.io.output.MultiEmailWriter;
import adams.data.io.output.PropertiesEmailFileWriter;

/**
 <!-- globalinfo-start -->
 * Actor that writes Email objects to files.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- flow-summary-start -->
 * Input&#47;output:<br>
 * - accepts:<br>
 * &nbsp;&nbsp;&nbsp;adams.core.net.Email<br>
 * <br><br>
 <!-- flow-summary-end -->
 *
 <!-- options-start -->
 * Valid options are: <br><br>
 * 
 * <pre>-D &lt;int&gt; (property: debugLevel)
 * &nbsp;&nbsp;&nbsp;The greater the number the more additional info the scheme may output to 
 * &nbsp;&nbsp;&nbsp;the console (0 = off).
 * &nbsp;&nbsp;&nbsp;default: 0
 * &nbsp;&nbsp;&nbsp;minimum: 0
 * </pre>
 * 
 * <pre>-name &lt;java.lang.String&gt; (property: name)
 * &nbsp;&nbsp;&nbsp;The name of the actor.
 * &nbsp;&nbsp;&nbsp;default: EmailFileWriter
 * </pre>
 * 
 * <pre>-annotation &lt;adams.core.base.BaseText&gt; (property: annotations)
 * &nbsp;&nbsp;&nbsp;The annotations to attach to this actor.
 * &nbsp;&nbsp;&nbsp;default: 
 * </pre>
 * 
 * <pre>-skip (property: skip)
 * &nbsp;&nbsp;&nbsp;If set to true, transformation is skipped and the input token is just forwarded 
 * &nbsp;&nbsp;&nbsp;as it is.
 * </pre>
 * 
 * <pre>-stop-flow-on-error (property: stopFlowOnError)
 * &nbsp;&nbsp;&nbsp;If set to true, the flow gets stopped in case this actor encounters an error;
 * &nbsp;&nbsp;&nbsp; useful for critical actors.
 * </pre>
 * 
 * <pre>-output &lt;adams.core.io.PlaceholderFile&gt; (property: outputFile)
 * &nbsp;&nbsp;&nbsp;The name of the output file.
 * &nbsp;&nbsp;&nbsp;default: ${TMP}&#47;out.txt
 * </pre>
 * 
 * <pre>-writer &lt;adams.data.io.output.EmailFileWriter&gt; (property: writer)
 * &nbsp;&nbsp;&nbsp;The writer for storing the spreadsheet.
 * &nbsp;&nbsp;&nbsp;default: adams.data.io.output.PropertiesEmailFileWriter
 * </pre>
 * 
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class EmailFileWriter
  extends AbstractFileWriter {

  /** for serialization. */
  private static final long serialVersionUID = 393925191813730213L;

  /** the writer to use. */
  protected adams.data.io.output.EmailFileWriter m_Writer;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Actor that writes Email objects to files.";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "writer", "writer",
	    new PropertiesEmailFileWriter());
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    String	result;
    
    result = super.getQuickInfo();
    result += QuickInfoHelper.toString(this, "writer", m_Writer, ", writer: ");
    
    return result;
  }

  /**
   * Returns the default output file.
   *
   * @return		the file
   */
  @Override
  protected PlaceholderFile getDefaultOutputFile() {
    return new PlaceholderFile("${TMP}/out.txt");
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  @Override
  public String outputFileTipText() {
    return "The name of the output file.";
  }

  /**
   * Sets the writer to use.
   *
   * @param value	the writer to use
   */
  public void setWriter(adams.data.io.output.EmailFileWriter value) {
    m_Writer = value;
    reset();
  }

  /**
   * Returns the writer in use.
   *
   * @return		the writer in use
   */
  public adams.data.io.output.EmailFileWriter getWriter() {
    return m_Writer;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String writerTipText() {
    return "The writer for storing the spreadsheet.";
  }

  /**
   * Returns the class that the consumer accepts.
   *
   * @return		<!-- flow-accepts-start -->adams.core.net.Email.class<!-- flow-accepts-end -->
   */
  public Class[] accepts() {
    if (m_Writer instanceof MultiEmailWriter)
      return new Class[]{adams.core.net.Email[].class, adams.core.net.Email.class};
    else
      return new Class[]{adams.core.net.Email.class};
  }

  /**
   * Executes the flow item.
   *
   * @return		null if everything is fine, otherwise error message
   */
  @Override
  protected String doExecute() {
    String			result;
    adams.core.net.Email	sheet;
    adams.core.net.Email[]	sheets;
    String			msg;

    result = null;

    m_Writer.setOutput(m_OutputFile);
    if (m_InputToken.getPayload() instanceof adams.core.net.Email) {
      sheet = (adams.core.net.Email) m_InputToken.getPayload();
      msg   = m_Writer.write(sheet);
      if (msg != null)
	result = "Problems writing email to '" + m_OutputFile + "': " + msg;
    }
    else {
      sheets = (adams.core.net.Email[]) m_InputToken.getPayload();
      msg    = ((MultiEmailWriter) m_Writer).write(Arrays.asList(sheets));
      if (msg != null)
	result = "Problems writing emails to '" + m_OutputFile + "': " + msg;
    }

    return result;
  }
}
