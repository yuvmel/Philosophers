/*
 * Maman 15 course 20554 question 2 by Yuval Melamed, ID 035870864
 */

/**
 *
 * @author yuval.melamed
 */
public class Philosophers {

    private static final int COUNT = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Chopstick[] chopstick = new Chopstick[COUNT];
        PhilosopherThread[] philosopher = new PhilosopherThread[COUNT];
        int i;

        for (i = 0; i < COUNT; i++) {
            chopstick[i] = new Chopstick(i + 1);
        }

        for (i = 0; i < COUNT; i++) {
            philosopher[i] = new PhilosopherThread(i + 1,
                    chopstick[i], chopstick[(i + 1) % COUNT]);
            philosopher[i].start();
        }
    }

}
