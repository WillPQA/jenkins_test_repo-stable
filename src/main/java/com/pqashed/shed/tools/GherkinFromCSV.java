package com.pqashed.shed.tools;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GherkinFromCSV {

    private String[] columnNames;
    private ArrayList<ArrayList<String>> columns = new ArrayList<ArrayList<String>>();
    private int[] columnMaxLengths;

    private String result;

    public GherkinFromCSV(String CSV) {
        this.result = "\tExamples:\n";
        extractFromCSV(CSV);
        writeExamplesOnly();

    }

    public void extractFromCSV(String CSV) {
        Scanner line = new Scanner(CSV);
        columnNames = line.nextLine().split(",");
        columnMaxLengths = new int[columnNames.length];

        for (String name : columnNames) {
            System.out.println(name);
            ArrayList<String> column = new ArrayList<String>();
            column.add(name);
            columns.add(column);
        }

        // columns = new ArrayList<ArrayList<String>>(columnNames.length);

        Scanner tokenizer;
        while (line.hasNextLine()) {
            tokenizer = new Scanner(line.nextLine()).useDelimiter(",");
            for (int i = 0; tokenizer.hasNext(); i++) {
                columns.get(i).add(tokenizer.next());
                System.out.println(columns.get(i));
            }
        }

        for (int o = 0; o < columns.size(); o++) {
            for (int i = 0; i < columns.get(0).size(); i++) {
                if (columns.get(o).get(i).length() > columnMaxLengths[o]) {
                    columnMaxLengths[o] = columns.get(o).get(i).length();
                }
            }
        }

        for (int len : columnMaxLengths) {
            System.out.println(len);
        }
    }

    private void writeExamplesOnly() {
        for (int o = 0; o < columns.get(0).size(); o++) {
            String line = "\t\t| ";

            for (int i = 0; i < columns.size(); i++) {
                line += columns.get(i).get(o);
                for (int x = columns.get(i).get(o).length(); x < columnMaxLengths[i]; x++) {
                    line += " ";
                }
                line += " | ";
            }

            this.result += line + "\n";
        }
    }


    public String getResult(){
        return result;
    }
}