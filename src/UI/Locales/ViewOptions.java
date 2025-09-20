package UI.Locales;

import Sistema.Empleado;
import Sistema.Producto;
import UI.Interface.Application;
import UI.Misc.ScrollBar;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewOptions extends javax.swing.JPanel {
    private Application frame;
    
    public ViewOptions() {
        initComponents();
        
        sp_table_productos.setVerticalScrollBar(new ScrollBar());
        sp_table_productos.getVerticalScrollBar().setBackground(Color.WHITE);
        sp_table_productos.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p1 = new JPanel();
        p1.setBackground(Color.WHITE);
        sp_table_productos.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p1);
        
        sp_table_empleados.setVerticalScrollBar(new ScrollBar());
        sp_table_empleados.getVerticalScrollBar().setBackground(Color.WHITE);
        sp_table_empleados.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p2 = new JPanel();
        p2.setBackground(Color.WHITE);
        sp_table_empleados.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p2);
    }
    
    public void setData(Object[] data) {
        jLabel7.setText((String) data[0]);
        jLabel8.setText((String) data[1]);
        jLabel9.setText((String) data[2]);
        jLabel10.setText((String) data[3]);
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    public void setProductos(ArrayList<Integer> productos) {
        for (Integer producto_id : productos) {
            Producto producto = frame.findProductoByCodigo(producto_id);
            table_productos.addRow(new Object[] { producto.getNombre(), producto.getPrecio() });
        }
    }
    
    public void setEmpleados(ArrayList<String> empleados) {
        for (String empleado_dni : empleados) {
            Empleado empleado = frame.findEmpleadoByDNI(empleado_dni);
            table_empleados.addRow(new Object[] { empleado.getNombresApellidos(), empleado.getCargo() });
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sp_table_productos = new javax.swing.JScrollPane();
        table_productos = new UI.Table.Table();
        jLabel11 = new javax.swing.JLabel();
        sp_table_empleados = new javax.swing.JScrollPane();
        table_empleados = new UI.Table.Table();

        setBackground(new java.awt.Color(204, 204, 204));
        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Código");

        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("jLabel9");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Teléfono");

        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("jLabel8");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Dirección");

        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("jLabel7");
        jLabel7.setToolTipText("");
        jLabel7.setToolTipText(jLabel7.getText());

        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("jLabel10");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Comentario");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Productos");

        sp_table_productos.setBorder(null);
        sp_table_productos.setOpaque(false);

        table_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Marca"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_productos.setOpaque(false);
        sp_table_productos.setViewportView(table_productos);

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Empleados");

        sp_table_empleados.setBorder(null);
        sp_table_empleados.setOpaque(false);

        table_empleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Persona", "Cargo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_empleados.setOpaque(false);
        sp_table_empleados.setViewportView(table_empleados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(sp_table_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sp_table_empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_table_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sp_table_empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane sp_table_empleados;
    private javax.swing.JScrollPane sp_table_productos;
    private UI.Table.Table table_empleados;
    private UI.Table.Table table_productos;
    // End of variables declaration//GEN-END:variables
}
