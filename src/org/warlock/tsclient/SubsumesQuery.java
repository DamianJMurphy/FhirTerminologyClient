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
public class SubsumesQuery 
        implements QueryData
{
    private String codeA = null;
    private String codeB = null;

    /**
     * @return the codeA
     */
    public String getCodeA() {
        return codeA;
    }

    /**
     * @param codeA the codeA to set
     */
    public void setCodeA(String codeA) {
        this.codeA = codeA;
    }

    /**
     * @return the codeB
     */
    public String getCodeB() {
        return codeB;
    }

    /**
     * @param codeB the codeB to set
     */
    public void setCodeB(String codeB) {
        this.codeB = codeB;
    }
}
