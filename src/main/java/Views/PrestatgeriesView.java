package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;
import controladors.CtrlDomini;
import classes.Prestatgeria;
import Exepcions.*;
import java.util.Vector;

public class PrestatgeriesView extends JFrame {
    private CtrlDomini ctrlDomini;
    private JPanel mainPanel;
    private JPanel prestatgeriesPanel;
    
    public PrestatgeriesView(CtrlDomini ctrlDomini) {
        this.ctrlDomini = ctrlDomini;
        setupUI();
    }

    // In PrestatgeriesView.java, modify setupUI():

    private void setupUI() {
        setTitle("Gestió de Prestatgeries");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
    
        // Initialize all panels first
        mainPanel = new JPanel(new BorderLayout());
        prestatgeriesPanel = new JPanel();
        prestatgeriesPanel.setLayout(new BoxLayout(prestatgeriesPanel, BoxLayout.Y_AXIS));
        
        // Create main buttons container
        JPanel buttonsContainer = new JPanel(new GridLayout(2, 1, 5, 5));
        
        // First row of buttons
        JPanel topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton addButton = new JButton("Afegir Prestatgeria");
        JButton deleteButton = new JButton("Eliminar Prestatgeria");
        JButton addProductButton = new JButton("Afegir Producte");
        JButton removeProductButton = new JButton("Retirar Producte");
        JButton fixProductButton = new JButton("Fixar Producte");
    
        topButtonsPanel.add(addButton);
        topButtonsPanel.add(deleteButton);
        topButtonsPanel.add(addProductButton);
        topButtonsPanel.add(removeProductButton);
        topButtonsPanel.add(fixProductButton);
    
        // Second row of buttons
        JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton unfixProductButton = new JButton("Desfixar Producte");
        JButton generateDistButton = new JButton("Generar Distribució");
        JButton decrementStockButton = new JButton("Decrementar Stock");
        JButton addShelfButton = new JButton("Afegir Prestatge");
        JButton removeShelfButton = new JButton("Eliminar Prestatge");
    
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
    
        // Add main panel to frame
        add(mainPanel);
        try{
            refreshPrestatgeriesView();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
}

    private void refreshPrestatgeriesView() throws IOException {
        prestatgeriesPanel.removeAll();
        Map<String, Prestatgeria> prestatgeries = ctrlDomini.getPrestatgeries();
        
        // Create layout for multiple prestatgeries
        prestatgeriesPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 prestatgeries per row
        
        for (Map.Entry<String, Prestatgeria> entry : prestatgeries.entrySet()) {
            Prestatgeria prest = entry.getValue();
            
            // Create panel for single prestatgeria
            JPanel prestPanel = new JPanel(new BorderLayout());
            prestPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Prestatgeria " + prest.getId()
            ));
            
            // Calculate shelf dimensions
            int numShelves = prest.getMidaPrestatgeria() / prest.getMidaPrestatge();
            int shelfSize = prest.getMidaPrestatge();
            
            // Create shelves panel
            JPanel shelvesPanel = new JPanel(new GridLayout(numShelves, 1, 5, 5));
            
            // Create individual shelves
            for (int shelf = 0; shelf < numShelves; shelf++) {
                JPanel shelfPanel = new JPanel(new GridLayout(1, shelfSize, 2, 2));
                shelfPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                
                // Fill shelf cells
                for (int pos = shelf * shelfSize; pos < (shelf + 1) * shelfSize; pos++) {
                    String productName = prest.getPosicions().get(pos);
                    JPanel cellPanel = createProductCell(prest, pos, productName);
                    shelfPanel.add(cellPanel);
                }
                
                shelvesPanel.add(shelfPanel);
            }
            
            prestPanel.add(shelvesPanel, BorderLayout.CENTER);
            
            // Add metadata panel
            JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            metaPanel.add(new JLabel("Mida Total: " + prest.getMidaPrestatgeria()));
            metaPanel.add(new JLabel("  Mida Prestatge: " + prest.getMidaPrestatge()));
            prestPanel.add(metaPanel, BorderLayout.SOUTH);
            
            prestatgeriesPanel.add(prestPanel);
        }
        
        prestatgeriesPanel.revalidate();
        prestatgeriesPanel.repaint();
    }

    private JPanel createProductCell(Prestatgeria prest, int position, String productName) {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cell.setName("pos_" + position); // Tag position for identification
        
        // Make all cells drop targets
        TransferHandler handler = new ProductTransferHandler(prest.getId(), position);
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
            
            // Add drag capability only to cells with products
            cell.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    JComponent c = (JComponent)e.getSource();
                    c.getTransferHandler().exportAsDrag(c, e, TransferHandler.MOVE);
                }
            });
        } else {
            // Visual feedback for empty cells
            cell.setBackground(new Color(240, 240, 240));
        }
        
        cell.setPreferredSize(new Dimension(100, 80));
        return cell;
    }
    
    // Update ProductTransferHandler:
    private class ProductTransferHandler extends TransferHandler {
        private final int position;
        private final String prestatgeriaId;
    
        public ProductTransferHandler(String prestatgeriaId, int position) {
            this.prestatgeriaId = prestatgeriaId;
            this.position = position;
        }
    
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(position + "");
        }
    
        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }
    
        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }
    
        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) return false;
    
            try {
                String data = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                int sourcePos = Integer.parseInt(data);
                
                // Get target position from component name
                JPanel targetCell = (JPanel)support.getComponent();
                int targetPos = Integer.parseInt(targetCell.getName().split("_")[1]);
    
                // Don't move to same position
                if (sourcePos == targetPos) {
                    return false;
                }
    
                // Move product
                ctrlDomini.moureProducteDeHueco(prestatgeriaId, sourcePos, targetPos);
                refreshPrestatgeriesView();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(PrestatgeriesView.this, 
                    "Error moving product: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
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
        String id = JOptionPane.showInputDialog(this, "ID de la prestatgeria a eliminar:");
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
        
        JTextField prestIdField = new JTextField();
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
        
        JTextField prestIdField = new JTextField();
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
        
        JTextField prestIdField = new JTextField();
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
        
        JTextField prestIdField = new JTextField();
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
        
        JTextField prestIdField = new JTextField();
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
        
        JTextField prestIdField = new JTextField();
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
        String id = JOptionPane.showInputDialog(this, "ID de la prestatgeria:");
        if (id != null && !id.isEmpty()) {
            try {
                ctrlDomini.afegirPrestatge(id);
                refreshPrestatgeriesView();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showRemoveShelfDialog() {
        String id = JOptionPane.showInputDialog(this, "ID de la prestatgeria:");
        if (id != null && !id.isEmpty()) {
            try {
                ctrlDomini.eliminarPrestatge(id);
                refreshPrestatgeriesView();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}