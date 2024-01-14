package sistema.datos;

import Sistema.pojos.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    PreparedStatement st = null;
    ResultSet rs = null;
    
    public BaseDatos(){
        try {
            Class.forName("org.postgresql.Driver");
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void insertarProducto(Producto producto){
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db-sistema", "postgres", "admin");
            FileInputStream fis = new FileInputStream(producto.getFotoProducto());
            
            String sql = "INSERT INTO cat_productos(id_prod, nombre_prod, desc_prod, stock_prod, foto_prod, unidad_prod, precio_compra_prod, precio_venta_prod, existencias_prod, id_categoria_prod, id_proveedor)"
                    + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            
            st.setString(1, producto.getIdProducto());
            st.setString(2, producto.getNomProducto());
            st.setString(3, producto.getDescProducto());
            st.setDouble(4, producto.getStockProducto());
            st.setBinaryStream(5, fis, (int)producto.getFotoProducto().length());
            st.setString(6, producto.getUnidadProducto());
            st.setDouble(7, producto.getPrecioCompraProducto());
            st.setDouble(8, producto.getPrecioVentaProducto());
            st.setDouble(9, producto.getExistenciasProducto());
            st.setInt(10, producto.getIdCategoria());
            st.setInt(11, producto.getIdProveedor());
            
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                st.close();
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
            st = conn.prepareStatement(sql);
            
            st.setString(1, categoria.getNomCategoriaProd());
            st.setString(2, categoria.getDescCategoriaProd());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        finally{
            try {
                st.close();
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
            
            st = conn.prepareStatement(sql);
            
            st.setString(1, prov.getNomProveedor());
            st.setString(2, prov.getDirProveedor());
            st.setString(3, prov.getTelProveedor());
            st.setString(4, prov.getEmailProveedor());
            st.setString(5, prov.getContactoProveedor());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                st.close();
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
            
            st = conn.prepareStatement(sql);
            
            st.setDouble(1, venta.getMontoVenta());
            st.setDate(2, venta.getFechaVenta());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                st.close();
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
            
            st = conn.prepareStatement(sql);
            
            st.setInt(1, detalle.getIdVenta());
            st.setInt(2, detalle.getIdProducto());
            st.setDouble(3, detalle.getCantidadVendida());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                st.close();
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
            
            String sql = "SELECT * FROM cat_productos";
            
            st = conn.prepareStatement(sql);
            
            rs = st.executeQuery();
            
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
            
            st = conn.prepareStatement(sql);
            
            rs = st.executeQuery();
            
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
                st.close();
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
            
            rs = st.executeQuery();
            
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
                st.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaProveedores;
    }
    // PROBAR BASE DE DATOS
//    public static void main(String[] args) {
//        CategoriaProd categoria = new CategoriaProd(1, "Categoria de prueba", "Descripcion de la categoria de prueba");
//        
//        BaseDatos base = new BaseDatos();
//        
//        base.insertarCategoriaProducto(categoria);
//    }
}
