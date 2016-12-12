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
public class AltaClientesModel {

    MySQLClass conexion;

    public AltaClientesModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public void guardar(int pClienteId,
            String pNombre,
            String pApellidoPat,
            String pApellidoMat,
            String pEmail,
            String pUsuario,
            char[] pPassWord,
            File pFotoFile, 
            String pInfo, 
            float pCreditoMax) {

        try {

            FileInputStream fis = null;
            if (pFotoFile != null) {
                fis = new FileInputStream(pFotoFile);
            }

            this.conexion.connect();

            //si el cliente ya existe
            ResultSet rs = conexion.execQuery(""
                    + "SELECT count(*) existe\n"
                    + "FROM clientes\n"
                    + "WHERE clienteid = " + pClienteId);
            rs.first();

            PreparedStatement pstm;
            if (rs.getInt(1) == 1) {

                //hacemos el update
                pstm = conexion.getPrepareStatement(""
                        + "UPDATE clientes "
                        + "SET nombre = ?,apellidopat = ?,apellidomat = ? , "
                        + "email = ?, usuario = ? , foto = ? , password = ?, info = ?, creditomax = ?\n"
                        + "WHERE clienteid = " + pClienteId);

                pstm.setString(1, Utilerias.emptyToNull(pNombre));
                pstm.setString(2, Utilerias.emptyToNull(pApellidoPat));
                pstm.setString(3, Utilerias.emptyToNull(pApellidoMat));
                pstm.setString(4, Utilerias.emptyToNull(pEmail));
                pstm.setString(5, Utilerias.emptyToNull(pUsuario));

                if (fis == null) {
                    pstm.setBinaryStream(6, null);
                } else {
                    pstm.setBinaryStream(6, fis, (int) pFotoFile.length());
                }
                
                if (Utilerias.emptyToNull(String.valueOf(pPassWord)) != null) {
                    pstm.setString(7, String.valueOf(pPassWord));
                } else {
                    pstm.setBinaryStream(7, null);
                }
                
                pstm.setString(8,  Utilerias.emptyToNull(pInfo));
                pstm.setFloat(9,  pCreditoMax);
                
                

            } else {
                //hacemos el insert
                pstm = conexion.getPrepareStatement(""
                        + "INSERT INTO clientes "
                        + "            (nombre, "
                        + "             apellidopat, "
                        + "             apellidomat, "
                        + "             email, "
                        + "             usuario, "
                        + "             password, "
                        + "             foto, info, creditomax) "
                        + "VALUES      (?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?, "
                        + "             ?,?,?)");

                pstm.setString(1, Utilerias.emptyToNull(pNombre));
                pstm.setString(2, Utilerias.emptyToNull(pApellidoPat));
                pstm.setString(3, Utilerias.emptyToNull(pApellidoMat));
                pstm.setString(4, Utilerias.emptyToNull(pEmail));
                pstm.setString(5, Utilerias.emptyToNull(pUsuario));

                if (Utilerias.emptyToNull(String.valueOf(pPassWord)) != null) {
                    pstm.setString(6, String.valueOf(pPassWord));
                } else {
                    pstm.setBinaryStream(6, null);
                }

                if (fis == null) {
                    pstm.setBinaryStream(7, null);
                } else {
                    pstm.setBinaryStream(7, fis, (int) pFotoFile.length());
                }
                
                pstm.setString(8,  Utilerias.emptyToNull(pInfo));
                pstm.setFloat(9, pCreditoMax);

            }

            pstm.execute();



        } catch (SQLException | FileNotFoundException | ClassNotFoundException ex) {
            this.conexion.disconnect();
            Utilerias.showMessage("Error", ex.toString(), ConstantesGenericas.CG_MESSAGE_ERROR_TYPE_INT);

        }
        this.conexion.disconnect();

    }

    public ArrayList<Map> getDatosCliente(int pClienteId) {

        ArrayList<Map> vlClientesLst = new ArrayList<>();


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery("SELECT clienteid, nombre, apellidopat,\n"
                    +"apellidomat, email, usuario, password,\n"
                    +"foto, info, creditomax\n"
                    + "FROM clientes\n"
                    + "WHERE clienteid = " + pClienteId);

            while (rs.next()) {
                Map<String, String> clientesDcy = new HashMap<>();

                clientesDcy.put("CLIENTEID", Utilerias.nullToEmptyString(rs.getString(1)));
                clientesDcy.put("NOMBRE", Utilerias.nullToEmptyString(rs.getString(2)));
                clientesDcy.put("APELLIDOPAT", Utilerias.nullToEmptyString(rs.getString(3)));
                clientesDcy.put("APELLIDOMAT", Utilerias.nullToEmptyString(rs.getString(4)));
                clientesDcy.put("EMAIL", Utilerias.nullToEmptyString(rs.getString(5)));
                clientesDcy.put("USUARIO", Utilerias.nullToEmptyString(rs.getString(6)));

                //se lee la cadena de bytes de la base de datos
                byte[] vlFotoClienteBte = rs.getBytes(8);
                // esta cadena de bytes sera convertida en una imagen
                Image vlFotoClienteImg = Utilerias.toImagen(vlFotoClienteBte, "jpg");
                //codificamos la imagen en cadena para poderla meter en el hasmap
                String vlFotoClienteStr = Utilerias.encodeToString(Utilerias.toBufferedImage(vlFotoClienteImg), "jpg");
                clientesDcy.put("FOTO", vlFotoClienteStr);
                
                clientesDcy.put("INFO", Utilerias.nullToEmptyString(rs.getString(9)));
                clientesDcy.put("CREDITOMAX", Utilerias.nullToEmptyString(rs.getString(10)));

                vlClientesLst.add(clientesDcy);
            }


        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

        return vlClientesLst;

    }

    public int getNextId() {

        int vlNextIdInt = 1;


        try {
            conexion.connect();
            ResultSet rs = conexion.execQuery(""
                    + "SELECT ifnull(max(clienteid)+1,1)\n"
                    + "FROM clientes");
            rs.first();
            vlNextIdInt = rs.getInt(1);

        } catch (SQLException | ClassNotFoundException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        }



        conexion.disconnect();

        return vlNextIdInt;
    }
}
