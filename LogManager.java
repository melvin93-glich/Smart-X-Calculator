import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogManager implements Serializable {
    private List<String> logs = new ArrayList<>();

    public void addLog(String log) {
        logs.add(log);
    }

    public List<String> getLogs() {
        return logs;
    }

    public void showLogs() {
        if (logs.isEmpty()) {
            System.out.println("No calculation logs found.");
        } else {
            System.out.println("\n--- Calculation Logs ---");
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }

    public void saveLogs(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(logs);
            System.out.println("Logs saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving logs: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadLogs(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            logs = (ArrayList<String>) ois.readObject();
            System.out.println("Previous logs loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous log file found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading logs: " + e.getMessage());
        }
    }
}