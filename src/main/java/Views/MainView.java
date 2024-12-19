package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controladors.CtrlDomini;

public class MainView extends JFrame {

    private CtrlDomini ctrlDomini;
    private PrestatgeriesView prestatgeriesView;
    private ComandesView comandesView;
    private CaixaView caixaView;
    private ProductesView productesView;

    public MainView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

    private void setupUI() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton prestatgeriesButton = new JButton("Gestió de Prestatgeries");
        JButton comandesButton = new JButton("Gestió de Comandes");
        JButton productesButton = new JButton("Gestió de Productes");
        JButton caixaButton = new JButton("Gestió de Caixa");
        
        prestatgeriesButton.setFocusable(false);
        comandesButton.setFocusable(false);
        productesButton.setFocusable(false);
        caixaButton.setFocusable(false);

        prestatgeriesButton.setFont(new Font("Arial", Font.BOLD, 16));
        comandesButton.setFont(new Font("Arial", Font.BOLD, 16));
        productesButton.setFont(new Font("Arial", Font.BOLD, 16));
        caixaButton.setFont(new Font("Arial", Font.BOLD, 16));

        prestatgeriesView = new PrestatgeriesView(ctrlDomini);
        comandesView = new ComandesView(ctrlDomini);
        caixaView = new CaixaView(ctrlDomini);
        productesView = new ProductesView(ctrlDomini);

        prestatgeriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    // Establecer tamaño y ubicación igual a la vista actual
                    prestatgeriesView.setSize(getSize());
                    prestatgeriesView.setLocation(getLocation());

                    prestatgeriesView.setVisible(true);
                    // Ocultar MainView
                    dispose();                });
            }
        });

        comandesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    comandesView.setSize(getSize());
                    comandesView.setLocation(getLocation());

                    comandesView.setVisible(true);
                    // Ocultar MainView
                    dispose();});
            }
        });

        productesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    productesView.setSize(getSize());
                    productesView.setLocation(getLocation());

                    productesView.setVisible(true);
                    // Ocultar MainView
                    dispose();                });
            }
        });

        caixaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    caixaView.setSize(getSize());
                    caixaView.setLocation(getLocation());

                    caixaView.setVisible(true);
                    // Ocultar MainView
                    dispose();                });
            }
        });

        mainPanel.add(prestatgeriesButton);
        mainPanel.add(comandesButton);
        mainPanel.add(productesButton);
        mainPanel.add(caixaButton);

        add(mainPanel);
    }

}

