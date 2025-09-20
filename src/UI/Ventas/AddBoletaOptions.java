package UI.Ventas;

import Sistema.Boleta;
import Sistema.Cliente;
import Sistema.Empleado;
import Sistema.Local;
import Sistema.Producto;
import UI.Interface.Application;
import UI.Misc.ScrollBar;
import UI.Table.TableButtonCellEditor;
import UI.Table.TableButtonCellRender;
import UI.Table.TableButtonEvent;
import UI.Table.TableCantidadCellEditor;
import UI.Table.TableCantidadCellRender;
import UI.Table.TableCantidadEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AddBoletaOptions extends javax.swing.JPanel {   
    private final DecimalFormat decimal_format = new DecimalFormat("#0.00");
    private AddBoleta parent_frame;
    private double subtotal;
    private double descuento_proveedor;
    private double post_descuento;
    private double descuento_membresia_porcentaje;
    private double descuento_membresia;
    private double descuento_total;
    private double importe;
    private Application frame;
    private ArrayList<Cliente> clientes;
    private ArrayList<Empleado> empleados_local;
    private ArrayList<Producto> productos_local;
    private ArrayList<Local> locales;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidades;
    private ArrayList<Integer> producto_in_list;
    private ArrayList<Boolean> inList;
    private TableCantidadCellRender render;    
    private TableCantidadCellEditor editor;
    
    public AddBoletaOptions() {
        initComponents();
        
        clientes = new ArrayList<>();
        empleados_local = new ArrayList<>();
        productos_local = new ArrayList<>();
        locales = new ArrayList<>();
        productos = new ArrayList<>();
        cantidades = new ArrayList<>();
        producto_in_list = new ArrayList<>();
        inList = new ArrayList<>();
        
        subtotal = 0;
        descuento_proveedor = 0;
        descuento_membresia = 0;
        descuento_total = 0;
        importe = 0;
        
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        
        TableCantidadEvent event = new TableCantidadEvent() {
            @Override
            public void onMinus(int row) {
                int cantidad = cantidades.get(row);
                
                if (cantidad > 1) {
                    cantidades.set(row, cantidad - 1);
                    updateRendererEditor(row);
                    cantidadUpdated(row, -1);
                }
            }

            @Override
            public void onPlus(int row) {
                int cantidad = cantidades.get(row);
                
                if (cantidad < productos.get(row).getCantidad()) {
                    cantidades.set(row, cantidad + 1);
                    updateRendererEditor(row);
                    cantidadUpdated(row, 1);
                }
            }
        };
        
        TableButtonEvent eliminar_event = (int row) -> {
            Producto producto = productos.get(row);
            double precio = producto.getPrecio() * cantidades.get(row);
            
            subtotal -= precio;
            descuento_proveedor -= frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * precio;
            post_descuento -= precio - frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * precio;
            
            descuentoUpdated();
            importeUpdated();
            
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            
            table.removeRow(row);
            productos.remove(row);
            cantidades.remove((int) row);
            render.getCantidades().remove((int) row);
            editor.getCantidades().remove((int) row);
            inList.set(producto_in_list.get(row), false);
            producto_in_list.remove((int) row);
        };
        
        render = new TableCantidadCellRender();
        editor = new TableCantidadCellEditor(event) {
            @Override
            public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
                getPanelCantidad().initEvent(event, row, productos.get(row).getCantidad());
                getPanelCantidad().setBackground(new Color(230, 230, 230, 255));
                getPanelCantidad().getNumber().setText(String.valueOf(getCantidades().get(row)));
                return getPanelCantidad();
            }
        };
        
        table.getColumnModel().getColumn(0).setCellRenderer(new TableButtonCellRender("eliminar", Color.WHITE, new Color(0, 0, 0, 0), new Color(230, 230, 230, 255)));
        table.getColumnModel().getColumn(0).setCellEditor(new TableButtonCellEditor(eliminar_event, "eliminar"));
        
        table.getColumnModel().getColumn(3).setCellRenderer(render);
        table.getColumnModel().getColumn(3).setCellEditor(editor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        
        comboBox1.addActionListener((ActionEvent e) -> {
            int selected = comboBox1.getSelectedIndex();
            comboBox2.removeAllItems();
            comboBox4.removeAllItems();
            
            empleados_local.clear();
            productos_local.clear();
            productos.clear();
            cantidades.clear();
            producto_in_list.clear();
            inList.clear();
            render.getCantidades().clear();
            editor.getCantidades().clear();
            table.clearTable();
            
            subtotal = 0;
            descuento_proveedor = 0;
            post_descuento = 0;
            
            descuentoUpdated();
            importeUpdated();
            localUpdated(selected);
        });
        
        comboBox3.addActionListener((ActionEvent e) -> {
            int selected = comboBox3.getSelectedIndex();
            clienteChanged(selected);
            descuentoUpdated();
            importeUpdated();
        });
    }

    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    public void setParent(AddBoleta parent_frame) {
        this.parent_frame = parent_frame;
    }
    
    public final void setData() {
        label1.setText(UUID.randomUUID().toString());
        label2.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        
        for (Local local : frame.getLocalesList()) {
            locales.add(local);
            comboBox1.addItem(local.getDireccion());
        }
        
        for (Cliente cliente : frame.getClientesList()) {
            clientes.add(cliente);
            comboBox3.addItem(cliente.getNombresApellidos());
        }
        
        clienteChanged(0);
        importeUpdated();
    }
    
    public Boleta getBoleta() {
        Boleta boleta = new Boleta();
        boleta.setId(label1.getText());
        boleta.setLocal(locales.get(comboBox1.getSelectedIndex()).getCodigo());
        boleta.setEmpleado(empleados_local.get(comboBox2.getSelectedIndex()).getDni());
        
        try {
            boleta.setFecha(new SimpleDateFormat("dd-MM-yyyy").parse(label2.getText()));
        } 
        catch (ParseException ex) {
        }
        
        boleta.setProductos(getProductosCantidad());
        boleta.setComentario(hintedText1.getText());
        boleta.setSubtotal(subtotal);
        boleta.setDescuento(descuento_total);
        boleta.setImporteFinal(importe);
        boleta.setCliente(clientes.get(comboBox3.getSelectedIndex()).getDni());
        
        return boleta;
    }
    
    public ArrayList<Object[]> getProductosCantidad() {
        ArrayList<Object[]> productos_cantidad = new ArrayList<>();
        
        for (int i = 0; i < productos.size(); i++) {
            productos_cantidad.add(new Object[] { productos.get(i).getCodigo(), cantidades.get(i) } );
        }
        
        return productos_cantidad;
    }
    
    public void importeUpdated() {
        label3.setText("S/ " + decimal_format.format(subtotal));
        label4.setText("S/ " + decimal_format.format(descuento_total));
        label5.setText("S/ " + decimal_format.format(importe));
    }
    
    public void localUpdated(int index) {
        for (String empleado : frame.getLocalesList().get(index).getEmpleados()) {
            empleados_local.add(frame.findEmpleadoByDNI(empleado));
            comboBox2.addItem(frame.findEmpleadoByDNI(empleado).getNombresApellidos());
        }
        
        for (int producto_id : frame.getLocalesList().get(index).getProductos()) {
            productos_local.add(frame.findProductoByCodigo(producto_id));
            String item = frame.findProductoByCodigo(producto_id).getNombre();
            if (frame.findProductoByCodigo(producto_id).getCantidad() == 0) item += " [AGOTADO]";
            comboBox4.addItem(item);
            inList.add(false);
        }
    }
    
    public void descuentoUpdated() {
        descuento_membresia = post_descuento * descuento_membresia_porcentaje;
        descuento_total = descuento_proveedor + descuento_membresia;
        importe = post_descuento - descuento_membresia;
    }
    
    public void cantidadUpdated(int row, int amount) {
        Producto producto = productos.get(row);
        double precio = producto.getPrecio();
        int cantidad = cantidades.get(row);
        table.setValueAt(precio * cantidad, row, 4);
        
        if (amount == -1) {
            subtotal -= precio;
            descuento_proveedor -= frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
            post_descuento -= producto.getPrecio() - frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
        }
        else if (amount == 1) {
            subtotal += precio;
            descuento_proveedor += frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
            post_descuento += producto.getPrecio() - frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
        }
        
        descuentoUpdated();
        importeUpdated();
    }
    
    public void clienteChanged(int index) {
        String membresia = frame.getClientesList().get(index).getMembresia();

        if (null != membresia) switch (membresia) {
            case "Ninguno" -> descuento_membresia_porcentaje = 0;
            case "Plata" -> descuento_membresia_porcentaje = 0.05;
            case "Oro" -> descuento_membresia_porcentaje = 0.10;
            case "Diamante" -> descuento_membresia_porcentaje = 0.15;
            default -> {
            }
        }
    }
    
    public void updateRendererEditor(int row) {       
        render.setCantidad(row, cantidades.get(row));
        editor.setCantidad(row, cantidades.get(row));
    }
    
    public void updateClientes() {
        clientes.add(frame.getClientesList().get(frame.getClientesList().size() - 1));
        comboBox3.addItem(clientes.get(clientes.size() - 1).getNombresApellidos());
        comboBox3.setSelectedIndex(comboBox3.getItemCount() - 1);
        clienteChanged(comboBox3.getSelectedIndex());
        descuentoUpdated();
        importeUpdated();
    }
    
    public boolean isTableEmpty() {
        return table.getRowCount() == 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBox1 = new UI.Misc.ComboBoxSuggestion();
        comboBox2 = new UI.Misc.ComboBoxSuggestion();
        comboBox3 = new UI.Misc.ComboBoxSuggestion();
        comboBox4 = new UI.Misc.ComboBoxSuggestion();
        agregar = new UI.Misc.CustomButton();
        hintedText1 = new UI.Misc.HintedText();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        label5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        table = new UI.Table.Table();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        agregar_cliente = new UI.Misc.CustomButton();

        setBackground(new java.awt.Color(242, 242, 242));
        setOpaque(false);

        comboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N

        comboBox2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N

        comboBox3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N

        comboBox4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N

        agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus2.png"))); // NOI18N
        agregar.setBorderColor(new java.awt.Color(242, 242, 242));
        agregar.setColor(new java.awt.Color(51, 51, 51));
        agregar.setColorClick(new java.awt.Color(204, 204, 204));
        agregar.setColorOver(new java.awt.Color(102, 102, 102));
        agregar.setRadius(50);
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        hintedText1.setBackground(new java.awt.Color(242, 242, 242));
        hintedText1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 0, 13), new java.awt.Color(242, 242, 242))); // NOI18N
        hintedText1.setHint("Comentario");

        label1.setForeground(new java.awt.Color(102, 102, 102));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label1.setText("Código");

        label2.setForeground(new java.awt.Color(102, 102, 102));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label2.setText("Fecha");

        label3.setForeground(new java.awt.Color(102, 102, 102));
        label3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label3.setText("Subtotal");

        label4.setForeground(new java.awt.Color(102, 102, 102));
        label4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label4.setText("Descuento");

        label5.setForeground(new java.awt.Color(102, 102, 102));
        label5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label5.setText("Importe");

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Local");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Fecha");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Cliente");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Productos");

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Subtotal");

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Atendido por");

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Descuento");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Importe a pagar");

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Comentario");

        jLabel19.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Sistema Ventas");

        jLabel20.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("RUC 20584691237");

        jLabel21.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Sistema Ventas S.A.C.");

        jLabel22.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Av. Huaylas 123, Chorrillos, Lima");

        spTable.setBorder(null);
        spTable.setOpaque(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Nombre", "PU", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setOpaque(false);
        spTable.setViewportView(table);

        agregar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus2.png"))); // NOI18N
        agregar_cliente.setBorderColor(new java.awt.Color(242, 242, 242));
        agregar_cliente.setColor(new java.awt.Color(51, 51, 51));
        agregar_cliente.setColorClick(new java.awt.Color(204, 204, 204));
        agregar_cliente.setColorOver(new java.awt.Color(102, 102, 102));
        agregar_cliente.setRadius(50);
        agregar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(225, 225, 225))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(234, 234, 234))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(176, 176, 176))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(54, 54, 54))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(39, 39, 39))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addContainerGap(308, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(210, 210, 210))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(agregar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                                    .addComponent(comboBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(15, 15, 15))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(hintedText1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label1)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(label2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(agregar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(label3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(label4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hintedText1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        int selected = comboBox4.getSelectedIndex();
        
        if (!inList.get(selected) && !((String) comboBox4.getSelectedItem()).contains("[AGOTADO]")) {
            Producto producto = productos_local.get(selected);
            productos.add(producto);
            cantidades.add(1);
            table.addRow(new Object[] { "", producto.getNombre(), producto.getPrecio(), "", producto.getPrecio() });
            render.getCantidades().add(1);
            editor.getCantidades().add(1);
            inList.set(selected, true);
            producto_in_list.add(selected);

            subtotal += producto.getPrecio();
            descuento_proveedor += frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
            post_descuento += producto.getPrecio() - frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();

            descuentoUpdated();
            importeUpdated();
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void agregar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_clienteActionPerformed
        UI.Clientes.AddCliente obj = new UI.Clientes.AddCliente() {
            @Override
            public void quit() {
                parent_frame.setEnabled(true);
                this.dispose();
            }
            
            @Override
            public void updateCliente() {
                updateClientes();
            }
        };
        obj.setApplication(frame);
        parent_frame.setEnabled(false);
        obj.setVisible(true);
    }//GEN-LAST:event_agregar_clienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.CustomButton agregar;
    private UI.Misc.CustomButton agregar_cliente;
    private UI.Misc.ComboBoxSuggestion comboBox1;
    private UI.Misc.ComboBoxSuggestion comboBox2;
    private UI.Misc.ComboBoxSuggestion comboBox3;
    private UI.Misc.ComboBoxSuggestion comboBox4;
    private UI.Misc.HintedText hintedText1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JScrollPane spTable;
    private UI.Table.Table table;
    // End of variables declaration//GEN-END:variables
}
