package sistema.datos;

import Sistema.pojos.*;
import java.io.File;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Julio
 */
public class BaseDatos {
    Connection conn = null;
    PreparedStatement prepSt = null;
    Statement st = null;
    ResultSet rs = null;
    
    public BaseDatos(){
        try {
            Class.forName("org.postgresql.Driver");
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Error al ingresar a la base de datos");
        }
    }
    
    public void insertarProducto(Producto producto){
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            FileInputStream fis = new FileInputStream(producto.getFotoProducto());
            
            String sql = "INSERT INTO cat_productos(id_prod, nombre_prod, desc_prod, stock_prod, foto_prod, unidad_prod, precio_compra_prod, precio_venta_prod, existencias_prod, id_categoria_prod, id_proveedor)"
                    + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prepSt = conn.prepareStatement(sql);
            
            prepSt.setString(1, producto.getIdProducto());
            prepSt.setString(2, producto.getNomProducto());
            prepSt.setString(3, producto.getDescProducto());
            prepSt.setDouble(4, producto.getStockProducto());
            prepSt.setBinaryStream(5, fis, (int)producto.getFotoProducto().length());
            prepSt.setString(6, producto.getUnidadProducto());
            prepSt.setDouble(7, producto.getPrecioCompraProducto());
            prepSt.setDouble(8, producto.getPrecioVentaProducto());
            prepSt.setDouble(9, producto.getExistenciasProducto());
            prepSt.setInt(10, producto.getIdCategoria());
            prepSt.setInt(11, producto.getIdProveedor());
            
            prepSt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void insertarCategoriaProducto(CategoriaProd categoria){
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "INSERT INTO cat_categorias (nom_categoria_prod, desc_categoria_prod)"
                    + "VALUES (?, ?)";
            prepSt = conn.prepareStatement(sql);
            
            prepSt.setString(1, categoria.getNomCategoriaProd());
            prepSt.setString(2, categoria.getDescCategoriaProd());
            
            prepSt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
    public void insertarProveedor(Proveedor prov){
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "INSERT INTO cat_proveedores (nom_proveedor, dir_proveedor, telefono_proveedor, email_proveedor, contacto_proveedor)"
                    + "VALUES (?, ?, ?, ?, ?)";
            
            prepSt = conn.prepareStatement(sql);
            
            prepSt.setString(1, prov.getNomProveedor());
            prepSt.setString(2, prov.getDirProveedor());
            prepSt.setString(3, prov.getTelProveedor());
            prepSt.setString(4, prov.getEmailProveedor());
            prepSt.setString(5, prov.getContactoProveedor());
            
            prepSt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void insertarVenta(Venta venta){
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "INSERT INTO ventas (monto_venta, fecha_venta)"
                    + "VALUES (?, ?)";
            
            prepSt = conn.prepareStatement(sql);
            
            prepSt.setDouble(1, venta.getMontoVenta());
            prepSt.setDate(2, venta.getFechaVenta());
            
            prepSt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void insertarDetalleVenta(DetalleVenta detalle){
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad_vendida)"
                    + "VALUES (?, ?, ?)";
            
            prepSt = conn.prepareStatement(sql);
            
            prepSt.setInt(1, detalle.getIdVenta());
            prepSt.setInt(2, detalle.getIdProducto());
            prepSt.setDouble(3, detalle.getCantidadVendida());
            
            prepSt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void actualizarInventario(Producto producto, double cantidad){
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "UPDATE cat_productos SET existencias_prod = ?"
                    + "WHERE id_prod = ?";
            prepSt = conn.prepareStatement(sql);
            
            prepSt.setDouble(1, cantidad);
            prepSt.setString(2, producto.getIdProducto());
            
            prepSt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al hacer conexión con la bd");
        }
        
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
    public ArrayList<Producto> obtenerProductos (){
        // lista de productos
        ArrayList<Producto> listaProductos = new ArrayList<Producto>();
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "SELECT * FROM cat_productos ORDER BY nombre_prod";
            
            prepSt = conn.prepareStatement(sql);
            
            rs = prepSt.executeQuery();
            
            while(rs.next()){
                String id = rs.getString("id_prod");
                String nombre = rs.getString("nombre_prod");
                String descripcion = rs.getString("desc_prod");
                double stock = rs.getDouble("stock_prod");
                //String foto = rs.getString("foto_prod");
                String unidad = rs.getString("unidad_prod");
                double precioCompra = rs.getDouble("precio_compra_prod");
                double precioVenta = rs.getDouble("precio_venta_prod");
                double existencias = rs.getDouble("existencias_prod");
                int idCategoria = rs.getInt("id_categoria_prod");
                int idProveedor = rs.getInt("id_proveedor");
                
                Producto producto = new Producto(id, nombre, descripcion, stock, null,
                unidad, precioCompra, precioVenta, existencias, idCategoria, idProveedor);
                
                // añadir cada producto a la lista
                listaProductos.add(producto);
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaProductos;
    }
    
    public ArrayList<Producto> obtenerPorductosPorCriterio(String criterio){
        // lista de productos
        ArrayList<Producto> listaProductos = new ArrayList<Producto>();
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "SELECT * FROM cat_productos WHERE id_prod LIKE '%"+ criterio +"%'"
                    + "OR nombre_prod LIKE '%"+ criterio +"%'"
                    + "ORDER BY nombre_prod";
            
//              prepSt = conn.prepareStatement(sql);
//              rs = prepSt.executeQuery();
                st = conn.createStatement();
                rs = st.executeQuery(sql);
            
            while(rs.next()){
                String id = rs.getString("id_prod");
                String nombre = rs.getString("nombre_prod");
                String descripcion = rs.getString("desc_prod");
                double stock = rs.getDouble("stock_prod");
                //String foto = rs.getString("foto_prod");
                String unidad = rs.getString("unidad_prod");
                double precioCompra = rs.getDouble("precio_compra_prod");
                double precioVenta = rs.getDouble("precio_venta_prod");
                double existencias = rs.getDouble("existencias_prod");
                int idCategoria = rs.getInt("id_categoria_prod");
                int idProveedor = rs.getInt("id_proveedor");
                
                Producto producto = new Producto(id, nombre, descripcion, stock, null,
                unidad, precioCompra, precioVenta, existencias, idCategoria, idProveedor);
                
                // añadir cada producto a la lista
                listaProductos.add(producto);
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                st.close();
//                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaProductos;
    }
    
    public ArrayList<CategoriaProd> obtenerCategorias (){
        // lista de productos
        ArrayList<CategoriaProd> listaCategorias = new ArrayList<CategoriaProd>();
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "SELECT * FROM cat_categorias";
            
            prepSt = conn.prepareStatement(sql);
            
            rs = prepSt.executeQuery();
            
            while(rs.next()){
                
                int id = rs.getInt("id_categoria_prod");
                String nombre = rs.getString("nom_categoria_prod");
                String descripcion = rs.getString("desc_categoria_prod");
                
                CategoriaProd categoria = new CategoriaProd(id, nombre, descripcion);
                
                // añadir cada producto a la lista
                listaCategorias.add(categoria);
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
//                st.close();
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaCategorias;
    }
    
    public ArrayList<Proveedor> obtenerProveedores (){
        // lista de productos
        ArrayList<Proveedor> listaProveedores = new ArrayList<Proveedor>();
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "SELECT * FROM cat_proveedores";
            
            st = conn.prepareStatement(sql);
            
            rs = prepSt.executeQuery();
            
            while(rs.next()){
                
                int id = rs.getInt("id_proveedor");
                String nombre = rs.getString("nom_proveedor");
                String direccion = rs.getString("dir_proveedor");
                String telefono = rs.getString("telefono_proveedor");
                String email = rs.getString("email_proveedor");
                String contacto = rs.getString("contacto_proveedor");
                
                Proveedor proveedor = new Proveedor(id, nombre, direccion, telefono, email, contacto);
                
                // añadir cada producto a la lista
                listaProveedores.add(proveedor);
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaProveedores;
    }
     
    public void borrarProducto(Producto producto){
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "DELETE FROM cat_productos WHERE id_prod=?";
            
            prepSt = conn.prepareStatement(sql);
            prepSt.setString(1, producto.getIdProducto());
            
            prepSt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                prepSt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    } 
    
    public InputStream buscarFoto(Producto producto){
        InputStream streamFoto = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            String sql = "SELECT foto_prod FROM cat_productos WHERE id_prod=?";
            
            prepSt = conn.prepareStatement(sql);
            prepSt.setString(1, producto.getIdProducto());
            
            rs = prepSt.executeQuery();
            
            while(rs.next()){
                streamFoto = rs.getBinaryStream("foto_prod");
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return streamFoto;
    }
    
    public void actualizarProducto(Producto producto, boolean cambiarFoto){
        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            
            if (cambiarFoto==true) {
                File fileFoto = producto.getFotoProducto();
                FileInputStream fis = new FileInputStream(fileFoto);
                
                String sql = "UPDATE cat_producto SET desc_prod=?, stock_prod=?, foto_prod=?, unidad_prod=?"
                        + "precio_compra_prod=?, precio_venta_prod=?, id_categoria_prod=?, id_proveedor=?"
                        + "WHERE id_prod=?";
                
                prepSt = conn.prepareStatement(sql);
                
                prepSt.setString(1, producto.getDescProducto());
                prepSt.setDouble(2, producto.getStockProducto());
                long tamanoFoto = fileFoto.length();
                prepSt.setBinaryStream(3, fis, tamanoFoto);
                prepSt.setString(4, producto.getUnidadProducto());
                prepSt.setDouble(5, producto.getPrecioCompraProducto());
                prepSt.setDouble(6, producto.getPrecioVentaProducto());
                prepSt.setInt(7, producto.getIdCategoria());
                prepSt.setInt(8, producto.getIdProveedor());
                prepSt.setString(9, producto.getIdProducto());  
            }else{
                String sql = "UPDATE cat_producto SET desc_prod=?, stock_prod=?, unidad_prod=?"
                        + "precio_compra_prod=?, precio_venta_prod=?, id_categoria_prod=?, id_proveedor=?"
                        + "WHERE id_prod=?";
                
                prepSt = conn.prepareStatement(sql);
                
                prepSt.setString(1, producto.getDescProducto());
                prepSt.setDouble(2, producto.getStockProducto());
                prepSt.setString(3, producto.getUnidadProducto());
                prepSt.setDouble(4, producto.getPrecioCompraProducto());
                prepSt.setDouble(5, producto.getPrecioVentaProducto());
                prepSt.setInt(6, producto.getIdCategoria());
                prepSt.setInt(7, producto.getIdProveedor());
                prepSt.setString(8, producto.getIdProducto());  
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // PROBAR BASE DE DATOS
//    public static void main(String[] args) {
////        CategoriaProd categoria = new CategoriaProd(4, "Categoria de prueba", "Descripcion de la categoria de prueba");
//        Producto producto = new Producto("12678", "Doritos", "Doritos de 120g", 32, null, "gramos", 20, 28, 55, 2, 1);
//        BaseDatos base = new BaseDatos();
//        
//        base.insertarProducto(producto);
//    }
}
