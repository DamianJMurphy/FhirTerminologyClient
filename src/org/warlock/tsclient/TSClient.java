/*
 * 
 *   Copyright 2019 Damian Murphy <murff@warlock.org>
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

/**
 *
 * @author murff
 */
public class TSClient {

    public static final String TERMINOLOGY_SERVER_URL_BASE = "org.warlock.tsclient.urlbase";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO: Replace this with command-line handling
        //
//        System.setProperty(TERMINOLOGY_SERVER_URL_BASE, "https://ontoserver-dev.dataproducts.nhs.uk/fhir");
        System.setProperty(TERMINOLOGY_SERVER_URL_BASE, "https://ontoserver.dataproducts.nhs.uk/fhir");
        
//        ValueSetRequest v1 = new ValidationQuery();
//        v1.setValueSetExpression("<!272141005");
//        ValueSetRequest v2 = new ValidationQuery();
//        v2.setValueSetExpression("<!272379006");
//        ValidationQuery v1 = new ValidationQuery();
//        LookupQuery v1 = new LookupQuery();
//        v1.setCode("371923003");
        SubsumesQuery v1 = new SubsumesQuery();
        v1.setCodeA("419048002");
        v1.setCodeB("272379006");
        SubsumesQuery v2 = new SubsumesQuery();
//        v2.setCodeA("272379006");
        v2.setCodeA("ABCDEF");
        v2.setCodeB("419048002");
        SubsumesQuery v3 = new SubsumesQuery();
        v3.setCodeA("272379006");
        v3.setCodeB("27123005");
//        v1.setValueSetExpression("<272141005");
//        ValidationQuery v2 = new ValidationQuery();
//        v2.setCode("371923003");
//        v2.setValueSetExpression("<!272379006");
//        ValidationQuery v3 = new ValidationQuery();
//        v3.setCode("218225007");
//        v3.setValueSetExpression("<!272379006");       
        try {
//            ValueSetExpansionRequest vsvr = new ValueSetExpansionRequest();
//            vsvr.addExpansionRequest(v1);
//            vsvr.addExpansionRequest(v2);
            
//            ExpansionResultSet r = vsvr.query();
//            ValueSetValidationRequest vsvr = new ValueSetValidationRequest();
//            vsvr.addValidationRequest(v1);
//            vsvr.addValidationRequest(v2);
//            vsvr.addValidationRequest(v3);
            
//            ValidationResultSet r = vsvr.query();
//            LookupRequest lr = new LookupRequest();
//           lr.addLookup(v1);
//           LookupResultSet lrs = lr.query();
            SubsumesRequest sr = new SubsumesRequest();
            sr.addSubsumesRequest(v1);
            sr.addSubsumesRequest(v2);
            sr.addSubsumesRequest(v3);
            SubsumesResultSet srs = sr.query();
            int i = 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // TODO NEXT: Implement "Request" for the other operation types
    }
    
}
