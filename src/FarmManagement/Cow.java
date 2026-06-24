package FarmManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cow extends Animal implements MilkProducer {

    private Breed breed;
    private String boughtFrom;
    private double buyingPrice;

    private ReproductiveStatus reproStatus;
    private LocalDate inseminationDate;
    private LocalDate expectedCalvingDate;
    private SemenType semenType;

    private String buyerName = "NA";
    private double salePrice = 0;
    private LocalDate closeDate;

    private String semenName = "NA";


    private List<MilkRecord> milkRecords = new ArrayList<>();

    public Cow(String id, Breed breed, String boughtFrom, double buyingPrice,
               LocalDate entryDate, ReproductiveStatus reproStatus,
               LocalDate inseminationDate, SemenType semenType,String semenName) {

        super(id, entryDate);
        this.breed = breed;
        this.boughtFrom = boughtFrom;
        this.buyingPrice = buyingPrice;
        this.reproStatus = reproStatus;
        this.inseminationDate = inseminationDate;
        this.semenType = semenType;

        if (inseminationDate != null)
            this.expectedCalvingDate = inseminationDate.plusDays(280);

        if (semenName != null && !semenName.isBlank())
            this.semenName = semenName;
    }

    // ---------- MILK ----------
    @Override
    public void addMilk(double liters) {
        if (status != AnimalStatus.ACTIVE)
            throw new IllegalStateException("Milk can only be added to ACTIVE cows");

        milkRecords.add(new MilkRecord(LocalDate.now(), liters));
    }

    public void addMilkRecord(LocalDate date, double amount) {
        if (status != AnimalStatus.ACTIVE)
            throw new IllegalStateException("Milk can only be added to ACTIVE cows");

        milkRecords.add(new MilkRecord(date, amount));
    }

    @Override
    public double getDailyAverageMilk() {
        if (milkRecords.isEmpty()) return 0;
        return getTotalMilk() / milkRecords.size();
    }

    public String getSemenName() {
        return semenName;
    }


    public double getTotalMilk() {
        return milkRecords.stream().mapToDouble(MilkRecord::getLiters).sum();
    }


    public List<MilkRecord> getMilkRecords() {
        return milkRecords;
    }

    // ---------- STATUS ----------
    public void sell(String buyer, double price, LocalDate date) {
        if (date.isBefore(entryDate))
            throw new IllegalArgumentException("Sale date before entry date");

        this.buyerName = buyer;
        this.salePrice = price;
        this.closeDate = date;
        this.status = AnimalStatus.SOLD;
    }

    public void markDeceased(LocalDate date) {
        if (date.isBefore(entryDate))
            throw new IllegalArgumentException("Death date before entry date");

        this.salePrice = 0;
        this.closeDate = date;
        this.status = AnimalStatus.DECEASED;
    }

    public double getProfitLoss() {
        if (status == AnimalStatus.SOLD) return salePrice - buyingPrice;
        if (status == AnimalStatus.DECEASED) return -buyingPrice;
        return 0;
    }
    @Override
    public String toString() {
        return "ID: " + this.id; // Or just return this.id;
    }

    // ---------- GETTERS ----------
    public Breed getBreed() { return breed; }
    public String getBoughtFrom() { return boughtFrom; }
    public double getBuyingPrice() { return buyingPrice; }
    public double getSalePrice() { return salePrice; }
    public LocalDate getCloseDate() { return closeDate; }
    public ReproductiveStatus getReproStatus() { return reproStatus; }
    public LocalDate getInseminationDate() { return inseminationDate; }
    public LocalDate getExpectedCalvingDate() { return expectedCalvingDate; }
    public SemenType getSemenType() { return semenType; }
}
