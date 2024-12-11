package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.TimerTask;

import Components.SaveButton;
import Components.StyledButton;
import Components.BackButton;
import Components.StyledButtonGuardar;
import classes.Producte;
import controladors.CtrlDomini;

public class ProductesView extends JFrame {
    private CtrlDomini ctrlDomini;
    private JPanel mainPanel;
    private JList<String> productesList;
    private DefaultListModel<String> listModel;

    public ProductesView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

    private void setupUI() {
        setTitle("Gestió de Productes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Main panel setup
        mainPanel = new JPanel(new BorderLayout());

        // Back button
        JButton backButton = new BackButton("Back", e -> dispose());
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 5, 10, 10));

        // Buttons
        JButton createComandaButton = new StyledButton("Crear Producte");
        JButton deleteComandaButton = new StyledButton("Eliminar producte");
        JButton addProductButton = new StyledButtonGuardar("Afegir Similitud Productes");
        JButton removeProductButton = new StyledButtonGuardar("Eliminar Similitud Productes");

        // Set larger font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        createComandaButton.setFont(buttonFont);
        deleteComandaButton.setFont(buttonFont);
        addProductButton.setFont(buttonFont);
        removeProductButton.setFont(buttonFont);

        // Add buttons to panel
        buttonsPanel.add(createComandaButton);
        buttonsPanel.add(deleteComandaButton);
        buttonsPanel.add(addProductButton);
        buttonsPanel.add(removeProductButton);

        // Add action listeners
        createComandaButton.addActionListener(e -> showCreateProducteDialog());
        deleteComandaButton.addActionListener(e -> showDeleteProducteDialog());
        addProductButton.addActionListener(e -> showAddSimilitudDialog());
        removeProductButton.addActionListener(e -> showRemoveSimilitudDialog());

        // Combine back button and other buttons
        JPanel combinedTopPanel = new JPanel(new BorderLayout());
        combinedTopPanel.add(backButtonPanel, BorderLayout.NORTH);
        combinedTopPanel.add(buttonsPanel, BorderLayout.SOUTH);

        mainPanel.add(combinedTopPanel, BorderLayout.NORTH);

