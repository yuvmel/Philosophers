/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 * Main program - the dining philosophers
 *
 * @author yuval.melamed
 */
public class DiningPhilosophers {

    private static final int COUNT = 5;
    private static final int SIZE = 300;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Relevant data structures
        ChopstickMonitor[] chopsticks = new ChopstickMonitor[COUNT];
        PhilosopherThread[] philosophers = new PhilosopherThread[COUNT];
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT);

        // Graphics representation
        PhilosopherPanel panel = new PhilosopherPanel(philosophers);
        JFrame frame = new JFrame("Dining Philosophers");

        // Initialize chopsticks
        for (int i = 0; i < COUNT; i++) {
            chopsticks[i] = new ChopstickMonitor(i + 1);
        }

        // Initialize philosopher threads
        for (int i = 0; i < COUNT; i++) {
            philosophers[i] = new PhilosopherThread(i + 1, panel,
                    chopsticks[i], chopsticks[(i + 1) % COUNT]);
            executorService.execute(philosophers[i]);
        }
        executorService.shutdown();

        // Initialize graphics
        frame.add(panel);
        frame.setSize(SIZE, SIZE);

        // Ignore default close actions - we will create our own listener
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Assign threads-cleanup action on GUI window close
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent windowEvent) {

                // Signal all philosopher's threads to be done
                for (int i = 0; i < COUNT; i++) {
                    philosophers[i].setDone();
                }

                // Interrupt all the threads (possibly sleeping)
                executorService.shutdownNow();

                // Wait until all the threads completed
                while (true) {
                    try {
                        if (executorService.awaitTermination(Long.MAX_VALUE,
                                TimeUnit.DAYS)) {
                            break;
                        }
                    } catch (InterruptedException interruptedException) {
                        // OK to just keep looping until we're really done
                    }
                }

                // Once all done, it's OK to quit main thread (and GUI too)
                System.exit(0);
            }

        });

        // Show GUI
        frame.setVisible(true);
    }
}
