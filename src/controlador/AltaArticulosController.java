/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import modelo.AltaArticulosModel;
import utilerias.Utilerias;
import vista.AltaArticulosView;

/**
 *
 * @author Noe
 */
public class AltaArticulosController implements ActionListener, FocusListener, KeyListener {

    private AltaArticulosView view;
    private AltaArticulosModel model;
    private File imagenArticulo;

    public AltaArticulosController(AltaArticulosView pView, AltaArticulosModel pModel) {
        this.view = pView;
        this.model = pModel;

        init();
    }

    private void init() {
        view.setBounds(10, 10, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles del formulario padre
        this.view.seleccionarImagenBtn.setActionCommand("seleccionar imagen");
        this.view.guardarTodoBtn.setActionCommand("guardar todo");

        //Se pone a escuchar las acciones del usuario
        this.view.seleccionarImagenBtn.addActionListener(this);
        this.view.guardarTodoBtn.addActionListener(this);
        this.view.articuloIdTxt.addFocusListener(this);
        this.view.porcGananciaTxt.addKeyListener(this);
        this.view.precioCompraTxt.addKeyListener(this);
        this.view.precioVentaTxt.addKeyListener(this);


    }

    public void iniciar() {
        //this.view.setVisible(true);
    }

    private float calcularPrecioVenta(float pPrecioCompra, float pPorcentaje) {
        float vlPrecioVentaFlt = pPrecioCompra * (pPorcentaje / 100);
        if (Float.compare(vlPrecioVentaFlt, Float.NaN) == 0) {
            vlPrecioVentaFlt = 0;
        }

        return vlPrecioVentaFlt +pPrecioCompra;
    }
    
    private float calcularPorcentajeGanancia(float pPrecioCompra, float pPrecioVenta) {
        float vlPorcGananciaFlt = ((pPrecioVenta - pPrecioCompra) * 100)/pPrecioCompra;
        if (Float.compare(vlPorcGananciaFlt, Float.NaN) == 0) {
            vlPorcGananciaFlt = 0;
        }

        return vlPorcGananciaFlt;
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getActionCommand().equalsIgnoreCase("seleccionar imagen")) {
            JFileChooser vlChooser = new JFileChooser();
            int result = vlChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = vlChooser.getSelectedFile();
                imagenArticulo = file;
                try {
                    BufferedImage vlFotoClienteBim = ImageIO.read(file);

                    Image dimg = vlFotoClienteBim.getScaledInstance(this.view.imagenLbl.getWidth(),
                            this.view.imagenLbl.getHeight(),
                            Image.SCALE_SMOOTH);

                    ImageIcon imageIcon = new ImageIcon(dimg);
                    this.view.imagenLbl.setIcon(imageIcon);
                } catch (IOException ex) {
                    System.out.println("Exception seleccionar imagen " + ex);
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("guardar todo")) {
            
            this.view.articuloIdTxt.setText(Utilerias.emptyTo(this.view.articuloIdTxt.getText(), ""+this.model.getNextId()));


            try {
                //revisar campos obligatorios
                Utilerias.checkEmptyFieldStr(this.view.nombreTxt, "Nombre");
                Utilerias.checkEmptyFieldStr(this.view.descripcionTar, "Descripcion");
                Utilerias.checkEmptyFieldStr(this.view.precioVentaTxt, "Precio de venta");

                this.model.guardar(
                        Integer.parseInt(this.view.articuloIdTxt.getText()),
                        this.view.codigoUpcTxt.getText(),
                        this.view.nombreTxt.getText(),
                        this.view.descripcionTar.getText(),
                        Float.parseFloat(this.view.precioVentaTxt.getText()),
                        Float.parseFloat(this.view.precioCompraTxt.getText()),
                        imagenArticulo);

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

        if (e.getComponent() == this.view.articuloIdTxt) {

            if (this.view.articuloIdTxt.getText().equalsIgnoreCase("")) {
                this.view.articuloIdTxt.setText("0");
            }

            ArrayList<Map> vlArticulosLst = this.model.getDatosArticulo(Integer.parseInt(this.view.articuloIdTxt.getText()));

            //si encontro el articulo
            if (!vlArticulosLst.isEmpty()) {

                this.view.codigoUpcTxt.setText(vlArticulosLst.get(0).get("CODIGO").toString());
                this.view.nombreTxt.setText(vlArticulosLst.get(0).get("NOMBRE").toString());
                this.view.descripcionTar.setText(vlArticulosLst.get(0).get("DESCRIPCION").toString());
                this.view.precioVentaTxt.setText(vlArticulosLst.get(0).get("PRECIO").toString());
                this.view.precioCompraTxt.setText(vlArticulosLst.get(0).get("COSTO").toString());

                float vlPorcGananciaFlt = Float.parseFloat(this.view.precioVentaTxt.getText()) - Float.parseFloat(this.view.precioCompraTxt.getText());
                vlPorcGananciaFlt = (vlPorcGananciaFlt * 100) / Float.parseFloat(this.view.precioCompraTxt.getText());
                this.view.porcGananciaTxt.setText("" + vlPorcGananciaFlt);


                Image vlFotoClienteImg = Utilerias.decodeToImage(String.valueOf(vlArticulosLst.get(0).get("IMAGEN")));
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
                this.view.articuloIdTxt.setText("" + this.model.getNextId());
                //limpiampos todos los campos
                this.view.codigoUpcTxt.setText(null);
                this.view.nombreTxt.setText(null);
                this.view.descripcionTar.setText(null);
                this.view.precioVentaTxt.setText(null);
                this.view.precioCompraTxt.setText(null);
                this.view.imagenLbl.setIcon(null);
            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getComponent() == this.view.porcGananciaTxt) {
            Utilerias.onlyFloatTextFieldEvent(e);
        } else if (e.getComponent() == this.view.precioCompraTxt) {
            Utilerias.onlyFloatTextFieldEvent(e);
        } else if (e.getComponent() == this.view.precioVentaTxt) {
            Utilerias.onlyFloatTextFieldEvent(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getComponent() == this.view.porcGananciaTxt) {
            float vlPrecioCompraFlt = Float.parseFloat(Utilerias.emptyTo(this.view.precioCompraTxt.getText(), "0"));
            float vlPorcGananciaFlt = Float.parseFloat(Utilerias.emptyTo(this.view.porcGananciaTxt.getText(), "0"));
            this.view.precioVentaTxt.setText("" + calcularPrecioVenta(vlPrecioCompraFlt, vlPorcGananciaFlt));

        }
        else if (e.getComponent() == this.view.precioCompraTxt) {

            float vlPrecioCompraFlt = Float.parseFloat(Utilerias.emptyTo(this.view.precioCompraTxt.getText(), "0"));
            float vlPorcGananciaFlt = Float.parseFloat(Utilerias.emptyTo(this.view.porcGananciaTxt.getText(), "0"));
            this.view.precioVentaTxt.setText("" + calcularPrecioVenta(vlPrecioCompraFlt, vlPorcGananciaFlt));
        }
        else if (e.getComponent() == this.view.precioVentaTxt){
             float vlPrecioCompraFlt = Float.parseFloat(Utilerias.emptyTo(this.view.precioCompraTxt.getText(), "0"));
            float vlPrecioVentaFlt = Float.parseFloat(Utilerias.emptyTo(this.view.precioVentaTxt.getText(), "0"));
            this.view.porcGananciaTxt.setText("" + calcularPorcentajeGanancia(vlPrecioCompraFlt, vlPrecioVentaFlt));

        }

    }
}
