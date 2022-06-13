package com.pqashed.shed.tools;

import org.apache.commons.text.similarity.LevenshteinDistance;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;

public class Search {

    private JSONArray topFiveResults = new JSONArray();
    private ArrayList<Double> similaritySet;
    private ArrayList<Integer> keySet;
    private ArrayList<String> entrySet;
    double comparisonThreshold = 0.3;

    public Search(String baseWord, HashMap<Integer, String> dataSet) {
        this.keySet = new ArrayList<>(dataSet.keySet());
        this.entrySet = new ArrayList<>(dataSet.values());

        similaritySet = new ArrayList<Double>(this.keySet.size());
        for(int i = 0; i < this.keySet.size(); i++){
            this.similaritySet.add(similarity(this.entrySet.get(i), baseWord));
        }
        //System.out.println(similaritySet);

        this.setTopFiveResults();
    }

    private static double similarity(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        if (s1.length() < s2.length()) { //s1 will always be the longer string
            String temp = s2;
            s1 = s2;
            s2 = temp;
        }
        if (s1.length() == 0) { return 1.0; }
        return (s1.length() - new LevenshteinDistance().apply(s1, s2)) / (double) s1.length();
    }

    private void setTopFiveResults() {
        for(int o = 0; o < 5; o++) {
            double maxSimilarity = -1;
            int index = -1;
            for(int i = 0; i < this.similaritySet.size(); i++) {
                if(this.similaritySet.get(i) > maxSimilarity) {
                    index = i;
                    maxSimilarity = this.similaritySet.get(i);
                }
            }
            //System.out.println(maxSimilarity);
            if(maxSimilarity <= this.comparisonThreshold) {
                break;
            } else {
                JSONObject result = new JSONObject();
                //System.out.println(this.entrySet.get(index));
                result.put("ticket_id", this.keySet.get(index));
                result.put("title", this.entrySet.get(index));

                this.topFiveResults.put(result);
                this.entrySet.remove(index);
                this.similaritySet.remove(index);
                this.keySet.remove(index);
            }
        }

    }

    public JSONArray getTopFiveResults() {
        return topFiveResults;
    }

}
