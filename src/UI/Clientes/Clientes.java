package UI.Clientes;

import Conexiones.Procedimientos;
import Sistema.Cliente;
import Sistema.Empresa;
import UI.Interface.Advertencia;
import UI.Interface.Application;
import UI.Table.TableActionCellEditor;
import UI.Table.TableActionCellRender;
import UI.Table.TableActionEvent;
import UI.Table.TableFilter;
import UI.Table.TableMembresiaCellRender;
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

public class Clientes extends javax.swing.JPanel {
    private Color color1;
    private Color color2;
    private final Application frame;
    private final TableRowSorter st1;
    private final TableRowSorter st2;
    private Personas personas;
    private Empresas empresas;
    private UI.Table.Table table_personas;
    private UI.Table.Table table_empresas;
    private int selectedTable;
    
    public Clientes(Application frame) {
        initComponents();
        setOpaque(false);
        
        color1 = Color.WHITE;
        color2 = Color.GRAY;
        
        this.frame = frame;
        selectedTable = 1;
        personas = new Personas();
        empresas = new Empresas();
        
        st1 = new TableRowSorter(personas.getTable().getModel());
        table_personas = personas.getTable();
        table_personas.setRowSorter(st1);
        
        st2 = new TableRowSorter(empresas.getTable().getModel());
        table_empresas = empresas.getTable();
        table_empresas.setRowSorter(st2);
        
        comboBoxSuggestion1.addActionListener((ActionEvent e) -> {
            String selectedOption = (String) comboBoxSuggestion1.getSelectedItem();
            
            if (selectedOption.equals("Personas")) {
                selectedTable = 1;
                setViewableTable(personas);
            }
            else if (selectedOption.equals("Empresas")) {
                selectedTable = 2;
                setViewableTable(empresas);
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
                            return filterRowCliente(entry, this, filters);
                        }
                    });
                    
                    else st2.setRowFilter(new TableFilter(filters) {
                        @Override
                        public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                            return filterRowEmpresa(entry, this, filters);
                        }
                    });
                }
            }
        });
        
        TableActionEvent event_personas = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                EditCliente obj = new EditCliente();
                obj.setApplication(frame);
                obj.setIndex(row);
                obj.setClienteEditar(frame.findClienteByDNI((String) table_personas.getValueAt(row, 0)));
                obj.initData();
                frame.setEnabled(false);
                obj.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (table_personas.isEditing()) {
                    table_personas.getCellEditor().stopCellEditing();
                }
                
                Advertencia obj = new Advertencia();
                frame.setEnabled(false);
                obj.setVisible(true);
                
                obj.getEliminar().addMouseListener(new MouseAdapter() {
                    @Override
                    @SuppressWarnings("CallToPrintStackTrace")
                    public void mousePressed(MouseEvent me) {
                        try {
                            deleteCliente(row);
                        } 
                        catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "El cliente está siendo referenciado en una boleta.", "Error de Procedimiento", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
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
                ViewCliente obj = new ViewCliente();
                obj.setCliente(frame.findClienteByDNI((String) table_personas.getValueAt(row, 0)));
                obj.setApplication(frame);
                obj.initData();
                frame.setEnabled(false);
                obj.setVisible(true);
            }
        };
        
        for (Cliente cliente : frame.getClientesList()) {
            table_personas.addRow(new Object[] { cliente.getDni(), cliente.getNombres(), cliente.getApellidos(), cliente.getTelefono(), cliente.getMembresia(), "" });
        }
        
        table_personas.getColumnModel().getColumn(4).setCellRenderer(new TableMembresiaCellRender());
        table_personas.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        table_personas.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event_personas));
        
        table_personas.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_personas.getColumnModel().getColumn(5).setPreferredWidth(40);
        
        TableActionEvent event_empresas = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                EditEmpresa obj = new EditEmpresa();
                obj.setIndex(row);
                obj.setEmpresaEditar(frame.findEmpresaByRUC((String) table_empresas.getValueAt(row, 0)));
                obj.initData();
                obj.setApplication(frame);
                frame.setEnabled(false);
                obj.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (table_empresas.isEditing()) {
                    table_empresas.getCellEditor().stopCellEditing();
                }
                
                Advertencia obj = new Advertencia();
                frame.setEnabled(false);
                obj.setVisible(true);
                
                obj.getEliminar().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                        try {
                            deleteEmpresa(row);
                        } 
                        catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "La empresa está siendo referenciada en una factura.", "Error de Procedimiento", JOptionPane.ERROR_MESSAGE);
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
                ViewEmpresa obj = new ViewEmpresa();
                obj.setEmpresa(frame.findEmpresaByRUC((String) table_empresas.getValueAt(row, 0)));
                obj.initData();
                obj.setApplication(frame);
                frame.setEnabled(false);
                obj.setVisible(true);
            }
        };
        
        for (Empresa empresa : frame.getEmpresasList()) {
            table_empresas.addRow(new Object[] { empresa.getRUC(), empresa.getNombre(), empresa.getDireccion(), empresa.getTelefono(), "" });
        }

        table_empresas.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        table_empresas.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event_empresas));
        
        table_empresas.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_empresas.getColumnModel().getColumn(1).setPreferredWidth(200);
        table_empresas.getColumnModel().getColumn(2).setPreferredWidth(100);
        table_empresas.getColumnModel().getColumn(3).setPreferredWidth(80);
        table_empresas.getColumnModel().getColumn(4).setPreferredWidth(40);
        
        setViewableTable(personas);
    }
    
    public boolean filterRowCliente(RowFilter.Entry<? extends TableModel, ? extends Integer> entry, TableFilter table_filter, String[] filters) {
        TableModel model = entry.getModel();
        int row_index = entry.getIdentifier();
        Cliente cliente = frame.findClienteByDNI((String) model.getValueAt(row_index, 0));
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
                    case "dni", "DNI" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getDni();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "nombres" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getNombres();
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "apellidos" ->                         {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getApellidos();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "telefono" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getTelefono();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "estado" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getEstadoCivil();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "direccion" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getDireccion();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "fecha", "nacimiento" -> {
                        try {
                            find_this = find_this.replaceAll("\"", "");
                            Date comparado = new SimpleDateFormat("dd-MM-yyyy").parse(find_this.replaceAll("/", "-"));
                            Date original = cliente.getFechaNacimiento();

                            if (!frame.compareFilter(original, comparado, comparator)) return false;
                        }
                        catch (ParseException e) {
                            return false;
                        }
                    }
                    case "membresia" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = cliente.getMembresia();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
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
    
    public boolean filterRowEmpresa(RowFilter.Entry<? extends TableModel, ? extends Integer> entry, TableFilter table_filter, String[] filters) {
        TableModel model = entry.getModel();
        int row_index = entry.getIdentifier();
        Empresa empresa = frame.findEmpresaByRUC((String) model.getValueAt(row_index, 0));
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
                    case "ruc", "RUC" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = empresa.getRUC();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "nombre" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = empresa.getNombre();
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "direccion" ->                         {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = empresa.getDireccion();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
                    }
                    case "telefono" -> {
                        String comparado = find_this.replaceAll("\"", "");
                        String original = empresa.getTelefono();
                        
                        if (!frame.compareFilter(original, comparado, comparator)) return false;
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
    
    public UI.Table.Table getTablePersonas() {
        return table_personas;
    }
    
    public UI.Table.Table getTableEmpresas() {
        return table_empresas;
    }
    
    public UI.Misc.Search getSearch() {
        return search1;
    }
    
    public void addCliente(Cliente cliente) throws SQLException {
        Procedimientos.addCliente(cliente);
        frame.getClientesList().add(cliente);
        table_personas.addRow(new Object[] { cliente.getDni(), cliente.getNombres(), cliente.getApellidos(), cliente.getTelefono(), cliente.getMembresia(), "" });
        frame.getBienvenido().updateCountMembresias();
    }
    
    public void updateCliente(int row, Cliente cliente) throws SQLException {
        Cliente cliente_original = frame.findClienteByDNI(cliente.getDni());
        
        if (cliente_original != null) {
            Procedimientos.updateCliente(cliente);
            
            cliente_original.setNombres(cliente.getNombres());
            cliente_original.setApellidos(cliente.getApellidos());
            cliente_original.setTelefono(cliente.getTelefono());
            cliente_original.setEstadoCivil(cliente.getEstadoCivil());
            cliente_original.setDireccion(cliente.getDireccion());
            cliente_original.setFechaNacimiento(cliente.getFechaNacimiento());
            cliente_original.setMembresia(cliente.getMembresia());
            
            table_personas.setValueAt(cliente.getDni(), row, 0);
            table_personas.setValueAt(cliente.getNombres(), row, 1);
            table_personas.setValueAt(cliente.getApellidos(), row, 2);
            table_personas.setValueAt(cliente.getTelefono(), row, 3);
            table_personas.setValueAt(cliente.getMembresia(), row, 4);
            
            frame.getBienvenido().updateCountMembresias();
        }
    }
    
    public void deleteCliente(int row) throws SQLException {
        Cliente cliente = frame.findClienteByDNI((String) table_personas.getValueAt(row, 0));
        Procedimientos.deleteCliente(cliente);
        frame.getClientesList().remove(cliente);
        table_personas.removeRow(row);
        frame.getBienvenido().updateCountMembresias();
    }
    
    public void addEmpresa(Empresa empresa) throws SQLException {
        Procedimientos.addEmpresa(empresa);
        frame.getEmpresasList().add(empresa);
        table_empresas.addRow(new Object[] { empresa.getRUC(), empresa.getNombre(), empresa.getDireccion(), empresa.getTelefono(), "" });
    }
    
    public void updateEmpresa(int row, Empresa empresa) throws SQLException {
        Empresa empresa_original = frame.findEmpresaByRUC(empresa.getRUC());
        
        if (empresa_original != null) {
            Procedimientos.updateEmpresa(empresa);
            
            empresa_original.setNombre(empresa.getNombre());
            empresa_original.setDireccion(empresa.getDireccion());
            empresa_original.setTelefono(empresa.getTelefono());
            
            table_empresas.setValueAt(empresa.getRUC(), row, 0);
            table_empresas.setValueAt(empresa.getNombre(), row, 1);
            table_empresas.setValueAt(empresa.getDireccion(), row, 2);
            table_empresas.setValueAt(empresa.getTelefono(), row, 3);
        }
    }
    
    public void deleteEmpresa(int row) throws SQLException {
        Empresa empresa = frame.findEmpresaByRUC((String) table_empresas.getValueAt(row, 0));
        Procedimientos.deleteEmpresa(empresa);
        frame.getEmpresasList().remove(empresa);
        table_empresas.removeRow(row);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        search1 = new UI.Misc.Search();
        background = new UI.Misc.SimpleBackground();
        addButton1 = new UI.Misc.CustomButton();
        comboBoxSuggestion1 = new UI.Misc.ComboBoxSuggestion();
        tablePanel = new javax.swing.JPanel();

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
        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Personas", "Empresas" }));
        comboBoxSuggestion1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N

        tablePanel.setOpaque(false);
        tablePanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(581, 581, 581)
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
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButton1ActionPerformed
        if (selectedTable == 1) {
            AddCliente obj = new AddCliente();
            obj.setApplication(frame);
            frame.setEnabled(false);
            obj.setVisible(true);
        }
        else if (selectedTable == 2) {
            AddEmpresa obj = new AddEmpresa();
            obj.setApplication(frame);
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
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}
