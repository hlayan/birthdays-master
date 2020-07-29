package com.hlayanhtetaung.birthdaysmaster.data;

public class DataForExtra {

    private int totalYears;
    private int totalMonths;
    private int totalWeeks;
    private int totalDays;
    private int totalHours;
    private int totalMinutes;
    private int totalSeconds;

    public DataForExtra(int totalYears, int totalMonths, int totalWeeks, int totalDays, int totalHours, int totalMinutes, int totalSeconds) {
        this.totalYears = totalYears;
        this.totalMonths = totalMonths;
        this.totalWeeks = totalWeeks;
        this.totalDays = totalDays;
        this.totalHours = totalHours;
        this.totalMinutes = totalMinutes;
        this.totalSeconds = totalSeconds;
    }

    public int getTotalYears() {
        return totalYears;
    }

    public int getTotalMonths() {
        return totalMonths;
    }

    public int getTotalWeeks() {
        return totalWeeks;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }
}
