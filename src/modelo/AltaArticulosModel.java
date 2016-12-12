/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexion.MySQLClass;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import utilerias.ConstantesGenericas;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class AltaArticulosModel {

    MySQLClass conexion;

    public AltaArticulosModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public void guardar(int pArticuloId,
            String pCodigoUpc,
            String pNombre,
            String pDescripcion,
            float pPrecio,
            float pCosto,
            File pFotoFile) {

        try {

            FileInputStream fis = null;
            if (pFotoFile != null) {
                fis = new FileInputStream(pFotoFile);
            }

            this.conexion.connect();

            //si el cliente ya existe
            ResultSet rs = conexion.execQuery(""
                    + "SELECT count(*) existe\n"
                    + "FROM articulos\n"
                    + "WHERE articuloid = " + pArticuloId);
            rs.first();

            PreparedStatement pstm;
            if (rs.getInt(1) == 1) {

                //hacemos el update
                pstm = conexion.getPrepareStatement(""
                        + "UPDATE articulos "
                        + "SET codigo = ?,nombre = ?,descripcion = ? , "
                        + "precio = ?, costo = ? , imagen = ?\n"
                        + "WHERE articuloid = " + pArticuloId);

                pstm.setString(1, Utilerias.emptyToNull(pCodigoUpc));
                pstm.setString(2, Utilerias.emptyToNull(pNombre));
                pstm.setString(3, Utilerias.emptyToNull(pDescripcion));
                pstm.setFloat(4, pPrecio);
                pstm.setFloat(5, pCosto);
                if (fis == null) {
                    pstm.setBinaryStream(6, null);
                } else {
                    pstm.setBinaryStream(6, fis, (int) pFotoFile.length());
                }

            } else {
                //hacemos el insert
                pstm = conexion.getPrepareStatement(""
                        + "INSERT INTO articulos "
                        + "            (codigo, "
                        + "             nombre, "
                        + "             descripcion, "
                        + "             precio, "
                        + "             costo, "
                        + "             imagen) "
                        + "VALUES      (?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?)");

                pstm.setString(1, Utilerias.emptyToNull(pCodigoUpc));
                pstm.setString(2, Utilerias.emptyToNull(pNombre));
                pstm.setString(3, Utilerias.emptyToNull(pDescripcion));
                pstm.setFloat(4, pPrecio);
                pstm.setFloat(5, pCosto);


                if (fis == null) {
                    pstm.setBinaryStream(6, null);
                } else {
                    pstm.setBinaryStream(6, fis, (int) pFotoFile.length());
                }

            }

            pstm.execute();

            this.conexion.disconnect();

        } catch (SQLException | FileNotFoundException | ClassNotFoundException ex) {

            Utilerias.showMessage("Error", ""+ex.getMessage(), ConstantesGenericas.CG_MESSAGE_ERROR_TYPE_INT);
        }
        this.conexion.disconnect();
    }

    public ArrayList<Map> getDatosArticulo(int pArticuloId) {

        ArrayList<Map> vlArticulosLst = new ArrayList<>();


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery("SELECT *\n"
                    + "FROM articulos\n"
                    + "WHERE articuloid = " + pArticuloId);

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

                vlArticulosLst.add(artiulosDcy);
            }


        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

        return vlArticulosLst;

    }

    public int getNextId() {

        int vlNextIdInt = 0;


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery(""
                    + "SELECT ifnull(max(articuloid)+1,1)\n"
                    + "FROM articulos");
            rs.first();
            vlNextIdInt = rs.getInt(1);

        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }



        conexion.disconnect();

        return vlNextIdInt;
    }
}
