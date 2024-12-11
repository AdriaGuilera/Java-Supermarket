package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import controladors.CtrlDomini;

public class MainView extends JFrame {

    private CtrlDomini ctrlDomini;

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

        prestatgeriesButton.setFont(new Font("Arial", Font.BOLD, 16));
        comandesButton.setFont(new Font("Arial", Font.BOLD, 16));
        productesButton.setFont(new Font("Arial", Font.BOLD, 16));
        caixaButton.setFont(new Font("Arial", Font.BOLD, 16));

        PrestatgeriesView prestatgeriesView = new PrestatgeriesView(ctrlDomini);
        ComandesView comandesView = new ComandesView(ctrlDomini);
        CaixaView caixaView = new CaixaView(ctrlDomini);
        ProductesView productesView = new ProductesView(ctrlDomini);

        prestatgeriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    // Establecer tamaño y ubicación igual a la vista actual
                    prestatgeriesView.setSize(getSize());
                    prestatgeriesView.setLocation(getLocation());

                    prestatgeriesView.setVisible(true);
                    try {
                        prestatgeriesView.refreshPrestatgeriesView();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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
                    comandesView.refreshComandesList();
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
                    productesView.refreshProductesList();
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
                    caixaView.refreshProductList();
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

