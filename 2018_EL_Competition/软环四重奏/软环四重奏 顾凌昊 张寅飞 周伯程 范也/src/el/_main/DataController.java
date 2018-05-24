package el._main;

import el.clock._ui.Day;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class DataController {
	//ArrayList<Day> days=new ArrayList<Day>();
	final static String path="days.data";
	
	
    public static void saveDays(ArrayList<Day> days) {
        try (ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get("days.data")))) {
            stream.writeObject(days);
            System.out.println("Saved!");
        } catch (Exception e) {
            System.out.println("Failed to save: " + e);
        }
    }

    public static ArrayList<Day> getDays() {
        Path file = Paths.get("days.data");
        ArrayList<Day> days=new ArrayList<Day>();
        if (Files.exists(file)) {
            try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(file))) {
            	days = (ArrayList<Day>) stream.readObject();
                System.out.println("Loaded!");
            } catch (Exception e) {
                System.out.println("Failed to load: " + e);
            }
        }
        return days;
    }
	
	
}
