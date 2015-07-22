/**
 * 
 */
package org.auckland.ac.nz.tools;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * @author ebag753
 *
 */
@SuppressWarnings("serial")
public class VIDIVOXCellRenderer extends JTextArea implements TableCellRenderer {
    public VIDIVOXCellRenderer() {
      setLineWrap(true);
      setWrapStyleWord(true);
   }

  public Component getTableCellRendererComponent(JTable table, Object
          value, boolean isSelected, boolean hasFocus, int row, int column) {
      setText((String) value);
      setSize(table.getColumnModel().getColumn(column).getWidth(),
              getPreferredSize().height);
      if (table.getRowHeight(row) != getPreferredSize().height) {
              table.setRowHeight(row, getPreferredSize().height);
      }
      
      if(column == 0) {
          Font font = getFont();  
          setFont(font.deriveFont(Font.BOLD));
      }
      return this;
  }
} 

