/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.AltaClientesModel;
import utilerias.Utilerias;
import vista.altaClientes.AltaClientesView;

/**
 *
 * @author Noe
 */
public class AltaClientesController implements ActionListener, FocusListener {

    private AltaClientesView view;
    private AltaClientesModel model;
    private File fotoCliente;

    public AltaClientesController(AltaClientesView pView, AltaClientesModel pModel) {
        this.view = pView;
        this.model = pModel;

        init();
    }

    private void init() {
        view.setBounds(10, 10, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles del formulario padre
        this.view.seleccionarFotoBtn.setActionCommand("seleccionar foto");
        this.view.guardarTodoBtn.setActionCommand("guardar todo");

        //Se pone a escuchar las acciones del usuario
        this.view.seleccionarFotoBtn.addActionListener(this);
        this.view.guardarTodoBtn.addActionListener(this);
        this.view.clienteIdTxt.addFocusListener(this);

    }

    public void iniciar() {
        //this.view.setVisible(true);
    }

    private Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("seleccionar foto")) {
            JFileChooser vlChooser = new JFileChooser();
            int result = vlChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = vlChooser.getSelectedFile();
                fotoCliente = file;
                try {
                    BufferedImage vlFotoClienteBim = ImageIO.read(file);

                    Image dimg = vlFotoClienteBim.getScaledInstance(this.view.fotoLbl.getWidth(),
                            this.view.fotoLbl.getHeight(),
                            Image.SCALE_SMOOTH);

                    ImageIcon imageIcon = new ImageIcon(dimg);
                    this.view.fotoLbl.setIcon(imageIcon);
                } catch (IOException ex) {
                    Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("guardar todo")) {
            
            
            this.view.clienteIdTxt.setText(Utilerias.emptyTo(this.view.clienteIdTxt.getText(), ""+this.model.getNextId()));

            try {
                Utilerias.checkEmptyFieldStr(this.view.nombreTxt, "Nombre");
                Utilerias.checkEmptyFieldStr(this.view.apellidoPatTxt, "Apelldio paterno");
                Utilerias.checkEmptyFieldStr(this.view.apellidoMatTxt, "Apellido materno");

                this.model.guardar(
                        Integer.parseInt(this.view.clienteIdTxt.getText()),
                        this.view.nombreTxt.getText(),
                        this.view.apellidoPatTxt.getText(),
                        this.view.apellidoMatTxt.getText(),
                        this.view.emailTxt.getText(),
                        this.view.usuarioTxt.getText(),
                        this.view.passwordPfd.getPassword(),
                        fotoCliente,
                        this.view.infoTxa.getText(),
                        Float.parseFloat( this.view.maxCretidTxt.getText())
                       );

            } catch (Exception ex) {
                Utilerias.showMessage("Error", ""+ex, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {


        if (e.getComponent() == this.view.clienteIdTxt) {

            if (this.view.clienteIdTxt.getText().equalsIgnoreCase("")) {
                this.view.clienteIdTxt.setText("0");
            }

            ArrayList<Map> vlClientesLst = this.model.getDatosCliente(Integer.parseInt(this.view.clienteIdTxt.getText()));

            //si encontro el cliente
            if (!vlClientesLst.isEmpty()) {

                this.view.nombreTxt.setText(vlClientesLst.get(0).get("NOMBRE").toString());
                this.view.apellidoPatTxt.setText(vlClientesLst.get(0).get("APELLIDOPAT").toString());
                this.view.apellidoMatTxt.setText(vlClientesLst.get(0).get("APELLIDOMAT").toString());
                this.view.emailTxt.setText(vlClientesLst.get(0).get("EMAIL").toString());
                this.view.usuarioTxt.setText(vlClientesLst.get(0).get("USUARIO").toString());

                Image vlFotoClienteImg = Utilerias.decodeToImage(String.valueOf(vlClientesLst.get(0).get("FOTO")));
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
                
                this.view.infoTxa.setText(vlClientesLst.get(0).get("INFO").toString());
                this.view.maxCretidTxt.setText(vlClientesLst.get(0).get("CREDITOMAX").toString());

            } else {
                //si si esta vacio, proponemos el siguiente id
                this.view.clienteIdTxt.setText("" + this.model.getNextId());
                //limpiampos todos los campos
                this.view.nombreTxt.setText(null);
                this.view.apellidoPatTxt.setText(null);
                this.view.apellidoMatTxt.setText(null);
                this.view.emailTxt.setText(null);
                this.view.usuarioTxt.setText(null);
                this.view.fotoLbl.setIcon(null);
                this.view.infoTxa.setText(null);
                this.view.maxCretidTxt.setText(null);
            }

        }

    }
}
