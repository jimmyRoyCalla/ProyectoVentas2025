package Conexiones;

import Sistema.*;
import UI.Interface.Application;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Types;

public class Procedimientos {
    private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
    
    public static boolean validatePassword(String username, String password) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getPassword(?, ?)}");
        statement.setString(1, username);
        statement.registerOutParameter(2, Types.VARCHAR);
        statement.execute();
        
        String hashed = statement.getString(2);
        
        return BCrypt.checkpw(password, hashed);
    }
    
    public static void setPassword(String username, String password) throws SQLException {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        
        CallableStatement statement = Conexion.getConexion().prepareCall("{call setPassword(?, ?)}");
        statement.setString(1, username);
        statement.setString(2, hashed);
        statement.executeUpdate();
    }
    
    public static ArrayList<Cliente> getClientes() throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getClientes()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            String dni = result_set.getString("dni");
            String nombres = result_set.getString("nombres");
            String apellidos = result_set.getString("apellidos");
            String telefono = result_set.getString("telefono");
            String estado_civil = result_set.getString("estado_civil");
            String direccion = result_set.getString("direccion");
            Date fecha_nacimiento = result_set.getDate("fecha_nacimiento");
            String membresia = result_set.getString("membresia");

            Cliente cliente = new Cliente(dni, nombres, apellidos, telefono, estado_civil, direccion, fecha_nacimiento, membresia);
            clientes.add(cliente);
        }
        
        return clientes;
    }
    
    public static void addCliente(Cliente cliente) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addCliente(?, ?, ?, ?, ?, ?, ?, ?)}");
        statement.setString(1, cliente.getDni());
        statement.setString(2, cliente.getNombres());
        statement.setString(3, cliente.getApellidos());
        statement.setString(4, cliente.getTelefono());
        statement.setString(5, cliente.getEstadoCivil());
        statement.setString(6, cliente.getDireccion());

        String formated_fecha = date_format.format(cliente.getFechaNacimiento());
        java.sql.Date fecha_sql = java.sql.Date.valueOf(formated_fecha);
        statement.setDate(7, fecha_sql);

        statement.setString(8, cliente.getMembresia());
        statement.executeUpdate();
    }
    
    public static void updateCliente(Cliente cliente) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateCliente(?, ?, ?, ?, ?, ?, ?, ?)}");
        statement.setString(1, cliente.getDni());
        statement.setString(2, cliente.getNombres());
        statement.setString(3, cliente.getApellidos());
        statement.setString(4, cliente.getTelefono());
        statement.setString(5, cliente.getEstadoCivil());
        statement.setString(6, cliente.getDireccion());

        String formated_fecha = date_format.format(cliente.getFechaNacimiento());
        java.sql.Date fecha_sql = java.sql.Date.valueOf(formated_fecha);
        statement.setDate(7, fecha_sql);

        statement.setString(8, cliente.getMembresia());
        statement.executeUpdate();
    }
    
    public static void deleteCliente(Cliente cliente) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteCliente(?)}");
        statement.setString(1, cliente.getDni());
        statement.execute();
    }
    
    public static ArrayList<Empresa> getEmpresas() throws SQLException {
        ArrayList<Empresa> empresas = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getEmpresas()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            String ruc = result_set.getString("ruc");
            String nombre = result_set.getString("nombre");
            String direccion = result_set.getString("direccion");
            String telefono = result_set.getString("telefono");

            Empresa empresa = new Empresa(ruc, nombre, direccion, telefono);
            empresas.add(empresa);
        }
        
        return empresas;
    }
    
    public static void addEmpresa(Empresa empresa) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addEmpresa(?, ?, ?, ?)}");
        statement.setString(1, empresa.getRUC());
        statement.setString(2, empresa.getNombre());
        statement.setString(3, empresa.getDireccion());
        statement.setString(4, empresa.getTelefono());
        statement.executeUpdate();
    }
    
    public static void updateEmpresa(Empresa empresa) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateEmpresa(?, ?, ?, ?)}");
        statement.setString(1, empresa.getRUC());
        statement.setString(2, empresa.getNombre());
        statement.setString(3, empresa.getDireccion());
        statement.setString(4, empresa.getTelefono());
        statement.executeUpdate();
    }
    
    public static void deleteEmpresa(Empresa empresa) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteEmpresa(?)}");
        statement.setString(1, empresa.getRUC());
        statement.execute();
    }
    
    public static ArrayList<Proveedor> getProveedores() throws SQLException {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getProveedores()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            int id = result_set.getInt("id");
            String nombre = result_set.getString("nombre");
            String telefono = result_set.getString("telefono");
            double descuento = result_set.getDouble("descuento");

            Proveedor proveedor = new Proveedor(id, nombre, telefono, descuento);
            proveedores.add(proveedor);

            CallableStatement statement2 = Conexion.getConexion().prepareCall("{call getProductosProveedor(?)}");
            statement2.setInt(1, proveedor.getCodigo());
            ResultSet result_set2 = statement2.executeQuery();

            while (result_set2.next()) {
                int producto_id = result_set2.getInt("id");
                proveedor.getProductos().add(producto_id);
            }
        }

        return proveedores;
    }
    
    public static int addProveedor(Proveedor proveedor) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addProveedor(?, ?, ?, ?)}");
        
        statement.setString(1, proveedor.getNombre());
        statement.setString(2, proveedor.getTelefono());
        statement.setDouble(3, proveedor.getDescuento());
        statement.registerOutParameter(4, Types.INTEGER);

        statement.execute();
        int id = statement.getInt(4);
        return id;
    }

    public static void updateProveedor(Proveedor proveedor) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateProveedor(?, ?, ?, ?)}");
        statement.setInt(1, proveedor.getCodigo());
        statement.setString(2, proveedor.getNombre());
        statement.setString(3, proveedor.getTelefono());
        statement.setDouble(4, proveedor.getDescuento());
        statement.executeUpdate();
    }
    
    public static void updateProveedorProducto(ArrayList<Producto> productos) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateProveedorProducto(?, ?)}");
        Conexion.getConexion().setAutoCommit(false);

        for (Producto producto : productos) {
            statement.setInt(1, producto.getCodigo());
            statement.setInt(2, producto.getProveedor());
            statement.addBatch();
        }

        statement.executeBatch();
        Conexion.getConexion().commit();
        Conexion.getConexion().setAutoCommit(true);
    }
    
    public static void deleteProveedor(Proveedor proveedor) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteProveedor(?)}");
        statement.setInt(1, proveedor.getCodigo());
        statement.execute();
    }
    
    public static ArrayList<Local> getLocales(Application frame) throws SQLException {
        ArrayList<Local> locales = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getLocales()}");
        ResultSet result_set = statement.executeQuery();
            
        while (result_set.next()) {
            int id = result_set.getInt("id");
            String nombre = result_set.getString("direccion");
            String telefono = result_set.getString("telefono");
            String comentario = result_set.getString("comentario");

            Local local = new Local(id, nombre, telefono, comentario);
            locales.add(local);

            CallableStatement statement2 = Conexion.getConexion().prepareCall("{call getProductosLocal(?)}");
            statement2.setInt(1, local.getCodigo());
            ResultSet result_set2 = statement2.executeQuery();

            while (result_set2.next()) {
                int producto_id = result_set2.getInt("producto_id");
                local.getProductos().add(producto_id);
            }
            
            local.getProductos().sort((a, b) -> a.compareTo(b));

            CallableStatement statement3 = Conexion.getConexion().prepareCall("{call getEmpleadosLocal(?)}");
            statement3.setInt(1, local.getCodigo());
            ResultSet result_set3 = statement3.executeQuery();

            while (result_set3.next()) {
                String dni = result_set3.getString("dni");
                local.getEmpleados().add(dni);
            }
            
            local.getProductos().sort((a, b) -> a.compareTo(b));
            local.getEmpleados().sort((a, b) -> a.compareTo(b));
        }
        
        return locales;
    }
    
    public static int addLocal(Local local) throws SQLException {
        int id = 0;
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addLocal(?, ?, ?, ?)}");
        
        statement.setString(1, local.getDireccion());
        statement.setString(2, local.getTelefono());
        statement.setString(3, local.getComentario());
        statement.registerOutParameter(4, Types.INTEGER);

        int affected_rows = statement.executeUpdate();
        if (affected_rows > 0) id = statement.getInt(4);
        
        if (!local.getProductos().isEmpty()) {
            CallableStatement statement2 = Conexion.getConexion().prepareCall("{call updateProductosLocal(?, ?)}");
            
            String producto_ids = "";   
            for (Integer producto : local.getProductos()) producto_ids += String.valueOf(producto) + ",";
            producto_ids = producto_ids.substring(0, producto_ids.length() - 1);

            statement2.setInt(1, id);
            statement2.setString(2, producto_ids);
            statement2.executeUpdate();
        }
        
        return id;
    }
    
    public static void updateLocal(Local local) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateLocal(?, ?, ?, ?)}");
        statement.setInt(1, local.getCodigo());
        statement.setString(2, local.getDireccion());
        statement.setString(3, local.getTelefono());
        statement.setString(4, local.getComentario());
        statement.executeUpdate();
        
        if (!local.getProductos().isEmpty()) {
            CallableStatement statement2 = Conexion.getConexion().prepareCall("{call updateProductosLocal(?, ?)}");

            String producto_ids = "";   
            for (Integer producto : local.getProductos()) producto_ids += String.valueOf(producto) + ",";
            producto_ids = producto_ids.substring(0, producto_ids.length() - 1);

            statement2.setInt(1, local.getCodigo());
            statement2.setString(2, producto_ids);
            statement2.executeUpdate();
        }
    }
    
    public static void updateLocalesEmpleados(ArrayList<Empleado> empleados) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateLocalEmpleado(?, ?)}");
        Conexion.getConexion().setAutoCommit(false);
        
        for (Empleado empleado : empleados) {
            statement.setString(1, empleado.getDni());
            if (empleado.getLocalAsignado() != null) statement.setInt(2, empleado.getLocalAsignado());
            else statement.setNull(2, java.sql.Types.INTEGER);
            statement.addBatch();
            
        }
        
        statement.executeBatch();
        Conexion.getConexion().commit();
        Conexion.getConexion().setAutoCommit(true);
    }
    
    public static void deleteLocal(Local local) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteLocal(?)}");
        statement.setInt(1, local.getCodigo());
        statement.execute();
        
        statement = Conexion.getConexion().prepareCall("{call deleteLocal(?)}");
        statement.setInt(1, local.getCodigo());
        statement.execute();
    }
    
    public static ArrayList<Empleado> getEmpleados() throws SQLException {
        ArrayList<Empleado> empleados = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getEmpleados()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            String dni = result_set.getString("dni");
            String nombres = result_set.getString("nombres");
            String apellidos = result_set.getString("apellidos");
            String telefono = result_set.getString("telefono");
            String estado_civil = result_set.getString("estado_civil");
            String direccion = result_set.getString("direccion");
            Date fecha_nacimiento = result_set.getDate("fecha_nacimiento");
            String cargo = result_set.getString("cargo");
            double salario = result_set.getDouble("salario");
            Integer local_id = result_set.getInt("local_id");
            
            if (local_id == 0) local_id = null;

            Empleado empleado = new Empleado(dni, nombres, apellidos, telefono, estado_civil, direccion, fecha_nacimiento, cargo, salario, local_id);
            empleados.add(empleado);
        }
        
        return empleados;
    }
    
    public static void addEmpleado(Empleado empleado) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        statement.setString(1, empleado.getDni());
        statement.setString(2, empleado.getNombres());
        statement.setString(3, empleado.getApellidos());
        statement.setString(4, empleado.getTelefono());
        statement.setString(5, empleado.getEstadoCivil());
        statement.setString(6, empleado.getDireccion());

        String formated_fecha = date_format.format(empleado.getFechaNacimiento());
        java.sql.Date fecha_sql = java.sql.Date.valueOf(formated_fecha);
        statement.setDate(7, fecha_sql);

        statement.setString(8, empleado.getCargo());
        statement.setDouble(9, empleado.getSalario());

        if (empleado.getLocalAsignado() != null) statement.setInt(10, empleado.getLocalAsignado()); 
        else statement.setNull(10, Types.INTEGER);

        statement.executeUpdate();
    }
    
    public static void updateEmpleado(Empleado empleado) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        statement.setString(1, empleado.getDni());
        statement.setString(2, empleado.getNombres());
        statement.setString(3, empleado.getApellidos());
        statement.setString(4, empleado.getTelefono());
        statement.setString(5, empleado.getEstadoCivil());
        statement.setString(6, empleado.getDireccion());

        String formated_fecha = date_format.format(empleado.getFechaNacimiento());
        java.sql.Date fecha_sql = java.sql.Date.valueOf(formated_fecha);
        statement.setDate(7, fecha_sql);

        statement.setString(8, empleado.getCargo());
        statement.setDouble(9, empleado.getSalario());

        if (empleado.getLocalAsignado() != null) statement.setInt(10, empleado.getLocalAsignado()); 
        else statement.setNull(10, Types.INTEGER);

        statement.executeUpdate();
    }
    
    public static void deleteEmpleado(Empleado empleado) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteEmpleado(?)}");
        statement.setString(1, empleado.getDni());
        statement.execute();
    }
    
    public static ArrayList<Producto> getProductos() throws SQLException {
        ArrayList<Producto> productos = new ArrayList<>();
        
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getProductos()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            int id = result_set.getInt("id");
            String nombre = result_set.getString("nombre");
            double precio = result_set.getDouble("precio");
            int cantidad = result_set.getInt("cantidad");
            int proveedor_id = result_set.getInt("proveedor_id");

            Producto producto = new Producto(id, nombre, precio, cantidad, proveedor_id);
            productos.add(producto);
        }
        
        return productos;
    }
    
    public static int addProducto(Producto producto) throws SQLException {
        int id = 0;
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addProducto(?, ?, ?, ?, ?)}");
        
        statement.setString(1, producto.getNombre());
        statement.setDouble(2, producto.getPrecio());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getProveedor());
        statement.registerOutParameter(5, Types.INTEGER);

        int affected_rows = statement.executeUpdate();
        if (affected_rows > 0) id = statement.getInt(5);
        
        return id;
    }
    
    public static ArrayList<Producto> addProductos(ArrayList<Producto> productos) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addProducto(?, ?, ?, ?, ?)}");
        Conexion.getConexion().setAutoCommit(false);
        
        for (Producto producto : productos) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCantidad());
            statement.setInt(4, producto.getProveedor());
            statement.registerOutParameter(5, Types.INTEGER);
            
            statement.addBatch();
        }
        
        int[] affected_rows = statement.executeBatch();
        
        for (int i = 0; i < affected_rows.length; i++) {
            int id = (affected_rows[i] > 0) ? statement.getInt(5) : 0;
            productos.get(i).setCodigo(id);
        }
        
        Conexion.getConexion().commit();
        Conexion.getConexion().setAutoCommit(true);
        
        return productos;
    }
    
    public static void updateProducto(Producto producto) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateProducto(?, ?, ?, ?, ?)}");
        statement.setInt(1, producto.getCodigo());
        statement.setString(2, producto.getNombre());
        statement.setDouble(3, producto.getPrecio());
        statement.setInt(4, producto.getCantidad());
        statement.setInt(5, producto.getProveedor());
        statement.executeUpdate();
    }
    
    public static void deleteProducto(Producto producto) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteProducto(?)}");
        statement.setInt(1, producto.getCodigo());
        statement.execute();
    }
    
    public static ArrayList<Boleta> getBoletas(Application frame) throws SQLException {
        ArrayList<Boleta> boletas = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getBoletas()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            String id = result_set.getString("id");
            int local_id = result_set.getInt("local_id");
            String empleado_dni = result_set.getString("empleado_dni");
            Date fecha = result_set.getDate("fecha");
            String comentario = result_set.getString("comentario");
            double subtotal = result_set.getDouble("subtotal");
            double descuento = result_set.getDouble("descuento");
            double importe_final = result_set.getDouble("importe_final");
            String cliente_dni = result_set.getString("cliente_dni");

            Boleta boleta = new Boleta();
            boleta.setId(id);
            boleta.setLocal(local_id);
            boleta.setEmpleado(empleado_dni);
            boleta.setFecha(fecha);
            boleta.setProductos(new ArrayList<>());
            boleta.setComentario(comentario);
            boleta.setSubtotal(subtotal);
            boleta.setDescuento(descuento);
            boleta.setImporteFinal(importe_final);
            boleta.setCliente(cliente_dni);

            CallableStatement statement2 = Conexion.getConexion().prepareCall("{call getProductosBoleta(?)}");
            statement2.setString(1, boleta.getId());
            ResultSet result_set2 = statement2.executeQuery();

            while (result_set2.next()) {
                int producto_id = result_set2.getInt("producto_id");
                int cantidad = result_set2.getInt("cantidad");
                
                boleta.getProductos().add(new Object[] { producto_id, cantidad });
            }

            boletas.add(boleta);
        }

        return boletas;
    }
    
    public static void addBoleta(Boleta boleta) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addBoleta(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        
        statement.setString(1, boleta.getId());
        statement.setInt(2, boleta.getLocal());
        statement.setString(3, boleta.getEmpleado());
        
        String formated_fecha = date_format.format(boleta.getFecha());
        java.sql.Date fecha_sql = java.sql.Date.valueOf(formated_fecha);
        statement.setDate(4, fecha_sql);
        
        statement.setString(5, boleta.getComentario());
        statement.setDouble(6, boleta.getSubtotal());
        statement.setDouble(7, boleta.getDescuento());
        statement.setDouble(8, boleta.getImporteFinal());
        statement.setString(9, boleta.getCliente());
        statement.executeUpdate();
        
        statement = Conexion.getConexion().prepareCall("{call addProductosBoleta(?, ?, ?)}");
        Conexion.getConexion().setAutoCommit(false);
        
        for (Object[] producto : boleta.getProductos()) {
            
            statement.setString(1, boleta.getId());
            statement.setInt(2, (int) producto[0]);
            statement.setInt(3, (int) producto[1]);
            statement.addBatch();
        }
        
        statement.executeBatch();
        Conexion.getConexion().commit();
        Conexion.getConexion().setAutoCommit(true);
    }
    
    public static void updateBoleta(Boleta boleta) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateBoleta(?, ?)}");
        statement.setString(1, boleta.getId());
        statement.setString(2, boleta.getComentario());
        statement.executeUpdate();
    }
    
    public static void deleteBoleta(Boleta boleta) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteBoleta(?)}");
        statement.setString(1, boleta.getId());
        statement.execute();
    }
    
    public static ArrayList<Factura> getFacturas(Application frame) throws SQLException {
        ArrayList<Factura> facturas = new ArrayList<>();
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getFacturas()}");
        ResultSet result_set = statement.executeQuery();

        while (result_set.next()) {
            String id = result_set.getString("id");
            int local_id = result_set.getInt("local_id");
            String empleado_dni = result_set.getString("empleado_dni");
            Date fecha = result_set.getDate("fecha");
            String comentario = result_set.getString("comentario");
            double subtotal = result_set.getDouble("subtotal");
            double impuestos = result_set.getDouble("impuestos");
            double descuento = result_set.getDouble("descuento");
            double importe_final = result_set.getDouble("importe_final");
            String empresa_ruc = result_set.getString("empresa_ruc");

            Factura factura = new Factura();
            factura.setId(id);
            factura.setLocal(local_id);
            factura.setEmpleado(empleado_dni);
            factura.setFecha(fecha);
            factura.setProductos(new ArrayList<>());
            factura.setComentario(comentario);
            factura.setSubtotal(subtotal);
            factura.setImpuestos(impuestos);
            factura.setDescuento(descuento);
            factura.setImporteFinal(importe_final);
            factura.setEmpresa(empresa_ruc);

            CallableStatement statement2 = Conexion.getConexion().prepareCall("{call getProductosFactura(?)}");
            statement2.setString(1, factura.getId());
            ResultSet result_set2 = statement2.executeQuery();

            while (result_set2.next()) {
                int producto_id = result_set2.getInt("producto_id");
                int cantidad = result_set2.getInt("cantidad");

                factura.getProductos().add(new Object[] { producto_id, cantidad });
            }

            facturas.add(factura);
        }

        return facturas;
    }

    public static void addFactura(Factura factura) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call addFactura(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        statement.setString(1, factura.getId());
        statement.setInt(2, factura.getLocal());
        statement.setString(3, factura.getEmpleado());

        String formatted_fecha = date_format.format(factura.getFecha());
        java.sql.Date fecha_sql = java.sql.Date.valueOf(formatted_fecha);
        statement.setDate(4, fecha_sql);

        statement.setString(5, factura.getComentario());
        statement.setDouble(6, factura.getSubtotal());
        statement.setDouble(7, factura.getImpuestos());
        statement.setDouble(8, factura.getDescuento());
        statement.setDouble(9, factura.getImporteFinal());
        statement.setString(10, factura.getEmpresa());
        statement.executeUpdate();

        statement = Conexion.getConexion().prepareCall("{call addProductosFactura(?, ?, ?)}");
        Conexion.getConexion().setAutoCommit(false);

        for (Object[] producto : factura.getProductos()) {
            statement.setString(1, factura.getId());
            statement.setInt(2, (int) producto[0]);
            statement.setInt(3, (int) producto[1]);
            statement.addBatch();
        }

        statement.executeBatch();
        Conexion.getConexion().commit();
        Conexion.getConexion().setAutoCommit(true);
    }

    public static void updateFactura(Factura factura) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call updateFactura(?, ?)}");
        statement.setString(1, factura.getId());
        statement.setString(2, factura.getComentario());
        statement.executeUpdate();
    }

    public static void deleteFactura(Factura factura) throws SQLException {
        CallableStatement statement = Conexion.getConexion().prepareCall("{call deleteFactura(?)}");
        statement.setString(1, factura.getId());
        statement.execute();
    }
    
    public static Object[] getGananciasTotal() throws SQLException {
        Object[] ganancias = new Object[] { 0, 0.0 };
        
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getTotalGanancias()}");
        ResultSet result_set = statement.executeQuery();
        
        if (result_set.next()) {
            ganancias[0] = result_set.getInt("total_count");
            ganancias[1] = result_set.getDouble("total_importe");
        }
        
        return ganancias;
    }
    
    public static Object[] getProductoMasVendido() throws SQLException {
        Object[] producto = new Object[] { -1, -1 };
        
        CallableStatement statement = Conexion.getConexion().prepareCall("{call getProductoMasVendido()}");
        ResultSet result_set = statement.executeQuery();
        
        if (result_set.next()) {
            producto[0] = result_set.getInt("producto_popular");
            producto[1] = result_set.getInt("total_vendidos");
        }
        
        return producto;
    }
}
