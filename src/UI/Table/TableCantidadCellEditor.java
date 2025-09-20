package UI.Table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCantidadCellEditor extends DefaultCellEditor {
    private TableCantidadEvent event;
    private PanelCantidad panel;
    private ArrayList<Integer> cantidades;

    public TableCantidadCellEditor(TableCantidadEvent event) {
        super(new JCheckBox());
        panel = new PanelCantidad();
        cantidades = new ArrayList<>();
        this.event = event;
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
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        panel.setBackground(new Color(230, 230, 230, 255));
        getPanelCantidad().initEvent(event, row, 100);
        getPanelCantidad().setBackground(new Color(230, 230, 230, 255));
        getPanelCantidad().getNumber().setText(String.valueOf(getCantidades().get(row)));
        return getPanelCantidad();
    }
    
    public void updateRowCount(int row_count) {
        cantidades.ensureCapacity(row_count);
    }
}
