/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.articulosPanel;

import vista.articulosPanel.controlador.ArticulosPanelController;
import vista.articulosPanel.modelo.ArticulosPanelModel;
import vista.articulosPanel.vista.ArticulosPanelView;
import java.util.Map;
import conexion.MySQLClass;

/**
 *
 * @author Noe
 */
public class ArticulosPanel {
    
    
    //interface para delegar
    public interface ArticulosPanelDelegate {
        void seleccionaArticulo(Map articulo);
    }
    
    private  ArticulosPanelDelegate  articulosPanelDelegateIns;
    
    private ArticulosPanelView view;
    
    public ArticulosPanel(MySQLClass pConexion, ArticulosPanelDelegate pDelegate)
    {
        view = new ArticulosPanelView();
        ArticulosPanelModel model = new ArticulosPanelModel(pConexion);
        articulosPanelDelegateIns = pDelegate;
        
        new ArticulosPanelController(view,model,articulosPanelDelegateIns).iniciar();
    }
    
    public ArticulosPanelView getView() {
        return view;
    }
    
}
