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
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author murff
 */
public class LookupResultSet 
        extends AbstractResultSet
{

    LookupResultSet(String s) {
        parse(s);
    }
    
    LookupResultSet(Exception ex) {
        exception = ex;
    }
    
    @Override
    protected Result getResult(JSONObject j) {
        Result r = new Result();
        String stage = null;
        try {
            stage = "Getting resource";
            JSONObject res = (JSONObject)j.get("resource");
            JSONObject response = (JSONObject)j.get("response");
            r.setStatus((String)response.get("status"));
            stage = "Getting parameters";
            JSONArray params = (JSONArray)res.get("parameter");
            ArrayList<String> properties = new ArrayList<>();
            Iterator paramIterator = params.iterator();
            while (paramIterator.hasNext()) {
                JSONObject jo = (JSONObject)paramIterator.next();
                String n = (String)jo.get("name");
                Object o = jo.get("valueString");
                if (o == null) {
                    // Data is a "part" JSON array
                    JSONArray part = (JSONArray)jo.get("part");
                    if (n.contentEquals("property")) {
                       properties.add(makePart(part));
                    } else {
                        o = makePart(part);                        
                    }
                }
                if (!n.contentEquals("property")) {
                    r.addParameter(n, o);
                }
            }
            r.addParameter("property", properties);
            // Handle "issue" cases here, and replicate code to other operations.
            //
            handleIssue(r, res);
        }
        catch (Exception e) {
            Exception ex = new Exception("Result structural failure: " + stage, e);
            r.setException(ex);
            return r;
        }
        return r;        
    }
    
    private String makePart(JSONArray p) {
        StringBuilder sb = new StringBuilder();
        Iterator partIterator = p.iterator();
        while (partIterator.hasNext()) {
            JSONObject jo = (JSONObject)partIterator.next();
            sb.append((String)jo.get("name"));
            sb.append(" : ");
            String o = (String)jo.get("valueString");
            if (o == null) {            
                o = (String)jo.get("valueCode");
                if (o == null) {
                    JSONObject coding = (JSONObject)jo.get("valueCoding");
                    if (coding != null) {
                        StringBuilder c = new StringBuilder((String)coding.get("code"));
                        c.append("|");
                        c.append((String)coding.get("display"));
                        o = c.toString();
                    } else {
                        Object obj = jo.get("valueBoolean");
                        if (obj != null) {
                            o = obj.toString();
                        } else {
                            o = "Not found";
                        }
                    }
                }
            }
            sb.append(o);
            sb.append("\n");            
        }
        return sb.toString();
    }
}
