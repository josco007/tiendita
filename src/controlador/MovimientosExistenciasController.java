/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;
import modelo.MovimientosExistenciasModel;
import utilerias.Utilerias;
import vista.MovimientosExistenciasView;
import vista.articulosPanel.ArticulosPanel;

/**
 *
 * @author Noe
 */
public class MovimientosExistenciasController implements ActionListener, FocusListener, ArticulosPanel.ArticulosPanelDelegate {

    private MovimientosExistenciasView view;
    private MovimientosExistenciasModel model;
    ArrayList<Map> infoExistenciaLst;
    ArrayList<Map> almacenesLst;

    public MovimientosExistenciasController(MovimientosExistenciasView pView, MovimientosExistenciasModel pModel) {
        this.view = pView;
        this.model = pModel;

        init();
    }

    private void init() {
        view.setBounds(10, 10, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles
        this.view.guardarTodoBtn.addActionListener(this);

        //Se pone a escuchar las acciones del usuario
        this.view.guardarTodoBtn.setActionCommand("guardar todo");
        this.view.articuloIdTxt.addFocusListener(this);


        //se agregan componentes
        //agregamos el panel de articulos a la ventana
        ArticulosPanel articulosPanelIns = new ArticulosPanel(this.model.getConexion(), this);
        this.view.add(articulosPanelIns.getView());
        articulosPanelIns.getView().setBounds(10, 350, 900, 230);

    }

    public void iniciar() {
        //this.view.setVisible(true);
        //llenamos el combobox de almacenes
        almacenesLst = this.model.getAlmacenes();

        for (int i = 0; i < almacenesLst.size(); i++) {
            this.view.almacenCbx.addItem(almacenesLst.get(i).get("NOMBRE"));
        }

    }

    private void buscarArticuloWithId(int pArticuloId) {

        if (this.view.articuloIdTxt.getText().equalsIgnoreCase("")) {
            this.view.articuloIdTxt.setText("0");
        }


        int vlAlmacenId = Integer.parseInt(
                String.valueOf(
                almacenesLst.get(
                this.view.almacenCbx.getSelectedIndex()).get("ALMACENID")));

        infoExistenciaLst = this.model.getDatosArticulo(vlAlmacenId, pArticuloId);

        //si encontro el articulo
        if (!infoExistenciaLst.isEmpty()) {

            this.view.codigoUpcTxt.setText(infoExistenciaLst.get(0).get("CODIGO").toString());
            this.view.nombreTxt.setText(infoExistenciaLst.get(0).get("NOMBRE").toString());
            this.view.descripcionTar.setText(infoExistenciaLst.get(0).get("DESCRIPCION").toString());
            this.view.precioVentaTxt.setText(infoExistenciaLst.get(0).get("PRECIO").toString());
            this.view.precioCompraTxt.setText(infoExistenciaLst.get(0).get("COSTO").toString());
            this.view.cantidadActualTxt.setText(infoExistenciaLst.get(0).get("CANTIDAD").toString());

            float vlPorcGananciaFlt = Float.parseFloat(this.view.precioVentaTxt.getText()) - Float.parseFloat(this.view.precioCompraTxt.getText());
            vlPorcGananciaFlt = (vlPorcGananciaFlt * 100) / Float.parseFloat(this.view.precioCompraTxt.getText());
            this.view.porcGananciaTxt.setText("" + vlPorcGananciaFlt);


            Image vlFotoClienteImg = Utilerias.decodeToImage(String.valueOf(infoExistenciaLst.get(0).get("IMAGEN")));
            BufferedImage vlFotoClienteBim = Utilerias.toBufferedImage(vlFotoClienteImg);

            if (vlFotoClienteBim != null) {
                Image dimg = vlFotoClienteBim.getScaledInstance(this.view.imagenLbl.getWidth(),
                        this.view.imagenLbl.getHeight(),
                        Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                this.view.imagenLbl.setIcon(imageIcon);
            } else {
                this.view.imagenLbl.setIcon(null);
            }

        } else {
            //si si esta vacio, proponemos el siguiente id
            //limpiampos todos los campos
            this.view.codigoUpcTxt.setText(null);
            this.view.nombreTxt.setText(null);
            this.view.descripcionTar.setText(null);
            this.view.precioVentaTxt.setText(null);
            this.view.precioCompraTxt.setText(null);
            this.view.imagenLbl.setIcon(null);
            this.view.cantidadActualTxt.setText(null);
        }
        
        this.view.cantidadTxt.setText(null);


    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {

        if (e.getComponent() == this.view.articuloIdTxt) {

            buscarArticuloWithId(Integer.parseInt(this.view.articuloIdTxt.getText()));
        }

    }

    @Override
    public void seleccionaArticulo(Map articulo) {

        this.view.articuloIdTxt.setText("" + articulo.get("ARTICULOID"));
        this.view.entSalCbx.setSelectedIndex(0);
        buscarArticuloWithId(Integer.parseInt(this.view.articuloIdTxt.getText()));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("guardar todo")) {

            int vlCantidadInt;

            //entrada
            if (this.view.entSalCbx.getSelectedIndex() == 0) {
                vlCantidadInt = Integer.parseInt(this.view.cantidadTxt.getText());
            } else {
                //salida
                vlCantidadInt = Integer.parseInt(this.view.cantidadTxt.getText()) * -1;
            }

            int vlAlmacenId = Integer.parseInt(
                String.valueOf(
                almacenesLst.get(
                this.view.almacenCbx.getSelectedIndex()).get("ALMACENID")));
            int vlArticuloId = Integer.parseInt(String.valueOf(infoExistenciaLst.get(0).get("ARTICULOID")));

            
            String vlTipoMovStr = null; 
            if (this.view.entSalCbx.getSelectedIndex() == 0) {
                vlTipoMovStr = "EN";
            }
            else if(this.view.entSalCbx.getSelectedIndex() == 1){
                vlTipoMovStr = "SA";
            }

            this.model.guardarTodo(
                    vlAlmacenId,
                    vlArticuloId,
                    vlCantidadInt,
                    vlTipoMovStr);

            buscarArticuloWithId(vlArticuloId);
       
        }

    }
}
