package FarmManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FarmManager {

    // Logic Update: Use ObservableList so the GUI TableView updates automatically
    private ObservableList<Cow> cows = FXCollections.observableArrayList();
    private static final String FILE = "farm_data.txt";

    public ObservableList<Cow> getCows() {
        return cows;
    }

    public Cow getCow(String id) {
        for (Cow c : cows)
            if (c.getId().equals(id))
                return c;
        return null;
    }

    public Cow getActiveCow(String id) {
        for (Cow c : cows)
            if (c.getId().equals(id) && c.getStatus() == AnimalStatus.ACTIVE)
                return c;
        return null;
    }

    public boolean idExists(String id) {
        return getCow(id) != null;
    }

    public boolean activeCowExists(String id) {
        return getActiveCow(id) != null;
    }

    public void addCow(Cow cow) {
        cows.add(cow);
    }

    public void addMilk(String id, double liters) {
        Cow c = getActiveCow(id);
        if (c == null) throw new IllegalArgumentException("Active cow not found");
        c.addMilk(liters);
    }

    public void sellCow(String id, String buyer, double price, LocalDate date) {
        Cow c = getActiveCow(id);
        if (c == null) throw new IllegalArgumentException("Active cow not found");
        c.sell(buyer, price, date);
    }

    public void markDeceased(String id, LocalDate date) {
        Cow c = getActiveCow(id);
        if (c == null) throw new IllegalArgumentException("Active cow not found");
        c.markDeceased(date);
    }

    // UPDATED: Save Logic using the ObservableList
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Cow c : cows) {
                pw.println("COW|" + c.getId() + "|" + c.getBreed() + "|" +
                        c.getBoughtFrom() + "|" + c.getBuyingPrice() + "|" +
                        c.getEntryDate() + "|" + c.getReproStatus() + "|" +
                        c.getInseminationDate() + "|" + c.getSemenType() + "|" +
                        c.getSemenName() + "|" + c.getStatus() + "|" +
                        c.getSalePrice() + "|" + c.getCloseDate());

                for (MilkRecord m : c.getMilkRecords())
                    pw.println("MILK|" + c.getId() + "|" + m.getDate() + "|" + m.getLiters());
            }
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    // UPDATED: Load Logic to populate the ObservableList
    public void loadFromFile() {
        File f = new File(FILE);
        if (!f.exists()) return;
        cows.clear();
        Map<String, Cow> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p[0].equals("COW")) {
                    Cow cow = new Cow(p[1], Breed.valueOf(p[2]), p[3], Double.parseDouble(p[4]),
                            LocalDate.parse(p[5]), ReproductiveStatus.valueOf(p[6]),
                            p[7].equals("null") ? null : LocalDate.parse(p[7]),
                            p[8].equals("null") ? null : SemenType.valueOf(p[8]), p[9]);
                    AnimalStatus st = AnimalStatus.valueOf(p[10]);
                    if (st == AnimalStatus.SOLD) cow.sell("LOADED", Double.parseDouble(p[11]), LocalDate.parse(p[12]));
                    else if (st == AnimalStatus.DECEASED) cow.markDeceased(LocalDate.parse(p[12]));
                    cows.add(cow);
                    map.put(cow.getId(), cow);
                }
                if (p[0].equals("MILK")) {
                    Cow c = map.get(p[1]);
                    if (c != null) c.getMilkRecords().add(new MilkRecord(LocalDate.parse(p[2]), Double.parseDouble(p[3])));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data");
        }
    }
}