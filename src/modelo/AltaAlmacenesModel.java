/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexion.MySQLClass;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class AltaAlmacenesModel {

    MySQLClass conexion;

    public AltaAlmacenesModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public void guardar(int pAlmacenId,
            String pNombre,
            String pDescripcion) {

        try {


            this.conexion.connect();

            //si el cliente ya existe
            ResultSet rs = conexion.execQuery(""
                    + "SELECT count(*) existe\n"
                    + "FROM almacenes\n"
                    + "WHERE almacenid = " + pAlmacenId);
            rs.first();

            PreparedStatement pstm;
            if (rs.getInt(1) == 1) {

                //hacemos el update
                pstm = conexion.getPrepareStatement(""
                        + "UPDATE almacenes "
                        + "SET nombre = ?,descripcion = ? \n"
                        + "WHERE almacenid = " + pAlmacenId);

                pstm.setString(1, Utilerias.emptyToNull(pNombre));
                pstm.setString(2, Utilerias.emptyToNull(pDescripcion));

            } else {
                //hacemos el insert
                pstm = conexion.getPrepareStatement(""
                        + "INSERT INTO almacenes "
                        + "            (nombre, "
                        + "             descripcion) "
                        + "VALUES      (?, "
                        + "             ?)");

                pstm.setString(1, Utilerias.emptyToNull(pNombre));
                pstm.setString(2, Utilerias.emptyToNull(pDescripcion));


            }

            pstm.execute();


        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        this.conexion.disconnect();
    }

    public ArrayList<Map> getDatosAlmacen(int pAlmacenId) {

        ArrayList<Map> vlAlmacenesLst = new ArrayList<>();


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery("SELECT *\n"
                    + "FROM almacenes\n"
                    + "WHERE almacenid = " + pAlmacenId);

            while (rs.next()) {
                Map<String, String> almacenesDcy = new HashMap<>();

                almacenesDcy.put("ALMACENID", rs.getString(1));
                almacenesDcy.put("NOMBRE", rs.getString(2));
                almacenesDcy.put("DESCRIPCION", rs.getString(3));

                vlAlmacenesLst.add(almacenesDcy);
            }


        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

        return vlAlmacenesLst;

    }

    public int getNextId() {

        int vlNextIdInt = 0;


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery(""
                    + "SELECT ifnull(max(almacenid)+1,1)\n"
                    + "FROM almacenes");
            rs.first();
            vlNextIdInt = rs.getInt(1);

        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }



        conexion.disconnect();

        return vlNextIdInt;
    }
}
