package UI.Table;

import java.awt.event.ActionEvent;

public class PanelButton extends javax.swing.JPanel {
    public PanelButton() {
        initComponents();
    }
    
    public UI.Misc.CustomButton getButton() {
        return customButton1;
    }
    
    public void initEvent(TableButtonEvent event, int row) {
        customButton1.addActionListener((ActionEvent e) -> {
            event.onClick(row);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customButton1 = new UI.Misc.CustomButton();

        setBackground(new java.awt.Color(255, 255, 255));

        customButton1.setForeground(new java.awt.Color(255, 255, 255));
        customButton1.setBorderColor(new java.awt.Color(255, 255, 255));
        customButton1.setColor(new java.awt.Color(51, 51, 51));
        customButton1.setColorClick(new java.awt.Color(204, 204, 204));
        customButton1.setColorOver(new java.awt.Color(102, 102, 102));
        customButton1.setRadius(50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(customButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.CustomButton customButton1;
    // End of variables declaration//GEN-END:variables
}
