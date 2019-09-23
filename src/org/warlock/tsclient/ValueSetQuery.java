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

/**
 *
 * @author murff
 */
public class ValueSetQuery 
        implements QueryData
{
    protected String valueSetExpression = null;
    protected String valueSet = null;
    protected String status = "active";
    
    public ValueSetQuery() {}
    
    /**
     * @return the valueSetExpression
     */
    public String getValueSetExpression() {
        return valueSetExpression;
    }

    /**
     * @return the valueSet
     */
    public String getValueSet() {
        return valueSet;
    }

    /**
     * @param valueSetExpression the valueSetExpression to set
     */
    public void setValueSetExpression(String valueSetExpression) {
        this.valueSetExpression = valueSetExpression;
    }

    /**
     * @param valueSet the valueSet to set
     */
    public void setValueSet(String valueSet) {
        this.valueSet = valueSet;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }   
}
