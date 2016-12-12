/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Map;
import modelo.AltaAlmacenesModel;
import utilerias.Utilerias;
import vista.AltaAlmacenesView;

/**
 *
 * @author Noe
 */
public class AltaAlmacenesController implements ActionListener , FocusListener{
    
     private AltaAlmacenesView view;
    private AltaAlmacenesModel model;

    public AltaAlmacenesController(AltaAlmacenesView pView, AltaAlmacenesModel pModel) {
        this.view = pView;
        this.model = pModel;

        init();
    }

    private void init() {
        view.setBounds(10, 10, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles del formulario padre
        this.view.guardarTodoBtn.setActionCommand("guardar todo");

        //Se pone a escuchar las acciones del usuario
        this.view.guardarTodoBtn.addActionListener(this);
        this.view.almacenIdTxt.addFocusListener(this);

    }

    public void iniciar() {
        //this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equalsIgnoreCase("guardar todo")) {
            
            this.view.almacenIdTxt.setText(Utilerias.emptyTo(this.view.almacenIdTxt.getText(), ""+this.model.getNextId()));


            try {
                //revisar campos obligatorios
                Utilerias.checkEmptyFieldStr(this.view.nombreTxt, "Nombre");
                Utilerias.checkEmptyFieldStr(this.view.descripcionTar, "Descripcion");

                this.model.guardar(
                        Integer.parseInt(this.view.almacenIdTxt.getText()),
                        this.view.nombreTxt.getText(),
                        this.view.descripcionTar.getText());

            } catch (Exception ex) {
                System.out.println("Exception en guardar todo " + ex);
            }
            
        }
        
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        
        if (e.getComponent() == this.view.almacenIdTxt) {
            
           if (this.view.almacenIdTxt.getText().equalsIgnoreCase("")) {
                this.view.almacenIdTxt.setText("0");
            }

            ArrayList<Map> vlArticulosLst = this.model.getDatosAlmacen(Integer.parseInt(this.view.almacenIdTxt.getText()));

            //si encontro el articulo
            if (!vlArticulosLst.isEmpty()) {

                this.view.nombreTxt.setText(vlArticulosLst.get(0).get("NOMBRE").toString());
                this.view.descripcionTar.setText(vlArticulosLst.get(0).get("DESCRIPCION").toString());

            } else {
                //si si esta vacio, proponemos el siguiente id
                this.view.almacenIdTxt.setText("" + this.model.getNextId());
                //limpiampos todos los campos
                this.view.nombreTxt.setText(null);
                this.view.descripcionTar.setText(null);
            } 
        }
        
    }
    
}
