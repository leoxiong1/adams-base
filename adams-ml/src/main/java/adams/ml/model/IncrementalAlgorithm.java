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
 * IncrementalAlgorithm.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.ml.model;

import adams.data.spreadsheet.Row;
import adams.ml.data.Dataset;

/**
 * Interface for incremental algorithms.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public interface IncrementalAlgorithm<T extends Model>
  extends Algorithm<T> {

  /**
   * Updates the model with the given data.
   *
   * @param row		the data to train with
   * @return		the updated model
   */
  public T updateModel(Row row);

  /**
   * Updates the model with the given data.
   *
   * @param data		the data to train with
   * @return		the updated model
   */
  public T updateModel(Dataset data);
}
