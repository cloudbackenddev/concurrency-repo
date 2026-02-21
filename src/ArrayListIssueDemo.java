import java.util.ArrayList;
import java.util.List;

public class ArrayListIssueDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");

        // Thread 1: Reader - Iterating through the list
        new Thread(() -> {
            try {
                for (String item : list) {
                    System.out.println("Reading: " + item);
                    Thread.sleep(50); // Give writer time to strike
                }
            } catch (Exception e) {
                System.out.println("READER CAUGHT: " + e);
            }
        }).start();

        // Thread 2: Writer - Modifying the list simultaneously
        new Thread(() -> {
            try {
                Thread.sleep(20);
                list.add("Item 4"); // CRASH! The reader is iterating
                System.out.println("Writer added Item 4");
            } catch (Exception e) {
                System.out.println("WRITER CAUGHT: " + e);
            }
        }).start();
    }
}