package com.pqashed.shed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

public class DbController {
    private final String HOST = "localhost";
    private final int PORT = 5432;
    private Connection c;
    MongoCollection<Document> collection;
    private JSONObject tools;
    private String[] toolCategoriesById = {"java", "javascript", "python", "csharp", "cucumber"};

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    public DbController() {
        String url = "jdbc:postgresql://"+HOST+":"+PORT+"/Toolshed";
        try {
            this.c = DriverManager.getConnection(url, "postgres", "Hoy87111");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public JSONObject getToolMetaData(){
        this.tools = new JSONObject();
        try {
            for (int cID = 1; cID < toolCategoriesById.length + 1; cID++) {
                Statement statement = this.c.createStatement();
                ResultSet results = statement.executeQuery("select tool_id, category_id, version, name from tools where category_id="+cID);

                JSONArray toolsPerCategory = new JSONArray();

                while(results.next()){
                    JSONObject tool = new JSONObject();
                    tool.put("toolId", results.getInt(1));
                    tool.put("categoryId", results.getInt(2));
                    tool.put("version", results.getBigDecimal(3));
                    tool.put("name", results.getString(4));
                    toolsPerCategory.put(tool);
                }
                this.tools.put(this.toolCategoriesById[cID - 1], toolsPerCategory);
            }
        } catch(SQLException e){
            logger.error(e.getMessage());
        }
        return this.tools;
    }

    public JSONObject getToolFullData(int toolId){
        JSONObject tool = new JSONObject();
        try {
            PreparedStatement ps = this.c.prepareStatement("select tool_id, category_id, version, name, description, is_interactive, source from tools where tool_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, toolId);
            ResultSet results = ps.executeQuery();
            results.first();

            tool.put("toolId", results.getInt(1));
            tool.put("categoryId", results.getInt(2));
            tool.put("version", results.getBigDecimal(3));
            tool.put("name", results.getString(4));
            tool.put("description", results.getString(5));
            tool.put("isInteractive", results.getBoolean(6));

            String code = String.join("\n", Files.readAllLines(Paths.get("./src/main/java/com/pqashed/shed/tools/"+(results.getBoolean(6)?"html/":"text/") + results.getString(7)), StandardCharsets.UTF_8));

            System.out.println(">>>"+code);

            tool.put("code", code);
        } catch(SQLException e) {
            logger.error("SQL ERROR:  "+e.getMessage());
        } catch(IOException e){
            logger.error("FILE ERROR:  "+e.getMessage());
        }

        return tool;
    }

    public JSONObject getTools() {
        return this.tools;
    }

    public HashMap<Integer, String> getErrorTitles() {
        HashMap<Integer, String> titles = new HashMap<Integer, String>();
        try {
            Statement statement = this.c.createStatement();
            ResultSet results = statement.executeQuery("select ticket_id, title from reported_errors");

            while(results.next()){
                //System.out.println(results.getString(2));
                titles.put(results.getInt(1), results.getString(2));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return titles;
    }

    public JSONObject getFullErrorDetails(int ticketId) {
        JSONObject fullErrorDetails = new JSONObject();
        try {
            PreparedStatement ps = this.c.prepareStatement("select * from reported_errors where date_solved is null and ticket_id=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, ticketId);
            ResultSet results = ps.executeQuery();

            results.last();
            if(results.getRow() != 1){
                throw new SQLException("Invalid number of responses");
            }

            fullErrorDetails.put("ticket_id", results.getInt("ticket_id"));
            fullErrorDetails.put("title", results.getString("title"));
            fullErrorDetails.put("subject", results.getString("subject"));
            fullErrorDetails.put("date_solved", results.getBoolean("date_solved"));

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return fullErrorDetails;
    }

    public void submitErrorTicket(JSONObject errorTicket) throws SQLException {
        PreparedStatement statement = this.c.prepareStatement("insert into reported_errors(title, subject) values(?, ?);");
        statement.setString(1, errorTicket.get("title").toString());
        statement.setString(2, errorTicket.get("subject").toString());

        statement.executeUpdate();
    }

}
