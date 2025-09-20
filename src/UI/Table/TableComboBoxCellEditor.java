package UI.Table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableComboBoxCellEditor extends DefaultCellEditor {
    private TableComboBoxCellRender render;
    private PanelComboBox combo_box;
    
    public TableComboBoxCellEditor(TableComboBoxCellRender render, String[] items) {
        super(new JCheckBox());
        this.render = render;
        combo_box = new PanelComboBox();
        
        for (String item : items) combo_box.getComboBox().addItem(item);
    }
    
    public ArrayList<Integer> getSelectedIndex() {
        return render.getSelectedIndex();
    }
    
    public void setSelectedIndex(int row, int value) {
        render.getSelectedIndex().set(row, value);
    }
    
    public PanelComboBox getComboBox() {
        return combo_box;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        ItemListener[] item_listeners = combo_box.getComboBox().getItemListeners();
        
        for (ItemListener listener : item_listeners) {
            combo_box.getComboBox().removeItemListener(listener);
        }
        
        combo_box.getComboBox().addItemListener((ItemEvent ie) -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                int selected = combo_box.getComboBox().getSelectedIndex();
                setSelectedIndex(row, selected);
            }
        });
        
        combo_box.getComboBox().setSelectedIndex((int) getSelectedIndex().get(row));
        combo_box.setBackground(new Color(230, 230, 230, 255));
        combo_box.getComboBox().setBackground(new Color(230, 230, 230, 255));
        combo_box.getComboBox().getEditor().getEditorComponent().setBackground(new Color(230, 230, 230, 255));
        
        return combo_box;
    }
}
