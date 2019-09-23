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
public class LookupRequest 
        implements Request
{
    private final ArrayList<LookupQuery> requests = new ArrayList<>();
    
    private static final String BUNDLETEMPLATE = "/org/warlock/tsclient/templates/BundleTemplate";
    private static final String ENTRYTEMPLATE = "/org/warlock/tsclient/templates/LookupEntry";
    
    private static final String OPERATIONTYPE = "$lookup";
    private static IOException initException = null;
    
    private static String bundleTemplate = null;
    private static String entryTemplate = null;
    
    private int contentLength = -1;
    private byte[] content = null;
    
    static {
        try {
            bundleTemplate = org.warlock.tsclient.util.Utils.loadStringResource(BUNDLETEMPLATE);
            entryTemplate = org.warlock.tsclient.util.Utils.loadStringResource(ENTRYTEMPLATE);
        }
        catch (IOException e) {
            initException = e;
        }
    }
    
    public LookupRequest() 
            throws Exception
    {
        if (initException != null)
            throw initException;
    }

    public void addLookup(LookupQuery l) {
        requests.add(l);
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
            LookupQuery v = requests.get(i);
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
    
    private String makeEntry(LookupQuery v) {
        return entryTemplate.replace("__CODE__", v.getCode());
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
    public LookupResultSet query() throws Exception {
        HttpCall c = new HttpCall(this);
        LookupResultSet r = null;
        try {
            String s = c.call();
            r = new LookupResultSet(s);
        }
        catch (Exception e) {
            return new LookupResultSet(e);   
        }
        for (int i= 0; i < requests.size(); i++) {
            r.addRequestData(i, requests.get(i));
        }
        r.setOperationType(OPERATIONTYPE);
        return r;        
    }
    
}
