/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author yuval.melamed
 */
public class PhilosopherThread extends Thread {

    private static final int MAX_MSEC_FOR_ACTION = 3000;
    private final Random random = new Random();
    private boolean done = false;
    private final int id;
    private JPanel panel;
    private PhilosopherStat stat = PhilosopherStat.HUNGRY;

    private final Chopstick firstChopstick, secondChopstick;

    public PhilosopherThread(int id, JPanel panel,
            Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.panel = panel;
        if (leftChopstick.getId() < rightChopstick.getId()) {
            firstChopstick = leftChopstick;
            secondChopstick = rightChopstick;
        } else {
            firstChopstick = rightChopstick;
            secondChopstick = leftChopstick;
        }
    }

    public void setStat(PhilosopherStat stat) {
        this.stat = stat;
        panel.repaint();
    }

    private void act() {
        try {
            Thread.sleep(random.nextInt(MAX_MSEC_FOR_ACTION));
        } catch (InterruptedException e) {
        }
    }

    private void eat() {
        setStat(PhilosopherStat.EATING);
        System.out.println("Philosopher " + id + " is eating...");
        act();
    }

    private void think() {
        setStat(PhilosopherStat.THINKING);
        System.out.println("Philosopher " + id + " is thinking...");
        act();
    }

    public void setDone() {
        done = true;
        this.interrupt();
    }

    public PhilosopherStat getStat() {
        return stat;
    }

    @Override
    public void run() {
        while (!done) {
            setStat(PhilosopherStat.HUNGRY);
            firstChopstick.pickUp();
            secondChopstick.pickUp();
            eat();
            secondChopstick.putDown();
            firstChopstick.putDown();
            think();
        }
    }

    public enum PhilosopherStat {
        HUNGRY,
        EATING,
        THINKING
    }
}
