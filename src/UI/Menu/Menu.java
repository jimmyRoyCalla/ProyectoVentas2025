package UI.Menu;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

public class Menu extends javax.swing.JPanel {
    private EventMenuSelected event;
    
    public Menu() {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        Init();
    }
    
    private void Init() {
        listMenu1.addItem(new ModelMenu("", "", ModelMenu.MenuType.EMPTY));
        listMenu1.addItem(new ModelMenu("bienvenido", "Bienvenido", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("empleados", "Empleados", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("productos", "Productos", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("proveedores", "Proveedores", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("clientes", "Clientes", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("ventas", "Ventas", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("locales", "Locales", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("", "", ModelMenu.MenuType.EMPTY));
        listMenu1.addItem(new ModelMenu("", "", ModelMenu.MenuType.EMPTY));
        listMenu1.addItem(new ModelMenu("config", "Configuración", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("logout", "Logout", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("salir", "Salir", ModelMenu.MenuType.MENU));
        listMenu1.addItem(new ModelMenu("", "", ModelMenu.MenuType.EMPTY));
    }
    
    public UI.Menu.ListMenu<String> getListMenu() {
        return listMenu1;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        listMenu1 = new UI.Menu.ListMenu<>();

        panelMoving.setOpaque(false);

        Title.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo.png"))); // NOI18N
        Title.setText("Sistema Ventas");

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovingLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(Title)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }
    
    @Override
    protected void paintChildren(Graphics graphics) {
        Graphics2D g2 = (Graphics2D)graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(graphics);
    }
    
    private int x;
    private int y;
    
    public void initMoving(JFrame frame) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        });
        
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                frame.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    private UI.Menu.ListMenu<String> listMenu1;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
}
