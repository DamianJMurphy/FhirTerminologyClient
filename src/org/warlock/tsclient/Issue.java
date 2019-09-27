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

/**
 *
 * @author damian
 */
public class Issue {
    
    private String code = null;
    private String diagnostics = null;
    private String severity = null;
    private ArrayList<String> expression = null;
    
    public Issue(String c, String d, String s) {
        code = c;
        diagnostics = d;
        severity = s;
    }

    public void addExpression(String s) {
        if (expression == null) {
            expression = new ArrayList<>();
        }
        expression.add(s);
    }
    
    public Iterator<String> getExpressions() { 
        if (expression == null) {
            return null;
        }
        return expression.iterator();
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the diagnostics
     */
    public String getDiagnostics() {
        return diagnostics;
    }

    /**
     * @param diagnostics the diagnostics to set
     */
    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }

    /**
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
}
