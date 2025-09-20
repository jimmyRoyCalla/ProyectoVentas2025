package UI.Ventas;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import Sistema.Boleta;
import Sistema.Producto;
import UI.Interface.Application;
import UI.Misc.ScrollBar;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewBoletaOptions extends javax.swing.JPanel {
    private final DecimalFormat decimal_format = new DecimalFormat("#0.00");
    private Application frame;
    
    public ViewBoletaOptions() {
        initComponents();
        
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(new Color(242, 242, 242, 255));
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
    }
    
    public void setData(Boleta boleta) {
        SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
        String format_fecha = date_format.format(boleta.getFecha());
        
        label1.setText(boleta.getId());
        label2.setText(frame.findLocalByCodigo(boleta.getLocal()).getDireccion());
        label3.setText(frame.findEmpleadoByDNI(boleta.getEmpleado()).getNombresApellidos());
        label4.setText(format_fecha);
        label5.setText(decimal_format.format(boleta.getSubtotal()));
        label6.setText(decimal_format.format(boleta.getDescuento()));
        label7.setText(decimal_format.format(boleta.getImporteFinal()));
        label8.setText(boleta.getComentario());
        label9.setText(frame.findClienteByDNI(boleta.getCliente()).getNombresApellidos());
        
        for (Object[] producto : boleta.getProductos()) {
            Producto obj = frame.findProductoByCodigo((int) producto[0]);
            table.addRow(new Object[] { obj.getNombre(), obj.getPrecio(), (int) producto[1], obj.getPrecio() * (int) producto[1] });
        }
    }
    
    public void setApplication(Application frame) {
        this.frame = frame;
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void toPDF() throws IOException {
        PDDocument document = new PDDocument();
        
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream content_stream = new PDPageContentStream(document, page);
        content_stream.setFont(PDType1Font.COURIER_BOLD, 12);

        int y = 800;
        
        String[] headers = new String[] { "Sistema Ventas", "Sistema Ventas S.A.C.", "RUC 20584691237", "Av. Huaylas 123, Chorrillos, Lima" };
        
        for (String header : headers) {
            float header_width = PDType1Font.COURIER_BOLD.getStringWidth(header) / 1000f * 12;
            float header_x = (PDRectangle.A4.getWidth() - header_width) / 2;
            content_stream.beginText();
            content_stream.setLeading(15);
            content_stream.newLineAtOffset(header_x, y);
            content_stream.showText(header);
            content_stream.endText();
            y -= 20;
        } 
        
        y -= 20;
        content_stream.setFont(PDType1Font.COURIER, 12);
        
        content_stream.beginText();
        content_stream.newLineAtOffset(60, y);
        
        content_stream.showText("ID            :   " + label1.getText());
        content_stream.newLine();

        content_stream.showText("Local         :   " + label2.getText());
        content_stream.newLine();

        content_stream.showText("Fecha         :   " + label4.getText());
        content_stream.newLine();

        content_stream.showText("Cliente       :   " + label9.getText());
        content_stream.newLine();
        
        content_stream.showText("Atendido por  :   " + label3.getText());
        content_stream.newLine();
        content_stream.endText();
        
        int row_count = table.getRowCount();
        int column_count = table.getColumnCount();
        float cell_width = 120f;
        float table_x = 80f;
        float table_y = y - 100f;

        content_stream.setFont(PDType1Font.COURIER_BOLD, 12);
        float header_y = table_y - 15f;
        float header_x = table_x;
        
        for (int i = 0; i < column_count; i++) {
            content_stream.beginText();
            content_stream.newLineAtOffset(header_x + (cell_width * i), header_y);
            content_stream.showText(table.getColumnName(i));
            content_stream.endText();
        }

        content_stream.setFont(PDType1Font.COURIER, 12);
        float row_y = table_y - 30f;
        
        for (int row = 0; row < row_count; row++) {
            for (int col = 0; col < column_count; col++) {
                content_stream.beginText();
                content_stream.newLineAtOffset(table_x + (cell_width * col), row_y);
                content_stream.showText(table.getValueAt(row, col).toString());
                content_stream.endText();
            }

            row_y -= 15f;
            y -= 15f;
        }
        
        y -= 120;
        
        content_stream.beginText();
        content_stream.newLineAtOffset(60, y);
        
        content_stream.newLine();
        content_stream.newLine();
        content_stream.showText("------------------------------------------------------------------");
        content_stream.newLine();
        content_stream.newLine();
        
        content_stream.showText("Subtotal      :   S/ " + label5.getText());
        content_stream.newLine();
        
        content_stream.showText("Descuento     :   S/ " + label6.getText());
        content_stream.newLine();
        
        content_stream.showText("Importe       :   S/ " + label7.getText());
        content_stream.newLine();
        
        content_stream.newLine();
        content_stream.showText("------------------------------------------------------------------");
        content_stream.newLine();
        content_stream.newLine();
        
        String comentario;
        if (label8.getText().equals("")) comentario = "Sin comentarios.";
        else comentario = label8.getText();
        
        content_stream.showText("Comentario    :   " + comentario);
                
        content_stream.endText();
        content_stream.close();
        
        String project = System.getProperty("user.dir");
        String directory = project + File.separator + "boletas";
        String path =  directory + File.separator + label1.getText() + ".pdf";
        
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();
        
        document.save(path);
        document.close();
        
        /*File file = new File(path);
        if (file.exists()) Runtime.getRuntime().exec("firefox " + file.getAbsolutePath());*/
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        label5 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        label7 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        label6 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        table = new UI.Table.Table();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        label8 = new javax.swing.JLabel();
        label9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setOpaque(false);

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Comentario");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Importe a pagar");

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Descuento");

        label5.setForeground(new java.awt.Color(102, 102, 102));
        label5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label5.setText("Subtotal");

        label2.setForeground(new java.awt.Color(102, 102, 102));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label2.setText("Local");
        label2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        label1.setForeground(new java.awt.Color(102, 102, 102));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label1.setText("Código");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Local");

        jLabel19.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Sistema Ventas");

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("ID");

        label7.setForeground(new java.awt.Color(102, 102, 102));
        label7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label7.setText("Importe");

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Atendido por");

        label6.setForeground(new java.awt.Color(102, 102, 102));
        label6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label6.setText("Descuento");

        spTable.setBorder(null);
        spTable.setOpaque(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "PU", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Subtotal");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Productos");

        jLabel22.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Av. Huaylas 123, Chorrillos, Lima");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Cliente");

        jLabel21.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Sistema Ventas S.A.C.");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Fecha");

        jLabel20.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("RUC 20584691237");

        label3.setForeground(new java.awt.Color(102, 102, 102));
        label3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label3.setText("Empleado");
        label3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        label4.setForeground(new java.awt.Color(102, 102, 102));
        label4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label4.setText("Fecha");

        label8.setForeground(new java.awt.Color(102, 102, 102));
        label8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label8.setText("Comentario");
        label8.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        label9.setForeground(new java.awt.Color(102, 102, 102));
        label9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label9.setText("Cliente");
        label9.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addGap(210, 210, 210))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(205, 205, 205)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(152, 152, 152)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel11))
                        .addContainerGap(19, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(72, 72, 72))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(225, 225, 225))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(234, 234, 234))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(176, 176, 176))))
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
                    .addComponent(label2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(label1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(label4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(label9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label8)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel label6;
    private javax.swing.JLabel label7;
    private javax.swing.JLabel label8;
    private javax.swing.JLabel label9;
    private javax.swing.JScrollPane spTable;
    private UI.Table.Table table;
    // End of variables declaration//GEN-END:variables
}
