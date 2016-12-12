/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ventaPanel.modelo;

import conexion.MySQLClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class VentaPanelModel {

    MySQLClass conexion;

    public VentaPanelModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public boolean tieneDedua(int pClienteId, AtomicInteger pDeudaId) {

try{
        conexion.connect();

        ResultSet rs = conexion.execQuery("SELECT deudaid\n"
                + "FROM deudaxcliente\n"
                + "WHERE clienteid = " + pClienteId+"\n"
                +"AND liquidada = 0");

        while (rs.next()) {

            if (rs.getInt(1) != 0) {
                pDeudaId.set(rs.getInt(1));
                System.out.println("deuda id caca = " + pDeudaId);
                return true;
            }
        }
 } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

        return false;

    }

    public void addDeuda(int pClienteId)  {

        try{
        conexion.connect();

        conexion.execInstruc("INSERT\n"
                + "INTO deudaxcliente (clienteid,liquidada)\n"
                + "VALUES (" + pClienteId + ",0)");
        
         } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

    }

    public ArrayList<Map> getDetDeuda(int pDeudaId) {
        System.out.println("getDeuda deudaId = " + pDeudaId);

        //iniciliciar el arraylist de la deuda
        ArrayList<Map> vlDeudaLst = new ArrayList<>();

        try{
        //consultamos los clientes que hay
        conexion.connect();
        ResultSet vlResultadoRst = conexion.execQuery(""
                + "SELECT * "
                + "FROM  (SELECT a.descripcion, "
                + "              d.cantidad, "
                + "              d.precio, "
                + "              d.cantidad * d.precio total, "
                + "              d.deudaid, "
                + "              d.partida, "
                + "              d.fecha "
                + "       FROM   detdeudaxcliente d, "
                + "              articulos a "
                + "       WHERE  d.deudaid = " + pDeudaId
                + "              AND a.articuloid = d.articuloid "
                + "       UNION "
                + "       SELECT 'pago'   descripcion, "
                + "              1        cantidad, "
                + "              p.pago*-1   precio, "
                + "              p.pago*-1   total, "
                + "              p.deudaid, "
                + "              p.pagoid partida, "
                + "              p.fecha "
                + "       FROM   pagos p "
                + "       WHERE  p.deudaid = " + pDeudaId + ""
                + "       UNION "
                + "       SELECT a.descripcion, "
                + "       d.cantidad, "
                + "       d.precio, "
                + "       d.cantidad * d.precio total, "
                + "       d.deudaid, "
                + "       d.partida, "
                + "       d.fecha "
                + "FROM   detdeudaxcliente d, "
                + "       articuloscomodin a "
                + "WHERE  d.deudaid = " + pDeudaId + "\n"
                + "       AND a.articulocomid = d.articulocomid ) q "
                + "ORDER  BY q.fecha");


        while (vlResultadoRst.next()) {

            Map<String, String> deudaDcy = new HashMap<>();

            deudaDcy.put("DESCRIPCION", Utilerias.nullToEmptyString(vlResultadoRst.getString(1)));
            deudaDcy.put("CANTIDAD", Utilerias.nullToEmptyString(vlResultadoRst.getString(2)));
            deudaDcy.put("PRECIO", Utilerias.nullToEmptyString(vlResultadoRst.getString(3)));
            deudaDcy.put("TOTAL", Utilerias.nullToEmptyString(vlResultadoRst.getString(4)));
            deudaDcy.put("DEUDAID", Utilerias.nullToEmptyString(vlResultadoRst.getString(5)));
            deudaDcy.put("PARTIDA", Utilerias.nullToEmptyString(vlResultadoRst.getString(6)));
            deudaDcy.put("FECHA", Utilerias.nullToEmptyString(vlResultadoRst.getString(7)));

            vlDeudaLst.add(deudaDcy);

        }

         } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();

        return vlDeudaLst;
    }

    public void addArticuloDetDeudaXCliente(int pDeudaId, int pArticuloId, int pCantidad, float pPrecio, float pCosto) {

        try{
        conexion.connect();

        conexion.execInstruc("INSERT\n"
                + "INTO detdeudaxcliente (deudaid,articuloid, cantidad, almacenid, precio, costo)\n"
                + "VALUES (" + pDeudaId + "," + pArticuloId + "," + pCantidad + ",1, "+pPrecio+","+pCosto+")");

         } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
        conexion.disconnect();


    }

    public void addAnyArticuloToDetDeudaXCliente(String pNombre,
            String pDescripcion,
            float pCosto,
            float pPrecio,
            int pDeudaId,
            int pCantidad) {

        
        try {
conexion.connect();
            //insertar un articulo comodin
            conexion.execInstruc("INSERT\n"
                    + "INTO articulosComodin (nombre, descripcion, precio, costo)\n"
                    + "VALUES ('" + pNombre + "','" + pDescripcion + "'," + pPrecio + ","+pCosto+")");

            //obtener el id que se inserto
            ResultSet rs = conexion.execQuery(""
                    + "SELECT max(articulocomid)\n"
                    + "FROM articuloscomodin");
            rs.first();

            //insertar el articulo a la deuda
            conexion.execInstruc("INSERT\n"
                    + "INTO detdeudaxcliente (deudaid, cantidad, articulocomid, precio, costo)\n"
                    + "VALUES (" + pDeudaId + "," + pCantidad + "," + rs.getInt(1) + ","+pPrecio+","+pCosto+")");

         } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }

        conexion.disconnect();


    }

    public void updateCantidad(int pDeudaId, int pPartida, int pCantidad) {
        try {
            conexion.connect();

            System.out.println("UPDATE detdeudaxcliente\n"
                    + "SET cantidad = " + pCantidad + "\n"
                    + "WHERE deudaId = " + pDeudaId + "\n"
                    + "AND partida = " + pPartida);

            conexion.execInstruc("UPDATE detdeudaxcliente\n"
                    + "SET cantidad = " + pCantidad + "\n"
                    + "WHERE deudaId = " + pDeudaId + "\n"
                    + "AND partida = " + pPartida);

             } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
            conexion.disconnect();
       


    }

    public void deleteArticuloDetDeudaXCliente(int pDeudaId, int pPartidaId) {

        
        try {
            conexion.connect();
            conexion.execInstruc("DELETE\n"
                    + "FROM detdeudaxcliente\n"
                    + "WHERE deudaid = " + pDeudaId + "\n"
                    + "AND   partida = " + pPartidaId);
             } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
            conexion.disconnect();

        

    }

    public void deletePago(int pDeudaId, int pPagoId) {

        
        try {
            conexion.connect();
            conexion.execInstruc("DELETE\n"
                    + "FROM pagos\n"
                    + "WHERE deudaid = " + pDeudaId + "\n"
                    + "AND   pagoid = " + pPagoId);
             } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
            conexion.disconnect();

  

    }

    public void addPago(int pDeudaId, float pPago) {

       
        try { 
            conexion.connect();
            conexion.execInstruc("INSERT\n"
                    + "INTO pagos (deudaid, pago)\n"
                    + "VALUES (" + pDeudaId + "," + pPago + ")");
 } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
            conexion.disconnect();
       


    }

    public int getDeudaId(int pClienteId) {


       

        ResultSet rs;
        try {
            conexion.connect();
            rs = conexion.execQuery("SELECT deudaid\n"
                    + "FROM deudaxcliente\n"
                    + "WHERE clienteid = " + pClienteId+"\n"
                    + "AND liquidada = 0");


            while (rs.next()) {

                return rs.getInt(1);

            }

         } catch (SQLException |ClassNotFoundException ex) {
            Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
        }
            conexion.disconnect();
        


        return 0;


    }
}
