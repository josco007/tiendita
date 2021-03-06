/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.articulosPanel.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Noe
 */
public class ArticulosPanelView extends javax.swing.JPanel {

    /**
     * Creates new form ArticulosPanelView
     */
    
    public JFlowPanel flowpanelIns = new JFlowPanel();
    
    public ArticulosPanelView() {
        initComponents();
       
       /* flowpanelIns.setBounds(0, 0, 830, 170);
        flowpanelIns.setBorder(BorderFactory.createLineBorder(Color.black));
        flowpanelIns.setPreferredSize(new Dimension(830, 170));
        flowpanelIns.setAutoscrolls(true);
         
        articulosPanel.add(flowpanelIns);
        */
        
        flowpanelIns = new JFlowPanel();
        //flowpanelIns.setBounds(0, 0, 830, 170);
       // flowpanelIns.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scroll = new JScrollPane(flowpanelIns);
        scroll.setBounds(0, 0, 830, 170);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
        
        articulosPanel.add(scroll, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descripcionArtJtf = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        buscarBtn = new javax.swing.JButton();
        articulosPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("    Articulos"));
        setPreferredSize(new java.awt.Dimension(920, 233));

        jLabel5.setText("Descripcion:");

        buscarBtn.setText("buscar");

        articulosPanel.setPreferredSize(new java.awt.Dimension(830, 170));

        javax.swing.GroupLayout articulosPanelLayout = new javax.swing.GroupLayout(articulosPanel);
        articulosPanel.setLayout(articulosPanelLayout);
        articulosPanelLayout.setHorizontalGroup(
            articulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
        articulosPanelLayout.setVerticalGroup(
            articulosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descripcionArtJtf, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarBtn))
                    .addComponent(articulosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(descripcionArtJtf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(articulosPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel articulosPanel;
    public javax.swing.JButton buscarBtn;
    public javax.swing.JTextField descripcionArtJtf;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
