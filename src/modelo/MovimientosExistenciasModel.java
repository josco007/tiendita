/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexion.MySQLClass;
import java.awt.Image;
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
public class MovimientosExistenciasModel {

    MySQLClass conexion;

    public MovimientosExistenciasModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public MySQLClass getConexion() {
        return conexion;
    }

    public ArrayList<Map> getAlmacenes() {

        ArrayList<Map> vlAlmacenesLst = new ArrayList<>();


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery("SELECT *\n"
                    + "FROM almacenes");

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

    public ArrayList<Map> getDatosArticulo(int pAlmacenId, int pArticuloId) {

        ArrayList<Map> vlArticulosLst = new ArrayList<>();


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery(""
                    + "SELECT a.articuloid, "
                    + "       a.codigo, "
                    + "       a.nombre, "
                    + "       a.descripcion, "
                    + "       a.precio, "
                    + "       a.costo, "
                    + "       a.imagen, "
                    + "       e.almacenid, "
                    + "       Ifnull(e.cantidad, 0) cantidad "
                    + "FROM   articulos a "
                    + "       LEFT OUTER JOIN existencias e "
                    + "                    ON e.articuloid =  " + pArticuloId + "\n"
                    + "                    AND e.almacenid =  " + pAlmacenId + "\n"
                    + "WHERE  a.articuloid = " + pArticuloId);

            while (rs.next()) {
                Map<String, String> artiulosDcy = new HashMap<>();

                artiulosDcy.put("ARTICULOID", Utilerias.nullToEmptyString(rs.getString(1)));
                artiulosDcy.put("CODIGO", Utilerias.nullToEmptyString(rs.getString(2)));
                artiulosDcy.put("NOMBRE", Utilerias.nullToEmptyString(rs.getString(3)));
                artiulosDcy.put("DESCRIPCION", Utilerias.nullToEmptyString(rs.getString(4)));
                artiulosDcy.put("PRECIO", Utilerias.nullToEmptyString(rs.getString(5)));
                artiulosDcy.put("COSTO", Utilerias.nullToEmptyString(rs.getString(6)));

                //se lee la cadena de bytes de la base de datos
                byte[] vlFotoClienteBte = rs.getBytes(7);
                // esta cadena de bytes sera convertida en una imagen
                Image vlFotoClienteImg = Utilerias.toImagen(vlFotoClienteBte, "jpg");
                //codificamos la imagen en cadena para poderla meter en el hasmap
                String vlFotoClienteStr = Utilerias.encodeToString(Utilerias.toBufferedImage(vlFotoClienteImg), "jpg");
                artiulosDcy.put("IMAGEN", vlFotoClienteStr);

                artiulosDcy.put("ALMACENID", rs.getString(8));
                artiulosDcy.put("CANTIDAD", rs.getString(9));

                vlArticulosLst.add(artiulosDcy);
            }


        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

        return vlArticulosLst;

    }

    public void guardarTodo(int pAlmacenId, int pArticuloId, int pCantidad, String pTipoMov) {
        System.out.println("guardar todo existenciaId"
                + "almacenid = " + pAlmacenId
                + "articuloid = " + pArticuloId
                + "cantidad = " + pCantidad);


        try {
            conexion.connect();
            //ver si existe la existencia
            ResultSet rs = conexion.execQuery(""
                    + "SELECT count(*) existe\n"
                    + "FROM existencias\n"
                    + "WHERE   almacenid = " + pAlmacenId + "\n"
                    + "AND   articuloid = " + pArticuloId);
            rs.first();
            //si no existe
            if (rs.getInt(1) == 0) {
                conexion.execInstruc(""
                        + "INSERT INTO existencias "
                        + "            (almacenid, "
                        + "             articuloid, "
                        + "             cantidad, "
                        + "             tipomov) "
                        + "VALUES      (" + pAlmacenId + ", "
                        + "             " + pArticuloId + ", "
                        + "             " + pCantidad + ", "
                        + "             '" + pTipoMov + "')");
            } else {
                //si existe
                conexion.execInstruc(""
                        + "UPDATE existencias "
                        + "SET    cantidad =  cantidad + " + pCantidad + ",\n"
                        + "tipomov = '" + pTipoMov + "'\n"
                        + "WHERE almacenid = " + pAlmacenId + "\n "
                        + "AND articuloid = " + pArticuloId + "\n");
            }


        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }

        conexion.disconnect();

    }
}
