package models;

public class Dashboard {
    private String name;
    private String planName;
    private double price;
    private String startDate;
    private String endDate;

    public Dashboard(String name, String planName, double price, String startDate, String endDate) {
        this.name = name;
        this.planName = planName;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getPlanName() {
        return planName;
    }

    public double getPrice() {
        return price;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
