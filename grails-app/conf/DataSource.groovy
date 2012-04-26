dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            //            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost:3306/20071461_2?autoreconnect=true&useUnicode=yes&characterEncoding=UTF-8"
            dbCreate = "update"
            username = "root"
                        loggingSql = true
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }
    }
    production {
        dataSource {
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            url = "jdbc:mysql://217.65.6.152:3306/20071461_2?autoreconnect=true&useUnicode=yes&characterEncoding=UTF-8"
            dbCreate = "validate"
            username = "b20071461_2"
            password = "Jkl9nV7h"
        }
    }
}
