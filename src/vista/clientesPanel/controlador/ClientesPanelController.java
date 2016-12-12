/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.clientesPanel.controlador;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import vista.clientesPanel.ClientesPanel.ClientesPanelDelegates;
import vista.clientesPanel.modelo.ClientesPanelModel;
import vista.clientesPanel.vista.ClientesPanelView;

/**
 *
 * @author Noe
 */
public class ClientesPanelController implements ActionListener, KeyListener, ListSelectionListener {

    private ArrayList<Map> clientesLst;
    private ClientesPanelModel model;
    private ClientesPanelView view;
    private ClientesPanelDelegates delegate;

    public ClientesPanelController(ClientesPanelModel pModel, ClientesPanelView pView, ClientesPanelDelegates pDelegate) {

        this.model = pModel;
        this.view = pView;
        this.delegate = pDelegate;

        init();
    }

    private void init() {

        view.setBounds(0, 0, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles del formulario padre
        //this.view.nombreJtf.setActionCommand("Buscar por nombre");
        this.view.clientesJtb.getSelectionModel().addListSelectionListener(this);
        
        //Se pone a escuchar las acciones del usuario
        //this.view.nombreJtf.addActionListener(this);
        this.view.nombreJtf.addKeyListener(this);
        
        //this.view.clientesJtb.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        //this.view.clientesJtb.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
    }

    public void iniciar() {
        //this.view.setVisible(true);
        buscaClientes();
    }

    private void buscaClientes() {


       
            
            clientesLst = this.model.getClientesWithNombre(this.view.nombreJtf.getText());

            DefaultTableModel vlModelDtm = (DefaultTableModel) this.view.clientesJtb.getModel();

            //limpiar la tabla
            vlModelDtm.setRowCount(0);

            for (Map clientes : clientesLst) {

                vlModelDtm.addRow(new Object[]{clientes.get("CLIENTEID"),
                            clientes.get("NOMBRE"),
                            clientes.get("APELLIDOPAT"),
                            clientes.get("APELLIDOMAT")});

            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Captura en String el comando accionado por el usuario
        String comando = e.getActionCommand();


        /*
         * Acciones del formulario padre
         */

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (this.view.clientesJtb.getSelectedRow() != -1) {
                  delegate.seleccionaCliente(clientesLst.get(this.view.clientesJtb.getSelectedRow()));                
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
       
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");

        //Captura en Jcomponent el componenres que retiro una tecla    
        JComponent component = (JComponent) e.getComponent();

        /*
         * Acciones del formulario padre
         */
        if (component == this.view.nombreJtf) {
            //consulta los clientes
            //consulta los clientes
            if (this.view.nombreJtf.getText().length() == 0){
                buscaClientes();
            }
            if (this.view.nombreJtf.getText().length() < 3){
                return;
            }
            buscaClientes();
        }


    }
}
