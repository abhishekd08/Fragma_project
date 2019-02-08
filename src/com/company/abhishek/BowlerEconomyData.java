package com.company.abhishek;

public class BowlerEconomyData {
    private String bowler;
    private int year;
    private int runs;
    private int overs;

    public BowlerEconomyData(String bowler, int year, int runs, int overs) {
        this.bowler = bowler;
        this.year = year;
        this.runs = runs;
        this.overs = overs;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }
}
