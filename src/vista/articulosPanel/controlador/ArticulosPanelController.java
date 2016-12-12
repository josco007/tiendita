/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.articulosPanel.controlador;

import vista.articulosPanel.ArticulosPanel.ArticulosPanelDelegate;
import vista.articulosPanel.modelo.ArticulosPanelModel;
import vista.articulosPanel.vista.ArticulosPanelView;
import vista.articulosPanel.vista.JCustomButton;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import utilerias.Utilerias;

/**
 *
 * @author Noe
 */
public class ArticulosPanelController implements ActionListener, KeyListener {

    private ArticulosPanelView view;
    private ArticulosPanelModel model;
    private ArrayList<Map> articulosLst;
    private ArticulosPanelDelegate delegate;

    public ArticulosPanelController(ArticulosPanelView pView, ArticulosPanelModel pModel, ArticulosPanelDelegate pDelegate) {

        view = pView;
        model = pModel;
        this.delegate = pDelegate;

        init();

    }

    private void init() {
        view.setBounds(5, 360, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles del formulario padre
        this.view.buscarBtn.setActionCommand("buscar");

        //Se pone a escuchar las acciones del usuario
        this.view.buscarBtn.addActionListener(this);
        
        this.view.descripcionArtJtf.addKeyListener(this);
        
    }

    public void iniciar() {
        buscarArticulosWithDesc();
    }

    private void buscarArticulosWithDesc() {
       
        this.view.flowpanelIns.clearScroll();
            articulosLst = model.getArticulosWithDesc(this.view.descripcionArtJtf.getText());


            for (int i = 0; i < articulosLst.size(); i++) {

                JCustomButton btn = new JCustomButton();
                // btn.applyComponentOrientation(getComponentOrientation());
                btn.setText(articulosLst.get(i).get("NOMBRE").toString());
                //btn.setIcon(new ImageIcon(img));
                btn.setFocusPainted(false);
                btn.setFocusable(false);
                btn.setRequestFocusEnabled(false);
                btn.setHorizontalTextPosition(SwingConstants.CENTER);
                btn.setVerticalTextPosition(SwingConstants.BOTTOM);
                btn.setMargin(new Insets(2, 2, 2, 2));
                btn.setMaximumSize(new Dimension(80, 70));
                btn.setPreferredSize(new Dimension(80, 70));
                btn.setMinimumSize(new Dimension(80, 70));

                btn.setId(i);
                btn.addActionListener(this);
                btn.setActionCommand("selecciona articulo");
                

                Image vlImagenImg = Utilerias.decodeToImage(String.valueOf(articulosLst.get(i).get("IMAGEN")));
                BufferedImage vlImagenBim = Utilerias.toBufferedImage(vlImagenImg);

                if (vlImagenBim != null) {
                    Image dimg = vlImagenBim.getScaledInstance(btn.getPreferredSize().width,
                            btn.getPreferredSize().height,
                            Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(dimg);
                    btn.setIcon(imageIcon);
                } else {
                    System.out.println("caca2");
                    btn.setIcon(null);
                }


                view.flowpanelIns.add(btn);

            }

      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (e.getActionCommand().equalsIgnoreCase("selecciona articulo")) {
            int vlIndiceSeleccionadoInt = ((JCustomButton) e.getSource()).getId();
            delegate.seleccionaArticulo(articulosLst.get(vlIndiceSeleccionadoInt));
        }
        else if (e.getActionCommand().equalsIgnoreCase("buscar")) {
            buscarArticulosWithDesc();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getComponent() == this.view.descripcionArtJtf) {
            if (this.view.descripcionArtJtf.getText().length() <= 2) {
                return;
            }
            buscarArticulosWithDesc();
        }
    }
}
