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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author murff
 */
public abstract class AbstractResultSet {

    protected ArrayList<Result> results = null;    
    protected Exception exception = null;
    protected String operationType = null;
    protected String fhirData = null;
    protected Date performedDate = new Date();

    protected final void parse(String s) {
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
        catch (ParseException e) {
            exception = e;
        }
        
    }
    
    protected abstract Result getResult(JSONObject j);
    
    void addRequestData(int n, RequestData rd) {
        results.get(n).setRequestData(rd);
    }
        
    public Date getPerformedDate() { return performedDate; }
    public String getFhirData() { return fhirData; }
    
    void setOperationType(String t) { operationType = t; }
    public boolean isError()  { return (exception != null); }
    public Exception getException() { return exception; }
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
