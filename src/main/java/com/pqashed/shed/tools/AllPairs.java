package com.pqashed.shed.tools;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AllPairs {

    private Object[][] combinatorics;
    private int numberOfCombinatorics;
    private ArrayList<ArrayList<Object>> permutations;
    private ArrayList<Object> dataSet;
    private int m;
    private int outputPointer;

    private int permutationCounter = 0;

    public AllPairs(ArrayList<Object> set, int m) {
        int n = set.size();
        int c = factorial(n)/(factorial(m)*factorial(n-m));

        this.numberOfCombinatorics = c;
        this.dataSet = set;
        this.m = m;
        this.combinatorics = new Object[c][m];
    }

    public AllPairs(){}

    private void setCombinatorics(int pointer, int startPosition, Object[] result){
        if (pointer == 0){
            combinatorics[outputPointer] = Arrays.copyOf(result, result.length);
            this.outputPointer+=1;
            return;
        }
        for (int i = startPosition; i <= this.dataSet.size()-pointer; i++){
            result[result.length - pointer] = this.dataSet.get(i);
            setCombinatorics(pointer-1, i+1, result);
        }
    }

    public void setCombinatorics(){
        this.outputPointer = 0;
        if(this.m >= this.dataSet.size()) {
            combinatorics[0] = this.dataSet.toArray(new Object[this.dataSet.size()]);
        } else {
            setCombinatorics(this.m, 0, new Object[this.m]);
        }
    }

    public void setPermutations(){
        permutations = new ArrayList<ArrayList<Object>>();
        getPermutations(this.dataSet, 0);
    }
    public void setPermutations(ArrayList<Object> set){
        permutations = new ArrayList<ArrayList<Object>>();
        getPermutations(set, 0);
    }

    private void getPermutations(ArrayList<Object> set, int index){
        if(index >= set.size()){permutations.add(new ArrayList<>(set));}

        for(int i = index; i < set.size(); i++) {
            Object tmp = set.get(index);
            set.set(index, set.get(i));
            set.set(i, tmp);
            getPermutations(set, index+1);
            tmp = set.get(index);
            set.set(index, set.get(i));
            set.set(i, tmp);
        }
    }

    private int factorial(int num){
        int tmp = 1;
        for(int i = 1; i <= num; i++){
            tmp *= i;
        }
        return tmp;
    }

    public Object[][] getCombinatorics(){
        return this.combinatorics;
    }
    public ArrayList<ArrayList<Object>> getPermutations() {return this.permutations;}

}
