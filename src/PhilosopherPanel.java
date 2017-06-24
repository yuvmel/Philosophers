/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author yuval.melamed
 */
public class PhilosopherPanel extends JPanel {

    private PhilosopherThread[] philosophers;

    public PhilosopherPanel(PhilosopherThread[] philosophers) {
        this.philosophers = philosophers;
    }

    @Override
    public void paintComponent(Graphics g) {
        int diameter = (int) (0.8 * Math.min(getWidth(), getHeight()));

        g.setColor(Color.GRAY);
        g.fillOval((getWidth() - diameter) / 2, (getHeight() - diameter) / 2,
                diameter, diameter);
        for (int i = 0; i < philosophers.length; i++) {
            if (philosophers[i] == null) {
                continue;
            }
            switch (philosophers[i].getStat()) {
                case HUNGRY:
                    g.setColor(Color.ORANGE);
                    break;
                case EATING:
                    g.setColor(Color.RED);
                    break;
                case THINKING:
                    g.setColor(Color.YELLOW);
                    break;
            }
            double angle = 2 * Math.PI * i / philosophers.length;
            int x = (int) (getWidth() / 2 + diameter / 2 * Math.cos(angle));
            int y = (int) (getHeight() / 2 + diameter / 2 * Math.sin(angle));
            g.fillOval(x - 10, y - 10, 20, 20);
        }
    }
}
