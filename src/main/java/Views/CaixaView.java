package Views;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import controladors.CtrlDomini;
import Exepcions.*;
import Components.*;

public class CaixaView extends JFrame {
    private final CtrlDomini ctrlDomini;
    private  DefaultListModel<String> productListModel;
    private  JList<String> productList;
    boolean canvis = false;
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


        add(mainPanel);

        // Button actions
        addButton.addActionListener(e -> showAddProductDialog());
        removeButton.addActionListener(e -> showRemoveProductDialog());
        payButton.addActionListener(e -> payCaixa());

        // Load initial data
        refreshProductList();
    }

    public void refreshProductList() {
        productListModel.clear();
        try {
            Map<String, Integer> ticket = ctrlDomini.getTicket();
            ticket.forEach((product, quantity) -> productListModel.addElement(product + "  " + quantity));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

        JButton confirmButton = new JButton("Confirmar");
        JButton cancelButton = new JButton("Cancel·lar");

        confirmButton.addActionListener(e -> {
            try {
                String product = productField.getText().toLowerCase();;
                int quantity = Integer.parseInt(quantityField.getText());
                String prestatgeria = prestatgeriaField.getText().toLowerCase();;
                ctrlDomini.afegir_producte_caixa(product, quantity, prestatgeria);
                canvis=true;
                dialog.dispose();
                refreshProductList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(confirmButton);
        dialog.add(cancelButton);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

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
                String product = productField.getText().toLowerCase();;
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
                canvis=true;
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

    private void payCaixa() {
        try {
            ctrlDomini.pagar_caixa();
            canvis=true;
            refreshProductList();
            JOptionPane.showMessageDialog(this, "Pagament realitzat!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLeaveDialog() {
        if (canvis) {
            try {
                ctrlDomini.guardar();
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
}
