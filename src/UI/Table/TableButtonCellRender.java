package UI.Table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableButtonCellRender extends DefaultTableCellRenderer {
    private final String text;
    private Color background;
    private Color button_background;
    private Color selected_background;
    
    public TableButtonCellRender(String text, Color background, Color button_background, Color selected_background) {
        super();
        this.text = text;
        this.background = background;
        this.button_background = button_background;
        this.selected_background = selected_background;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int row, int column) {
        setBorder(noFocusBorder);
        
        PanelButton button = new PanelButton();
        button.getButton().setBackground(button_background);
        button.getButton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/" + text + ".png")));
        
        if (selected) {
            button.setBackground(selected_background);
            button.getButton().setBorderColor(selected_background);
        } 
        else {
            button.setBackground(background);
            button.getButton().setBorderColor(background);
        }
        
        return button;
    }
}
