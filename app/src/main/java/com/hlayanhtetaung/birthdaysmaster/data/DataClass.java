package com.hlayanhtetaung.birthdaysmaster.data;

public class DataClass {

    private int id;
    private String name;
    private int years;
    private final int months;
    private final int days;

    public DataClass(int id, String name, int years, int months, int days) {
        this.id = id;
        this.name = name;
        this.years = years;
        this.months = months;
        this.days = days;
    }

    public DataClass(int years, int months, int days) {
        this.years = years;
        this.months = months;
        this.days = days;
    }

    public DataClass(int months, int days) {
        this.months = months;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }
}
