/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ventaPanel.controlador;

import controlador.AddAnyItemDlgController;
import controlador.AddAnyItemDlgController.AddAnyItemDlgControllerDelegate;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utilerias.CJTextField;
import utilerias.Utilerias;
import vista.ventaPanel.VentaPanel.VentaPanelDelegate;
import vista.ventaPanel.modelo.VentaPanelModel;
import vista.ventaPanel.vista.VentaPanelView;

/**
 *
 * @author Noe
 */
public class VentaPanelController implements ActionListener, KeyListener, AddAnyItemDlgControllerDelegate {

    private VentaPanelView view;
    private VentaPanelModel model;
    private VentaPanelDelegate delegate;
    private ArrayList<Map> ventaLst;
    private Map cliente;
    private int deudaIdInt;
    AddAnyItemDlgController addAnyItemDlgController;
    JTextField cantidadAnyArticuloTxt;
    JTextField nombreAnyArticuloTxt;
    JTextField descripcionAnyArticuloTxt;
    JTextField costoAnyArticuloTxt;
    JTextField precioAnyArticuloTxt;
    double total = 0;

    public VentaPanelController(VentaPanelView pVista, VentaPanelModel pModel, VentaPanelDelegate pDelegate) {

        this.view = pVista;
        this.model = pModel;
        this.delegate = pDelegate;

        init();

    }

