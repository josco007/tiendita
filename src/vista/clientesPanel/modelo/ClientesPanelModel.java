/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.clientesPanel.modelo;

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
public class ClientesPanelModel {

    private MySQLClass conexionMysql;

    public ClientesPanelModel(MySQLClass pConexion) {

        conexionMysql = pConexion;
    }

    public ArrayList<Map> getClientesWithNombre(String pNombre) {

        //iniciliciar el arraylist de clientes
        ArrayList<Map> vlClientesLst = new ArrayList<>();
        try {
            //consultamos los clientes que hay
            conexionMysql.connect();
            ResultSet vlResultadoRst = conexionMysql.execQuery(""
                    + "SELECT clienteid, nombre, apellidopat,\n"
                    + "apellidomat, foto, creditomax\n "
                    + "FROM clientes "
                    + "WHERE concat(nombre,ifnull(apellidopat,''), ifnull(apellidomat,'')) like '%" + pNombre + "%'"
                    + "ORDER BY clienteid");


            while (vlResultadoRst.next()) {

                Map<String, String> clientesDcy = new HashMap<>();

                clientesDcy.put("CLIENTEID", vlResultadoRst.getString(1));
                clientesDcy.put("NOMBRE", vlResultadoRst.getString(2));
                clientesDcy.put("APELLIDOPAT", vlResultadoRst.getString(3));
                clientesDcy.put("APELLIDOMAT", vlResultadoRst.getString(4));

                //se lee la cadena de bytes de la base de datos
                byte[] vlFotoClienteBte = vlResultadoRst.getBytes(5);
                // esta cadena de bytes sera convertida en una imagen
                Image vlFotoClienteImg = Utilerias.toImagen(vlFotoClienteBte, "jpg");
                //codificamos la imagen en cadena para poderla meter en el hasmap
                String vlFotoClienteStr = Utilerias.encodeToString(Utilerias.toBufferedImage(vlFotoClienteImg), "jpg");
                clientesDcy.put("FOTO", vlFotoClienteStr);
                
                clientesDcy.put("CREDITOMAX", vlResultadoRst.getString(6));

                vlClientesLst.add(clientesDcy);

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        conexionMysql.disconnect();

        return vlClientesLst;
    }
}
