package Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyledButton extends JButton {
    private static final Color DEFAULT_BACKGROUND = new Color(70, 130, 180); // Steel Blue
    private static final Color HOVER_BACKGROUND = new Color(100, 149, 237);  // Cornflower Blue
    private static final Color PRESSED_BACKGROUND = new Color(51, 101, 138); // Darker Steel Blue
    private static final Color TEXT_COLOR = Color.WHITE;

    public StyledButton(String text) {
        super(text);
        setupButton();
    }

    private void setupButton() {
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(DEFAULT_BACKGROUND);
        setForeground(TEXT_COLOR);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setBorder(new EmptyBorder(8, 15, 8, 15));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_BACKGROUND);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(DEFAULT_BACKGROUND);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(PRESSED_BACKGROUND);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(HOVER_BACKGROUND);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background with rounded corners
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        // Draw text
        FontMetrics metrics = g2.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}