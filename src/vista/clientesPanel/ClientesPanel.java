/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.clientesPanel;

import vista.clientesPanel.controlador.ClientesPanelController;
import vista.clientesPanel.modelo.ClientesPanelModel;
import vista.clientesPanel.vista.ClientesPanelView;
import java.util.Map;
import conexion.MySQLClass;

/**
 *
 * @author Noe
 */
public class ClientesPanel {
    
    
    //interface para delegar
    public interface ClientesPanelDelegates {
        void seleccionaCliente(Map cliente);
    }
    
    private ClientesPanelDelegates clientesPanelDelegatesIns;
    private ClientesPanelView view;
    
    public ClientesPanel(MySQLClass pConexion, ClientesPanelDelegates pClientesPanelDelegatesIns){

         ClientesPanelModel model = new ClientesPanelModel(pConexion);
         view = new ClientesPanelView();
         clientesPanelDelegatesIns = pClientesPanelDelegatesIns;
         
         new ClientesPanelController(model,view, clientesPanelDelegatesIns).iniciar();

    }
    
    public ClientesPanelView getView() {
        return view;
    }
   
    
    
}
