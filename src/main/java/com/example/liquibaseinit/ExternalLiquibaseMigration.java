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

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ResourceAccessor;

/**
 * Wrapper object for a liquibase migration contained in a jar file on the local
 * filesystem or in a maven artifact or any location for which you can provide
 * a ResourceAccessor
 *
 * @author jason
 */
public class ExternalLiquibaseMigration {

    private final ResourceAccessor resourceAccessor;

    private final List<String> dbchangelogs;

    private final DataSource dbToUpdate;
    
    /**
     * 
     * @param dbToUpdate The DB connection to use
     * @param resourceAccessor The class to grab the changeset resources
     * @param changelogs A list of paths to master changelogs allowing you
     *                   to apply multiple sets of changes. (NOTE: The change
     *                   ids will all live in the same table, so they will
     *                   need to be unique across master changesets.
     */
    ExternalLiquibaseMigration(DataSource dbToUpdate, ResourceAccessor resourceAccessor, List<String> changelogs) {
        this.resourceAccessor = resourceAccessor;
        this.dbchangelogs = changelogs;
        this.dbToUpdate = dbToUpdate;
    }

    /**
     * Call all the master changesets in this object.
     */
    public void update() {

        try {
            JdbcConnection jdbcConnection = new JdbcConnection(dbToUpdate.getConnection());
            for (String changelog : dbchangelogs) {
                Liquibase liquibase = new Liquibase(changelog, resourceAccessor, jdbcConnection);
                liquibase.setIgnoreClasspathPrefix(true);
                liquibase.update(null, new LabelExpression());
            }
        } catch (LiquibaseException ex) {
            Logger.getLogger(ExternalLiquibaseMigration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ExternalLiquibaseMigration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
