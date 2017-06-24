/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

import java.util.Random;

/**
 *
 * @author yuval.melamed
 */
public class PhilosopherThread extends Thread {

    private static final int MAX_MSEC_FOR_ACTION = 3000;
    private final Random random = new Random();
    private boolean done = false;
    private final int id;
    private final Chopstick firstChopstick, secondChopstick;

    public PhilosopherThread(int id,
            Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        if (leftChopstick.getId() < rightChopstick.getId()) {
            firstChopstick = leftChopstick;
            secondChopstick = rightChopstick;
        } else {
            firstChopstick = rightChopstick;
            secondChopstick = leftChopstick;
        }
    }

    private void act() {
        try {
            Thread.sleep(random.nextInt(MAX_MSEC_FOR_ACTION));
        } catch (InterruptedException e) {
        }
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating...");
        act();
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking...");
        act();
    }

    public void setDone() {
        done = true;
        this.interrupt();
    }

    @Override
    public void run() {
        while (!done) {
            firstChopstick.pickUp();
            secondChopstick.pickUp();
            eat();
            secondChopstick.putDown();
            firstChopstick.putDown();
            think();
        }
    }
}
