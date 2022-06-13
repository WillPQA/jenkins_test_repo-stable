package com.pqashed.shed;
//import io.swagger.annotations.ApiOperation;

import com.pqashed.shed.tools.AllPairs;
import com.pqashed.shed.tools.Search;
import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.pqashed.shed.tools.GherkinFromCSV;
import com.pqashed.shed.tools.FakerToJSON;

@RestController
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    //@ApiOperation(value = "", tags = "welcome test")
    @GetMapping("/api/welcome")
    public String weeds() {
        logger.info("Welcome Test Accessed");
        return "Greetings from Spring Boot!";
    }

    //@ApiOperation(value = "", tags = "error tickets")
    @PostMapping("/api/tickets/issues/meta")
    public String searchMetas(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);
        HashMap<Integer, String> issueTitles = new HashMap<Integer, String>();

        DbController db = new DbController();
        issueTitles = db.getErrorTitles();

        logger.info("Search query: "+bodyObj.get("text").toString());
        Search search = new Search(bodyObj.get("text").toString(), issueTitles);

        JSONArray topFiveResults = search.getTopFiveResults();
        logger.info(topFiveResults.toString());

        JSONObject response = new JSONObject();
        response.put("Results", topFiveResults);
        logger.info(response.toString());
        return response.toString();
    }

    //@ApiOperation(value = "", tags = "error tickets")
    @PostMapping("/api/tickets/issues/material")
    public String searchMaterials(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        logger.warn("Connecting to Database at port 5432");
        DbController db = new DbController();

        JSONObject fullErrorDetails = db.getFullErrorDetails(Integer.parseInt(bodyObj.get("ticket_id").toString()));

        logger.info(fullErrorDetails.toString());
        return(fullErrorDetails.toString());
    }

    //@ApiOperation(value = "", tags = "error tickets")
    @PostMapping("/api/tickets/issues/submit")
    public String submitErrorTicket(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        DbController db = new DbController();

        try {
            db.submitErrorTicket(bodyObj);
            return("Success");
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket Not Created!", e);
        }
    }

    @PostMapping("/api/tools/get")
    public String getTools(@RequestBody String bodyObjAsString) {

        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        DbController db = new DbController();

        System.out.println(db.getToolMetaData().toString());

        return(db.getToolMetaData().toString());
    }

    @PostMapping("/api/tools/get/fulldetails")
    public String getToolFullDetails(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        DbController db = new DbController();

        JSONObject tool = db.getToolFullData(bodyObj.getInt("toolId"));

        System.out.println(tool.toString());

        return(tool.toString());
    }

    @PostMapping("/api/tools/gherkinhelper")
    public String getGherkinTable(@RequestBody String bodyObjAsString) {
        System.out.println(bodyObjAsString);

        GherkinFromCSV gfc = new GherkinFromCSV(bodyObjAsString);

        return(gfc.getResult());
    }

    @PostMapping("/api/tools/faker")
    public String getFakerJSON(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        FakerToJSON ftc = new FakerToJSON(Integer.parseInt(bodyObj.get("numRows").toString()), bodyObj.get("parameterString").toString());

        return(ftc.getJson());
    }

    @GetMapping("/api/tools/faker/options")
    public String getFakerOptions() {

        return(FakerToJSON.getFakerOptions());
    }

    @PostMapping("/api/tools/allpairs/permutations")
    public String getNumberOfPossibleCombinations(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        AllPairs ap = new AllPairs();

        JSONArray js = new JSONArray(bodyObj.get("set").toString());
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i=0; i<js.length(); i++) {
            list.add(js.get(i));
        }

        logger.warn("WARNING: Resulting Permutations List will have a Length of the Factorial of the Number of Elements");
        ap.setPermutations(list);

        return(ap.getPermutations().toString().replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    @PostMapping("/api/tools/allpairs/combinatorics")
    public String getCombinatorics(@RequestBody String bodyObjAsString) {
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        System.out.println(bodyObjAsString);

        JSONArray js = new JSONArray(bodyObj.get("set").toString());
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i=0; i<js.length(); i++) {
            list.add(js.get(i));
        }

        AllPairs ap = new AllPairs(list, Integer.parseInt(bodyObj.get("m").toString()));

        ap.setCombinatorics();

        return(Arrays.deepToString(ap.getCombinatorics()).replace("], ", "]\n"));
    }

    @PutMapping("/api/suggestions/tool")
    public String submitToolSuggestion(@RequestBody String bodyObjAsString){
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        EmailController ec = new EmailController();

        EmailController.EmailBody emailBody = ec.new EmailBody();

        emailBody.setSuggestionBody(bodyObj.getString("title"), bodyObj.getString("email"), bodyObj.getString("name"), bodyObj.getString("city"), bodyObj.getString("subject"));

        ec.sendEmail(emailBody);

        logger.info("Sending Suggestion Email");

        return("Suggestion Submitted Successfully");
    }

    @GetMapping("/api/flowdiagramparser/fromid")
    public String testExcelParse(@RequestBody String bodyObjAsString){
        JSONObject bodyObj = new JSONObject(bodyObjAsString);

        FlowDiagramParser fdp = new FlowDiagramParser();

        String path = fdp.extractPath(bodyObj.getString("endPoint"));

        return(path);
    }


}
