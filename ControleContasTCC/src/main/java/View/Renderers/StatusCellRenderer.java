/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ariel
 */
public class StatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 2) {
            String status = (String) value;

            switch (status) {
                case "Negativado":
                    cell.setBackground(Color.YELLOW);
                    break;
                case "Pequenas Causas":
                    cell.setBackground(Color.RED);
                    break;
                case "Acordo":
                    cell.setBackground(Color.GREEN);
                    break;
                case "CDL":
                    cell.setBackground(Color.CYAN);
                    break;
                default:
                    cell.setBackground(Color.WHITE);
            }


            cell.setForeground(Color.BLACK);
        } else {
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
        }

        return cell;
    }
}
