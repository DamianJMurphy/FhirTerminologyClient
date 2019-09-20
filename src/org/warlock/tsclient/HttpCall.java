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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author murff
 */
public class HttpCall {

    private URL url = null;
    private Request request = null;
    
    
    HttpCall(Request r) 
            throws MalformedURLException, IllegalArgumentException
    {
        request = r;
        StringBuilder sb = new StringBuilder(System.getProperty(TSClient.TERMINOLOGY_SERVER_URL_BASE));
        sb.append(r.getUrlContextPath());
        url = new URL(sb.toString());
        if (!url.getProtocol().contentEquals("https")) {
            throw new IllegalArgumentException("FHIR Terminology Server requires HTTPS: " + url.toString());
        }
    }
    
    ResultSet call()
    {
        @SuppressWarnings("UnusedAssignment")
        StringBuilder sb = null;
        try {
            request.makeBody();
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            if (request.isPost()) {
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/fhir+json");
                connection.setRequestProperty("Accept", "application/fhir+json");
                connection.setRequestProperty("Content-length", Integer.toString(request.getContentLength()));
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.getOutputStream().write(request.serialiseContent());
    //            connection.connect();
            } else {
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/fhir+json");            
                connection.setDoOutput(true);
            }     
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            @SuppressWarnings("UnusedAssignment")
            String input = null;
            while ((input = br.readLine()) != null) {
                sb.append(input);
            }
        }
        catch (IOException e) {
            return new ResultSet(e);
        }
        return new ResultSet(sb.toString());
    }
}