    private void init() {
        view.setBounds(365, 0, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles del formulario padre
        this.view.eliminarBtn.setActionCommand("Eliminar");
        this.view.pagoBtn.setActionCommand("Pago");
        this.view.agregarArticuloBtn.setActionCommand("agregar articulo");
        this.view.cantidadBtn.setActionCommand("cantidad");

        //Se pone a escuchar las acciones del usuario
        this.view.eliminarBtn.addActionListener(this);
        this.view.pagoBtn.addActionListener(this);
        this.view.agregarArticuloBtn.addActionListener(this);
        this.view.cantidadBtn.addActionListener(this);
        this.view.ventaJtb.addKeyListener(this);
        
    }

    public void iniciar() {
    }

    private void scrollToVisible(JTable table, int rowIndex, int vColIndex) {

        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        if (table.getRowCount() < 1) {
            return;
        }
        JViewport viewport = (JViewport) table.getParent();
        // view dimension
        Dimension dim = viewport.getExtentSize();
        // cell dimension
        Dimension dimOne = new Dimension(0, 0);

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
        Rectangle rectOne;
        if (rowIndex + 1 < table.getRowCount()) {
            if (vColIndex + 1 < table.getColumnCount()) {
                vColIndex++;
            }
            rectOne = table.getCellRect(rowIndex + 1, vColIndex, true);
            dimOne.width = rectOne.x - rect.x;
            dimOne.height = rectOne.y - rect.y;
        }

        // '+ veiw dimension - cell dimension' to set first selected row on the top

        rect.setLocation(rect.x + dim.width - dimOne.width, rect.y + dim.height - dimOne.height);

        table.scrollRectToVisible(rect);
    }

    public void checkMaxCredit(){
        
        if ((total ) > 
                Double.parseDouble(cliente.get("CREDITOMAX").toString())){
            
            JOptionPane.showMessageDialog(view, "El maximo de credito se esta sobrepasando por " +
                    ((total ) -   Double.parseDouble(cliente.get("CREDITOMAX").toString())),
                    "Total de credito sobrepasado", JOptionPane.WARNING_MESSAGE);
            
        }
    }
    
    public void addArticulo(Map pArticulo) {


        int articuloId = Integer.parseInt(pArticulo.get("ARTICULOID").toString());
        float vlPrecioFlt = Float.parseFloat(String.valueOf(pArticulo.get("PRECIO")));
        float vlCostoFlt = Float.parseFloat(String.valueOf(pArticulo.get("COSTO")));
        //agregamos el articulo a la lista de deudaXCliente
        this.model.addArticuloDetDeudaXCliente(deudaIdInt, articuloId, 1, vlPrecioFlt, vlCostoFlt);

        loadDeuda(cliente);

        int rowIndex = this.view.ventaJtb.getRowCount();

        scrollToVisible(this.view.ventaJtb, rowIndex, 0);

        calculaTotalDeuda();
        
        checkMaxCredit();

    }

    public void loadDeuda(Map pCliente) {

        this.cliente = pCliente;

        //coloca en el label el cliente que se selecciona
        this.view.clienteLbl.setText("Cliente: " + pCliente.get("NOMBRE").toString()
                + " "
                + pCliente.get("APELLIDOPAT").toString());

        //coloca la imagen del cliente
        Image vlFotoClienteImg = Utilerias.decodeToImage(String.valueOf(pCliente.get("FOTO")));
        BufferedImage vlFotoClienteBim = Utilerias.toBufferedImage(vlFotoClienteImg);

        if (vlFotoClienteBim != null) {
            Image dimg = vlFotoClienteBim.getScaledInstance(this.view.fotoLbl.getWidth(),
                    this.view.fotoLbl.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            this.view.fotoLbl.setIcon(imageIcon);
        } else {
            this.view.fotoLbl.setIcon(null);
        }

        int vlClienteId = Integer.parseInt(pCliente.get("CLIENTEID").toString());

        //ver si el cliente tiene una deuda no liquidada
        AtomicInteger deudaId = new AtomicInteger(0);
        boolean vlTieneDeudaBoo = this.model.tieneDedua(vlClienteId, deudaId);

        //si no tiene deuda
        if (!vlTieneDeudaBoo) {
            //le creamos una
            this.model.addDeuda(vlClienteId);

            //obtenemos la deuda id
            deudaIdInt = this.model.getDeudaId(vlClienteId);
            ventaLst = this.model.getDetDeuda(deudaIdInt);

        } else {
            //si no si si tiene cargamos la deuda del cliente
            ventaLst = this.model.getDetDeuda(deudaId.get());
            deudaIdInt = deudaId.get();


        }

        reloadTable();

        calculaTotalDeuda();



    }

    private void calculaTotalDeuda() {

        DefaultTableModel vlModelDtm = (DefaultTableModel) this.view.ventaJtb.getModel();

         total = 0;
        int column = 5;

        for (int i = 0; i < vlModelDtm.getRowCount(); i++) {
            total += Double.parseDouble(vlModelDtm.getValueAt(i, column).toString());        // getValueAt(row, column)
        }

        this.view.totalLbl.setText("$" + total);


        


    }

    private void reloadTable() {
        DefaultTableModel vlModelDtm = (DefaultTableModel) this.view.ventaJtb.getModel();

        //limpiar la tabla
        vlModelDtm.setRowCount(0);

        for (Map clientes : ventaLst) {

            vlModelDtm.addRow(new Object[]{clientes.get("IMAGEN"),
                        clientes.get("DESCRIPCION"),
                        clientes.get("CANTIDAD"),
                        clientes.get("PRECIO"),
                        clientes.get("FECHA"),
                        clientes.get("TOTAL")});

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");

        if (e.getActionCommand().equalsIgnoreCase("eliminar")) {
            
           
            
            //si hay un articulo seleccionado
            if (this.view.ventaJtb.getSelectedRow() != -1) {
                
                 int resp =  JOptionPane.showConfirmDialog(view, "Esta seguro que quiere eliminar?", "Elimnar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

     
            if (resp == JOptionPane.CANCEL_OPTION){
                return;
            }
                
                int deudaId = Integer.parseInt(ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("DEUDAID").toString());
                int partida = Integer.parseInt(ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("PARTIDA").toString());

                //si la descripcion es pago
                String vlDescripcionStr = ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("DESCRIPCION").toString();
                if (vlDescripcionStr.equalsIgnoreCase("pago")) {

                    this.model.deletePago(deudaId, partida);

                } else {
                    //quitamos el articulo de la deuda
                    this.model.deleteArticuloDetDeudaXCliente(deudaId, partida);
                }
                loadDeuda(cliente);
            }

        } else if (e.getActionCommand().equalsIgnoreCase("pago")) {
            //si hay cliente seleccionado
            if (!this.view.clienteLbl.getText().equalsIgnoreCase("Cliente:")) {
                //mostrar ventana para pedir el pago

                CJTextField vlPagoTxt = new CJTextField();
                vlPagoTxt.setOnly(CJTextField.FLOATS);

                Object[] msg = {
                    "Cantidad", vlPagoTxt
                };
                int result = JOptionPane.showConfirmDialog(null,
                        msg, "Modifica cantidad",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);


                if (result == JOptionPane.OK_OPTION) {


                    this.model.addPago(this.deudaIdInt, Float.parseFloat(vlPagoTxt.getText()));
                    loadDeuda(cliente);
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("agregar articulo")) {

            //si hay cliente seleccionado
            if (!this.view.clienteLbl.getText().equalsIgnoreCase("Cliente:")) {

                JPanel vlpanelPnl = new JPanel(null);
                //vlpanelPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
                vlpanelPnl.setBounds(0, 5, 200, 300);
                
                
                
                JRadioButton custom = new JRadioButton("Custom");
                JRadioButton addToAccount = new JRadioButton("agregar a cuenta");
               
                ButtonGroup bG = new ButtonGroup();
                bG.add(addToAccount);
                bG.add(custom);
                
                addToAccount.addActionListener(
                 new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                }
                
                );
                
                

                addAnyItemDlgController = new AddAnyItemDlgController();
                addAnyItemDlgController.setDelegate(this);
                addAnyItemDlgController.view.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar primero a un cliente", "Alerta", JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getActionCommand().equalsIgnoreCase("aceptar articulo")) {


        } else if (e.getActionCommand().equalsIgnoreCase("cancelar articulo")) {
            
        }
        else if (e.getActionCommand().equalsIgnoreCase("cantidad")){
                //mostrar ventana para pedir la cantidad
                CJTextField vlCantidadTxt = new CJTextField();
                vlCantidadTxt.setOnly(CJTextField.NUMBERS);
                Object[] msg = {
                    "Cantidad", vlCantidadTxt
                };
                int result = JOptionPane.showConfirmDialog(null,
                        msg, "Modifica cantidad",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);


                if (result == JOptionPane.OK_OPTION) {
                    int deudaId = Integer.parseInt(ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("DEUDAID").toString());
                    int partida = Integer.parseInt(ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("PARTIDA").toString());
                    int cantidad = Integer.parseInt(vlCantidadTxt.getText());
                    this.model.updateCantidad(deudaId, partida, cantidad);

                    int vlSelectedRow = this.view.ventaJtb.getSelectedRow();
                    loadDeuda(cliente);

                    this.view.ventaJtb.setRowSelectionInterval(vlSelectedRow, vlSelectedRow);
                } else {
                    // cancelled
                    System.out.println("cancel");
                }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("" + e.getKeyCode());

        if (e.getComponent() == this.view.ventaJtb) {

            if (e.getKeyCode() == 106) {

                // si es igual a *
                //mostrar ventana para pedir la cantidad
                CJTextField vlCantidadTxt = new CJTextField();
                vlCantidadTxt.setOnly(CJTextField.NUMBERS);
                Object[] msg = {
                    "Cantidad", vlCantidadTxt
                };
                int result = JOptionPane.showConfirmDialog(null,
                        msg, "Modifica cantidad",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);


                if (result == JOptionPane.OK_OPTION) {
                    int deudaId = Integer.parseInt(ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("DEUDAID").toString());
                    int partida = Integer.parseInt(ventaLst.get(this.view.ventaJtb.getSelectedRow()).get("PARTIDA").toString());
                    int cantidad = Integer.parseInt(vlCantidadTxt.getText());
                    this.model.updateCantidad(deudaId, partida, cantidad);

                    int vlSelectedRow = this.view.ventaJtb.getSelectedRow();
                    loadDeuda(cliente);

                    this.view.ventaJtb.setRowSelectionInterval(vlSelectedRow, vlSelectedRow);
                } else {
                    // cancelled
                    System.out.println("cancel");
                }

            }//fin de si es *

        }
    }

    @Override
    public void addAnyItemDlgControllerEvent(AddAnyItemDlgController pAddAnyItemDlgController, AddAnyItemDlgController.Events pEvent, Object pData) {
        
         switch (pEvent){
            case  DID_ITEM_ADDED:
                
                
                 //agregar el articulo 
            this.model.addAnyArticuloToDetDeudaXCliente(pAddAnyItemDlgController.view.itemNameTxt.getText(),
                    pAddAnyItemDlgController.view.descriptionTxt.getText(),
                    Float.parseFloat(pAddAnyItemDlgController.view.costTxt.getText()),
                    Float.parseFloat(pAddAnyItemDlgController.view.priceTxt.getText()),
                    deudaIdInt,
                    Integer.parseInt(pAddAnyItemDlgController.view.amountTxt.getText()));

            addAnyItemDlgController.view.dispose();

            loadDeuda(cliente);
            checkMaxCredit();
                
                break;
            case DID_CANCEL:
                addAnyItemDlgController.view.dispose();

                break;
        }
        
        
    }
}
