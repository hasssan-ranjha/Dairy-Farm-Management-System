package FarmManagement;

import java.time.LocalDate;

public class MilkEntry {
    private LocalDate date;
    private double liters;

    public MilkEntry(LocalDate date, double liters) {
        this.date = date;
        this.liters = liters;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getLiters() {
        return liters;
    }
}
