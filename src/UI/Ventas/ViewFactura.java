package UI.Ventas;

import Sistema.Factura;
import UI.Interface.Application;
import UI.Misc.ModernScrollBarUI;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewFactura extends javax.swing.JFrame {    
    private Application frame;
    
    public ViewFactura() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        initMoving(ViewFactura.this);
        
        jScrollPane1.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        jScrollPane1.getVerticalScrollBar().setBackground(new Color(242, 242, 242, 255));
        jScrollPane1.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p = new JPanel();
        p.setBackground(new Color(242, 242, 242, 255));
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
    }
    
    private int x;
    private int y;
    
    private void initMoving(JFrame frame) {
        jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        });
        
        jLabel1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                frame.setLocation(me.getXOnScreen() - x - 15, me.getYOnScreen() - y - 15);
            }
        });
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
        viewOptions1.setApplication(frame);
    }
    
    public void initData(Factura factura) {
        viewOptions1.setData(factura);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gradientBackground1 = new UI.Misc.GradientBackground();
        simpleBackground1 = new UI.Misc.SimpleBackground();
        jLabel1 = new javax.swing.JLabel();
        customButton1 = new UI.Misc.CustomButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewOptions1 = new UI.Ventas.ViewFacturaOptions();
        customButton2 = new UI.Misc.CustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        simpleBackground1.setBackground(new java.awt.Color(242, 242, 242));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("     Detalles");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        customButton1.setForeground(new java.awt.Color(255, 255, 255));
        customButton1.setText("Volver");
        customButton1.setBorderColor(new java.awt.Color(242, 242, 242));
        customButton1.setColor(new java.awt.Color(51, 51, 51));
        customButton1.setColorClick(new java.awt.Color(204, 204, 204));
        customButton1.setColorOver(new java.awt.Color(102, 102, 102));
        customButton1.setRadius(10);
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(242, 242, 242));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setForeground(new java.awt.Color(242, 242, 242));
        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewportView(viewOptions1);

        customButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/pdf.png"))); // NOI18N
        customButton2.setBorderColor(new java.awt.Color(242, 242, 242));
        customButton2.setColor(new java.awt.Color(242, 242, 242));
        customButton2.setColorClick(new java.awt.Color(102, 102, 102));
        customButton2.setColorOver(new java.awt.Color(204, 204, 204));
        customButton2.setRadius(50);
        customButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout simpleBackground1Layout = new javax.swing.GroupLayout(simpleBackground1);
        simpleBackground1.setLayout(simpleBackground1Layout);
        simpleBackground1Layout.setHorizontalGroup(
            simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simpleBackground1Layout.createSequentialGroup()
                .addGroup(simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(simpleBackground1Layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(simpleBackground1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(simpleBackground1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        simpleBackground1Layout.setVerticalGroup(
            simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simpleBackground1Layout.createSequentialGroup()
                .addGroup(simpleBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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

    private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
        frame.setEnabled(true);
        dispose();
    }//GEN-LAST:event_customButton1ActionPerformed

    private void customButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton2ActionPerformed
        try {
            viewOptions1.toPDF();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_customButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new ViewFactura().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.CustomButton customButton1;
    private UI.Misc.CustomButton customButton2;
    private UI.Misc.GradientBackground gradientBackground1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private UI.Misc.SimpleBackground simpleBackground1;
    private UI.Ventas.ViewFacturaOptions viewOptions1;
    // End of variables declaration//GEN-END:variables

}
