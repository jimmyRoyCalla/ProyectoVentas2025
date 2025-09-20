package UI.Bienvenido;

import Conexiones.Procedimientos;
import UI.Interface.Application;
import java.sql.SQLException;

public class Bienvenido extends javax.swing.JPanel {
    Application frame;
    
    public Bienvenido(Application frame) {
        initComponents();
        setOpaque(false);
        
        this.frame = frame;
        
        updateEmpleadoMes();
        updateProductoPopular();
        updateProveedoresHoy();
        updateCountMembresias();
        updateTotalVentasGanancias();
        updateLocalesPopulares();
    }
    
    public final void updateEmpleadoMes() {
        if (frame.getClientesList().isEmpty()) card11.getText().setText("No hay");
        else card11.getText().setText(Application.getRandomItem(frame.getClientesList()).getNombresApellidos());
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public final void updateProductoPopular() {
        if (frame.getBoletasList().isEmpty() && frame.getFacturasList().isEmpty()) {
            card21.getText1().setText("No hay");
            card21.getText2().setText("0 vendidos");
        }
        else {
            try {
                Object[] producto = Procedimientos.getProductoMasVendido();
                card21.getText1().setText(frame.findProductoByCodigo((int) producto[0]).getNombre());
                card21.getText2().setText(String.valueOf(producto[1]) + " vendidos");
            } 
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public final void updateProveedoresHoy() {
        if (frame.getProveedoresList().isEmpty()) {
            card31.getText1().setText("No hay");
            card31.getText2().setText("No hay");
        }
        else {
            card31.getText1().setText(Application.getRandomItem(frame.getProveedoresList()).getNombre());

            if (frame.getProveedoresList().size() > 1) {
                do {
                    card31.getText2().setText(Application.getRandomItem(frame.getProveedoresList()).getNombre());
                } while (card31.getText1().equals(card31.getText2()));
            }
        }
    }
    
    public final void updateCountMembresias() {
        card41.getText1().setText(String.valueOf(frame.countClientesMembresia("Plata")));
        card41.getText2().setText(String.valueOf(frame.countClientesMembresia("Oro")));
        card41.getText3().setText(String.valueOf(frame.countClientesMembresia("Diamante")));
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public final void updateTotalVentasGanancias() {
        if (frame.getBoletasList().isEmpty() && frame.getFacturasList().isEmpty()) {
            card51.getText1().setText("0");
            card51.getText2().setText("S/ 0.00");
        }
        else {
            try {
                Object[] ganancias = Procedimientos.getGananciasTotal();
                card51.getText1().setText(String.valueOf(ganancias[0]));
                card51.getText2().setText("S/ " + String.valueOf(ganancias[1]));
            } 
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public final void updateLocalesPopulares() {
        if (frame.getLocalesList().isEmpty()) {
            card61.getText1().setText("No hay");
            card61.getText2().setText("No hay");
        }
        else {
            card61.getText1().setText(Application.getRandomItem(frame.getLocalesList()).getDireccion());
            
            if (frame.getLocalesList().size() > 1) {
                do {
                    card61.getText2().setText(Application.getRandomItem(frame.getLocalesList()).getDireccion());
                } while (card61.getText1().equals(card61.getText2()));
            }
        }  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card11 = new UI.Bienvenido.Card1();
        card21 = new UI.Bienvenido.Card2();
        card31 = new UI.Bienvenido.Card3();
        card41 = new UI.Bienvenido.Card4();
        card51 = new UI.Bienvenido.Card5();
        card61 = new UI.Bienvenido.Card6();
        generalBorder1 = new UI.Bienvenido.BienvenidoBorder();

        setBackground(new java.awt.Color(255, 255, 255));

        card11.setColor1(new java.awt.Color(0, 180, 60));
        card11.setColor2(new java.awt.Color(4, 160, 43));

        card21.setColor1(new java.awt.Color(51, 51, 255));
        card21.setColor2(new java.awt.Color(0, 0, 153));

        card31.setColor1(new java.awt.Color(255, 51, 0));
        card31.setColor2(new java.awt.Color(153, 0, 0));

        card41.setColor1(new java.awt.Color(204, 204, 0));
        card41.setColor2(new java.awt.Color(102, 102, 0));

        card51.setColor1(new java.awt.Color(0, 204, 204));
        card51.setColor2(new java.awt.Color(0, 153, 153));

        card61.setColor1(new java.awt.Color(255, 153, 51));
        card61.setColor2(new java.awt.Color(164, 66, 0));

        generalBorder1.setColor1(new java.awt.Color(102, 102, 102));
        generalBorder1.setColor2(new java.awt.Color(242, 242, 242));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(card11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(generalBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(generalBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Bienvenido.Card1 card11;
    private UI.Bienvenido.Card2 card21;
    private UI.Bienvenido.Card3 card31;
    private UI.Bienvenido.Card4 card41;
    private UI.Bienvenido.Card5 card51;
    private UI.Bienvenido.Card6 card61;
    private UI.Bienvenido.BienvenidoBorder generalBorder1;
    // End of variables declaration//GEN-END:variables
}
