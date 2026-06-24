/*package FarmManagement;

import java.time.LocalDate;

public class MilkRecord {

    private LocalDate date;
    private double liters;

    public MilkRecord(LocalDate date, double liters) {
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
*/
/*
package FarmManagement;

import java.time.LocalDate;

public class MilkRecord {
    private LocalDate date;
    private double liters;

    public MilkRecord(LocalDate date, double liters) {
        this.date = date;
        this.liters = liters;
    }

    public LocalDate getDate() { return date; }
    public double getLiters() { return liters; }

    @Override
    public String toString() {
        return date + ":" + liters;
    }

    public static MilkRecord parse(String s) {
        String[] p = s.split(":");
        return new MilkRecord(LocalDate.parse(p[0]), Double.parseDouble(p[1]));
    }
}*/
package FarmManagement;

import java.time.LocalDate;

public class MilkRecord {
    private LocalDate date;
    private double liters;

    public MilkRecord(LocalDate date, double liters) {
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
