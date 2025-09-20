package UI.Table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableComboBoxCellRender extends DefaultTableCellRenderer {
    private ArrayList<Integer> selected_index;
    PanelComboBox combo_box;
    
    public TableComboBoxCellRender(String[] items) {
        super();
        selected_index = new ArrayList<>();
        combo_box = new PanelComboBox();
        
        for (String item : items) combo_box.getComboBox().addItem(item);
    }
    
    public ArrayList<Integer> getSelectedIndex() {
        return selected_index;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, row, column);
        setBorder(noFocusBorder);
        
        combo_box.getComboBox().setSelectedIndex(selected_index.get(row));
        
        if (selected) {
            com.setBackground(new Color(230, 230, 230, 255));
            combo_box.setBackground(new Color(230, 230, 230, 255));
            combo_box.getComboBox().setBackground(new Color(230, 230, 230, 255));
            combo_box.getComboBox().getEditor().getEditorComponent().setBackground(new Color(230, 230, 230, 255));
        } 
        else {
            com.setBackground(Color.WHITE);
            combo_box.setBackground(Color.WHITE);
            combo_box.getComboBox().setBackground(Color.WHITE);
            combo_box.getComboBox().getEditor().getEditorComponent().setBackground(Color.WHITE);
        }
        
        return combo_box;
    }
}
