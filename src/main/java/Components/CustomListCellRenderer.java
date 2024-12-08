package Components;

import javax.swing.*;
import java.awt.*;

public class CustomListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setPreferredSize(new Dimension(list.getWidth(), 50)); // Adjust the height as needed

        // Calculate font size based on element width
        int elementWidth = list.getWidth();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        label.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        return label;
    }
}