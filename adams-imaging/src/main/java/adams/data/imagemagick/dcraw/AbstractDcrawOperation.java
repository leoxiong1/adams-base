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
 * AbstractDcrawOperation.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package adams.data.imagemagick.dcraw;

import adams.core.io.PlaceholderFile;
import adams.data.imagemagick.AbstractImageOperation;
import adams.data.imagemagick.ImageMagickHelper;

/**
 * Ancestor for DCRaw operations.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractDcrawOperation
  extends AbstractImageOperation {

  /** for serialization. */
  private static final long serialVersionUID = 4447009209054143230L;

  /**
   * Hook method for performing checks before applying the operation.
   * 
   * @param input	the input file
   * @param output	the output file
   * @return		null if successfull, otherwise error message
   */
  @Override
  protected String check(PlaceholderFile input, PlaceholderFile output) {
    if (!ImageMagickHelper.isDcrawAvailable())
      return ImageMagickHelper.getMissingDcrawErrorMessage();
    else
      return super.check(input, output);
  }
}
