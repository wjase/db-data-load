# db-data-load
A data loader using [Liquibase](http://www.liquibase.org/)

If you've ever had to setup a database using sql scripts then this might help.

It uses Liquibase to do the heavy lifting and maven repositories to handle storing the sql/changesets.

# How to Use wth Docker compose

 1. Package your sql as a liquibase changeset in a simple maven/gradle jar which allows it to be versioned and stored in a repository (eg nexus, Artifactory etc)
 2. Add an entry to the compose file for this app which specifies which artifact to grab and the path to the changeset inside the jar. Somethings like:
 TBD
 


