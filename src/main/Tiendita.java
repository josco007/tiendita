/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import vista.Principal.PrincipalView;
import conexion.MySQLClass;
import controlador.AltaClientesController;
import controlador.LoginController;
import controlador.LoginController.LoginDelegate;
import controlador.PrincipalController;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.AltaClientesModel;
import modelo.PrincipalModel;
import vista.LoginView;
import vista.altaClientes.AltaClientesView;

/**
 *
 * @author Noe
 */
public class Tiendita implements LoginDelegate {

    private void iniciar() {
        LoginView view = new LoginView();
        LoginController loginControllerIns = new LoginController(view);
        loginControllerIns.setDelegate(this);

        loginControllerIns.iniciar();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Tiendita().iniciar();

    }

    @Override
    public void loginController(LoginController loginController, String host, String usuario, String password) {
        
        //instanciar una conexion
        MySQLClass conexion = new MySQLClass(host, "tiendita", usuario, password);
        try {
            //nos conectamos para ver si no hay ningun erro
            conexion.connect();

            //nos desconectamos
            conexion.disconnect();

            //iniciamos la pantalla principal
            PrincipalView principalView = new PrincipalView();
            PrincipalModel principalModel = new PrincipalModel(conexion);

            new PrincipalController(principalView, principalModel).iniciar();
            
            //quitar la ventana del login
            loginController.cerrarLogin();

        } catch (ClassNotFoundException | SQLException ex) {
           utilerias.Utilerias.showMessage("Alerta", ""+ex, JOptionPane.WARNING_MESSAGE);
        }
    }
}
