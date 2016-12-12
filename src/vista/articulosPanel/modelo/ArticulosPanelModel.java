/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.articulosPanel.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import conexion.MySQLClass;
import java.awt.Image;
import javax.swing.JOptionPane;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class ArticulosPanelModel {

    MySQLClass conexionMysql;

    public ArticulosPanelModel(MySQLClass pConexion) {
        conexionMysql = pConexion;
    }

    public ArrayList<Map> getArticulosWithDesc(String pDesc) {

        ArrayList<Map> vlArticulosLst = new ArrayList<>();

        try {
            //consultamos los clientes que hay
            conexionMysql.connect();
            ResultSet vlResultadoRst = conexionMysql.execQuery("SELECT * "
                    + "FROM articulos "
                    + "WHERE descripcion like '%" + pDesc + "%'"
                    + "ORDER BY articuloid");

            while (vlResultadoRst.next()) {

                Map<String, String> articulosDcy = new HashMap<>();

                articulosDcy.put("ARTICULOID", Utilerias.nullToEmptyString(vlResultadoRst.getString(1)));
                articulosDcy.put("CODIGO", Utilerias.nullToEmptyString(vlResultadoRst.getString(2)));
                articulosDcy.put("NOMBRE", Utilerias.nullToEmptyString(vlResultadoRst.getString(3)));
                articulosDcy.put("DESCRIPCION", Utilerias.nullToEmptyString(vlResultadoRst.getString(4)));
                articulosDcy.put("PRECIO", Utilerias.nullToEmptyString(vlResultadoRst.getString(5)));
                articulosDcy.put("COSTO", Utilerias.nullToEmptyString(vlResultadoRst.getString(6)));

                //se lee la cadena de bytes de la base de datos
                byte[] vlImagenBte = vlResultadoRst.getBytes(7);
                // esta cadena de bytes sera convertida en una imagen
                Image vlImagenImg = Utilerias.toImagen(vlImagenBte, "jpg");
                //codificamos la imagen en cadena para poderla meter en el hasmap
                String vlImagenStr = Utilerias.encodeToString(Utilerias.toBufferedImage(vlImagenImg), "jpg");
                articulosDcy.put("IMAGEN", vlImagenStr);

                vlArticulosLst.add(articulosDcy);

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        conexionMysql.disconnect();


        return vlArticulosLst;

    }
}
