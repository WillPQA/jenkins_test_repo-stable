package com.pqashed.shed;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.tomcat.util.digester.ArrayStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowDiagramParser {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    ArrayList<String> endPoints = new ArrayList<String>();
    ArrayList<String> columnNames = new ArrayList<String>();
    XSSFSheet sheet;
    HashMap<String, ArrayStack<Row>> pathsToEndPoints;

    public FlowDiagramParser(){
        try{
            String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

            FileInputStream fis = new FileInputStream(new File(cwd + "/src/main/java/com/pqashed/shed/documents/CompleteToolshedFlowchart.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            this.sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            for(Cell cell : headerRow){
                columnNames.add(cell.getStringCellValue());
            }
            System.out.println();
            this.sheet.removeRow(headerRow);

        } catch(IOException e){
            logger.error(e.getMessage());
        }
        setEndPoints();
        setPathsToEndPoints();
    }

    private void setEndPoints() {
        for(Row row : this.sheet){
            if(!(row.getCell(0) == null) && row.getCell(2).getRichStringCellValue().getString().equals("")) {
                endPoints.add(row.getCell(0).getRichStringCellValue().toString());
            }
        }
    }

    private void setPathsToEndPoints() {
        this.pathsToEndPoints = new HashMap<>();
        for(String endPoint : endPoints){
            ArrayStack<Row> path = new ArrayStack<Row>();

            boolean firstAdded = false;
            boolean atStart = false;
            String point = endPoint;

            while(!atStart) {
                for (Row row : this.sheet) {
                    if(!firstAdded && row.getCell(0) != null && row.getCell(0).getRichStringCellValue().getString().contains(point)){
                        path.push(row);
                        firstAdded = true;
                    }
                    else if (firstAdded && row.getCell(2).getRichStringCellValue().getString().contains(point)) {
                        if(!row.getCell(4).getRichStringCellValue().getString().equals("Process")){
                            path.push(row);
                        }
                        point = row.getCell(0).getRichStringCellValue().getString();
                        if(row.getCell(4).getRichStringCellValue().getString().equals("Start")){
                            atStart = true;
                        }
                    }
                }
            }
            this.pathsToEndPoints.put(endPoint, path);
        }
    }

    public String extractPath(String stepID){
        String steps = "Steps to reach this end point:";
        for(int i = 0; i < this.pathsToEndPoints.get(stepID).size(); i++) {
            Row row = this.pathsToEndPoints.get(stepID).peek(i);
            steps += " -> " + row.getCell(1).getRichStringCellValue().getString();
        }
        return(steps);
    }
}

