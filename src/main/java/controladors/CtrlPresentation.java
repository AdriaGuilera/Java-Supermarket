package controladors;

import Views.PrestatgeriesView;
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
    private PrestatgeriesView prestatgeriesView;
    private ComandesView comandesView;
    private ProductesView productesView;
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



    /**
     * Initialize and display PrestatgeriesView
     */
    private void showPrestatgeriesView() {
        prestatgeriesView = new PrestatgeriesView(ctrlDomini);
        prestatgeriesView.setVisible(true);
    }

    /**
     * Initialize and display PrestatgeriesView
     */
    private void showComandesView() {
        comandesView = new ComandesView(ctrlDomini);
        comandesView.setVisible(true);
    }

    /**
     * Initialize and display PrestatgeriesView
     */
    private void showProductesView() {
        productesView = new ProductesView(ctrlDomini);
        productesView.setVisible(true);
    }

    /**
     * Start the application UI 
     */
    public void initializePresentation() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(() -> {
                showProductesView();
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
