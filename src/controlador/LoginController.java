/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.LoginView;

/**
 *
 * @author Noe
 */
public class LoginController implements ActionListener{
    
       //interface para delegar
    public interface LoginDelegate {
        void loginController(LoginController loginController, String host, String usuario, String password);
    }
    
    private LoginDelegate delegate;

    private LoginView view;
   

    public LoginController(LoginView pView) {
        this.view = pView;

        init();
    }

    private void init() {
        //view.setBounds(10, 10, view.getPreferredSize().width, view.getPreferredSize().height);

        //se añade las acciones a los controles
        this.view.entrarBtn.addActionListener(this);

        //Se pone a escuchar las acciones del usuario
        this.view.entrarBtn.setActionCommand("entrar");


    }

    public void iniciar() {
        this.view.setVisible(true);

    }
    
    public void setDelegate(LoginDelegate pDelegate){
       delegate = pDelegate;
    }
    
    public void cerrarLogin(){
        this.view.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("entrar")) {
            delegate.loginController(this, 
                    this.view.hostTxt.getText(),
                    this.view.usuarioTxt.getText(),
                    String.valueOf(this.view.passwordPfd.getPassword()));
        }
    }
    
}
