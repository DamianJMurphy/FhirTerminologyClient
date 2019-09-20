/*
 * 
 *   Copyright 2019  Damian Murphy <murff@warlock.org>
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *  
 */
package org.warlock.tsclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

/**
 *
 * @author murff
 */
public class ResultSet {
   
    private Exception exception = null;
    private ArrayList<Result> results = null;
    private String operationType = null;
    private String fhirData = null;
    private Date performedDate = new Date();
    
    ResultSet(String s) {
        results = new ArrayList<>();
        fhirData = s;
        JSONParser jp = new JSONParser();
        try {
            JSONObject j = (JSONObject)jp.parse(s);
            JSONArray entries = (JSONArray)j.get("entry");
            Iterator entryIterator = entries.iterator();
            while (entryIterator.hasNext()) {
                Result r = getResult((JSONObject)entryIterator.next());
                if (r != null) {
                    results.add(r);
                }
            }
        }
        catch (Exception e) {
            exception = e;
        }
    }
    
    ResultSet(Exception ex) {
        exception = ex;
    }
    
    public Date getPerformedDate() { return performedDate; }
    public String getFhirData() { return fhirData; }
    
    void setOperationType(String t) { operationType = t; }
    
    void addRequestData(int n, RequestData rd) {
        results.get(n).setRequestData(rd);
    }
    
    @SuppressWarnings("UnusedAssignment")
    private Result getResult(JSONObject j) {
        Result r = new Result();
        String stage = null;
        try {
            stage = "Getting resource";
            JSONObject res = (JSONObject)j.get("resource");
            JSONObject response = (JSONObject)j.get("response");
            r.setStatus((String)response.get("status"));
            stage = "Getting parameters";
            JSONArray params = (JSONArray)res.get("parameter");
            Iterator paramIterator = params.iterator();
            while (paramIterator.hasNext()) {
                JSONObject jo = (JSONObject)paramIterator.next();
                Object o = jo.get("valueString");
                String n = (String)jo.get("name");
                if (o == null) {
                    o = jo.get("valueBoolean");
                }
                r.addParameter(n, o);
            }
        }
        catch (Exception e) {
            Exception ex = new Exception("Result structural failure: " + stage, e);
            r.setException(ex);
            return r;
        }
        return r;
    }
    
    public boolean isError()  { return (exception != null); }
    public String getError() { 
        if (exception == null)
            return null;
        return exception.getMessage();
    }
    public String getErrorDetails() {
        if (exception == null)
            return null;
        StringBuilder sb = new StringBuilder(exception.toString());
        Throwable t = exception.getCause();
        while (t != null) {
            sb.append(t.toString());
            t = t.getCause();
        }
        return sb.toString();
    }
    
}
