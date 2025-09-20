package UI.Table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class TableMembresiaCellRender extends DefaultTableCellRenderer {
    private Font boldFont = getFont().deriveFont(Font.BOLD);
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, row, column);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(boldFont);
        setBorder(noFocusBorder);
        
        if (o instanceof String obj) {
            switch (obj) {
                case "Ninguno" -> setForeground(Color.BLACK);
                case "Plata" -> setForeground(Color.GRAY);
                case "Oro" -> setForeground(Color.ORANGE);
                case "Diamante" -> setForeground(Color.CYAN);
                default -> {
                }
            }
        }
        
        if (selected) {
            com.setBackground(new Color(230, 230, 230));
        } 
        else {
            com.setBackground(Color.WHITE);
        }
        
        return com;
    }
}
