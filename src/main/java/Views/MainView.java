package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controladors.CtrlDomini;

/**
 * Classe principal de la interfície gràfica, que mostra el menú principal de l'aplicació.
 * Permet accedir a diferents vistes (PrestatgeriesView, ComandesView, CaixaView i ProductesView).
 * Hereta de {@link JFrame}.
 */
public class MainView extends JFrame {

    /**
     * Controlador de domini compartit per les diferents vistes.
     */
    private CtrlDomini ctrlDomini;

    /**
     * Vista de prestatgeries.
     */
    private PrestatgeriesView prestatgeriesView;

    /**
     * Vista de comandes.
     */
    private ComandesView comandesView;

    /**
     * Vista de caixa.
     */
    private CaixaView caixaView;

    /**
     * Vista de productes.
     */
    private ProductesView productesView;

    /**
     * Constructor de la classe MainView. Inicialitza el controlador de domini i
     * configura la interfície gràfica.
     *
     * @param ctrlDomini Controlador de domini utilitzat per gestionar l'aplicació.
     */
    public MainView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

    /**
     * Configura la interfície gràfica principal, amb botons que permeten l'accés
     * a les vistes de Prestatgeries, Comandes, Productes i Caixa.
     */
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
                    prestatgeriesView.setSize(getSize());
                    prestatgeriesView.setLocation(getLocation());
                    prestatgeriesView.setVisible(true);
                    dispose();
                });
            }
        });

        comandesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    comandesView.setSize(getSize());
                    comandesView.setLocation(getLocation());
                    comandesView.setVisible(true);
                    dispose();
                });
            }
        });

        productesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    productesView.setSize(getSize());
                    productesView.setLocation(getLocation());
                    productesView.setVisible(true);
                    dispose();
                });
            }
        });

        caixaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    caixaView.setSize(getSize());
                    caixaView.setLocation(getLocation());
                    caixaView.setVisible(true);
                    dispose();
                });
            }
        });

        mainPanel.add(prestatgeriesButton);
        mainPanel.add(comandesButton);
        mainPanel.add(productesButton);
        mainPanel.add(caixaButton);

        add(mainPanel);
    }
}
