package UI.Proveedores;

import Sistema.Producto;
import Sistema.Proveedor;
import UI.Interface.Application;
import UI.Misc.ScrollBar;
import UI.Table.TableComboBoxCellEditor;
import UI.Table.TableComboBoxCellRender;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditProductosProveedor extends javax.swing.JFrame {
    private Application frame;
    private TableComboBoxCellRender render;
    private TableComboBoxCellEditor editor;
    
    public EditProductosProveedor() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        
        render = new TableComboBoxCellRender(new String [] { "Diferente", "Sí" });
        editor = new TableComboBoxCellEditor(render, new String [] { "Diferente", "Sí" });
        
        table.getColumnModel().getColumn(4).setCellRenderer(render);
        table.getColumnModel().getColumn(4).setCellEditor(editor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }

    public void initData(Proveedor proveedor) {
        ArrayList<Integer> productos_proveedor = proveedor.getProductos();
        
        for (Producto producto : frame.getProductosList()) {
            table.addRow(new Object[] { producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getCantidad(), 
                frame.findProveedorByCodigo(producto.getProveedor()).getNombre(), "" });
            
            if (productos_proveedor.contains((Integer) producto.getCodigo())) addRendererEditor(1);
            else addRendererEditor(0);
        }
    }
    
    public void addRendererEditor(int value) {
        editor.getSelectedIndex().add(value);
    }
    
    public void setRendererEditor(int row, int value) {
        editor.setSelectedIndex(row, value);
    }
    
    public UI.Misc.CustomButton getGuardar() {
        return guardar;
    }
    
    public UI.Misc.CustomButton getCancelar() {
        return cancelar;
    }
    
    public ArrayList<Integer> getProductos(Proveedor proveedor) {
        ArrayList<Integer> productos = new ArrayList<>();
        
        for (int i = 0; i < frame.getProductosList().size(); i++) {
            if (editor.getSelectedIndex().get(i) == 1) {
                frame.findProveedorByCodigo(frame.getProductosList().get(i).getProveedor()).getProductos().remove((Integer) frame.getProductosList().get(i).getCodigo());
                frame.getProductosList().get(i).setProveedor(proveedor.getCodigo());
                productos.add(frame.getProductosList().get(i).getCodigo());
            }
        }
        
        return productos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gradientBackground1 = new UI.Misc.GradientBackground();
        simpleBackground1 = new UI.Misc.SimpleBackground();
        spTable = new javax.swing.JScrollPane();
        table = new UI.Table.Table();
        guardar = new UI.Misc.CustomButton();
        cancelar = new UI.Misc.CustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        simpleBackground1.setBackground(new java.awt.Color(255, 255, 255));

        spTable.setBorder(null);
        spTable.setOpaque(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio", "Cantidad", "Proveedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setOpaque(false);
        spTable.setViewportView(table);

        guardar.setBackground(new java.awt.Color(51, 51, 51));
        guardar.setForeground(new java.awt.Color(255, 255, 255));
        guardar.setText("Guardar");
        guardar.setBorderColor(new java.awt.Color(255, 255, 255));
        guardar.setColor(new java.awt.Color(51, 51, 51));
        guardar.setColorClick(new java.awt.Color(204, 204, 204));
        guardar.setColorOver(new java.awt.Color(102, 102, 102));
        guardar.setRadius(10);

        cancelar.setBackground(new java.awt.Color(51, 51, 51));
        cancelar.setForeground(new java.awt.Color(255, 255, 255));
        cancelar.setText("Cancelar");
        cancelar.setBorderColor(new java.awt.Color(255, 255, 255));
        cancelar.setColor(new java.awt.Color(51, 51, 51));
        cancelar.setColorClick(new java.awt.Color(204, 204, 204));
        cancelar.setColorOver(new java.awt.Color(102, 102, 102));
        cancelar.setRadius(10);

        javax.swing.GroupLayout simpleBackground1Layout = new javax.swing.GroupLayout(simpleBackground1);
        simpleBackground1.setLayout(simpleBackground1Layout);
        simpleBackground1Layout.setHorizontalGroup(
            simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simpleBackground1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
            .addGroup(simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(simpleBackground1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        simpleBackground1Layout.setVerticalGroup(
            simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simpleBackground1Layout.createSequentialGroup()
                .addContainerGap(260, Short.MAX_VALUE)
                .addGroup(simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
            .addGroup(simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(simpleBackground1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(54, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout gradientBackground1Layout = new javax.swing.GroupLayout(gradientBackground1);
        gradientBackground1.setLayout(gradientBackground1Layout);
        gradientBackground1Layout.setHorizontalGroup(
            gradientBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradientBackground1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(simpleBackground1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        gradientBackground1Layout.setVerticalGroup(
            gradientBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gradientBackground1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(simpleBackground1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gradientBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gradientBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditProductosProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new EditProductosProveedor().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.CustomButton cancelar;
    private UI.Misc.GradientBackground gradientBackground1;
    private UI.Misc.CustomButton guardar;
    private UI.Misc.SimpleBackground simpleBackground1;
    private javax.swing.JScrollPane spTable;
    private UI.Table.Table table;
    // End of variables declaration//GEN-END:variables
}
