package com.company.abhishek;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyse {

    private final String deliveries_PATH = "/home/abhishek/IdeaProjects/Fragma_project/src/com/company/abhishek/deliveries.csv";
    private final String matches_PATH = "/home/abhishek/IdeaProjects/Fragma_project/src/com/company/abhishek/matches.csv";

    //Variables for 3rd question solution method
    private ArrayList<BowlerEconomyData> list = new ArrayList<>();
    private Map<Integer, Integer> yearList = new HashMap<>();
    private Map<String, BowlerData> bowlerMap = new HashMap<>();

    public void doStuff() {

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(matches_PATH), "UTF-8"));
            String line;
            String[] x = new String[10];
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null){
                x = line.split(",");
                if (!yearList.containsKey(Integer.parseInt(x[0]))){
                    yearList.put(Integer.parseInt(x[0]),Integer.parseInt(x[1]));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //TODO read each row and send its data to solve3rdProblem()
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(deliveries_PATH), "UTF-8"));
            String line = br.readLine();
            String[] values = new String[16];
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                String bowler = values[7];
                int matchID = Integer.parseInt(values[0]);
                int wideRuns = Integer.parseInt(values[8]);
                int noBallRuns = Integer.parseInt(values[11]);
                int byeBallRuns = Integer.parseInt(values[9]);
                int legByeRuns = Integer.parseInt(values[10]);
                int totalRuns = Integer.parseInt(values[15]);
                solve3rdQuestion(bowler, matchID, noBallRuns, wideRuns, byeBallRuns, legByeRuns, totalRuns);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        printTop10BowlersPerYear();
    }

    private void solve3rdQuestion(String bowler, int matchID, int noBallRUns, int wideBallRuns, int byeRuns, int legByeRuns, int totalRuns) {
        int year = yearList.get(matchID);
        if (bowlerMap.get(bowler+":"+year) == null){
            int[] result = getThisDeliveryResultForBowler(noBallRUns, wideBallRuns, byeRuns, legByeRuns, totalRuns);
            int ball = result[0];
            int runs = result[1];
            BowlerData bowlerData = new BowlerData(year, runs, ball);
            bowlerMap.put(bowler+":"+year, bowlerData);
        }else {
            BowlerData bowlerData = bowlerMap.get(bowler+":"+year);
            int[] result = getThisDeliveryResultForBowler(noBallRUns, wideBallRuns, byeRuns,legByeRuns, totalRuns);
            bowlerData.setRuns(bowlerData.getRuns()+result[1]);
            bowlerData.setBalls(bowlerData.getBalls()+result[0]);
            bowlerMap.put(bowler+":"+year, bowlerData);
        }
    }

    private void printTop10BowlersPerYear(){

        List<Integer> yearsCountList = new ArrayList<>();
        for (String k : bowlerMap.keySet()){
            int year = Integer.parseInt(k.split(":")[1]);
            if (!yearsCountList.contains(year)){
                yearsCountList.add(year);
            }
        }

        Map<Integer, BowlerEconomyClass>  bowlerEconomyMap = new HashMap<>();
        for (int year : yearsCountList){
            //System.out.println("outer : "+year);
            for (String key : bowlerMap.keySet()){
                String[] tmp = key.split(":");
                if (/*Integer.parseInt(tmp[1]) == year*/true){
                    String bowler = tmp[0];
                    int overInt = bowlerMap.get(key).getBalls() / 6;
                    float overFloat = bowlerMap.get(key).getBalls() % 6;
                    float economy = bowlerMap.get(key).getRuns() / (float)((float)overInt + overFloat);
                    BowlerEconomyClass bowlerEconomyClass = new BowlerEconomyClass(bowler, economy);
                    bowlerEconomyMap.put(year, bowlerEconomyClass);
                }
            }
        }

        for (int key : bowlerEconomyMap.keySet()){
            System.out.println(key + " - " + bowlerEconomyMap.get(key).getBowler() + " - " + bowlerEconomyMap.get(key).getEconomy());
        }
    }

    private int[] getThisDeliveryResultForBowler(int noBallRuns, int wideBallRuns, int byeRuns, int legByeRuns, int totalRuns){

        int[] result = new int[2];

        if (byeRuns > 0 || legByeRuns > 0){
            if (noBallRuns == 0 || wideBallRuns == 0){
                result[0]++;
            }
        }else {
            if (noBallRuns == 0 || wideBallRuns == 0){
                result[1] += totalRuns;
                result[0]++;
            }else {
                result[1] += totalRuns;
            }
        }

        return result;
    }

    class BowlerEconomyClass {
        private String bowler;
        private float economy;

        public BowlerEconomyClass(String bowler, float economy) {
            this.bowler = bowler;
            this.economy = economy;
        }

        public String getBowler() {
            return bowler;
        }

        public float getEconomy() {
            return economy;
        }
    }

    class BowlerData {
        private int year;
        private int runs;
        private int balls;

        public BowlerData(int year, int runs, int balls) {
            this.year = year;
            this.runs = runs;
            this.balls = balls;
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

        public int getBalls() {
            return balls;
        }

        public void setBalls(int balls) {
            this.balls = balls;
        }
    }

}


/*

    //TODO temporary
    int i=0;
            for (String k: bowlerMap.keySet()){
                    String key = k.toString();
                    BowlerData bowlerData = bowlerMap.get(k);
                    String name = key.split(":")[0];
                    if (name.equals("STR Binny")) {
                    System.out.println(i + ". " + key + " : " + bowlerData.getYear() + " - " + bowlerData.getBalls() + " - " + bowlerData.getRuns());
                    i++;
                    }
                    }
*/
