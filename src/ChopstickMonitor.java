/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

/**
 * Represents a chopstick that is required for eating (one from a pair)
 *
 * @author yuval.melamed
 */
public class ChopstickMonitor {

    // ID is used for consumption order
    private final int id;

    // Chopstick starts on the table
    private boolean isUp = false;

    public ChopstickMonitor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Wait until this chopstick can be picked up again
    public synchronized void pickUp() {
        while (isUp) {
            try {
                System.out.println("Chopstick " + id
                        + " is being waited for...");
                wait();
            } catch (InterruptedException e) {
                // OK to loop, "isUp" will be cleared if thread was interrupted
            }
        }
        isUp = true;
        System.out.println("Chopstick " + id + " is up.");
    }

    // Put the chopstick down
    public synchronized void putDown() {
        isUp = false;
        System.out.println("Chopstick " + id + " is down.");
        notifyAll();
    }
}
