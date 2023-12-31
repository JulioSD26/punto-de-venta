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
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:8080/db-sistema", "postgres", "admin");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void insertarProducto(Producto producto){
        String sql = "INSERT INTO cat_productos(id_prod, nombre_prod, desc_prod, stock_prod, foto_prod, unidad_prod, precio_compra_prod, S) values(?, ?, ?, ?, ?)";
        st = conn.prepareStatement(sql);
    }
    
}