        // List of comandes
        listModel = new DefaultListModel<>();
        productesList = new JList<>(listModel);
        productesList.setFont(new Font("Arial", Font.BOLD, 24)); // Set larger font size for list
        productesList.setCellRenderer(new CustomListCellRenderer()); // Custom renderer for colors and borders
        productesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(productesList);
        listScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        productesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detectar doble clic
                    int index = productesList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        productesList.setSelectedIndex(index); // Seleccionar l'índex clicat
                        showVeureProducteDialog(); // Mostrar la informació del producte
                    }
                }
            }
        });
        productesList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Create a container panel for the list with padding
        JPanel listContainerPanel = new JPanel(new BorderLayout());
        listContainerPanel.setBorder(BorderFactory.createEmptyBorder(20, (getWidth()- 500)/2, 20, (getWidth()- 500)/2));
        listContainerPanel.add(listScrollPane, BorderLayout.CENTER);

        // Add the container panel to the main panel
        mainPanel.add(listContainerPanel, BorderLayout.CENTER);

        // Save button
        JButton saveButton = new SaveButton("Guardar");
        saveButton.setFont(buttonFont); // Set larger font for save button
        saveButton.addActionListener(e -> {
            try {
                ctrlDomini.guardar();
                refreshProductesList();
                JOptionPane.showMessageDialog(this, "Dades guardades correctament!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en guardar les dades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(saveButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        refreshProductesList();
    }

    public void refreshProductesList() {
        try {
            listModel.clear();
            Map<String, Producte> productes = ctrlDomini.getMagatzem(); // Load comandes from the database
            for (Map.Entry<String, Producte> entry : productes.entrySet()) {
                Producte producte = entry.getValue();
                listModel.addElement(producte.getNom());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading comandes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCreateProducteDialog() {
        JDialog dialog = new JDialog(this, "Alta Producte", true);
        JPanel panel = new JPanel(new GridLayout(5, 1));

        JTextField idField = new JTextField();
        JTextField maxPresField = new JTextField();
        JTextField maxMagField = new JTextField();
        JTextField StockField = new JTextField();

        panel.add(new JLabel("Nom Producte:"));
        panel.add(idField);
        panel.add(new JLabel("Max Prestatgeria:"));
        panel.add(maxPresField);
        panel.add(new JLabel("Max Magatzem:"));
        panel.add(maxMagField);
        panel.add(new JLabel("Stock Magatzem:"));
        panel.add(StockField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                int maxPres = Integer.parseInt(maxPresField.getText());
                int maxMag = Integer.parseInt(maxMagField.getText());
                int stock = Integer.parseInt(StockField.getText());
                if (id.isEmpty()) throw new IllegalArgumentException("El Nom del producte no pot estar buit.");
                ctrlDomini.altaProducte(id, maxPres, maxMag, stock);
                refreshProductesList();
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

    private void showDeleteProducteDialog() {
        String id = productesList.getSelectedValue();
        if (id != null) {
            id = id.split(":")[0]; // Extract ID before colon
            int result = JOptionPane.showConfirmDialog(this, "Eliminar el producte " + id + "?", "Eliminar Comanda", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    ctrlDomini.eliminarProducte(id);
                    refreshProductesList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JDialog dialog = new JDialog(this, "Eliminar Producte", true);
            JPanel panel = new JPanel(new GridLayout(2, 2));

            JTextField idField = new JTextField();

            panel.add(new JLabel("Nom Producte:"));
            panel.add(idField);

            JButton acceptButton = new JButton("Acceptar");
            acceptButton.addActionListener(e -> {
                try {
                    String id2 = idField.getText();
                    if (id2.isEmpty()) throw new IllegalArgumentException("El Nom del Producte no pot estar buit.");
                    ctrlDomini.eliminarProducte(id2);
                    refreshProductesList();
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
    }

    private void showVeureProducteDialog() {
        String id = productesList.getSelectedValue();
        if(id == null){
            id = JOptionPane.showInputDialog(this, "Introdueix el nom del producte:", "Veure Producte", JOptionPane.PLAIN_MESSAGE);
        }
        if (id != null && !id.trim().isEmpty()) {
            try {
                Producte producte = ctrlDomini.getProducte(id);
                String informacion = "Nom del producte: " + producte.getNom() + "\nMàxima Capacitat Prestatgeria: "+producte.getMaxHueco()
                        + "\nMàxima Capacitat Magatzem "+ producte.getMaxMagatzem() + "\nStock: " + producte.getStock() +
                        "\nSimilituds: " + producte.getSimilituds().toString() ;
                JOptionPane.showMessageDialog(this, informacion , "Detalls del Producte", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAddSimilitudDialog() {
        JDialog dialog = new JDialog(this, "Afegir Similitud entre Productes", true);
        JPanel panel = new JPanel(new GridLayout(4, 1));

        JTextField producte1NameField;
        JTextField producte2NameField;

        // Check if two items are selected
        if (productesList.getSelectedValuesList().size() >= 2) {
            java.util.List<String> selectedValues = productesList.getSelectedValuesList();
            producte1NameField = new JTextField(selectedValues.get(0).split(":")[0]);
            producte2NameField = new JTextField(selectedValues.get(1).split(":")[0]);
        } else {
            producte1NameField = new JTextField();
            producte2NameField = new JTextField();
        }

        JTextField quantityField = new JTextField();

        panel.add(new JLabel("Nom Producte 1:"));
        panel.add(producte1NameField);
        panel.add(new JLabel("Nom Producte 2:"));
        panel.add(producte2NameField);
        panel.add(new JLabel("Quantitat:"));
        panel.add(quantityField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String producte1 = producte1NameField.getText();
                String producte2 = producte2NameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) throw new NumberFormatException();
                ctrlDomini.afegirSimilitud(producte1, producte2, quantity);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "La quantitat ha de ser un nombre positiu.", "Error", JOptionPane.ERROR_MESSAGE);
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



    private void showRemoveSimilitudDialog() {
        JDialog dialog = new JDialog(this, "Eliminar Similitud entre productes", true);
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JTextField producte1NameField;
        JTextField producte2NameField;

// Check if two items are selected
        if (productesList.getSelectedValuesList().size() == 2) {
            java.util.List<String> selectedValues = productesList.getSelectedValuesList();
            producte1NameField = new JTextField(selectedValues.get(0).split(":")[0]);
            producte2NameField = new JTextField(selectedValues.get(1).split(":")[0]);
        } else {
            producte1NameField = new JTextField();
            producte2NameField = new JTextField();
        }



        panel.add(new JLabel("Nom Producte 1:"));
        panel.add(producte1NameField);
        panel.add(new JLabel("Nom Producte 2:"));
        panel.add(producte2NameField);


        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String producte1 = producte1NameField.getText();
                String producte2 = producte2NameField.getText();

                ctrlDomini.eliminarSimilitud(producte1, producte2);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "La quantitat ha de ser un nombre positiu.", "Error", JOptionPane.ERROR_MESSAGE);
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

    // Custom cell renderer for coloring list items
    private static class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER); // Alinear texto al centro
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

