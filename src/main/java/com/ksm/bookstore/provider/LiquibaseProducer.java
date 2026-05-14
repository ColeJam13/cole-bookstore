package com.ksm.bookstore.provider;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

/**
 * CDI producer that configures Liquibase to run on application startup.
 * Points Liquibase at the master changelog and the BookstoreDS datasource.
 *
 * @return CDILiquibaseConfig the Liquibase configuration
 * @return ResourceAccessor the classpath resource accessor
 * @return DataSource the bookstore datasource
 */
@ApplicationScoped
public class LiquibaseProducer {

    @Resource(lookup = "java:jboss/datasources/BookstoreDS")
    private DataSource dataSource;

    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setChangeLog("db/changelog/db.changelog-master.xml");
        return config;
    }

    @Produces
    @LiquibaseType
    public DataSource createDataSource() {
        return dataSource;
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor createResourceAccessor() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }

}