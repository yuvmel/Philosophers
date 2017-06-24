/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

import java.util.Random;
import javax.swing.JPanel;

/**
 * Represents a philosopher as an independent thread
 *
 * @author yuval.melamed
 */
public class PhilosopherThread implements Runnable {

    private static final int MAX_MSEC_TO_EAT_OR_THINK = 3000;

    private final int id;
    private final JPanel panel; // might be used for painting the state
    private final ChopstickMonitor firstChopstick, secondChopstick;
    private final Random random = new Random();
    private PhilosopherStat stat;
    private boolean done = false;

    public PhilosopherThread(int id, JPanel panel,
            ChopstickMonitor leftChopstick, ChopstickMonitor rightChopstick) {
        this.id = id;
        this.panel = panel;

        // Avoid dead-locks by having the chopstick with lower ID as first
        if (leftChopstick.getId() < rightChopstick.getId()) {
            firstChopstick = leftChopstick;
            secondChopstick = rightChopstick;
        } else {
            firstChopstick = rightChopstick;
            secondChopstick = leftChopstick;
        }
    }

    // Change philosopher's state & optionally update graphics
    public void setStat(PhilosopherStat stat) {
        this.stat = stat;
        if (panel != null) {
            panel.repaint();
        }
    }

    // Generic "action" method, used for sleeping while philosopher eats/thinks
    private void act(String action) {
        if (done) {
            return;
        }
        System.out.println(action);
        try {
            Thread.sleep(random.nextInt(MAX_MSEC_TO_EAT_OR_THINK));
        } catch (InterruptedException e) {
            // Random sleep anyway, so OK to give up if interrupted
        }
    }

    private void eat() {
        setStat(PhilosopherStat.EATING);
        act("Philosopher " + id + " is eating...");
    }

    private void think() {
        setStat(PhilosopherStat.THINKING);
        act("Philosopher " + id + " is thinking...");
    }

    public void setDone() {
        done = true;
    }

    public PhilosopherStat getStat() {
        return stat;
    }

    @Override
    public void run() {

        // Main philosopher loop
        while (!done) {
            setStat(PhilosopherStat.HUNGRY);

            firstChopstick.pickUp();
            secondChopstick.pickUp();
            eat();
            secondChopstick.putDown();
            firstChopstick.putDown();

            think();
        }

        System.out.println("Philosopher " + id + " is done!");
    }

    public enum PhilosopherStat {
        HUNGRY,
        EATING,
        THINKING
    }
}
