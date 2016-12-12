/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.*;
import vista.Principal.PrincipalView;
import vista.articulosPanel.ArticulosPanel;
import vista.articulosPanel.ArticulosPanel.ArticulosPanelDelegate;
import vista.clientesPanel.ClientesPanel;
import vista.clientesPanel.ClientesPanel.ClientesPanelDelegates;
import vista.ventaPanel.VentaPanel;
import vista.ventaPanel.VentaPanel.VentaPanelDelegate;
import utilerias.ConstantesGenericas;
import utilerias.CustomFocusTraversalPolicy;
import vista.AltaAlmacenesView;
import vista.AltaArticulosView;
import vista.MovimientosExistenciasView;
import vista.ResultadosView;
import vista.altaClientes.AltaClientes;
import vista.altaClientes.AltaClientesView;

/**
 *
 * @author Noe
 */
public class PrincipalController implements ArticulosPanelDelegate, ClientesPanelDelegates, VentaPanelDelegate {

    private PrincipalView view;
    private PrincipalModel model;
    private VentaPanel ventaPanelIns;
    private AltaClientes altaClientesIns;

    public PrincipalController(PrincipalView pView, PrincipalModel pModel) {


        this.view = pView;
        this.model = pModel;

        init();
    }

    private void init() {

             //agregamos a la pestana 1 los siguentes paneles:
        //agregamos el panel de clientes a la vista principal
        ClientesPanel clientesPanelIns = new ClientesPanel(this.model.getConexion(), this);
        this.view.principalTab1Pnl.add(clientesPanelIns.getView());

        //agregamos el panel de articulos a la ventana
        ArticulosPanel articulosPanelIns = new ArticulosPanel(this.model.getConexion(), this);
        this.view.principalTab1Pnl.add(articulosPanelIns.getView());

        //agregamos el panel de la venta
        ventaPanelIns = new VentaPanel(this.model.getConexion(), this);
        this.view.principalTab1Pnl.add(ventaPanelIns.getView());
        
        //agregamos a la pestana 2 la vista para dar de alta clientes
        altaClientesIns = new AltaClientes(this.model.getConexion());
        this.view.principalTab2Pnl.add(altaClientesIns.getView());
        
        //agregamos a la pestana 3 la vista para dar de alta articulos
        AltaArticulosView altaArticulosViewIns = new AltaArticulosView();
        AltaArticulosModel altaArticulosModelIns = new AltaArticulosModel(this.model.getConexion());
        new AltaArticulosController(altaArticulosViewIns, altaArticulosModelIns).iniciar();
        this.view.principarlTab3Pnl.add(altaArticulosViewIns);
        
        //agregamos a la pestana almacens la vista para dar de alta almacens
        AltaAlmacenesView altaAlmacenesViewIns = new AltaAlmacenesView();
        AltaAlmacenesModel altaAlmacenesModelIns = new AltaAlmacenesModel(this.model.getConexion());
        new AltaAlmacenesController(altaAlmacenesViewIns, altaAlmacenesModelIns).iniciar();
        this.view.principalTabAlmacenesPnl.add(altaAlmacenesViewIns);
        
        //agregamos a la pestana moviemintos de existencias
        MovimientosExistenciasView movimientosExistenciasViewIns = new MovimientosExistenciasView();
        MovimientosExistenciasModel movimientosExistenciasModelIns = new MovimientosExistenciasModel(this.model.getConexion());
        new MovimientosExistenciasController(movimientosExistenciasViewIns, movimientosExistenciasModelIns).iniciar();
        this.view.principalTabMovExistPnl.add(movimientosExistenciasViewIns);
        
        //agregamos a la pestana resultados los resultados
        ResultadosView resultadosViewIns = new ResultadosView();
        ResultadosModel resultadosModelIns = new ResultadosModel(this.model.getConexion());
        new ResultadosController(resultadosViewIns, resultadosModelIns).iniciar();
        this.view.principalTabResultadosPnl.add(resultadosViewIns);
        
        
       ArrayList<Component> order = new ArrayList<>();
       order.add(clientesPanelIns.getView().nombreJtf);
       order.add(clientesPanelIns.getView().clientesJtb);
       order.add(articulosPanelIns.getView().descripcionArtJtf);
       
        CustomFocusTraversalPolicy customFocusTraversalPolicy = new CustomFocusTraversalPolicy(order);
        this.view.panelPrincipalTbp.setFocusTraversalPolicy(customFocusTraversalPolicy);
        
        clientesPanelIns.getView().clientesJtb.setNextFocusableComponent(articulosPanelIns.getView().descripcionArtJtf);
        articulosPanelIns.getView().descripcionArtJtf.setNextFocusableComponent(clientesPanelIns.getView().nombreJtf);
    
     
    }

    public void iniciar() {

        view.setVisible(true);
        //cremos el almacen general que de donde se sacan los prouctos para realizar ventas
        this.model.crearAlmacenGeneral();

    }

    @Override
    public void seleccionaArticulo(Map articulo) {

        //si hay cliente seleccionado

        if (!ventaPanelIns.getView().clienteLbl.getText().equalsIgnoreCase("Cliente:")) {

            ventaPanelIns.addArticulo(articulo);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar un cliente",
                    "Información",
                    ConstantesGenericas.CG_MESSAGE_INFO_TYPE_INT);
        }
    }

    @Override
    public void seleccionaCliente(Map cliente) {
        
        
        //carga la lista de deuda del cliente
        ventaPanelIns.loadDeuda(cliente);
        

    }

    @Override
    public void ventaPanel(VentaPanel ventaPanel, Map articulo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    
    
    
}
