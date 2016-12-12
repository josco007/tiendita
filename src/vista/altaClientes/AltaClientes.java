/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.altaClientes;

import conexion.MySQLClass;
import controlador.AltaClientesController;
import modelo.AltaClientesModel;

/**
 *
 * @author Noe
 */
public class AltaClientes {
    
    private AltaClientesView view;
    
    public AltaClientes(MySQLClass pConexion)
    {
                
        this.view = new AltaClientesView();
        AltaClientesModel model = new AltaClientesModel(pConexion);
        
        new AltaClientesController(view,model).iniciar();
    }
    
    public AltaClientesView getView() {
        return view;
    }
    
    
}
