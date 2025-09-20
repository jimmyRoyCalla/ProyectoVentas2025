package UI.Empleados;

import Sistema.Local;
import UI.Interface.Application;

public class Options extends javax.swing.JPanel {
    private Application frame;
    
    public Options() {
        initComponents();
        setInvisible();
    }
    
    public UI.Misc.HintedText getDniTextBox() {
        return hintedText1;
    }
    
    public Object[] getData() {
        Integer local;
        if (comboBoxSuggestion3.getSelectedIndex() == 0) local = null;
        else local = (Integer) frame.getLocalesList().get(comboBoxSuggestion3.getSelectedIndex() - 1).getCodigo();
        
        Object[] data = new Object[] { 
            hintedText1.getText(),
            hintedText2.getText(),
            hintedText3.getText(),
            "+51 " + hintedText4.getText(),
            (String) comboBoxSuggestion1.getSelectedItem(),
            hintedText6.getText(),
            hintedText7.getText(),
            (String) comboBoxSuggestion2.getSelectedItem(),
            hintedText9.getText(),
            local
        };
        
        return data;
    }
    
    public void setData(Object[] data) {
        hintedText1.setText((String) data[0]);
        hintedText2.setText((String) data[1]);
        hintedText3.setText((String) data[2]);
        hintedText4.setText(((String) data[3]).substring(4));
        comboBoxSuggestion1.setSelectedItem((String) data[4]);
        hintedText6.setText((String) data[5]);
        hintedText7.setText(((String) data[6]));
        comboBoxSuggestion2.setSelectedItem((String) data[7]);
        hintedText9.setText((String) data[8]);
        comboBoxSuggestion3.setSelectedItem((String) data[9]);
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    public final void setInvisible() {
        advertencia1.setVisible(false);
        advertencia2.setVisible(false);
        advertencia3.setVisible(false);
        advertencia4.setVisible(false);
        advertencia5.setVisible(false);
        advertencia6.setVisible(false);
        advertencia7.setVisible(false);
    }
    
    public void setLocales() {
        comboBoxSuggestion3.addItem("Ninguno");
        for (Local local : frame.getLocalesList()) {
            comboBoxSuggestion3.addItem(local.getDireccion());
        }
    }
    
    public boolean isLegal() {
        setInvisible();
        
        String text1 = hintedText1.getText();
        String text2 = hintedText4.getText();
        String text3 = hintedText9.getText();
        String text4 = hintedText2.getText();
        String text5 = hintedText3.getText();
        String text6 = hintedText6.getText();
        
        if (!text1.matches("\\d{8}")) advertencia1.setVisible(true);
        if (!text2.matches("9\\d{8}")) advertencia2.setVisible(true);
        
        try {
            double value = Double.parseDouble(text3);
            if (value < 1025) advertencia4.setVisible(true);
        } 
        catch (NumberFormatException e) {
            advertencia3.setVisible(true);
        }
        
        if (text4.equals("")) advertencia5.setVisible(true);
        if (text5.equals("")) advertencia6.setVisible(true);
        if (text6.equals("")) advertencia7.setVisible(true);
        
        return !(advertencia1.isVisible() || advertencia2.isVisible() || advertencia3.isVisible() || advertencia4.isVisible() || 
                 advertencia5.isVisible() || advertencia6.isVisible() || advertencia7.isVisible());
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        hintedText6 = new UI.Misc.HintedText();
        jLabel7 = new javax.swing.JLabel();
        hintedText7 = new UI.Misc.HintedText();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        hintedText9 = new UI.Misc.HintedText();
        jLabel10 = new javax.swing.JLabel();
        comboBoxSuggestion1 = new UI.Misc.ComboBoxSuggestion();
        comboBoxSuggestion2 = new UI.Misc.ComboBoxSuggestion();
        comboBoxSuggestion3 = new UI.Misc.ComboBoxSuggestion();
        advertencia1 = new javax.swing.JLabel();
        advertencia2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        advertencia3 = new javax.swing.JLabel();
        advertencia4 = new javax.swing.JLabel();
        advertencia5 = new javax.swing.JLabel();
        advertencia6 = new javax.swing.JLabel();
        advertencia7 = new javax.swing.JLabel();

        dateChooser1.setForeground(new java.awt.Color(51, 153, 255));
        dateChooser1.setTextRefernce(hintedText7);

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("DNI");

        hintedText1.setBackground(new java.awt.Color(242, 242, 242));
        hintedText1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText1.setHint("DNI");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nombres");

        hintedText2.setBackground(new java.awt.Color(242, 242, 242));
        hintedText2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText2.setHint("Nombres");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Apellidos");

        hintedText3.setBackground(new java.awt.Color(242, 242, 242));
        hintedText3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText3.setHint("Apellidos");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Teléfono");

        hintedText4.setBackground(new java.awt.Color(242, 242, 242));
        hintedText4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText4.setHint("Teléfono");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Estado Civil");

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Dirección");

        hintedText6.setBackground(new java.awt.Color(242, 242, 242));
        hintedText6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText6.setHint("Dirección");

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Nacimiento");

        hintedText7.setBackground(new java.awt.Color(242, 242, 242));
        hintedText7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText7.setHint("Fecha Nacimiento");

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Cargo");

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Salario");

        hintedText9.setBackground(new java.awt.Color(242, 242, 242));
        hintedText9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText9.setHint("Salario");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Local");

        comboBoxSuggestion1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        comboBoxSuggestion1.setEditable(false);
        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Casado", "Soltero", "Viudo", "Divorciado" }));

        comboBoxSuggestion2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        comboBoxSuggestion2.setEditable(false);
        comboBoxSuggestion2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Asesor de Ventas", "Encargado de Almacén", "Encargado de Tienda", "Supervisor de Ventas", "Vendedor" }));

        comboBoxSuggestion3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N

        advertencia1.setForeground(new java.awt.Color(255, 0, 0));
        advertencia1.setText("DNI inválido");

        advertencia2.setForeground(new java.awt.Color(255, 0, 0));
        advertencia2.setText("Número inválido");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("+51");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("S/");

        advertencia3.setForeground(new java.awt.Color(255, 0, 0));
        advertencia3.setText("Número inválido");

        advertencia4.setForeground(new java.awt.Color(255, 0, 0));
        advertencia4.setText("Salario menor a S/ 1025");

        advertencia5.setForeground(new java.awt.Color(255, 0, 0));
        advertencia5.setText("Campo obligatorio");

        advertencia6.setForeground(new java.awt.Color(255, 0, 0));
        advertencia6.setText("Campo obligatorio");

        advertencia7.setForeground(new java.awt.Color(255, 0, 0));
        advertencia7.setText("Campo obligatorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(advertencia7)
                    .addComponent(advertencia6)
                    .addComponent(advertencia5)
                    .addComponent(advertencia4)
                    .addComponent(advertencia3)
                    .addComponent(advertencia2)
                    .addComponent(advertencia1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(hintedText2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hintedText9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxSuggestion2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxSuggestion3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hintedText1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hintedText2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia5)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hintedText3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia6)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hintedText4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(0, 0, 0)
                .addComponent(advertencia2)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(hintedText6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(advertencia7)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(hintedText7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboBoxSuggestion2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(hintedText9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(0, 0, 0)
                .addComponent(advertencia3)
                .addGap(0, 0, 0)
                .addComponent(advertencia4)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboBoxSuggestion3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advertencia1;
    private javax.swing.JLabel advertencia2;
    private javax.swing.JLabel advertencia3;
    private javax.swing.JLabel advertencia4;
    private javax.swing.JLabel advertencia5;
    private javax.swing.JLabel advertencia6;
    private javax.swing.JLabel advertencia7;
    private UI.Misc.ComboBoxSuggestion comboBoxSuggestion1;
    private UI.Misc.ComboBoxSuggestion comboBoxSuggestion2;
    private UI.Misc.ComboBoxSuggestion comboBoxSuggestion3;
    private UI.Date.DateChooser dateChooser1;
    private UI.Misc.HintedText hintedText1;
    private UI.Misc.HintedText hintedText2;
    private UI.Misc.HintedText hintedText3;
    private UI.Misc.HintedText hintedText4;
    private UI.Misc.HintedText hintedText6;
    private UI.Misc.HintedText hintedText7;
    private UI.Misc.HintedText hintedText9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
