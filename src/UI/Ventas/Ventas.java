package UI.Ventas;

import Conexiones.Procedimientos;
import Sistema.Boleta;
import Sistema.Factura;
import UI.Interface.Advertencia;
import UI.Interface.Application;
import UI.Table.TableActionCellEditor;
import UI.Table.TableActionCellRender;
import UI.Table.TableActionEvent;
import UI.Table.TableFilter;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Ventas extends javax.swing.JPanel {
    private SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
    private Color color1;
    private Color color2;
    private final Application frame;
    private final TableRowSorter st1;
    private final TableRowSorter st2;
    private Boletas boletas;
    private Facturas facturas;
    private UI.Table.Table table_boletas;
    private UI.Table.Table table_facturas;
    private int selectedTable;
    
    public Ventas(Application frame) {
        initComponents();
        setOpaque(false);
        
        color1 = Color.WHITE;
        color2 = Color.GRAY;
        
        this.frame = frame;
        selectedTable = 1;
        boletas = new Boletas();
        facturas = new Facturas();
        
        st1 = new TableRowSorter(boletas.getTable().getModel());
        table_boletas = boletas.getTable();
        table_boletas.setRowSorter(st1);
        
        st2 = new TableRowSorter(facturas.getTable().getModel());
        table_facturas = facturas.getTable();
        table_facturas.setRowSorter(st2);
        
        comboBoxSuggestion1.addActionListener((ActionEvent e) -> {
            String selectedOption = (String) comboBoxSuggestion1.getSelectedItem();
            
            if (selectedOption.equals("Boletas")) {
                selectedTable = 1;
                setViewableTable(boletas);
            }
            else if (selectedOption.equals("Facturas")) {
                selectedTable = 2;
                setViewableTable(facturas);
            }
        });
        
        search1.getSearchText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String[] filters = search1.getFilters();
                    
                    if (selectedTable == 1) st1.setRowFilter(new TableFilter(filters) {
                        @Override
                        public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                            return filterRowBoletas(entry, this, filters);
                        }
                    });
                    
                    else st2.setRowFilter(new TableFilter(filters) {
                        @Override
                        public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                            return filterRowFacturas(entry, this, filters);
                        }
                    });
                }
            }
        });
        
        TableActionEvent event_boleta = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                EditBoleta obj = new EditBoleta();
                obj.setApplication(frame);
                obj.setIndex(row);
                obj.initData(frame.findBoletaById((String) table_boletas.getValueAt(row, 0)));
                frame.setEnabled(false);
                obj.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (table_boletas.isEditing()) {
                    table_boletas.getCellEditor().stopCellEditing();
                }
                
                Advertencia obj = new Advertencia();
                frame.setEnabled(false);
                obj.setVisible(true);
                
                obj.getEliminar().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                        try {
                            deleteBoleta(row);
                        } 
                        catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error de referencias de la boleta.", "Error de Procedimiento", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        obj.dispose();
                        frame.setEnabled(true);
                    }
                });
                
                obj.getCancelar().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                        obj.dispose();
                        frame.setEnabled(true);
                    }
                });
            }

            @Override
            public void onView(int row) {
                ViewBoleta obj = new ViewBoleta();
                obj.setApplication(frame);
                obj.initData(frame.findBoletaById((String) table_boletas.getValueAt(row, 0)));
                frame.setEnabled(false);
                obj.setVisible(true);
            }
        };
        
        for (Boleta boleta : frame.getBoletasList()) {
            table_boletas.addRow(new Object[] { boleta.getId(), date_format.format(boleta.getFecha()), 
                frame.findClienteByDNI(boleta.getCliente()).getNombresApellidos(), boleta.getImporteFinal(), boleta.getComentario(), "" });
        }
        
        table_boletas.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        table_boletas.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event_boleta));
        
        table_boletas.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_boletas.getColumnModel().getColumn(1).setPreferredWidth(40);
        table_boletas.getColumnModel().getColumn(2).setPreferredWidth(150);
        table_boletas.getColumnModel().getColumn(3).setPreferredWidth(30);
        table_boletas.getColumnModel().getColumn(4).setPreferredWidth(80);
        table_boletas.getColumnModel().getColumn(5).setPreferredWidth(40);
        
        TableActionEvent event_factura = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                EditFactura obj = new EditFactura();
                obj.setApplication(frame);
                obj.setIndex(row);
                obj.initData(frame.findFacturaById((String) table_facturas.getValueAt(row, 0)));
                frame.setEnabled(false);
                obj.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (table_facturas.isEditing()) {
                    table_facturas.getCellEditor().stopCellEditing();
                }
                
                Advertencia obj = new Advertencia();
                frame.setEnabled(false);
                obj.setVisible(true);
                
                obj.getEliminar().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                        try {
                            deleteFactura(row);
                        } 
                        catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error de referencias de la factura.", "Error de Procedimiento", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        obj.dispose();
                        frame.setEnabled(true);
                    }
                });
                
                obj.getCancelar().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                        obj.dispose();
                        frame.setEnabled(true);
                    }
                });
            }

            @Override
            public void onView(int row) {
                ViewFactura obj = new ViewFactura();
                obj.setApplication(frame);
                obj.initData(frame.findFacturaById((String) table_facturas.getValueAt(row, 0)));
                frame.setEnabled(false);
                obj.setVisible(true);
            }
        };
        
        for (Factura factura : frame.getFacturasList()) {
            table_facturas.addRow(new Object[] { factura.getId(), date_format.format(factura.getFecha()), 
                frame.findEmpresaByRUC(factura.getEmpresa()).getNombre(), factura.getImporteFinal(), factura.getComentario(), "" });
        }
        
        table_facturas.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        table_facturas.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event_factura));
        
        table_facturas.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_facturas.getColumnModel().getColumn(1).setPreferredWidth(40);
        table_facturas.getColumnModel().getColumn(2).setPreferredWidth(150);
        table_facturas.getColumnModel().getColumn(3).setPreferredWidth(30);
        table_facturas.getColumnModel().getColumn(4).setPreferredWidth(80);
        table_facturas.getColumnModel().getColumn(5).setPreferredWidth(40);
        
        setViewableTable(boletas);
    }
    
    public boolean filterRowBoletas(RowFilter.Entry<? extends TableModel, ? extends Integer> entry, TableFilter table_filter, String[] filters) {
        TableModel model = entry.getModel();
        int row_index = entry.getIdentifier();
        Boleta boleta = frame.findBoletaById((String) model.getValueAt(row_index, 0));
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)([<>]=?|!=|=)(.+)$");
        Pattern pattern2 = Pattern.compile("^(\\\".*\\\"|[a-zA-Z0-9]+)$");
        int count;

        for (String filter : filters) {
            Matcher matcher = pattern.matcher(filter);
            Matcher matcher2 = pattern2.matcher(filter);

            if (matcher.find()) {
                String field = matcher.group(1);
                String comparator = matcher.group(2);
                String find_this = matcher.group(3);

                switch (field) {
                    case "codigo", "código", "id" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = boleta.getId();

                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "fecha" -> {
                        try {
                            find_this = find_this.replaceAll("\"", "");
                            Date comparado = new SimpleDateFormat("dd-MM-yyyy").parse(find_this.replaceAll("/", "-"));
                            Date original = boleta.getFecha();

                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (ParseException e) {
                            return false;
                        }
                    }
                    case "comentario" ->                         {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = boleta.getComentario();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "subtotal" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = boleta.getSubtotal();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "descuento" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = boleta.getDescuento();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "importe" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = boleta.getImporteFinal();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "local" -> {
                        try {
                            int comparado = Integer.parseInt(find_this);
                            int original = boleta.getLocal();
                            
                            if (comparator.equals("=") || comparator.equals("!=")) {
                                if (comparator.equals("=") && comparado != original) return false; 
                                if (comparator.equals("!=") && comparado == original) return false;
                            }
                            else return false;
                        }
                        catch (NumberFormatException e) {
                            String comparado = find_this.replaceAll("\"", "");
                            String original = frame.findLocalByCodigo(boleta.getLocal()).getDireccion();

                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                    }
                    case "empleado" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = boleta.getEmpleado() + " " + frame.findEmpleadoByDNI(boleta.getEmpleado()).getNombresApellidos();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "cliente" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = boleta.getCliente()+ " " + frame.findClienteByDNI(boleta.getCliente()).getNombresApellidos();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "producto" -> {
                        try {
                            int comparado = Integer.parseInt(find_this);
                            
                            switch (comparator) {
                                case "=" -> {
                                    count = 0;
                                    for (Object[] producto : boleta.getProductos()) {
                                        if ((int) producto[0] == comparado) count++;
                                    }   if (count == 0) return false;
                                }
                                case "!=" -> {
                                    for (Object[] producto : boleta.getProductos()) {
                                        if ((int) producto[0] == comparado) return false;
                                    }
                                }
                                default -> {
                                    return false;
                                }
                            }
                        }
                        catch (NumberFormatException e) {
                            String comparado = find_this.replaceAll("\"", "");
                            String original;
                            count = 0;
                            
                            for (Object[] producto : boleta.getProductos()) {
                                original = frame.findProductoByCodigo((int) producto[0]).getNombre();
                                if (frame.compareFilter(original, comparado, comparator)) count++;
                            }
                            
                            if (count == 0) return false;
                        }
                    }
                    default -> {
                        return false;
                    }
                }
            }
            else if (matcher2.find()) {
                String find_this = matcher2.group(1);
                find_this = find_this.replaceAll("\"", "");
                count = 0;

                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (entry.getStringValue(i).contains(find_this)) count++;
                }
                
                if (count == 0) return false; 
            }
            else if (filter.equals("")) {} 
            else return false;
        }

        return true;
    }
    
    public boolean filterRowFacturas(RowFilter.Entry<? extends TableModel, ? extends Integer> entry, TableFilter table_filter, String[] filters) {
        TableModel model = entry.getModel();
        int row_index = entry.getIdentifier();
        Factura factura = frame.findFacturaById((String) model.getValueAt(row_index, 0));
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)([<>]=?|!=|=)(.+)$");
        Pattern pattern2 = Pattern.compile("^(\\\".*\\\"|[a-zA-Z0-9]+)$");
        int count;

        for (String filter : filters) {
            Matcher matcher = pattern.matcher(filter);
            Matcher matcher2 = pattern2.matcher(filter);

            if (matcher.find()) {
                String field = matcher.group(1);
                String comparator = matcher.group(2);
                String find_this = matcher.group(3);

                switch (field) {
                    case "codigo", "código", "id" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = factura.getId();

                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "fecha" -> {
                        try {
                            find_this = find_this.replaceAll("\"", "");
                            Date comparado = new SimpleDateFormat("dd-MM-yyyy").parse(find_this.replaceAll("/", "-"));
                            Date original = factura.getFecha();

                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (ParseException e) {
                            return false;
                        }
                    }
                    case "comentario" ->                         {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = factura.getComentario();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "subtotal" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = factura.getSubtotal();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "impuesto", "impuestos" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = factura.getImpuestos();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "descuento" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = factura.getDescuento();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "importe" -> {
                        try {
                            double comparado = Double.parseDouble(find_this);
                            double original = factura.getImporteFinal();
                            
                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "local" -> {
                        try {
                            int comparado = Integer.parseInt(find_this);
                            int original = factura.getLocal();
                            
                            if (comparator.equals("=") || comparator.equals("!=")) {
                                if (comparator.equals("=") && comparado != original) return false; 
                                if (comparator.equals("!=") && comparado == original) return false;
                            }
                            else return false;
                        }
                        catch (NumberFormatException e) {
                            String comparado = find_this.replaceAll("\"", "");
                            String original = frame.findLocalByCodigo(factura.getLocal()).getDireccion();

                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                    }
                    case "empleado" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = factura.getEmpleado() + " " + frame.findEmpleadoByDNI(factura.getEmpleado()).getNombresApellidos();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "empresa" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = factura.getEmpresa() + " " + frame.findEmpresaByRUC(factura.getEmpresa()).getNombre();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "producto" -> {
                        try {
                            int comparado = Integer.parseInt(find_this);
                            
                            switch (comparator) {
                                case "=" -> {
                                    count = 0;
                                    for (Object[] producto : factura.getProductos()) {
                                        if ((int) producto[0] == comparado) count++;
                                    }   if (count == 0) return false;
                                }
                                case "!=" -> {
                                    for (Object[] producto : factura.getProductos()) {
                                        if ((int) producto[0] == comparado) return false;
                                    }
                                }
                                default -> {
                                    return false;
                                }
                            }
                        }
                        catch (NumberFormatException e) {
                            String comparado = find_this.replaceAll("\"", "");
                            String original;
                            count = 0;
                            
                            for (Object[] producto : factura.getProductos()) {
                                original = frame.findProductoByCodigo((int) producto[0]).getNombre();
                                if (frame.compareFilter(original, comparado, comparator)) count++;
                            }
                            
                            if (count == 0) return false;
                        }
                    }
                    default -> {
                        return false;
                    }
                }
            }
            else if (matcher2.find()) {
                String find_this = matcher2.group(1);
                find_this = find_this.replaceAll("\"", "");
                count = 0;

                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (entry.getStringValue(i).contains(find_this)) count++;
                }
                
                if (count == 0) return false; 
            }
            else if (filter.equals("")) {} 
            else return false;
        }

        return true;
    }
    
    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }
    
    private void setViewableTable(JComponent com) {
        tablePanel.removeAll();
        tablePanel.add(com);
        tablePanel.repaint();
        tablePanel.revalidate();
    }
    
    public UI.Table.Table getTableBoletas() {
        return table_boletas;
    }
    
    public UI.Table.Table getTableFacturas() {
        return table_facturas;
    }
    
    public UI.Misc.Search getSearch() {
        return search1;
    }
    
    public void addBoleta(Boleta boleta) throws SQLException {
        String format_fecha = date_format.format(boleta.getFecha());
        
        Procedimientos.addBoleta(boleta);
        frame.getBoletasList().add(boleta);
        table_boletas.addRow(new Object[] { boleta.getId(), format_fecha, frame.findClienteByDNI(boleta.getCliente()).getNombresApellidos(), 
            boleta.getImporteFinal(), boleta.getComentario(), "" });
        frame.getBienvenido().updateProductoPopular();
        frame.getBienvenido().updateTotalVentasGanancias();
    }
    
    public void updateBoleta(int row, Boleta boleta) throws SQLException {
        Boleta boleta_original = frame.findBoletaById(boleta.getId());
        
        if (boleta_original != null) {
            Procedimientos.updateBoleta(boleta);
            boleta_original.setComentario(boleta.getComentario());
            table_boletas.setValueAt(boleta.getComentario(), row, 4);
        }
    }
    
    public void deleteBoleta(int row) throws SQLException {
        Boleta boleta = frame.findBoletaById((String) table_boletas.getValueAt(row, 0));
        Procedimientos.deleteBoleta(boleta);
        frame.getBoletasList().remove(boleta);
        table_boletas.removeRow(row);
        frame.getBienvenido().updateProductoPopular();
        frame.getBienvenido().updateTotalVentasGanancias();
    }
    
    public void addFactura(Factura factura) throws SQLException {
        String format_fecha = date_format.format(factura.getFecha());
        
        Procedimientos.addFactura(factura);
        frame.getFacturasList().add(factura);
        table_facturas.addRow(new Object[] { factura.getId(), format_fecha, frame.findEmpresaByRUC(factura.getEmpresa()).getNombre(), 
            factura.getImporteFinal(), factura.getComentario(), "" });
        frame.getBienvenido().updateProductoPopular();
        frame.getBienvenido().updateTotalVentasGanancias();
    }
    
    public void updateFactura(int row, Factura factura) throws SQLException {
        Factura factura_original = frame.findFacturaById(factura.getId());
        
        if (factura_original != null) {
            Procedimientos.updateFactura(factura);
            factura_original.setComentario(factura.getComentario());
            table_facturas.setValueAt(factura.getComentario(), row, 4);
        }
    }
    
    public void deleteFactura(int row) throws SQLException {
        Factura factura = frame.findFacturaById((String) table_facturas.getValueAt(row, 0));
        Procedimientos.deleteFactura(factura);
        frame.getFacturasList().remove(factura);
        table_facturas.removeRow(row);
        frame.getBienvenido().updateProductoPopular();
        frame.getBienvenido().updateTotalVentasGanancias();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spTable2 = new javax.swing.JScrollPane();
        table2 = new UI.Table.Table();
        search1 = new UI.Misc.Search();
        background = new UI.Misc.SimpleBackground();
        addButton1 = new UI.Misc.CustomButton();
        comboBoxSuggestion1 = new UI.Misc.ComboBoxSuggestion();
        tablePanel = new javax.swing.JPanel();

        spTable2.setBackground(new java.awt.Color(255, 255, 255));
        spTable2.setBorder(null);

        table2.setBackground(new java.awt.Color(255, 255, 255));
        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha", "Empresa", "Monto Total", "Comentario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        spTable2.setViewportView(table2);

        spTable2.getAccessibleContext().setAccessibleParent(background);

        setBackground(new java.awt.Color(204, 204, 204));
        setOpaque(false);

        background.setBackground(new java.awt.Color(255, 255, 255));

        addButton1.setBackground(new java.awt.Color(51, 153, 255));
        addButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/plus.png"))); // NOI18N
        addButton1.setBorderColor(new java.awt.Color(255, 255, 255));
        addButton1.setColor(new java.awt.Color(51, 153, 255));
        addButton1.setColorClick(new java.awt.Color(39, 81, 122));
        addButton1.setColorOver(new java.awt.Color(56, 127, 198));
        addButton1.setRadius(50);
        addButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButton1ActionPerformed(evt);
            }
        });

        comboBoxSuggestion1.setBackground(new java.awt.Color(255, 255, 255));
        comboBoxSuggestion1.setBorder(null);
        comboBoxSuggestion1.setEditable(false);
        comboBoxSuggestion1.setForeground(new java.awt.Color(102, 102, 102));
        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Boletas", "Facturas" }));
        comboBoxSuggestion1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N

        tablePanel.setOpaque(false);
        tablePanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButton1ActionPerformed
        if (selectedTable == 1) {
            AddBoleta obj = new AddBoleta();
            obj.setApplication(frame);
            obj.initData();
            frame.setEnabled(false);
            obj.setVisible(true);
        }
        else if (selectedTable == 2) {
            AddFactura obj = new AddFactura();
            obj.setApplication(frame);
            obj.initData();
            frame.setEnabled(false);
            obj.setVisible(true);
        }
    }//GEN-LAST:event_addButton1ActionPerformed
    
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(0, 0, 20, getHeight());
        g2.setColor(new Color(255, 255, 255, 50));
        super.paintComponent(graphics);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Misc.CustomButton addButton1;
    private UI.Misc.SimpleBackground background;
    private UI.Misc.ComboBoxSuggestion comboBoxSuggestion1;
    private UI.Misc.Search search1;
    private javax.swing.JScrollPane spTable2;
    private UI.Table.Table table2;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}
