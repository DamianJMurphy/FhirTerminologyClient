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

import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author murff
 */
public class ValidationResultSet 
        extends AbstractResultSet
{
      
    ValidationResultSet(String s) {
        parse(s);
    }
    
    ValidationResultSet(Exception ex) {
        exception = ex;
    }
            
    @SuppressWarnings("UnusedAssignment")
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
}
