package UI.Configuracion;

import UI.Interface.Application;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class Configuracion extends javax.swing.JPanel {
    private Color color1;
    private Color color2;
    private final Application frame;
    
    public Configuracion(Application frame) {
        initComponents();
        setOpaque(false);
        setInvisible();
        
        this.frame = frame;
        
        color1 = Color.WHITE;
        color2 = Color.GRAY;
        
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
    }
    
    public final void setInvisible() {
        advertencia1.setVisible(false);
        advertencia2.setVisible(false);
        exito1.setVisible(false);
    }
    
    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void validatePasswordChange() {
        setInvisible();
        
        try {
            boolean is_valid = Conexiones.Procedimientos.validatePassword(frame.getUsuario(), hintedPasswordText1.getText());
            
            if (!is_valid) advertencia1.setVisible(true);
            if (!hintedPasswordText2.getText().equals(hintedPasswordText3.getText())) advertencia2.setVisible(true);
            
            if (!advertencia1.isVisible() && !advertencia2.isVisible() && !hintedPasswordText2.getText().equals("")) {
                Conexiones.Procedimientos.setPassword(frame.getUsuario(), hintedPasswordText3.getText());
                exito1.setVisible(true);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new UI.Misc.SimpleBackground();
        jLabel1 = new javax.swing.JLabel();
        hintedPasswordText1 = new UI.Misc.HintedPasswordText();
        hintedPasswordText2 = new UI.Misc.HintedPasswordText();
        hintedPasswordText3 = new UI.Misc.HintedPasswordText();
        customButton1 = new UI.Misc.CustomButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        exito1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        advertencia1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        advertencia2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setOpaque(false);

        background.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cambiar Contraseña");

        hintedPasswordText1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        hintedPasswordText1.setHint("Contraseña actual");

        hintedPasswordText2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        hintedPasswordText2.setHint("Nueva contraseña");

        hintedPasswordText3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        hintedPasswordText3.setHint("Repetir nueva contraseña");
        hintedPasswordText3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hintedPasswordText3KeyPressed(evt);
            }
        });

        customButton1.setForeground(new java.awt.Color(255, 255, 255));
        customButton1.setText("Guardar");
        customButton1.setBorderColor(new java.awt.Color(255, 255, 255));
        customButton1.setColor(new java.awt.Color(51, 51, 51));
        customButton1.setColorClick(new java.awt.Color(204, 204, 204));
        customButton1.setColorOver(new java.awt.Color(102, 102, 102));
        customButton1.setRadius(10);
        customButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        exito1.setForeground(new java.awt.Color(0, 153, 0));
        exito1.setText("Contraseña actualizada");
        jScrollPane1.setViewportView(exito1);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        advertencia1.setForeground(new java.awt.Color(255, 0, 0));
        advertencia1.setText("Contraseña incorrecta");
        jScrollPane2.setViewportView(advertencia1);

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        advertencia2.setForeground(new java.awt.Color(255, 0, 0));
        advertencia2.setText("Contraseñas no coinciden");
        jScrollPane3.setViewportView(advertencia2);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hintedPasswordText3, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(hintedPasswordText2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hintedPasswordText1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addComponent(hintedPasswordText1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(hintedPasswordText2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hintedPasswordText3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void customButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customButton1ActionPerformed
        validatePasswordChange();
    }//GEN-LAST:event_customButton1ActionPerformed

    private void hintedPasswordText3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hintedPasswordText3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validatePasswordChange();
        }
    }//GEN-LAST:event_hintedPasswordText3KeyPressed
    
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(0, 0, 20, getHeight());
        g2.setColor(new Color(255, 255, 255, 50));
        super.paintComponent(graphics);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advertencia1;
    private javax.swing.JLabel advertencia2;
    private UI.Misc.SimpleBackground background;
    private UI.Misc.CustomButton customButton1;
    private javax.swing.JLabel exito1;
    private UI.Misc.HintedPasswordText hintedPasswordText1;
    private UI.Misc.HintedPasswordText hintedPasswordText2;
    private UI.Misc.HintedPasswordText hintedPasswordText3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
