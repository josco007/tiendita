/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import vista.add_any_item.AddAnyItemDlgView;

/**
 *
 * @author noe_i
 */
public class AddAnyItemDlgController  implements ActionListener, KeyListener{

   

 
    public interface AddAnyItemDlgControllerDelegate {
         void addAnyItemDlgControllerEvent(AddAnyItemDlgController pAddAnyItemDlgController, 
                Events pEvent, Object pData);
    }
    
    public  enum Events{
        DID_ITEM_ADDED,
        DID_CANCEL
    }
    
    
     enum Modes {
        ADD_TO_ACCOUNT ,
        FILL_FORM
    }
    
    
    public AddAnyItemDlgView view;
    
    private AddAnyItemDlgControllerDelegate delegate;
    
    private Modes mode;

    public AddAnyItemDlgController() {
        view = new AddAnyItemDlgView(null, true);
        init();
    }
    
    private void init(){
        addListeners();
        setMode(Modes.ADD_TO_ACCOUNT);
    }
    
    private void addListeners(){
        view.addItemBtn.addActionListener(this);
        view.addItemBtn.setActionCommand("addItemBtn");
        
        view.addToAccountRbt.addActionListener(this);
        view.addToAccountRbt.setActionCommand("addToAccountRbt");
        
        view.fillFormRbt.addActionListener(this);
        view.fillFormRbt.setActionCommand("fillFormRbt");
        
        view.priceTxt.addKeyListener(this);
    }

    public void setDelegate(AddAnyItemDlgControllerDelegate delegate) {
        this.delegate = delegate;
    }
    
    private void setMode(Modes pMode){
        mode = pMode;
        switch (mode){
            case  ADD_TO_ACCOUNT:
                setAddToAccountMode();
                break;
            case FILL_FORM:
                setFillFormMode();
                break;
        }
    }
    
    private void setAddToAccountMode(){
        view.priceTxt.grabFocus();
        view.itemNameTxt.setText("Generico");
        view.descriptionTxt.setText("Generico");
        view.costTxt.setText("");
        view.priceTxt.setText("");
        view.amountTxt.setText("1");
    }
    
    private void setFillFormMode(){
         view.itemNameTxt.setText("");
        view.descriptionTxt.setText("");
        view.costTxt.setText("");
        view.priceTxt.setText("");
        view.amountTxt.setText("");
    }


    
     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("addItemBtn")){
            delegate.addAnyItemDlgControllerEvent(this, Events.DID_ITEM_ADDED, e);
        }
        else if (e.getActionCommand().equalsIgnoreCase("addToAccountRbt")){
            setMode(Modes.ADD_TO_ACCOUNT);
        }
        else if (e.getActionCommand().equalsIgnoreCase("fillFormRbt")){
            setMode(Modes.FILL_FORM);
        }
        else if (e.getActionCommand().equalsIgnoreCase("cancelBtn")){
            delegate.addAnyItemDlgControllerEvent(this, Events.DID_CANCEL, e);
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
        if (e.getComponent().equals(view.priceTxt) && mode == Modes.ADD_TO_ACCOUNT){
            
            view.costTxt.setText(view.priceTxt.getText());
            
        }
    }
    
    
    
    
}
