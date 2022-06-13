package com.pqashed.shed.tools;

import net.datafaker.Faker;
import net.datafaker.Name;
import net.datafaker.Address;
import net.datafaker.fileformats.Format;
import org.json.JSONArray;
import org.json.JSONObject;

public class FakerToJSON {

    private static final JSONArray fakerOptions = new JSONArray("[\n\t{'name':'Name',\n\t\'options': [\n\t\t\t'First Name',\n\t\t\t'Last Name',\n\t\t\t'Prefix',\n\t\t\t'Suffix',\n\t\t\t'Title',\n\t\t\t'Blood Group',\n\t\t\t'Username'],\n\t}," +
            "\n\t{'name':'Address',\n\t\'options': [\n\t\t\t'Street Name',\n\t\t\t'Street Address Number',\n\t\t\t'Street Address',\n\t\t\t'Secondary Address',\n\t\t\t'Post Code',\n\t\t\t'Zip Code'],\n\t}," +
            "\n\t{'name':'Business',\n\t\'options': [\n\t\t\t'Credit Card Number',\n\t\t\t'Credit Card Type',\n\t\t\t'Credit Card Expiry'],\n\t}," +
            "\n\t{'name':'Vehicle',\n\t\'options': [\n\t\t\t'Car Type',\n\t\t\t'Color',\n\t\t\t'Doors',\n\t\t\t'Drive Type',\n\t\t\t'Engine',\n\t\t\t'Fuel Type',\n\t\t\t'License Plate',\n\t\t\t'Make And Model',\n\t\t\t'Transmission',\n\t\t\t'Vin'],\n\t},\n]");
    String csv;

    public FakerToJSON(int numRows, String parameterString){
        Faker f = new Faker();

        String expression = "#{csv '"+numRows+"'";

        String[] components = parameterString.split(",");
        for(String component : components) {
            expression += ",'" + component.split("\\.")[1] + "','#{"+component+"}'";
        }

       expression += "}";
       this.csv = f.expression(expression);
    }

    public String getJson() {
        return this.csv;
    }

    public static String getFakerOptions() {
        return fakerOptions.toString();
    }

}
