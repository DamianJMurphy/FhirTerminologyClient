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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author murff
 */
public class ValueSetExpansionRequest 
        implements Request
{
    private final ArrayList<ValueSetRequest> requests = new ArrayList<>();
    
    private static final String BUNDLETEMPLATE = "/org/warlock/tsclient/templates/BundleTemplate";
    private static final String ENTRYTEMPLATE = "/org/warlock/tsclient/templates/ValueSetExpansionEntry";
    private static final String VALUESETTEMPLATE = "/org/warlock/tsclient/templates/ValueSet";
    
    private static final String OPERATIONTYPE = "$expand";
    private static IOException initException = null;
    
    private static String bundleTemplate = null;
    private static String entryTemplate = null;
    private static String valueSetTemplate = null;
    
    private int contentLength = -1;
    private byte[] content = null;
    
    static {
        try {
            bundleTemplate = org.warlock.tsclient.util.Utils.loadStringResource(BUNDLETEMPLATE);
            entryTemplate = org.warlock.tsclient.util.Utils.loadStringResource(ENTRYTEMPLATE);
            valueSetTemplate = org.warlock.tsclient.util.Utils.loadStringResource(VALUESETTEMPLATE);        
        }
        catch (IOException e) {
            initException = e;
        }
    }

    public ValueSetExpansionRequest() 
            throws Exception
    {
        if (initException != null)
            throw initException;
    }
    
    public void addExpansionRequest(ValueSetRequest v) {
        requests.add(v);
    }
    
    @Override
    public String getUrlContextPath() {
        // ValueSet validation always uses POST, so we're not adding any context path
        return "";
    }

    @Override
    public String getMethod() {
        // ValueSet validation always uses POST
        return "POST";
    }

    @Override
    public void makeBody() 
    {
        String bundle = bundleTemplate.replace("__ENTRIES__", makeEntries());        
        try {
            content = bundle.getBytes("UTF-8");
            contentLength = content.length;
        }
        catch (UnsupportedEncodingException e) {
            System.err.println("Development error: encoding content");
        }
    }

    private String makeEntries() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < requests.size(); i++) {
            ValueSetRequest v = requests.get(i);
            sb.append(makeEntry(v));
            if (i < requests.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isPost() {
        return true;
    }
    
    private String makeEntry(ValueSetRequest v) {
        if (v.getValueSet() != null) {
            return entryTemplate.replace("__VALUE_SET__", v.getValueSet());
        }
        String vs = valueSetTemplate.replace("__ECL_EXPRESSION__", v.getValueSetExpression());
        vs = vs.replace("__STATUS__", v.getStatus());
        return entryTemplate.replace("__VALUE_SET__", vs);
    }
    
    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public byte[] serialiseContent() {
        return content;
    }

    @Override
    public ExpansionResultSet query() throws Exception {
        HttpCall c = new HttpCall(this);
        ExpansionResultSet r = null;
        try {
            String s = c.call();
            r = new ExpansionResultSet(s);
        }    
        catch (Exception e) {
            return new ExpansionResultSet(e);
        }
        for (int i= 0; i < requests.size(); i++) {
            r.addRequestData(i, requests.get(i));
        }
        r.setOperationType(OPERATIONTYPE);
        return r;
    }
    
}
