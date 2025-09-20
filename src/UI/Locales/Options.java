package UI.Locales;

import Sistema.Empleado;
import Sistema.Local;
import Sistema.Producto;
import UI.Interface.Application;
import UI.Misc.ScrollBar;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Options extends javax.swing.JPanel {
    private Application frame;
    private JFrame parent_frame;
    private Local local;
    
    public Options() {
        initComponents();
        setInvisible();
        
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
        
        local = new Local();
    }
    
    public Object[] getData() {
        Object[] data = new Object[] { 
            hintedText1.getText(),
            hintedText2.getText(),
            "+51 " + hintedText3.getText(),
            hintedText4.getText(),
            local.getProductos(),
            local.getEmpleados()
        };
        return data;
    }
    
    public void setData() {
        hintedText1.setText(String.valueOf(local.getCodigo()));
        hintedText2.setText(local.getDireccion());
        hintedText3.setText(local.getTelefono().substring(4));
        hintedText4.setText(local.getComentario());
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    public void setParentFrame(JFrame parent_frame) {
        this.parent_frame = parent_frame;
    }
    
    public void setLocal(Local local) {
        this.local = local;
    }
    
    public void setProductos() {
        for (Integer producto_id : local.getProductos()) {
            Producto producto = frame.findProductoByCodigo(producto_id);
            table_productos.addRow(new Object[] { producto.getNombre(), frame.findProveedorByCodigo(producto.getProveedor()).getNombre() });
        }
    }
    
    public void setEmpleados() {
        for (String empleado_dni : local.getEmpleados()) {
            Empleado empleado = frame.findEmpleadoByDNI(empleado_dni);
            table_empleados.addRow(new Object[] { empleado.getNombresApellidos(), empleado.getCargo() });
        }
    }

    public final void setInvisible() {
        advertencia1.setVisible(false);
        advertencia2.setVisible(false);
    }
    
    public boolean isLegal() {
        setInvisible();
        
        String text1 = hintedText3.getText();
        String text2 = hintedText2.getText();
        
        if (!text1.matches("9\\d{8}")) advertencia1.setVisible(true);
        if (text2.equals("")) advertencia2.setVisible(true);
        
        return !(advertencia1.isVisible() || advertencia2.isVisible());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        hintedText2 = new UI.Misc.HintedText();
        jLabel3 = new javax.swing.JLabel();
        hintedText3 = new UI.Misc.HintedText();
        jLabel4 = new javax.swing.JLabel();
        hintedText4 = new UI.Misc.HintedText();
        hintedText1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        advertencia1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        editar_productos = new UI.Misc.CustomButton();
        sp_table_productos = new javax.swing.JScrollPane();
        table_productos = new UI.Table.Table();
        jLabel6 = new javax.swing.JLabel();
        editar_empleados = new UI.Misc.CustomButton();
        sp_table_empleados = new javax.swing.JScrollPane();
        table_empleados = new UI.Table.Table();
        advertencia2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Dirección");

        hintedText2.setBackground(new java.awt.Color(242, 242, 242));
        hintedText2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText2.setHint("Dirección");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Teléfono");

        hintedText3.setBackground(new java.awt.Color(242, 242, 242));
        hintedText3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText3.setHint("Teléfono");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Comentario");

        hintedText4.setBackground(new java.awt.Color(242, 242, 242));
        hintedText4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText4.setHint("Comentario");

        hintedText1.setForeground(new java.awt.Color(153, 153, 153));
        hintedText1.setText("Autogenerado");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("+51");

        advertencia1.setForeground(new java.awt.Color(255, 0, 0));
        advertencia1.setText("Número inválido");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Productos");

        editar_productos.setForeground(new java.awt.Color(255, 255, 255));
        editar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/editar.png"))); // NOI18N
        editar_productos.setBorderColor(new java.awt.Color(242, 242, 242));
        editar_productos.setColor(new java.awt.Color(242, 242, 242));
        editar_productos.setColorClick(new java.awt.Color(102, 102, 102));
        editar_productos.setColorOver(new java.awt.Color(204, 204, 204));
        editar_productos.setRadius(50);
        editar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editar_productosActionPerformed(evt);
            }
        });

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

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Empleados");

        editar_empleados.setForeground(new java.awt.Color(255, 255, 255));
        editar_empleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/editar.png"))); // NOI18N
        editar_empleados.setBorderColor(new java.awt.Color(242, 242, 242));
        editar_empleados.setColor(new java.awt.Color(242, 242, 242));
        editar_empleados.setColorClick(new java.awt.Color(102, 102, 102));
        editar_empleados.setColorOver(new java.awt.Color(204, 204, 204));
        editar_empleados.setRadius(50);
        editar_empleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editar_empleadosActionPerformed(evt);
            }
        });

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

        advertencia2.setForeground(new java.awt.Color(255, 0, 0));
        advertencia2.setText("Campo obligatorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sp_table_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(jLabel9))
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(advertencia1)
                            .addComponent(hintedText2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hintedText4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hintedText1)
                            .addComponent(advertencia2)))
                    .addComponent(sp_table_empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editar_empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hintedText1))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hintedText2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia2)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 0, 0)
                .addComponent(advertencia1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hintedText4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(sp_table_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editar_empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(sp_table_empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editar_productosActionPerformed
        EditProductosLocal obj = new EditProductosLocal();
        obj.setApplication(frame);
        obj.initData(local);
        
        obj.getGuardar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                local.setProductos(obj.getProductos());
                table_productos.clearTable();
                setProductos();
                
                parent_frame.setEnabled(true);
                obj.dispose();
            }
        });
        
        obj.getCancelar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                parent_frame.setEnabled(true);
                obj.dispose();
            }
        });
        
        parent_frame.setEnabled(false);
        obj.setVisible(true);
    }//GEN-LAST:event_editar_productosActionPerformed

    private void editar_empleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editar_empleadosActionPerformed
        EditEmpleadosLocal obj = new EditEmpleadosLocal();
        obj.setApplication(frame);
        obj.initData(local);
        
        obj.getGuardar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                local.setEmpleados(obj.getEmpleados(local));
                table_empleados.clearTable();
                setEmpleados();
                
                parent_frame.setEnabled(true);
                obj.dispose();
            }
        });
        
        obj.getCancelar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                parent_frame.setEnabled(true);
                obj.dispose();
            }
        });
        
        parent_frame.setEnabled(false);
        obj.setVisible(true);
    }//GEN-LAST:event_editar_empleadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advertencia1;
    private javax.swing.JLabel advertencia2;
    private UI.Misc.CustomButton editar_empleados;
    private UI.Misc.CustomButton editar_productos;
    private javax.swing.JLabel hintedText1;
    private UI.Misc.HintedText hintedText2;
    private UI.Misc.HintedText hintedText3;
    private UI.Misc.HintedText hintedText4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane sp_table_empleados;
    private javax.swing.JScrollPane sp_table_productos;
    private UI.Table.Table table_empleados;
    private UI.Table.Table table_productos;
    // End of variables declaration//GEN-END:variables
}
