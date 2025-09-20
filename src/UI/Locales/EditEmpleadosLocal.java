package UI.Locales;

import Sistema.Empleado;
import Sistema.Local;
import UI.Interface.Application;
import UI.Misc.ScrollBar;
import UI.Table.TableComboBoxCellEditor;
import UI.Table.TableComboBoxCellRender;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditEmpleadosLocal extends javax.swing.JFrame {
    private Application frame;
    private TableComboBoxCellRender render;
    private TableComboBoxCellEditor editor;
    private ArrayList<Integer> original;
    
    public EditEmpleadosLocal() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        
        original = new ArrayList<>();
        
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        
        render = new TableComboBoxCellRender(new String[] { "Libre", "Ocupado", "Asignado" });
        editor = new TableComboBoxCellEditor(render, new String[] { "Libre", "Ocupado", "Asignado" });
        
        table.getColumnModel().getColumn(5).setCellRenderer(render);
        table.getColumnModel().getColumn(5).setCellEditor(editor);
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }

    public void initData(Local local) {
        int value;
        
        for (Empleado empleado : frame.getEmpleadosList()) {
            table.addRow(new Object[] { empleado.getDni(), empleado.getNombres(), empleado.getApellidos(), empleado.getTelefono(), 
                empleado.getCargo(), "" });
            
            if (empleado.getLocalAsignado() == null) value = 0;
            else if (empleado.getLocalAsignado() != local.getCodigo()) value = 1;
            else value = 2;
            
            addRendererEditor(value);
            original.add(value);
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
    
    public ArrayList<String> getEmpleados(Local local) {
        ArrayList<String> empleados = new ArrayList<>();
        
        for (int i = 0; i < frame.getEmpleadosList().size(); i++) {
            if (editor.getSelectedIndex().get(i) == 0) {
                frame.getEmpleadosList().get(i).setLocalAsignado(null);
            }
            else if (editor.getSelectedIndex().get(i) == 1 && original.get(i) == 2) {
                frame.getEmpleadosList().get(i).setLocalAsignado(local.getCodigo());
                empleados.add(frame.getEmpleadosList().get(i).getDni());
            }
            else if (editor.getSelectedIndex().get(i) == 2) {
                frame.getEmpleadosList().get(i).setLocalAsignado(local.getCodigo());
                empleados.add(frame.getEmpleadosList().get(i).getDni());
            }
        }
        
        return empleados;
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
                "DNI", "Nombres", "Apellidos", "Teléfono", "Cargo", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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
            java.util.logging.Logger.getLogger(EditEmpleadosLocal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new EditEmpleadosLocal().setVisible(true);
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
