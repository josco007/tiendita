/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexion.MySQLClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class ResultadosModel {

    MySQLClass conexion;

    public ResultadosModel(MySQLClass pConexion) {
        this.conexion = pConexion;
    }

    public MySQLClass getConexion() {
        return conexion;
    }

    
    public float getTotalEntradas() {

        float vlResultadoFlt = 0;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Sum(costo*cantidad) total \n"
                    + "FROM   historicoensa\n"
                    + "WHERE  tipo = 'EN'");

            rs.first();
            vlResultadoFlt = rs.getFloat(1);

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlResultadoFlt;
        }

    }
    
    public Map<String, String> getTotalesInventario() {

        Map<String, String> vlTotalesInv = null;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Sum(a.costo * Ifnull(e.cantidad, 1))  totalSinGanancia, "
                    + "       Sum(a.precio * Ifnull(e.cantidad, 1)) totalConGanancia, "
                    + "       Sum(a.precio * Ifnull(e.cantidad, 1)) - Sum(a.costo * "
                    + "                                                   Ifnull(e.cantidad, 1)) "
                    + "                                             gananciaFutura "
                    + "FROM   articulos a "
                    + "       LEFT OUTER JOIN existencias e "
                    + "                    ON a.articuloid = e.articuloid");

            rs.first();
            vlTotalesInv = new HashMap<>();
            vlTotalesInv.put("SIN GANANCIA", rs.getString(1));
            vlTotalesInv.put("CON GANANCIA", rs.getString(2));

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlTotalesInv;
        }

    }

    public float getTotalVendidoConDeudas() {

        float vlResultadoFlt = 0;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Sum(precio * cantidad) ventaTotal "
                    + "FROM   detdeudaxcliente");

            rs.first();
            vlResultadoFlt = rs.getFloat(1);

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlResultadoFlt;
        }

    }

    public float getTotalRecibido() {

        float vlResultadoFlt = 0;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Ifnull(Sum(pago), 0) recibido "
                    + "FROM   pagos");

            rs.first();
            vlResultadoFlt = rs.getFloat(1);

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlResultadoFlt;
        }

    }

    public float getTotalDeudas() {

        float vlResultadoFlt = 0;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Ifnull((SELECT Sum(precio * cantidad) "
                    + "               FROM   detdeudaxcliente), 0) - Ifnull((SELECT Sum(pago) "
                    + "                                                      FROM   pagos), 0) "
                    + "       deudatotal");

            rs.first();
            vlResultadoFlt = rs.getFloat(1);

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlResultadoFlt;
        }

    }

    public float getGananciaEsperada() {

        float vlResultadoFlt = 0;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Sum(precio * cantidad) - Sum(costo * cantidad) gananciaEsperada "
                    + "FROM   detdeudaxcliente");

            rs.first();
            vlResultadoFlt = rs.getFloat(1);

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlResultadoFlt;
        }

    }

    public Map<String, String> getGanancias() {

        Map<String, String> vlResultadoMap = null;

        try {
            conexion.connect();

            ResultSet rs = conexion.execQuery(""
                    + "SELECT Ifnull((SELECT Sum(pago) "
                    + "               FROM   pagos), 0) - (SELECT Sum(precio * cantidad) - "
                    + "                                           Sum(costo * cantidad) "
                    + "                                    FROM   detdeudaxcliente) gananciaactual, "
                    + "       Ifnull((SELECT Sum(pago) "
                    + "               FROM   pagos), 0) - (SELECT Sum(precio * cantidad) "
                    + "                                    FROM   detdeudaxcliente) gananciaYrecupe");
            rs.first();
            vlResultadoMap = new HashMap<>();
            vlResultadoMap.put("GANANCIA", rs.getString(1));
            vlResultadoMap.put("GANANCIA Y RECUPERACION", rs.getString(2));

        } catch (ClassNotFoundException | SQLException ex) {
            Utilerias.showMessage("Error", "" + ex, JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
            return vlResultadoMap;
        }

    }
}
