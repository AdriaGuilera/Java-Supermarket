package Views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;
import controladors.CtrlDomini;
import classes.Prestatgeria;
import Exepcions.*;
import Components.*;

public class PrestatgeriesView extends JFrame {
    public CtrlDomini ctrlDomini;
    private JPanel mainPanel;
    private JPanel prestatgeriesPanel;
    private CardLayout cardLayout;
    private JList<String> prestatgeriaList;
    private DefaultListModel<String> listModel;

    public PrestatgeriesView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

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

        // Define the save button
        SaveButton saveButton = new SaveButton("Guardar");

        // Add the save button to a new panel at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomPanel.add(saveButton);

        // Add the bottom panel to the main panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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

        // Implement the action listener for the save button
        saveButton.addActionListener(e -> {
            try {
                ctrlDomini.guardar();
                JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the back button to the top left corner
        BackButton backButton = new BackButton("Back", e -> {showLeaveDialog();});
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        backButtonPanel.add(backButton);
        backButton.addActionListener(e -> {
            MainView mainView = new MainView(ctrlDomini);
            mainView.setSize(getSize());
            mainView.setLocation(getLocation());
            mainView.setVisible(true);
            dispose(); // Cierra ProductesView
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshPrestatgeriesView() throws IOException {
        String selectedId = prestatgeriaList.getSelectedValue(); // Store the selected prestatgeria ID

        prestatgeriesPanel.removeAll();
        listModel.clear();
        Map<String, Prestatgeria> prestatgeries = ctrlDomini.getPrestatgeries();

        int maxElementWidth = 0;
        FontMetrics fontMetrics = prestatgeriaList.getFontMetrics(prestatgeriaList.getFont());

        for (Map.Entry<String, Prestatgeria> entry : prestatgeries.entrySet()) {
            Prestatgeria prest = entry.getValue();
            listModel.addElement(prest.getId());

            int elementWidth = fontMetrics.stringWidth(prest.getId());
            if (elementWidth > maxElementWidth) {
                maxElementWidth = elementWidth;
            }

            JPanel prestPanel = new JPanel(new BorderLayout());
            TitledBorder border = BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2),
                    "Prestatgeria " + prest.getId(),
                    TitledBorder.CENTER,
                    TitledBorder.TOP
            );
            border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
            border.setTitlePosition(TitledBorder.ABOVE_TOP);
            border.setTitleJustification(TitledBorder.CENTER);
            prestPanel.setBorder(border);

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
            prestatgeriesPanel.add(prestPanel, prest.getId());
        }

        prestatgeriaList.setPreferredSize(new Dimension(maxElementWidth + 20, prestatgeriaList.getPreferredSize().height));
        prestatgeriesPanel.revalidate();
        prestatgeriesPanel.repaint();

        if (selectedId != null) {
            prestatgeriaList.setSelectedValue(selectedId, true); // Reselect the previously selected prestatgeria
        }
    }

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
                String id = idField.getText();
                int mida = Integer.parseInt(midaField.getText());
                int midaPrest = Integer.parseInt(midaPrestField.getText());
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

    private void showDeletePrestatgeriaDialog() {
        String id = JOptionPane.showInputDialog(this, "ID de la prestatgeria a eliminar:",prestatgeriaList.getSelectedValue());
        if (id != null && !id.isEmpty()) {
            try {
                ctrlDomini.ctrlPrestatgeria.eliminarPrestatgeria(id);
                refreshPrestatgeriesView();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
                String prestId = prestIdField.getText();
                String productName = productNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                ctrlDomini.afegirProductePrestatgeria(productName, quantity, prestId);
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
                String prestId = prestIdField.getText();
                String productName = productNameField.getText();
                ctrlDomini.retirarProducteAMagatzem(prestId, productName);
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
                String prestId = prestIdField.getText();
                String productName = productNameField.getText();
                ctrlDomini.fixarProducte(prestId, productName);
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
                String prestId = prestIdField.getText();
                String productName = productNameField.getText();
                ctrlDomini.desfixarProducte(prestId, productName);
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
                String id = prestIdField.getText();
                ctrlDomini.generarDistribucioHillClimbing(id);
                refreshPrestatgeriesView();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backtrackingButton.addActionListener(e -> {
            try {
                String id = prestIdField.getText();
                ctrlDomini.generarDistribucioBacktracking(id);
                refreshPrestatgeriesView();
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
                String prestId = prestIdField.getText();
                String productName = productNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                ctrlDomini.decrementarStockAProducte(prestId, productName, quantity);
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
                String prestId = prestIdField.getText();
                ctrlDomini.afegirPrestatge(prestId);
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
                String prestId = prestIdField.getText();
                ctrlDomini.eliminarPrestatge(prestId);
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

    private void showLeaveDialog(){
        int result = JOptionPane.showConfirmDialog(this, "Quieres guardar los cambios antes de salir?", "Leave", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                ctrlDomini.guardar();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (result == JOptionPane.NO_OPTION) {
            dispose();
        }
    }
}