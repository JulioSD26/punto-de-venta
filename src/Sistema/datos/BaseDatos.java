package sistema.datos;

import Sistema.pojos.Producto;
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
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:8080/db-sistema", "postgres", "admin");
            
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
    
}
