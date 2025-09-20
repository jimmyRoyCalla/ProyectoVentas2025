package UI.Table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private Font defaultFont = getFont();
    private Font boldFont = defaultFont.deriveFont(Font.BOLD);
    
    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                TableHeader header = new TableHeader(o + "");
                header.setHorizontalAlignment(JLabel.CENTER);
                return header;
            }
        });
        
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                setBorder(noFocusBorder);
                
                if (isNumericValue(o) && i1 != 0) {
                    setHorizontalAlignment(SwingConstants.RIGHT);
                }
                else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
                
                if (o instanceof Double double_value) {
                    String formattedValue = decimalFormat.format(double_value);
                    setText("S/ " + formattedValue);
                    setFont(boldFont);
                }
                
                if (selected) {
                    com.setForeground(new Color(15, 89, 140));
                    com.setBackground(new Color(230, 230, 230, 255));
                } 
                else {
                    com.setForeground(new Color(102, 102, 102));
                    com.setBackground(Color.WHITE);
                }
                
                return com;
            }
            
            private boolean isNumericValue(Object o) {
                return o instanceof Number;
            }
        });
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
    
    public void removeRow(int row) {
        int model_row = convertRowIndexToModel(row);
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.removeRow(model_row);
    }
    
    public void insertRow(int index, Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.insertRow(0, row);
    }
    
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0);
    }
}