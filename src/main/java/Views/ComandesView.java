package Views;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import Components.SaveButton;
import Components.StyledButton;
import Components.BackButton;
import Components.StyledButtonGuardar;
import controladors.CtrlDomini;
import classes.Comanda;

public class ComandesView extends JFrame {
    private CtrlDomini ctrlDomini;
    private JPanel mainPanel;
    private JList<String> comandesList;
    private DefaultListModel<String> listModel;

    public ComandesView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

    private void setupUI() {
        setTitle("Gestió de Comandes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Main panel setup
        mainPanel = new JPanel(new BorderLayout());

        // Back button
        JButton backButton = new BackButton("Back", e -> showLeaveDialog());
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 4, 5, 10));

        // Buttons
        JButton createComandaButton = new StyledButton("Crear Comanda");
        JButton deleteComandaButton = new StyledButton("Eliminar Comanda");
        JButton addProductButton = new StyledButtonGuardar("Afegir Producte Comanda");
        JButton removeProductButton = new StyledButtonGuardar("Eliminar Producte Comanda");
        JButton generateComandaButton = new StyledButton("Generar Comanda Automàtica");
        JButton executeComandesButton = new StyledButton("Executar Comandes");

        // Set larger font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        createComandaButton.setFont(buttonFont);
        deleteComandaButton.setFont(buttonFont);
        addProductButton.setFont(buttonFont);
        removeProductButton.setFont(buttonFont);
        generateComandaButton.setFont(buttonFont);
        executeComandesButton.setFont(buttonFont);

        // Add buttons to panel
        buttonsPanel.add(createComandaButton);
        buttonsPanel.add(deleteComandaButton);
        buttonsPanel.add(addProductButton);
        buttonsPanel.add(removeProductButton);
        buttonsPanel.add(generateComandaButton);
        buttonsPanel.add(executeComandesButton);

        // Add action listeners
        createComandaButton.addActionListener(e -> showCreateComandaDialog());
        deleteComandaButton.addActionListener(e -> showDeleteComandaDialog());
        addProductButton.addActionListener(e -> showAddProductDialog());
        removeProductButton.addActionListener(e -> showRemoveProductDialog());
        generateComandaButton.addActionListener(e -> generateAutomaticComanda());
        executeComandesButton.addActionListener(e -> executeComandes());

        // Combine back button and other buttons
        JPanel combinedTopPanel = new JPanel(new BorderLayout());
        combinedTopPanel.add(backButtonPanel, BorderLayout.NORTH);
        combinedTopPanel.add(buttonsPanel, BorderLayout.SOUTH);

        mainPanel.add(combinedTopPanel, BorderLayout.NORTH);

        // List of comandes
        listModel = new DefaultListModel<>();
        comandesList = new JList<>(listModel);
        comandesList.setFont(new Font("Arial", Font.BOLD, 24)); // Set larger font size for list
        comandesList.setCellRenderer(new CustomListCellRenderer()); // Custom renderer for colors and borders
        comandesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(comandesList);
        listScrollPane.setPreferredSize(new Dimension(600, getHeight())); // Ensure it occupies sufficient width
        listScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

// Create a container panel for the list with padding
        JPanel listContainerPanel = new JPanel(new BorderLayout());
        listContainerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add 20px padding on all sides
        listContainerPanel.add(listScrollPane, BorderLayout.CENTER);

// Add the container panel to the main panel
        mainPanel.add(listContainerPanel, BorderLayout.CENTER);

