/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.liquibaseinit;

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

import static com.example.liquibaseinit.mavenUtils.LocalMavenArtifactFetcher.localFileURLForArtifact;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.maven.model.Dependency;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jason
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class LocalMavenArtifactFetcherTest {
    @Test
    public void shouldLocateLocalMavenDependency() throws URISyntaxException {
        Dependency d = new Dependency();
        d.setArtifactId("junit");
        d.setGroupId("junit");
        final URL localFileURLForArtifact = localFileURLForArtifact(d);
        
        assertThat(localFileURLForArtifact,not(nullValue()));
        assertThat(new File(localFileURLForArtifact.toURI()), anExistingFile());
                
    }

    private TypeSafeDiagnosingMatcher<File> anExistingFile() {
        return new TypeSafeDiagnosingMatcher<File>(){
            @Override
            protected boolean matchesSafely(File item, Description mismatchDescription) {
                boolean exists = item.exists();
                if(!exists){
                    mismatchDescription.appendText("File does not exist:"+item.getAbsolutePath());
                }
                return exists;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("File exists");
            }
            
        };
    }
}
