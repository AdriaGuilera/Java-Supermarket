package Views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import controladors.CtrlDomini;
import classes.Prestatgeria;
import Components.*;

/**
 * Classe que gestiona la vista de Prestatgeries dins l'aplicació.
 * Ofereix funcionalitats per visualitzar, afegir, eliminar i modificar prestatgeries
 * i els productes que contenen.
 * Hereta de {@link JFrame}.
 */
public class PrestatgeriesView extends JFrame {

    /**
     * Controlador de domini que gestiona les operacions relacionades amb prestatgeries i productes.
     */
    public CtrlDomini ctrlDomini;

    /**
     * Panell principal de la interfície de Prestatgeries.
     */
    private JPanel mainPanel;

    /**
     * Panell que conté les prestatgeries i permet canviar entre elles mitjançant un {@link CardLayout}.
     */
    private JPanel prestatgeriesPanel;

    /**
     * Disposició utilitzada per canviar entre les diverses prestatgeries al panell {@code prestatgeriesPanel}.
     */
    private CardLayout cardLayout;

    /**
     * Llista gràfica que mostra els identificadors de les prestatgeries.
     */
    private JList<String> prestatgeriaList;

    /**
     * Model de la llista de prestatgeries que permet afegir i eliminar elements dinàmicament.
     */
    private DefaultListModel<String> listModel;

    /**
     * Booleà que indica si s'han produït canvis en les prestatgeries.
     * Si és cert, caldrà guardar-los abans de tancar la finestra.
     */
    boolean canvis = false;

    /**
     * Constructor de la classe PrestatgeriesView. Inicialitza la interfície i carrega la informació
     * de les prestatgeries des del controlador de domini.
     *
     * @param ctrlDomini Controlador de domini utilitzat per gestionar les operacions de prestatgeries.
     */
    public PrestatgeriesView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

