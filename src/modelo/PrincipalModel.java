/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexion.MySQLClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class PrincipalModel {

    MySQLClass conexion;

    public PrincipalModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public MySQLClass getConexion() {
        return conexion;
    }

    public void crearAlmacenGeneral() {


        try {
            conexion.connect();
            //chacar si existe el almacen general
            ResultSet rs = conexion.execQuery(""
                    + "SELECT count(*) existe\n"
                    + "FROM almacenes\n"
                    + "WHERE  almacenid = 1");

            rs.first();
            //si no existe lo creamos
            if (rs.getInt(1) == 0) {

                conexion.execInstruc(""
                        + "INSERT\n"
                        + "INTO almacenes (almacenid, nombre, descripcion)\n"
                        + "VALUES(1,'Almacen General','Almacen General')");

            }



        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }

        conexion.disconnect();


    }
}
