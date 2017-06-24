/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Graphic representation of the dining philosophers
 *
 * @author yuval.melamed
 */
public class PhilosopherPanel extends JPanel {

    // Ratio of the dining-table's radius on the GUI window
    private static final double RADIUS_RATIO = 0.4;

    // Optional philosopher's representing-circle's radius
    private static final int PHILOSOPHER_RADIUS = 16;

    private final PhilosopherThread[] philosophers;

    public PhilosopherPanel(PhilosopherThread[] philosophers) {
        this.philosophers = philosophers;
    }

    @Override
    public void paintComponent(Graphics g) {

        // Calculate dimensions for current window
        int radius = (int) (RADIUS_RATIO * Math.min(getWidth(), getHeight()));
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        // Might be used to paint a philosopher's state
        URL url = null;
        ImageIcon icon = null;

        // Draw table as a big gray-filled circle
        g.setColor(Color.GRAY);
        g.fillOval(xCenter - radius, yCenter - radius, radius * 2, radius * 2);

        // Go over philosophers
        for (int i = 0; i < philosophers.length; i++) {

            // Make sure philosopher was initialized already
            if (philosophers[i] == null) {
                continue;
            }

            // Get philosopher's state & set its color or smiley face
            switch (philosophers[i].getStat()) {
                case HUNGRY:
                    url = getClass().getResource("Smilies/Hungry.png");
                    if (url == null) {
                        g.setColor(Color.ORANGE); // fall-back
                    } else {
                        icon = new ImageIcon(url); // open mouth
                    }
                    break;
                case EATING:
                    url = getClass().getResource("Smilies/Eating.png");
                    if (url == null) {
                        g.setColor(Color.RED); // fall-back
                    } else {
                        icon = new ImageIcon(url); // tongue out
                    }
                    break;
                case THINKING:
                    url = getClass().getResource("Smilies/Thinking.png");
                    if (url == null) {
                        g.setColor(Color.YELLOW); // fall-back
                    } else {
                        icon = new ImageIcon(url); // sunglasses on
                    }
                    break;
            }

            // Calcule philosopher's placement on table
            double angle = 2 * Math.PI * i / philosophers.length;
            int x = (int) (xCenter + radius * Math.cos(angle));
            int y = (int) (yCenter + radius * Math.sin(angle));

            // Draw either a colored circle or put the relevant smiley
            if (url == null) {
                g.fillOval(x - PHILOSOPHER_RADIUS, y - PHILOSOPHER_RADIUS,
                        PHILOSOPHER_RADIUS * 2, PHILOSOPHER_RADIUS * 2);
            } else {
                icon.paintIcon(this, g,
                        x - icon.getIconWidth() / 2,
                        y - icon.getIconHeight() / 2);
            }
        }
    }
}
