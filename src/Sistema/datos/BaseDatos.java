package sistema.datos;

import Sistema.pojos.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    // PROBAR BASE DE DATOS
//    public static void main(String[] args) {
//        CategoriaProd categoria = new CategoriaProd(1, "Categoria de prueba", "Descripcion de la categoria de prueba");
//        
//        BaseDatos base = new BaseDatos();
//        
//        base.insertarCategoriaProducto(categoria);
//    }
}
