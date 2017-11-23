/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.liquibaseinit.mavenUtils;

/*-
 * #%L
 * db-data-load
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.net.URL;
import static org.apache.maven.artifact.Artifact.LATEST_VERSION;
import org.apache.maven.model.Dependency;

/**
 *
 * @author jason
 */
public class LocalM2Repository {

    public static boolean isGavURL(String url){
        return url.startsWith("gav:");
        
    }
    public static Dependency parseGavUrl(String gav){
        String[] parts = gav.split(":");
        Dependency dep = fromPartsArray(parts, 1);
        return dep;
        
    }
    
    public static Dependency parseGav(String gav) {
        String[] parts = gav.split(":");
        Dependency dep = fromPartsArray(parts,0);
        return dep;
    }

    private static Dependency fromPartsArray(String[] parts, int offset) {
        Dependency dep = new Dependency();
        dep.setGroupId(parts[offset+0]);
        dep.setArtifactId(parts[offset+1]);
        dep.setVersion(parts.length == offset+3 ? parts[offset+2] : LATEST_VERSION);
        return dep;
    }
    
}
