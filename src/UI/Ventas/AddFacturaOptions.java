package UI.Ventas;

import Sistema.Empleado;
import Sistema.Empresa;
import Sistema.Factura;
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

public class AddFacturaOptions extends javax.swing.JPanel {   
    private final DecimalFormat decimal_format = new DecimalFormat("#0.00");
    private AddFactura parent_frame;
    private static final double IGV = 0.18;
    private double subtotal;
    private double impuestos;
    private double descuento;
    private double importe;
    private Application frame;
    private ArrayList<Empresa> empresas;
    private ArrayList<Empleado> empleados_local;
    private ArrayList<Producto> productos_local;
    private ArrayList<Local> locales;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidades;
    private ArrayList<Integer> producto_in_list;
    private ArrayList<Boolean> inList;
    private TableCantidadCellRender render;    
    private TableCantidadCellEditor editor;
    
    public AddFacturaOptions() {
        initComponents();
        
        empresas = new ArrayList<>();
        empleados_local = new ArrayList<>();
        productos_local = new ArrayList<>();
        locales = new ArrayList<>();
        cantidades = new ArrayList<>();
        productos = new ArrayList<>();
        producto_in_list = new ArrayList<>();
        inList = new ArrayList<>();
        
        subtotal = 0;
        impuestos = 0;
        descuento = 0;
        importe = 0;
        
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        
        TableCantidadEvent cantidad_event = new TableCantidadEvent() {
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
        
        TableButtonEvent delete_event = (int row) -> {
            Producto producto = productos.get(row);
            double precio = producto.getPrecio() * cantidades.get(row);
            
            subtotal -= precio;
            impuestos = subtotal * IGV;
            descuento -= frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * precio;
            importe = subtotal + impuestos - descuento;
            
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
        editor = new TableCantidadCellEditor(cantidad_event) {
            @Override
            public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
                getPanelCantidad().initEvent(cantidad_event, row, productos.get(row).getCantidad());
                getPanelCantidad().setBackground(Color.WHITE);
                getPanelCantidad().getNumber().setText(String.valueOf(getCantidades().get(row)));
                return getPanelCantidad();
            }
        };
        
        table.getColumnModel().getColumn(0).setPreferredWidth(32);
        table.getColumnModel().getColumn(0).setCellRenderer(new TableButtonCellRender("eliminar", Color.WHITE, new Color(0, 0, 0, 0), new Color(230, 230, 230, 255)));
        table.getColumnModel().getColumn(0).setCellEditor(new TableButtonCellEditor(delete_event, "eliminar"));
        
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        
        table.getColumnModel().getColumn(3).setCellRenderer(render);
        table.getColumnModel().getColumn(3).setCellEditor(editor);
        
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
            impuestos = 0;
            descuento = 0;
            importe = 0;
            
            importeUpdated();
            localUpdated(selected);
        });
    }

    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    public void setParent(AddFactura parent_frame) {
        this.parent_frame = parent_frame;
    }
    
    public final void setData() {
        label1.setText(UUID.randomUUID().toString());
        label2.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        
        for (Local local : frame.getLocalesList()) {
            locales.add(local);
            comboBox1.addItem(local.getDireccion());
        }
        
        for (Empresa empresa : frame.getEmpresasList()) {
            empresas.add(empresa);
            comboBox3.addItem(empresa.getNombre());
        }
        
        importeUpdated();
        localUpdated(0);
    }
    
    public Factura getFactura() {
        Factura factura = new Factura();
        factura.setId(label1.getText());
        factura.setLocal(locales.get(comboBox1.getSelectedIndex()).getCodigo());
        factura.setEmpleado(empleados_local.get(comboBox2.getSelectedIndex()).getDni());
        
        try {
            factura.setFecha(new SimpleDateFormat("dd-MM-yyyy").parse(label2.getText()));
        } 
        catch (ParseException ex) {
        }
        
        factura.setProductos(getProductosCantidad());
        factura.setComentario(hintedText1.getText());
        factura.setSubtotal(subtotal);
        factura.setImpuestos(impuestos);
        factura.setDescuento(descuento);
        factura.setImporteFinal(importe);
        factura.setEmpresa(empresas.get(comboBox3.getSelectedIndex()).getRUC());
        
        return factura;
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
        label4.setText("S/ " + decimal_format.format(impuestos));
        label5.setText("S/ " + decimal_format.format(descuento));
        label6.setText("S/ " + decimal_format.format(importe));
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
    
    public void cantidadUpdated(int row, int amount) {
        Producto producto = productos.get(row);
        double precio = producto.getPrecio();
        int cantidad = cantidades.get(row);
        table.setValueAt(precio * cantidad, row, 4);
        
        if (amount == -1) {
            subtotal -= precio;
            impuestos = subtotal * IGV;
            descuento -= frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
            importe = subtotal + impuestos - descuento;
        }
        else if (amount == 1) {
            subtotal += precio;
            impuestos = subtotal * IGV;
            descuento += frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
            importe = subtotal + impuestos - descuento;
        }
        
        importeUpdated();
    }
    
    public void updateRendererEditor(int row) {       
        render.setCantidad(row, cantidades.get(row));
        editor.setCantidad(row, cantidades.get(row));
    }
    
    public void updateEmpresas() {
        empresas.add(frame.getEmpresasList().get(frame.getEmpresasList().size() - 1));
        comboBox3.addItem(empresas.get(empresas.size() - 1).getNombre());
        comboBox3.setSelectedIndex(comboBox3.getItemCount() - 1);
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
        label6 = new javax.swing.JLabel();
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
        jLabel12 = new javax.swing.JLabel();
        agregar_empresa = new UI.Misc.CustomButton();

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
        label4.setText("Impuestos");

        label5.setForeground(new java.awt.Color(102, 102, 102));
        label5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label5.setText("Descuento");

        label6.setForeground(new java.awt.Color(102, 102, 102));
        label6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label6.setText("Importe");

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
        jLabel4.setText("Empresa");

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

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Impuestos");

        agregar_empresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus2.png"))); // NOI18N
        agregar_empresa.setBorderColor(new java.awt.Color(242, 242, 242));
        agregar_empresa.setColor(new java.awt.Color(51, 51, 51));
        agregar_empresa.setColorClick(new java.awt.Color(204, 204, 204));
        agregar_empresa.setColorOver(new java.awt.Color(102, 102, 102));
        agregar_empresa.setRadius(50);
        agregar_empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_empresaActionPerformed(evt);
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 210, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addGap(210, 210, 210))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(agregar_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                                .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hintedText1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addGap(176, 176, 176))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(54, 54, 54)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(label1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(label2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBox3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(agregar_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(label3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(label4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(label5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(label6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))
                    .addComponent(agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            impuestos = subtotal * IGV;
            descuento += frame.findProveedorByCodigo(producto.getProveedor()).getDescuento() * producto.getPrecio();
            importe = subtotal + impuestos - descuento;
            
            importeUpdated();
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void agregar_empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_empresaActionPerformed
        UI.Clientes.AddEmpresa obj = new UI.Clientes.AddEmpresa() {
            @Override
            public void quit() {
                parent_frame.setEnabled(true);
                this.dispose();
            }
            
            @Override
            public void updateEmpresa() {
                updateEmpresas();
            }
        };
        obj.setApplication(frame);
        parent_frame.setEnabled(false);
        obj.setVisible(true);
    }//GEN-LAST:event_agregar_empresaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.CustomButton agregar;
    private UI.Misc.CustomButton agregar_empresa;
    private UI.Misc.ComboBoxSuggestion comboBox1;
    private UI.Misc.ComboBoxSuggestion comboBox2;
    private UI.Misc.ComboBoxSuggestion comboBox3;
    private UI.Misc.ComboBoxSuggestion comboBox4;
    private UI.Misc.HintedText hintedText1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel label6;
    private javax.swing.JScrollPane spTable;
    private UI.Table.Table table;
    // End of variables declaration//GEN-END:variables
}
