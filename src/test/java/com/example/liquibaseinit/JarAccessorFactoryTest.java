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

import com.example.liquibaseinit.JarResourceAccessorFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import static java.util.stream.Collectors.joining;
import liquibase.resource.ResourceAccessor;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jason
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JarAccessorFactoryTest {
    
    @Test
    public void shouldCreateAccessorForJar() throws IOException {
        URL testJarUrl = getClass().getClassLoader().getResource("test.jar");
        assertThat(testJarUrl, not(nullValue()));
        
        ResourceAccessor resourceAccesor = JarResourceAccessorFactory.resourceAccessor(testJarUrl.toExternalForm());
        assertThat(resourceAccesor, not(nullValue()));
        
        Set<String> list = resourceAccesor.list("db/changelog", "", true, true, true);
        assertThat(list, not(empty()));
        
        String collect = list.stream().collect(joining("\n"));
        System.out.println(collect);
    }
}
