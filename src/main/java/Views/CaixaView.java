package Views;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import controladors.CtrlDomini;
import Components.*;

/**
 * Classe que gestiona la vista de la Caixa, permet afegir, retirar productes i realitzar el pagament.
 * Hereta de JFrame.
 */
public class CaixaView extends JFrame {
    /**
     * Controlador de domini associat a la gestió de la Caixa.
     */
    private final CtrlDomini ctrlDomini;

    /**
     * Model per representar la llista de productes a la Caixa.
     */
    private DefaultListModel<String> productListModel;

    /**
     * Element gràfic que mostra la llista de productes a la Caixa.
     */
    private JList<String> productList;

    /**
     * Indicador de si hi ha hagut canvis a la Caixa.
     */
    boolean canvis = false;

    /**
     * Constructor de la classe CaixaView.
     * Carrega l'estat de la Caixa i inicialitza la interfície gràfica.
     *
     * @param ctrlDomini Controlador de domini utilitzat per gestionar la Caixa.
     */
    public CaixaView(CtrlDomini ctrlDomini)  {
        this.ctrlDomini = ctrlDomini;
        try {
            this.ctrlDomini.carregarCaixa();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error carregant la caixa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        setupUI();
    }

    /**
     * Configura i disposa els elements de la interfície gràfica de l'aplicació.
     */
    private void setupUI() {
        setTitle("Gestió de Caixa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Product list
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.setCellRenderer(new CustomListCellRenderer());

        JScrollPane listScrollPane = new JScrollPane(productList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Productes a la Caixa"));
        mainPanel.add(listScrollPane, BorderLayout.CENTER);

        // Top buttons (Back + Operations)
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        BackButton backButton = new BackButton("Back", e -> showLeaveDialog());
        backPanel.add(backButton);
        JPanel operationButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        StyledButton addButton = new StyledButton("Afegir Producte");
        StyledButton removeButton = new StyledButton("Retirar Producte");
        StyledButton payButton = new StyledButton("Pagar");

        operationButtonsPanel.add(addButton);
        operationButtonsPanel.add(removeButton);
        operationButtonsPanel.add(payButton);

        JPanel topButtonsPanel = new JPanel(new BorderLayout());
        topButtonsPanel.add(backPanel, BorderLayout.NORTH);
        topButtonsPanel.add(operationButtonsPanel, BorderLayout.CENTER);

        mainPanel.add(topButtonsPanel, BorderLayout.NORTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                showLeaveDialog();
            }
        });

        add(mainPanel);

        // Button actions
        addButton.addActionListener(e -> showAddProductDialog());
        removeButton.addActionListener(e -> showRemoveProductDialog());
        payButton.addActionListener(e -> payCaixa());

        // Load initial data
        refreshProductList();
    }

    /**
     * Actualitza la llista de productes mostrada a la interfície gràfica amb les dades del controlador de domini.
     */
    public void refreshProductList() {
        productListModel.clear();
        try {
            Map<String, Integer> ticket = ctrlDomini.getTicket();
            ticket.forEach((product, quantity) -> productListModel.addElement(product + "  " + quantity));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mostra el diàleg per afegir un producte a la Caixa.
     * Permet introduir el nom, la quantitat i la prestatgeria del producte.
     */
    private void showAddProductDialog() {
        JDialog dialog = new JDialog(this, "Afegir Producte a la Caixa", true);
        dialog.setLayout(new GridLayout(4, 2));

        JTextField productField = new JTextField();
        // Get selected item if any
        String selected = productList.getSelectedValue();
        if (selected != null) {
            // Parse product name (everything before two spaces)
            String productName = selected.split("  ")[0];
            productField.setText(productName);
        }

        JTextField quantityField = new JTextField();
        JTextField prestatgeriaField = new JTextField();

        dialog.add(new JLabel("Nom del Producte:"));
        dialog.add(productField);
        dialog.add(new JLabel("Quantitat:"));
        dialog.add(quantityField);
        dialog.add(new JLabel("Prestatgeria:"));
        dialog.add(prestatgeriaField);

        JButton confirmButton = new JButton("Acceptar");

        confirmButton.addActionListener(e -> {
            try {
                String product = productField.getText().toLowerCase();
                int quantity = Integer.parseInt(quantityField.getText());
                String prestatgeria = prestatgeriaField.getText().toLowerCase();
                ctrlDomini.afegir_producte_caixa(product, quantity, prestatgeria);
                canvis = true;
                dialog.dispose();
                refreshProductList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(confirmButton);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra el diàleg per retirar un producte de la Caixa.
     * Permet introduir el nom i la quantitat del producte a retirar.
     */
    private void showRemoveProductDialog() {
        JDialog dialog = new JDialog(this, "Retirar Producte de la Caixa", true);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField productField = new JTextField();
        // Get selected item if any
        String selected = productList.getSelectedValue();
        if (selected != null) {
            // Parse product name (everything before two spaces)
            String productName = selected.split("  ")[0];
            productField.setText(productName);
        }
        JTextField quantityField = new JTextField();

        dialog.add(new JLabel("Nom del Producte:"));
        dialog.add(productField);
        dialog.add(new JLabel("Quantitat:"));
        dialog.add(quantityField);

        JButton confirmButton = new JButton("Acceptar");
        confirmButton.addActionListener(e -> {
            try {
                String product = productField.getText().toLowerCase();
                if (product.isEmpty()) {
                    throw new IllegalArgumentException("Cal seleccionar un producte");
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0) {
                        throw new IllegalArgumentException("La quantitat ha de ser positiva");
                    }
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("La quantitat ha de ser un número");
                }

                ctrlDomini.retirar_producte_caixa(product, quantity);
                canvis = true;
                dialog.dispose();
                refreshProductList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(confirmButton);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Realitza l'operació de pagament de la Caixa i actualitza la llista de productes.
     * En cas d'error, mostra un missatge d'error a l'usuari.
     */
    private void payCaixa() {
        try {
            ctrlDomini.pagar_caixa();
            canvis = true;
            refreshProductList();
            JOptionPane.showMessageDialog(this, "Pagament realitzat!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mostra un diàleg per sortir de la finestra de gestió de Caixa.
     * Si hi ha hagut canvis, guarda les dades abans de tancar la finestra i obrir la MainView.
     */
    private void showLeaveDialog() {
        if (canvis) {
            try {
                ctrlDomini.guardarCaixa();
                ctrlDomini.guardarProductes();
                ctrlDomini.guardarPrestatgeries();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error guardant les dades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        dispose();
        MainView mainView = new MainView(ctrlDomini);
        mainView.setSize(getSize());
        mainView.setLocation(getLocation());
        mainView.setVisible(true);
    }

    /**
     * Renderer personalitzat per a la llista de productes.
     * Canvia el color de fons, la mida de la font i el color del text de l'element seleccionat.
     */
    private static class CustomListCellRenderer extends DefaultListCellRenderer {
        /**
         * Mètode sobrescrit que retorna el component per representar cada element de la llista.
         *
         * @param list         la JList on es mostra l'element
         * @param value        l'objecte que s'està representant a la llista
         * @param index        l'índex de l'element
         * @param isSelected   indica si l'element està seleccionat
         * @param cellHasFocus indica si la cel·la té el focus
         * @return el component que representa l'element de la llista
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setOpaque(true);
            if (index % 2 == 0) {
                label.setBackground(new Color(154, 198, 232)); // Light blue
            } else {
                label.setBackground(new Color(236, 217, 182)); // Light coral
            }
            label.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font size
            if (isSelected) {
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border for selection
                label.setForeground(Color.BLACK); // Ensure text remains black
            } else {
                label.setBorder(BorderFactory.createEmptyBorder());
            }
            return label;
        }
    }
}
