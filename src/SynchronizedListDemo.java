import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListDemo {
    public static void main(String[] args) {
        // Create the synchronized wrapper
        List<String> list = Collections.synchronizedList(new ArrayList<>());

        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");

        // Thread 1: Reader
        new Thread(() -> {
            try {
                // IMPORTANT: You MUST manually synchronize on the list while iterating!
                // If you remove this synchronized block, it will throw ConcurrentModificationException
                synchronized (list) {
                    for (String item : list) {
                        System.out.println("Reading: " + item);
                        Thread.sleep(50);
                    }
                }
            } catch (Exception e) {
                System.out.println("READER CAUGHT: " + e);
            }
        }).start();

        // Thread 2: Writer
        new Thread(() -> {
            try {
                Thread.sleep(20);

                // No manual synchronized block needed here! 
                // The wrapper handles synchronization for individual calls like .add()
                list.add("Item 4");

                System.out.println("Writer added Item 4");
            } catch (Exception e) {
                System.out.println("WRITER CAUGHT: " + e);
            }
        }).start();
    }
}