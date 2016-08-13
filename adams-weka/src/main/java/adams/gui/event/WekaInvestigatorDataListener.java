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
 * WekaInvestigatorDataListener.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.gui.event;

/**
 * Interface for classes that get notified about changes in the data in
 * an {@link adams.gui.tools.wekainvestigator.InvestigatorPanel}.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public interface WekaInvestigatorDataListener {

  /**
   * Notifies the tab that the data changed.
   *
   * @param e		the event
   */
  public void dataChanged(WekaInvestigatorDataEvent e);
}
