package UI.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCantidad extends javax.swing.JPanel {
    public PanelCantidad() {
        initComponents();
    }
    
    public UI.Misc.CustomButton getMinus() {
        return minus;
    }
    
    public UI.Misc.CustomButton getPlus() {
        return plus;
    }
    
    public javax.swing.JLabel getNumber() {
        return jLabel1;
    }
    
    public void initEvent(TableCantidadEvent event, int row, int max) {
        ActionListener[] minus_listeners = minus.getActionListeners();
        ActionListener[] plus_listeners = plus.getActionListeners();

        for (ActionListener listener : minus_listeners) {
            minus.removeActionListener(listener);
        }
        for (ActionListener listener : plus_listeners) {
            plus.removeActionListener(listener);
        }
        
        minus.addActionListener((ActionEvent e) -> {
            event.onMinus(row);
            updateNumber(1, max, -1);
            
        });
        
        plus.addActionListener((ActionEvent e) -> {
            event.onPlus(row);
            updateNumber(1, max, 1);
        });
    }
    
    public void updateNumber(int min, int max, int change) {
        int num = Integer.parseInt(jLabel1.getText());
        
        if (num > min && change == -1) num--;
        else if (num < max && change == 1) num++;
        
        jLabel1.setText(String.valueOf(num));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        minus = new UI.Misc.CustomButton();
        plus = new UI.Misc.CustomButton();
        jLabel1 = new javax.swing.JLabel();

        minus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/minus.png"))); // NOI18N
        minus.setBorderColor(new java.awt.Color(204, 0, 0));
        minus.setColor(new java.awt.Color(204, 0, 0));
        minus.setColorClick(new java.awt.Color(102, 0, 0));
        minus.setColorOver(new java.awt.Color(153, 0, 0));
        minus.setRadius(50);

        plus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus3.png"))); // NOI18N
        plus.setBorderColor(new java.awt.Color(0, 153, 0));
        plus.setColor(new java.awt.Color(0, 153, 0));
        plus.setColorClick(new java.awt.Color(0, 51, 0));
        plus.setColorOver(new java.awt.Color(0, 102, 0));
        plus.setRadius(50);

        jLabel1.setForeground(new java.awt.Color(15, 89, 140));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(minus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(plus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private UI.Misc.CustomButton minus;
    private UI.Misc.CustomButton plus;
    // End of variables declaration//GEN-END:variables
}
