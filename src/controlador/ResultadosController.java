/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import modelo.ResultadosModel;
import vista.ResultadosView;

/**
 *
 * @author Noe
 */
public class ResultadosController implements ActionListener{
    
    private ResultadosView view;
    private ResultadosModel model;
  

    public ResultadosController(ResultadosView pView, ResultadosModel pModel) {


        this.view = pView;
        this.model = pModel;

        init();
    }

    private void init() {
      view.setBounds(10, 10, view.getPreferredSize().width, view.getPreferredSize().height);

      //se añade las acciones a los controles
        this.view.refrescarBtn.addActionListener(this);

        //Se pone a escuchar las acciones del usuario
        this.view.refrescarBtn.setActionCommand("refrescar");
      
    }

    private String textTotalEntrdasStr;
    private String textTotalInvenSinGanStr;
    private String textTotalInvConGanStr;
    private String textTotalVendidoConDeudStr;
    private String textTotalRecibidoStr;
    private String textTotalDeudasStr;
    private String textGananciaEsperadaStr;
    private String textGananciaActualStr;
    private String textGananciaYRecStr;
    public void iniciar() {
        textTotalEntrdasStr = this.view.totalEntradasLbl.getText();
        textTotalInvenSinGanStr = this.view.totalInvenSinGanLbl.getText();
        textTotalInvConGanStr = this.view.totalInvConGanLbl.getText();
        textTotalVendidoConDeudStr = this.view.totalVendidoConDeudLbl.getText();
        textTotalRecibidoStr = this.view.totalRecibidoLbl.getText();
        textTotalDeudasStr = this.view.totalDeudasLbl.getText();
        textGananciaEsperadaStr = this.view.gananciaEsperadaLbl.getText();
        textGananciaActualStr = this.view.gananciaActualLbl.getText();
        textGananciaYRecStr = this.view.gananciaYRecLbl.getText();
        
        refrescar();
    }
    
    private void refrescar(){
        //mostrar el total de entradas
        this.view.totalEntradasLbl.setText(textTotalEntrdasStr
                +" "+
                this.model.getTotalEntradas());
        
        //nos traemos los datos de las inverciones en el inv
        Map vlTotalInvInve = this.model.getTotalesInventario();
        
        this.view.totalInvenSinGanLbl.setText(
                textTotalInvenSinGanStr
                +" "+
                vlTotalInvInve.get("SIN GANANCIA"));
        
        this.view.totalInvConGanLbl.setText(
                textTotalInvConGanStr
                +" "+
                vlTotalInvInve.get("CON GANANCIA"));
        
        this.view.totalVendidoConDeudLbl.setText(
                textTotalVendidoConDeudStr
                +" "+
                this.model.getTotalVendidoConDeudas());
        
        this.view.totalRecibidoLbl.setText(
                textTotalRecibidoStr
                +" "+
                this.model.getTotalRecibido());
        
        this.view.totalDeudasLbl.setText(
                textTotalDeudasStr
                +" "+
                this.model.getTotalDeudas());
        
        this.view.gananciaEsperadaLbl.setText(
                textGananciaEsperadaStr
                +" "+
                this.model.getGananciaEsperada());
        
        //nos traemos los datos de la ganancia y la ganancia mas recuperacion
        Map vlGananciasMap = this.model.getGanancias();
        System.out.println("caca " +vlGananciasMap);
        this.view.gananciaActualLbl.setText(
                textGananciaActualStr
                +" "+
                vlGananciasMap.get("GANANCIA"));
        
        this.view.gananciaYRecLbl.setText(
                textGananciaYRecStr
                +" "+
                vlGananciasMap.get("GANANCIA Y RECUPERACION"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("refrescar")) {
            refrescar();
        }
    }

    
}