    /**
     * Configura la interfície gràfica: crea els botons, la llista de prestatgeries i el panell
     * principal on es mostren els detalls de cada prestatgeria.
     */
    private void setupUI() {
        setTitle("Gestió de Prestatgeries");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Initialize main panel
        mainPanel = new JPanel(new BorderLayout());

        // Initialize card layout for prestatgeries
        cardLayout = new CardLayout();
        prestatgeriesPanel = new JPanel(cardLayout);

        // Initialize list model and JList for selection
        listModel = new DefaultListModel<>();
        prestatgeriaList = new JList<>(listModel);
        prestatgeriaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prestatgeriaList.setLayoutOrientation(JList.VERTICAL);
        prestatgeriaList.setCellRenderer(new CustomListCellRenderer());
        prestatgeriaList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedId = prestatgeriaList.getSelectedValue();
                try {
                    refreshShelf();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (selectedId != null) {
                    cardLayout.show(prestatgeriesPanel, selectedId);
                }
            }
        });

        // Create main buttons container
        JPanel buttonsContainer = new JPanel(new GridLayout(2, 1, 5, 10));

        // First row of buttons
        JPanel topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton addButton = new StyledButton("Afegir Prestatgeria");
        JButton deleteButton = new StyledButton("Eliminar Prestatgeria");
        JButton addProductButton = new StyledButton("Afegir Producte");
        JButton removeProductButton = new StyledButton("Retirar Producte");
        JButton fixProductButton = new StyledButton("Fixar Producte");

        topButtonsPanel.add(addButton);
        topButtonsPanel.add(deleteButton);
        topButtonsPanel.add(addProductButton);
        topButtonsPanel.add(removeProductButton);
        topButtonsPanel.add(fixProductButton);

        // Second row of buttons
        JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton unfixProductButton = new StyledButton("Desfixar Producte");
        JButton generateDistButton = new StyledButton("Generar Distribució");
        JButton decrementStockButton = new StyledButton("Decrementar Stock");
        JButton addShelfButton = new StyledButton("Afegir Prestatge");
        JButton removeShelfButton = new StyledButton("Eliminar Prestatge");

        bottomButtonsPanel.add(unfixProductButton);
        bottomButtonsPanel.add(generateDistButton);
        bottomButtonsPanel.add(decrementStockButton);
        bottomButtonsPanel.add(addShelfButton);
        bottomButtonsPanel.add(removeShelfButton);

        // Add button rows to container
        buttonsContainer.add(topButtonsPanel);
        buttonsContainer.add(bottomButtonsPanel);

        // Add to main panel
        mainPanel.add(buttonsContainer, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(prestatgeriaList), BorderLayout.WEST);
        mainPanel.add(new JScrollPane(prestatgeriesPanel), BorderLayout.CENTER);

        // Add button listeners
        addButton.addActionListener(e -> showAddPrestatgeriaDialog());
        deleteButton.addActionListener(e -> showDeletePrestatgeriaDialog());
        addProductButton.addActionListener(e -> showAddProductDialog());
        removeProductButton.addActionListener(e -> showRemoveProductDialog());
        fixProductButton.addActionListener(e -> showFixProductDialog());
        unfixProductButton.addActionListener(e -> showUnfixProductDialog());
        generateDistButton.addActionListener(e -> showGenerateDistributionDialog());
        decrementStockButton.addActionListener(e -> showDecrementStockDialog());
        addShelfButton.addActionListener(e -> showAddShelfDialog());
        removeShelfButton.addActionListener(e -> showRemoveShelfDialog());

        // Add the back button to the top left corner
        BackButton backButton = new BackButton("Back", e -> {showLeaveDialog();});
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        backButtonPanel.add(backButton);

        //When the user closes window show leave dialog
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                showLeaveDialog();
            }
        });

        // Create a panel to combine the back button and the top buttons
        JPanel combinedTopPanel = new JPanel(new BorderLayout());
        combinedTopPanel.add(backButtonPanel, BorderLayout.NORTH);
        combinedTopPanel.add(buttonsContainer, BorderLayout.CENTER);

        // Add the combined panel to the main panel
        mainPanel.add(combinedTopPanel, BorderLayout.NORTH);

        // Add main panel to frame
        add(mainPanel);
        try {
            refreshPrestatgeriesView();
            if (!listModel.isEmpty()) {
                prestatgeriaList.setSelectedIndex(0); // Select the first element
            }
            refreshShelf();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualitza la llista de prestatgeries i el panell corresponent amb les darreres dades
     * obtingudes del controlador de domini.
     *
     * @throws IOException si hi ha algun problema en carregar la informació de les prestatgeries.
     */
    public void refreshPrestatgeriesView() throws IOException {
        // Save the currently selected prestatgeria ID
        String selectedId = prestatgeriaList.getSelectedValue();

        prestatgeriesPanel.removeAll(); // Clear existing components
        listModel.clear(); // Clear the list model
        List<String> prestatgeries = ctrlDomini.getIdsPrestatgeries(); // Fetch the IDs

        int maxElementWidth = 0;
        FontMetrics fontMetrics = prestatgeriaList.getFontMetrics(prestatgeriaList.getFont());

        // Add IDs to the list model and compute the widest element's width
        for (String id : prestatgeries) {
            listModel.addElement(id);

            int elementWidth = fontMetrics.stringWidth(id);
            if (elementWidth > maxElementWidth) {
                maxElementWidth = elementWidth;
            }
        }

        // Restore the previously selected prestatgeria, if it still exists
        if (selectedId != null && prestatgeries.contains(selectedId)) {
            prestatgeriaList.setSelectedValue(selectedId, true);
        } else if (!listModel.isEmpty()) {
            prestatgeriaList.setSelectedIndex(0); // Default to the first item if no valid selection
        }

        // Adjust the preferred size of the list to accommodate new content
        prestatgeriaList.setPreferredSize(new Dimension(maxElementWidth + 20, prestatgeriaList.getPreferredSize().height));
        prestatgeriaList.revalidate(); // Revalidate the JList
        prestatgeriesPanel.revalidate(); // Revalidate the panel to apply layout changes
        prestatgeriesPanel.repaint(); // Repaint the panel

        mainPanel.revalidate(); // Revalidate the main panel to apply layout changes
        mainPanel.repaint(); // Repaint the main panel

        // Show the card for the currently selected prestatgeria
        if (!listModel.isEmpty()) {
            cardLayout.show(prestatgeriesPanel, prestatgeriaList.getSelectedValue());
        }
    }

    /**
     * Actualitza la visualització de la prestatgeria seleccionada. Dibuixa cada prestatge
     * i la seva distribució de productes.
     *
     * @throws IOException si hi ha algun problema en carregar la informació de la prestatgeria.
     */
    public void refreshShelf() throws IOException {
        if (listModel.isEmpty()) {
            prestatgeriesPanel.removeAll();
            prestatgeriesPanel.revalidate();
            prestatgeriesPanel.repaint();
            return;
        }
        // Get the currently selected ID
        String selectedId = prestatgeriaList.getSelectedValue();
        if (selectedId == null) {
            return; // Do nothing if no selection
        }
        // Clear the panel and set up the new prestatgeria view
        prestatgeriesPanel.removeAll();
        JPanel prestPanel = new JPanel(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Prestatgeria " + selectedId,
                TitledBorder.CENTER,
                TitledBorder.TOP
        );
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        border.setTitlePosition(TitledBorder.ABOVE_TOP);
        border.setTitleJustification(TitledBorder.CENTER);
        prestPanel.setBorder(border);

        Prestatgeria prest = ctrlDomini.getPrestatgeria(selectedId);
        int numShelves = prest.getMidaPrestatgeria() / prest.getMidaPrestatge();
        int shelfSize = prest.getMidaPrestatge();
        JPanel shelvesPanel = new JPanel(new GridLayout(numShelves, 1, 5, 5));

        for (int shelf = 0; shelf < numShelves; shelf++) {
            JPanel shelfPanel = new JPanel(new GridLayout(1, shelfSize, 2, 2));
            shelfPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            for (int pos = shelf * shelfSize; pos < (shelf + 1) * shelfSize; pos++) {
                String productName = prest.getPosicions().get(pos);
                JPanel cellPanel = createProductCell(prest, pos, productName);
                shelfPanel.add(cellPanel);
            }

            shelvesPanel.add(shelfPanel);
        }

        prestPanel.add(shelvesPanel, BorderLayout.CENTER);
        prestatgeriesPanel.add(prestPanel, selectedId);

        prestatgeriesPanel.revalidate();
        prestatgeriesPanel.repaint();
    }

    /**
     * Crea un panell que representa una posició concreta dins d'una prestatgeria, mostrant
     * el producte i la quantitat que conté.
     *
     * @param prest        La prestatgeria a la qual pertany la posició.
     * @param position     La posició dins de la prestatgeria.
     * @param productName  El nom del producte en aquesta posició (pot ser null si està buit).
     * @return Un {@link JPanel} configurat per representar la posició i, si escau, el seu producte.
     */
    private JPanel createProductCell(Prestatgeria prest, int position, String productName) {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cell.setName("pos_" + position);

        TransferHandler handler = new ProductTransferHandler(this, prest.getId(), position);
        cell.setTransferHandler(handler);

        if (productName != null) {
            Integer quantity = prest.getProductes().get(productName);

            JLabel nameLabel = new JLabel(productName);
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel quantityLabel = new JLabel("Qty: " + quantity);
            quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

            if (prest.getProductesFixats().contains(productName)) {
                cell.setBackground(new Color(255, 220, 220));
                nameLabel.setForeground(Color.RED);
            } else {
                cell.setBackground(Color.WHITE);
            }

            cell.add(nameLabel, BorderLayout.CENTER);
            cell.add(quantityLabel, BorderLayout.SOUTH);

            cell.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    JComponent c = (JComponent) e.getSource();
                    c.getTransferHandler().exportAsDrag(c, e, TransferHandler.MOVE);
                }
            });
        } else {
            cell.setBackground(new Color(240, 240, 240));
        }

        cell.setPreferredSize(new Dimension(20, 20));
        return cell;
    }

    /**
     * Mostra un diàleg per afegir una nova prestatgeria al sistema.
     * Demana l'ID de la prestatgeria, la mida total i la mida de cada prestatge.
     */
    private void showAddPrestatgeriaDialog() {
        JDialog dialog = new JDialog(this, "Afegir Prestatgeria", true);
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField idField = new JTextField();
        JTextField midaField = new JTextField();
        JTextField midaPrestField = new JTextField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Mida Total:"));
        panel.add(midaField);
        panel.add(new JLabel("Mida Prestatge:"));
        panel.add(midaPrestField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String id = idField.getText().toLowerCase();
                int mida = Integer.parseInt(midaField.getText());
                int midaPrest = Integer.parseInt(midaPrestField.getText());
                System.out.println(id);
                ctrlDomini.afegirPrestatgeria(id, midaPrest, mida);
                refreshPrestatgeriesView();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per eliminar la prestatgeria seleccionada.
     */
    private void showDeletePrestatgeriaDialog() {
        JDialog dialog = new JDialog(this, "Eliminar Prestatgeria", true);
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                ctrlDomini.eliminarPrestatgeria(prestId);
                refreshPrestatgeriesView();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per afegir un producte a la prestatgeria seleccionada.
     * Demana el nom del producte i la quantitat a afegir.
     */
    private void showAddProductDialog() {
        JDialog dialog = new JDialog(this, "Afegir Producte", true);
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField productNameField = new JTextField();
        JTextField quantityField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);
        panel.add(new JLabel("Quantitat:"));
        panel.add(quantityField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                String productName = productNameField.getText().toLowerCase();
                int quantity = Integer.parseInt(quantityField.getText());
                ctrlDomini.afegirProductePrestatgeria(productName, quantity, prestId);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per retirar un producte de la prestatgeria seleccionada.
     * Demana el nom del producte a retirar.
     */
    private void showRemoveProductDialog() {
        JDialog dialog = new JDialog(this, "Retirar Producte", true);
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField productNameField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                String productName = productNameField.getText().toLowerCase();
                ctrlDomini.retirarProducteAMagatzem(prestId, productName);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per fixar un producte dins la prestatgeria seleccionada.
     * Un producte fixat no es mourà automàticament durant la generació de distribucions.
     */
    private void showFixProductDialog() {
        JDialog dialog = new JDialog(this, "Fixar Producte", true);
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField productNameField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                String productName = productNameField.getText().toLowerCase();
                ctrlDomini.fixarProducte(prestId, productName);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per desfixar un producte fixat prèviament a la prestatgeria seleccionada.
     */
    private void showUnfixProductDialog() {
        JDialog dialog = new JDialog(this, "Desfixar Producte", true);
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField productNameField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                String productName = productNameField.getText().toLowerCase();
                ctrlDomini.desfixarProducte(prestId, productName);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per generar una distribució automàtica de productes en la prestatgeria seleccionada.
     * Permet triar entre els algorismes de Hill Climbing i Backtracking.
     */
    private void showGenerateDistributionDialog() {
        JDialog dialog = new JDialog(this, "Generar Distribució", true);
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JPanel idPanel = new JPanel(new BorderLayout());
        idPanel.add(new JLabel("ID Prestatgeria:"), BorderLayout.WEST);
        idPanel.add(prestIdField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton hillClimbingButton = new JButton("Hill Climbing");
        JButton backtrackingButton = new JButton("Backtracking");

        hillClimbingButton.addActionListener(e -> {
            try {
                String id = prestIdField.getText().toLowerCase();
                ctrlDomini.generarDistribucioHillClimbing(id);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backtrackingButton.addActionListener(e -> {
            try {
                String id = prestIdField.getText().toLowerCase();
                ctrlDomini.generarDistribucioBacktracking(id);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(hillClimbingButton);
        buttonPanel.add(backtrackingButton);

        panel.add(idPanel);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per disminuir la quantitat d'un producte a la prestatgeria seleccionada.
     * Demana el nom del producte i la quantitat que es vol restar del seu estoc.
     */
    private void showDecrementStockDialog() {
        JDialog dialog = new JDialog(this, "Decrementar Stock", true);
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField productNameField = new JTextField();
        JTextField quantityField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);
        panel.add(new JLabel("Quantitat:"));
        panel.add(quantityField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                String productName = productNameField.getText().toLowerCase();
                int quantity = Integer.parseInt(quantityField.getText());
                ctrlDomini.decrementarStockAProducte(prestId, productName, quantity);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per afegir un prestatge addicional a la prestatgeria seleccionada.
     */
    private void showAddShelfDialog() {
        JDialog dialog = new JDialog(this, "Afegir Prestatge", true);
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField shelfSizeField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                ctrlDomini.afegirPrestatge(prestId);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per eliminar el darrer prestatge de la prestatgeria seleccionada.
     */
    private void showRemoveShelfDialog() {
        JDialog dialog = new JDialog(this, "Eliminar Prestatge", true);
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField prestIdField = new JTextField(prestatgeriaList.getSelectedValue());
        prestIdField.setEditable(false);
        JTextField shelfIndexField = new JTextField();

        panel.add(new JLabel("ID Prestatgeria:"));
        panel.add(prestIdField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String prestId = prestIdField.getText().toLowerCase();
                ctrlDomini.eliminarPrestatge(prestId);
                refreshShelf();
                canvis = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(acceptButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Mostra un diàleg per confirmar la sortida d'aquesta vista.
     * Si {@code canvis} és cert, guarda les dades abans de tancar la finestra.
     * Retorna a la vista principal {@link MainView}.
     */
    private void showLeaveDialog() {
        if (canvis) {
            try {
                ctrlDomini.guardarPrestatgeries();
                ctrlDomini.guardarProductes();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error guardant les dades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        dispose();
        MainView mainView = new MainView(ctrlDomini);
        mainView.setSize(getSize());
        mainView.setLocation(getLocation());
        mainView.setVisible(true);
    }
}
