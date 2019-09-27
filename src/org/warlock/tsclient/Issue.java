/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
