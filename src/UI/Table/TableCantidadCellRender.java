package UI.Table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCantidadCellRender extends DefaultTableCellRenderer {    
    private PanelCantidad panel;
    private ArrayList<Integer> cantidades;
    
    public TableCantidadCellRender() {
        super();
        panel = new PanelCantidad();
        cantidades = new ArrayList<>();
    }
    
    public PanelCantidad getPanelCantidad() {
        return panel;
    }
    
    public ArrayList<Integer> getCantidades() {
        return cantidades;
    }
    
    public void setCantidad(int row, int value) {
        cantidades.set(row, value);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, row, column);
        
        panel.setBackground(Color.WHITE);
        panel.getNumber().setText(String.valueOf(cantidades.get(row)));
        
        if (selected) {
            com.setBackground(new Color(230, 230, 230, 255));
            panel.setBackground(new Color(230, 230, 230, 255));
            panel.getNumber().setForeground(new Color(15, 89, 140));
        } 
        else {
            com.setBackground(Color.WHITE);
            panel.setBackground(Color.WHITE);
            panel.getNumber().setForeground(new Color(102, 102, 102));
        }
        
        return panel;
    }
    
    public void updateRowCount(int row_count) {
        cantidades.ensureCapacity(row_count);
    }
}
