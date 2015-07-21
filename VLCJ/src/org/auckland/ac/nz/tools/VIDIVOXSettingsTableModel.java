/**
 * 
 */
package org.auckland.ac.nz.tools;

import javax.swing.table.DefaultTableModel;

/**
 * @author ebag753
 *
 */
@SuppressWarnings("serial")
public class VIDIVOXSettingsTableModel extends DefaultTableModel {
    
    private VIDIVOXLogger logger = VIDIVOXLogger.getInstance();
    
    public VIDIVOXSettingsTableModel(String[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }
    
    public void setValueAt(Object aValue, int row, int column) {
        logger.logInfo("VIDIVOXSettingsTableModel::setValueAt(" + aValue.toString() + "," + row + "," + column + ")");
        super.setValueAt(aValue, row, column);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }
}
