package UI.Interface;

import Conexiones.*;
import Sistema.*;
import UI.Bienvenido.Bienvenido;
import UI.Clientes.Clientes;
import UI.Configuracion.Configuracion;
import UI.Empleados.Empleados;
import UI.Locales.Locales;
import UI.Productos.Productos;
import UI.Proveedores.Proveedores;
import UI.Ventas.Ventas;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JComponent;

public class Application extends javax.swing.JFrame {
    private String usuario;
    
    private ArrayList<Empleado> empleados_list = new ArrayList<>();
    private ArrayList<Producto> productos_list = new ArrayList<>();
    private ArrayList<Proveedor> proveedores_list = new ArrayList<>();
    private ArrayList<Cliente> clientes_list = new ArrayList<>();
    private ArrayList<Empresa> empresas_list = new ArrayList<>();
    private ArrayList<Boleta> boletas_list = new ArrayList<>();
    private ArrayList<Factura> facturas_list = new ArrayList<>();
    private ArrayList<Local> locales_list = new ArrayList<>();
    
    private Bienvenido bienvenido;
    private Empleados empleados;
    private Productos productos;
    private Proveedores proveedores;
    private Clientes clientes;
    private Ventas ventas;
    private Locales locales;
    private Configuracion configuracion;
    
    @SuppressWarnings({"CallToPrintStackTrace", "LeakingThisInConstructor"})
    public Application() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        
        try {
            empleados_list = Procedimientos.getEmpleados();
            productos_list = Procedimientos.getProductos();
            proveedores_list = Procedimientos.getProveedores();
            clientes_list = Procedimientos.getClientes();
            empresas_list = Procedimientos.getEmpresas();
            boletas_list = Procedimientos.getBoletas(this);
            facturas_list = Procedimientos.getFacturas(this);
            locales_list = Procedimientos.getLocales(this);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        bienvenido = new Bienvenido(this);
        empleados = new Empleados(this);
        productos = new Productos(this);
        proveedores = new Proveedores(this);
        clientes = new Clientes(this);
        ventas = new Ventas(this);
        locales = new Locales(this);
        configuracion = new Configuracion(this);
        
        menu.initMoving(Application.this);
        
        menu.addEventMenuSelected((var index) -> {
            switch (index) {
                case 1 -> setForm(bienvenido);
                case 2 -> setForm(empleados);
                case 3 -> setForm(productos);
                case 4 -> setForm(proveedores);
                case 5 -> setForm(clientes);
                case 6 -> setForm(ventas);
                case 7 -> setForm(locales);
                case 10 -> setForm(configuracion);
                case 11 -> {
                    UI.Login.Main obj = new UI.Login.Main();
                    obj.setVisible(true);
                    Conexion.closeConexion();
                    dispose();
                }
                case 12 -> {
                    Conexion.closeConexion();
                    dispose();
                }
                default -> {
                }
            }
        });

        setForm(bienvenido);
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public final void setForm(JComponent component) {
        mainPanel.removeAll();
        mainPanel.add(component);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
    
    public Bienvenido getBienvenido() {
        return bienvenido;
    }
    
    public Empleados getEmpleados() {
        return empleados;
    }
    
    public Productos getProductos() {
        return productos;
    }
    
    public Proveedores getProveedores() {
        return proveedores;
    }
    
    public Clientes getClientes() {
        return clientes;
    }
    
    public Ventas getVentas() {
        return ventas;
    }
    
    public Locales getLocales() {
        return locales;
    }
    
    public UI.Menu.Menu getMenu() {
        return menu;
    }
    
    public ArrayList<Empleado> getEmpleadosList() {
        return empleados_list;
    }
    
    public ArrayList<Producto> getProductosList() {
        return productos_list;
    }
    
    public ArrayList<Proveedor> getProveedoresList() {
        return proveedores_list;
    }
    
    public ArrayList<Cliente> getClientesList() {
        return clientes_list;
    }
    
    public ArrayList<Empresa> getEmpresasList() {
        return empresas_list;
    }
    
    public ArrayList<Boleta> getBoletasList() {
        return boletas_list;
    }
    
    public ArrayList<Factura> getFacturasList() {
        return facturas_list;
    }

    public ArrayList<Local> getLocalesList() {
        return locales_list;
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int random_index = random.nextInt(list.size());
        return list.get(random_index);
    }
    
    public int countClientesMembresia(String membresia) {
        int count = 0;
        for (Cliente cliente : clientes_list) {
            if (cliente.getMembresia().equals(membresia)) {
                count++;
            }
        }
        return count;
    }

    public Empleado findEmpleadoByDNI(String dni) {
        return empleados_list.stream().filter(empleado -> empleado.getDni().equals(dni)).findFirst().orElse(null);
    }
    
    public Producto findProductoByCodigo(int id) {
        return productos_list.stream().filter(producto -> producto.getCodigo() == id).findFirst().orElse(null);
    }
    
    public Proveedor findProveedorByCodigo(int codigo) {
        return proveedores_list.stream().filter(proveedor -> proveedor.getCodigo() == codigo).findFirst().orElse(null);
    }
    
    public Cliente findClienteByDNI(String dni) {
        return clientes_list.stream().filter(cliente -> cliente.getDni().equals(dni)).findFirst().orElse(null);
    }
    
    public Empresa findEmpresaByRUC(String ruc) {
        return empresas_list.stream().filter(empresa -> empresa.getRUC().equals(ruc)).findFirst().orElse(null);
    }
    
    public Boleta findBoletaById(String id) {
        return boletas_list.stream().filter(boleta -> boleta.getId().equals(id)).findFirst().orElse(null);
    }
    
    public Factura findFacturaById(String id) {
        return facturas_list.stream().filter(factura -> factura.getId().equals(id)).findFirst().orElse(null);
    }
    
    public Local findLocalByCodigo(int id) {
        return locales_list.stream().filter(local -> local.getCodigo() == id).findFirst().orElse(null);
    }
    
    public boolean compareFilter(int a, int b, String comparator) {
        return switch (comparator) {
            case ">" -> a > b;
            case ">=" -> a >= b;
            case "<" -> a < b;
            case "<=" -> a <= b;
            case "=" -> a == b;
            case "!=" -> a != b;
            default -> false;
        };
    }
    
    public boolean compareFilter(double a, double b, String comparator) {
        return switch (comparator) {
            case ">" -> a > b;
            case ">=" -> a >= b;
            case "<" -> a < b;
            case "<=" -> a <= b;
            case "=" -> a == b;
            case "!=" -> a != b;
            default -> false;
        };
    }
    
    public boolean compareFilter(String a, String b, String comparator) {
        if (!(comparator.equals("=") || comparator.equals("!="))) return false;
        
        if (comparator.equals("=")) return a.contains(b);
        else return !a.contains(b);
    }
    
    public boolean compareFilter(Date a, Date b, String comparator) {
        int compared = a.compareTo(b);
        
        return switch (comparator) {
            case ">" -> compared > 0;
            case ">=" -> compared >= 0;
            case "<" -> compared < 0;
            case "<=" -> compared <= 0;
            case "=" -> compared == 0;
            case "!=" -> compared != 0;
            default -> false;
        };
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        border1 = new UI.Misc.SimpleBackground();
        menu = new UI.Menu.Menu();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        border1.setBackground(new java.awt.Color(242, 242, 242));

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout border1Layout = new javax.swing.GroupLayout(border1);
        border1.setLayout(border1Layout);
        border1Layout.setHorizontalGroup(
            border1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(border1Layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE))
        );
        border1Layout.setVerticalGroup(
            border1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, border1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(border1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(border1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(border1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new Application().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.SimpleBackground border1;
    private javax.swing.JPanel mainPanel;
    private UI.Menu.Menu menu;
    // End of variables declaration//GEN-END:variables
}
