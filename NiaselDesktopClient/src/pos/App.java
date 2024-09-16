/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import pos.controller.AuthController;
import pos.controller.BaseController;
import pos.exception.InstanceNotFoundException;
import pos.view.LoginPage;

/**
 *
 * @author husainazkas
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            BaseController.connect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, new JLabel(ex.getMessage()), "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        instance = new App();
        instance.authController = new AuthController();

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            final LoginPage loginPage = new LoginPage();
//                loginPage.setExtendedState(Frame.MAXIMIZED_BOTH);
            loginPage.setVisible(true);
        });
    }

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private AuthController authController;

    public AuthController getAuthController() throws InstanceNotFoundException {
        if (authController == null) {
            throw new InstanceNotFoundException(AuthController.class);
        }
        return authController;
    }

}
