package UI.Table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableButtonCellEditor extends DefaultCellEditor {
    private TableButtonEvent event;
    private String text;

    public TableButtonCellEditor(TableButtonEvent event, String text) {
        super(new JCheckBox());
        this.event = event;
        this.text = text;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelButton button = new PanelButton();
        button.initEvent(event, row);
        button.setBackground(new Color(230, 230, 230, 255));
        button.getButton().setBorderColor(new Color(230, 230, 230, 255));
        button.getButton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/" + text + ".png")));
        return button;
    }
}