        // Save button
        JButton saveButton = new SaveButton("Guardar");
        saveButton.setFont(buttonFont); // Set larger font for save button
        saveButton.addActionListener(e -> {
            try {
                ctrlDomini.guardar();
                refreshComandesList();
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

        refreshComandesList();
    }

    private void refreshComandesList() {
        try {
            listModel.clear();
            Map<String, Comanda> comandes = ctrlDomini.getComandes(); // Load comandes from the database
            for (Map.Entry<String, Comanda> entry : comandes.entrySet()) {
                Comanda comanda = entry.getValue();
                StringBuilder comandaDetails = new StringBuilder(entry.getKey()).append(":\n");
                comanda.getOrdres().forEach((producte, quantitat) -> {
                    comandaDetails.append("  - ").append(producte).append(": ").append(quantitat).append("\n");
                });
                listModel.addElement(comandaDetails.toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading comandes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCreateComandaDialog() {
        JDialog dialog = new JDialog(this, "Crear Comanda", true);
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField idField = new JTextField();

        panel.add(new JLabel("ID Comanda:"));
        panel.add(idField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                if (id.isEmpty()) throw new IllegalArgumentException("El ID de la comanda no pot estar buit.");
                ctrlDomini.crearComanda(id);
                refreshComandesList();
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

    private void showDeleteComandaDialog() {
        String id = comandesList.getSelectedValue();
        if (id != null) {
            id = id.split(":")[0]; // Extract ID before colon
            int result = JOptionPane.showConfirmDialog(this, "Eliminar la comanda " + id + "?", "Eliminar Comanda", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    ctrlDomini.eliminarComanda(id);
                    refreshComandesList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JDialog dialog = new JDialog(this, "Eliminar Comanda", true);
            JPanel panel = new JPanel(new GridLayout(2, 2));

            JTextField idField = new JTextField();

            panel.add(new JLabel("ID Comanda:"));
            panel.add(idField);

            JButton acceptButton = new JButton("Acceptar");
            acceptButton.addActionListener(e -> {
                try {
                    String id2 = idField.getText();
                    if (id2.isEmpty()) throw new IllegalArgumentException("El ID de la comanda no pot estar buit.");
                    ctrlDomini.eliminarComanda(id2);
                    refreshComandesList();
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

    private void showAddProductDialog() {
        JDialog dialog = new JDialog(this, "Afegir Producte a Comanda", true);
        JPanel panel = new JPanel(new GridLayout(4, 1));

        String id = comandesList.getSelectedValue();
        JTextField comandaIdField;
        if(id!=null){
            comandaIdField = new JTextField(comandesList.getSelectedValue().split(":")[0]);
            //comandaIdField.setEditable(false);
        }
        else comandaIdField = new JTextField();

        JTextField productNameField = new JTextField();
        JTextField quantityField = new JTextField();

        panel.add(new JLabel("ID Comanda:"));
        panel.add(comandaIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);
        panel.add(new JLabel("Quantitat:"));
        panel.add(quantityField);


        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {


                String comandaId = comandaIdField.getText();
                String productName = productNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) throw new NumberFormatException();
                ctrlDomini.afegirProducteComanda(comandaId, productName, quantity);
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

    private void showRemoveProductDialog() {
        JDialog dialog = new JDialog(this, "Eliminar Producte de Comanda", true);
        JPanel panel = new JPanel(new GridLayout(4, 1));

        String id = comandesList.getSelectedValue();
        JTextField comandaIdField;
        if(id!=null){
            comandaIdField = new JTextField(comandesList.getSelectedValue().split(":")[0]);
            //comandaIdField.setEditable(false);
        }
        else comandaIdField = new JTextField();



        JTextField productNameField = new JTextField();
        JTextField quantityField = new JTextField();

        panel.add(new JLabel("ID Comanda:"));
        panel.add(comandaIdField);
        panel.add(new JLabel("Nom Producte:"));
        panel.add(productNameField);
        panel.add(new JLabel("Quantitat:"));
        panel.add(quantityField);

        JButton acceptButton = new JButton("Acceptar");
        acceptButton.addActionListener(e -> {
            try {
                String comandaId = comandaIdField.getText();
                String productName = productNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) throw new NumberFormatException();
                ctrlDomini.eliminarProducteComanda(comandaId, productName, quantity);
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

    private void generateAutomaticComanda() {
        String nomComanda = JOptionPane.showInputDialog(this, "Nom de la Comanda Automàtica:");
        if (nomComanda != null && !nomComanda.trim().isEmpty()) {
            try {
                ctrlDomini.generarComandaAutomatica(nomComanda);
                refreshComandesList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El nom de la comanda no pot estar buit.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void executeComandes() {
        java.util.List<String> selectedComandes = comandesList.getSelectedValuesList();
        if (selectedComandes.isEmpty()) {
            // Solicitar al usuario que introduzca los nombres de las comandas manualmente
            String input = JOptionPane.showInputDialog(this, "Introdueix els noms de les comandes separats per comes:", "Executar Comandes", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cap comanda seleccionada o introduïda.", "Atenció", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Procesar la entrada del usuario
            String[] manualIds = input.split(",");
            for (int i = 0; i < manualIds.length; i++) {
                manualIds[i] = manualIds[i].trim();
            }
            try {
                ctrlDomini.executarComandes(manualIds);
                refreshComandesList();
                JOptionPane.showMessageDialog(this, "Comandes executades correctament!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Executar les comandes" + "?", "Executar Comandes", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                String[] ids = selectedComandes.stream().map(item -> item.split(":")[0]).toArray(String[]::new);
                ctrlDomini.executarComandes(ids);
                refreshComandesList();
                JOptionPane.showMessageDialog(this, "Comandes executades correctament!", "Èxit", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Custom cell renderer for coloring list items
    private static class CustomListCellRenderer extends DefaultListCellRenderer {
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
    private void showLeaveDialog() {
        int result = JOptionPane.showConfirmDialog(this, "Voleu guardar els canvis abans de sortir?", "Sortir", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                ctrlDomini.guardar();
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
