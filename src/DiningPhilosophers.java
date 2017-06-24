
import javax.swing.JFrame;

/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */
/**
 *
 * @author yuval.melamed
 */
public class DiningPhilosophers {

    private static final int COUNT = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Chopstick[] chopsticks = new Chopstick[COUNT];
        PhilosopherThread[] philosophers = new PhilosopherThread[COUNT];
        PhilosopherPanel panel = new PhilosopherPanel(philosophers);
        JFrame window = new JFrame("Dining Philosophers");
        int i;

        for (i = 0; i < COUNT; i++) {
            chopsticks[i] = new Chopstick(i + 1);
        }

        for (i = 0; i < COUNT; i++) {
            philosophers[i] = new PhilosopherThread(i + 1, panel,
                    chopsticks[i], chopsticks[(i + 1) % COUNT]);
            philosophers[i].start();
        }

        window.add(panel);
        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

}
