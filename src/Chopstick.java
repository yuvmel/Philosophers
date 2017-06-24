/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

/**
 *
 * @author yuval.melamed
 */
public class Chopstick {

    private final int id;
    private boolean isUp = false;

    public Chopstick(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public synchronized void pickUp() {
        while (isUp) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        isUp = true;
    }

    public synchronized void putDown() {
        isUp = false;
        notifyAll();
    }
}
