package UI.Table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableActionCellRender extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, row, column);
        setBorder(noFocusBorder);
        
        PanelAction action = new PanelAction();
        
        
        if (selected) {
            com.setBackground(new Color(230, 230, 230, 255));
            action.setBackground(new Color(230, 230, 230, 255));
            action.buttonBackground(new Color(230, 230, 230, 255));
        } 
        else {
            com.setBackground(Color.WHITE);
            action.setBackground(Color.WHITE);
            action.buttonBackground(Color.WHITE);
        }
        
        return action;
    }
}
