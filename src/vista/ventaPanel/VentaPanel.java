/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ventaPanel;

import vista.clientesPanel.ClientesPanel;
import java.util.Map;
import conexion.MySQLClass;
import vista.ventaPanel.controlador.VentaPanelController;
import vista.ventaPanel.modelo.VentaPanelModel;
import vista.ventaPanel.vista.VentaPanelView;

/**
 *
 * @author Noe
 */
public class VentaPanel {
    
    
      //interface para delegar
    public interface VentaPanelDelegate {
        void ventaPanel(VentaPanel ventaPanel, Map articulo);
    }
    
    private VentaPanelDelegate ventaPanelDelegateIns;
    private VentaPanelView view;
    private VentaPanelController controller;
 
    public VentaPanel(MySQLClass pConexion, VentaPanelDelegate pClientesPanelDelegatesIns ){
        view = new VentaPanelView();
        VentaPanelModel model = new VentaPanelModel(pConexion);
        
        controller =  new VentaPanelController(view, model, ventaPanelDelegateIns);
        controller.iniciar();
         
        
    }
    
    public VentaPanelView getView() {
        return view;
    }
    
    public void addArticulo(Map pArticulo){
        
        controller.addArticulo(pArticulo);
        
    }
    
    public void loadDeuda(Map pCliente){
        controller.loadDeuda(pCliente);
    }

}
