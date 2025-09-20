package UI.Clientes;

public class EmpresaOptions extends javax.swing.JPanel {
    public EmpresaOptions() {
        initComponents();
        setInvisible();
    }
    
    public UI.Misc.HintedText getRucTextBox() {
        return hintedText1;
    }
    
    public Object[] getData() {
        Object[] data = new Object[] { 
            hintedText1.getText(),
            hintedText2.getText(),
            hintedText3.getText(),
            "+51 " + hintedText4.getText()
        };
        
        return data;
    }
    
    public void setData(Object[] data) {
        hintedText1.setText((String) data[0]);
        hintedText2.setText((String) data[1]);
        hintedText3.setText((String) data[2]);
        hintedText4.setText(((String) data[3]).substring(4));
    }
    
    public final void setInvisible() {
        advertencia1.setVisible(false);
        advertencia2.setVisible(false);
        advertencia3.setVisible(false);
        advertencia4.setVisible(false);
    }
    
    public boolean isLegal() {
        setInvisible();
        
        String text1 = hintedText1.getText();
        String text2 = hintedText4.getText();
        String text3 = hintedText2.getText();
        String text4 = hintedText3.getText();
        
        if (!text1.matches("20\\d{9}")) advertencia1.setVisible(true);
        if (!text2.matches("9\\d{8}")) advertencia2.setVisible(true);
        if (text3.equals("")) advertencia3.setVisible(true);
        if (text4.equals("")) advertencia4.setVisible(true);
        
        return !(advertencia1.isVisible() || advertencia2.isVisible() || advertencia3.isVisible() || advertencia4.isVisible());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new UI.Date.DateChooser();
        jLabel1 = new javax.swing.JLabel();
        hintedText1 = new UI.Misc.HintedText();
        jLabel2 = new javax.swing.JLabel();
        hintedText2 = new UI.Misc.HintedText();
        jLabel3 = new javax.swing.JLabel();
        hintedText3 = new UI.Misc.HintedText();
        jLabel4 = new javax.swing.JLabel();
        hintedText4 = new UI.Misc.HintedText();
        jLabel9 = new javax.swing.JLabel();
        advertencia1 = new javax.swing.JLabel();
        advertencia2 = new javax.swing.JLabel();
        advertencia3 = new javax.swing.JLabel();
        advertencia4 = new javax.swing.JLabel();

        dateChooser1.setForeground(new java.awt.Color(51, 153, 255));

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("RUC");

        hintedText1.setBackground(new java.awt.Color(242, 242, 242));
        hintedText1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText1.setHint("RUC");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nombre");

        hintedText2.setBackground(new java.awt.Color(242, 242, 242));
        hintedText2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText2.setHint("Nombre");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Dirección");

        hintedText3.setBackground(new java.awt.Color(242, 242, 242));
        hintedText3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText3.setHint("Dirección");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Teléfono");

        hintedText4.setBackground(new java.awt.Color(242, 242, 242));
        hintedText4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText4.setHint("Teléfono");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("+51");

        advertencia1.setForeground(new java.awt.Color(255, 0, 0));
        advertencia1.setText("RUC inválido");

        advertencia2.setForeground(new java.awt.Color(255, 0, 0));
        advertencia2.setText("Número inválido");

        advertencia3.setForeground(new java.awt.Color(255, 0, 0));
        advertencia3.setText("Campo obligatorio");

        advertencia4.setForeground(new java.awt.Color(255, 0, 0));
        advertencia4.setText("Campo obligatorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(advertencia4)
                    .addComponent(advertencia3)
                    .addComponent(advertencia2)
                    .addComponent(advertencia1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(hintedText2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hintedText1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(advertencia1)
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
                .addComponent(advertencia4)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hintedText4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(advertencia2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advertencia1;
    private javax.swing.JLabel advertencia2;
    private javax.swing.JLabel advertencia3;
    private javax.swing.JLabel advertencia4;
    private UI.Date.DateChooser dateChooser1;
    private UI.Misc.HintedText hintedText1;
    private UI.Misc.HintedText hintedText2;
    private UI.Misc.HintedText hintedText3;
    private UI.Misc.HintedText hintedText4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
