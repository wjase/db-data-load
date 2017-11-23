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

import com.example.liquibaseinit.mavenUtils.LocalMavenArtifactFetcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import static java.util.Collections.EMPTY_LIST;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.sql.DataSource;
import static org.apache.commons.lang3.StringUtils.isBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
public class LiquibaseDatabaseInitialiserApplication implements CommandLineRunner {

    //
    private Logger LOG = Logger.getLogger(LiquibaseDatabaseInitialiserApplication.class.getName());
    public static void main(String[] args) {
        SpringApplication.run(LiquibaseDatabaseInitialiserApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    }

    @Bean
    public ExternalLiquibaseMigrationList elm(DataSource ds, @Value("${db.migrations:}") String migrations, @Value("${maven.local.repo:${user.home}/.m2/repository}") String localMavenRepo) {
        LocalMavenArtifactFetcher.setMavenRoot(localMavenRepo);

        List<ExternalLiquibaseMigration> migrationsList = EMPTY_LIST;
        if (!isBlank(migrations)) {
            
            LOG.info("Migrations:"+migrations);

            Type listType = new TypeToken<List<ExternalJarMigration>>() {
            }.getType();
            List<ExternalJarMigration> mc = new Gson().fromJson(migrations, listType);
            if(mc!=null){
                migrationsList = mc
                        .stream()
                        .map(m -> new ExternalLiquibaseMigration(ds,
                        JarResourceAccessorFactory.resourceAccessor(m.getChangelogRoot()),
                        m.getChangelogs())).collect(toList());
                
            }
        }else{
            LOG.severe("No migrations set - no database changes will be applied.");
        }

        return new ExternalLiquibaseMigrationList(migrationsList);
    }

}
