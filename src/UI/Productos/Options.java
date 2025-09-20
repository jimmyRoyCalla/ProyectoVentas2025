package UI.Productos;

import Sistema.Proveedor;
import UI.Interface.Application;

public class Options extends javax.swing.JPanel {
    private Application frame;
    
    public Options() {
        initComponents();
        setInvisible();
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    public Object[] getData() {
        Object[] data = new Object[] { 
            hintedText1.getText(),
            hintedText2.getText(),
            hintedText3.getText(),
            hintedText4.getText(),
            frame.getProveedoresList().get(comboBoxSuggestion1.getSelectedIndex()).getCodigo()
        };
        return data;
    }
    
    public void setData(Object[] data) {
        hintedText1.setText(String.valueOf(data[0]));
        hintedText2.setText(String.valueOf(data[1]));
        hintedText3.setText(String.valueOf(data[2]));
        hintedText4.setText(String.valueOf(data[3]));
        comboBoxSuggestion1.setSelectedItem((String) data[4]);
    }
    
    public void setProveedores() {
        for (Proveedor proveedor : frame.getProveedoresList()) {
            comboBoxSuggestion1.addItem(proveedor.getNombre());
        }
    }
    
    public final void setInvisible() {
        advertencia1.setVisible(false);
        advertencia2.setVisible(false);
        advertencia3.setVisible(false);
    }
    
    public boolean isLegal() {
        setInvisible();
        
        String text1 = hintedText3.getText();
        String text2 = hintedText4.getText();
        String text3 = hintedText2.getText();
        
        try {
            double value = Double.parseDouble(text1);
            if (value <= 0) advertencia1.setVisible(true);
        }
        catch (NumberFormatException e) {
            advertencia1.setVisible(true);
        }
        
        try {
            int value = Integer.parseInt(text2);
            if (value < 0) advertencia2.setVisible(true);
        } 
        catch (NumberFormatException e) {
            advertencia2.setVisible(true);
        }
        
        if (text3.equals("")) advertencia3.setVisible(true);
        
        return !(advertencia1.isVisible() || advertencia2.isVisible() || advertencia3.isVisible());
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
        jLabel5 = new javax.swing.JLabel();
        comboBoxSuggestion1 = new UI.Misc.ComboBoxSuggestion();
        hintedText1 = new javax.swing.JLabel();
        advertencia1 = new javax.swing.JLabel();
        advertencia2 = new javax.swing.JLabel();
        advertencia3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nombre");

        hintedText2.setBackground(new java.awt.Color(242, 242, 242));
        hintedText2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText2.setHint("Nombre");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Precio");

        hintedText3.setBackground(new java.awt.Color(242, 242, 242));
        hintedText3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText3.setHint("Precio");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Cantidad");

        hintedText4.setBackground(new java.awt.Color(242, 242, 242));
        hintedText4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText4.setHint("Cantidad");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Proveedor");

        comboBoxSuggestion1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        hintedText1.setForeground(new java.awt.Color(153, 153, 153));
        hintedText1.setText("Autogenerado");

        advertencia1.setForeground(new java.awt.Color(255, 0, 0));
        advertencia1.setText("Número inválido");

        advertencia2.setForeground(new java.awt.Color(255, 0, 0));
        advertencia2.setText("Número inválido");

        advertencia3.setForeground(new java.awt.Color(255, 0, 0));
        advertencia3.setText("Campo obligatorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(advertencia3)
                    .addComponent(advertencia2)
                    .addComponent(advertencia1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(hintedText2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hintedText1))
                .addContainerGap(44, Short.MAX_VALUE))
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
                .addComponent(advertencia3)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hintedText4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia2)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advertencia1;
    private javax.swing.JLabel advertencia2;
    private javax.swing.JLabel advertencia3;
    private UI.Misc.ComboBoxSuggestion comboBoxSuggestion1;
    private javax.swing.JLabel hintedText1;
    private UI.Misc.HintedText hintedText2;
    private UI.Misc.HintedText hintedText3;
    private UI.Misc.HintedText hintedText4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
