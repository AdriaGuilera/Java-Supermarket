package controladors;

import Views.*;
import javax.swing.*;

import java.util.Map;
import classes.Prestatgeria;
import java.io.IOException;
import java.util.Vector;

/**
 * Controller for managing the presentation layer
 */
public class CtrlPresentation {
    
    private CtrlDomini ctrlDomini;
    private MainView mainView;
    /**
     * Constructor initializes controllers and sets up initial state
     */
    public CtrlPresentation() {
        try {
            initializeControllers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error initializing controllers: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initialize all required controllers
     */
    private void initializeControllers() {
        ctrlDomini = new CtrlDomini();
    }

    private void showMainView(){
        MainView mainView = new MainView(ctrlDomini);
        mainView.setVisible(true);
    }

    /**
     * Start the application UI 
     */
    public void initializePresentation() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(() -> {
                showMainView();
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error starting application: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Application entry point
     */
    public static void main(String[] args) {
        CtrlPresentation ctrlPresentation = new CtrlPresentation();
        ctrlPresentation.initializePresentation();
    }
}
