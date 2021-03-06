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
import java.util.HashMap;

/**
 *
 * @author murff
 */
public class Result {
    
    private Exception exception = null;
    private String status = null;
    private final HashMap<String,Object> parameters = new HashMap<>();
    private final HashMap<String,String> content = new HashMap<>();
    private QueryData requestData = null;
    private ArrayList<Issue> issues = new ArrayList<>();
    
    Result() {}
    
    void addParameter(String n, Object o) { parameters.put(n, o); }
    void addContent(String n, String s) { content.put(n, s); }
    void setException(Exception e) { exception = e; }
    void setStatus(String s) { status = s; }
    void setRequestData(QueryData rd) { requestData = rd; }
    void addIssue(Issue i) { issues.add(i); }
}
