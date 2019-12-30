# jdbc2csv-2.0.jar
### This version using only jdbc url string for connection to DBMS



### 1. Help
```sh
$ java -jar jdbc2csv-2.0.jar
jdbc2csv execute queries in diferent databases such as mysql, oracle, postgresql and etc.
Query with resultset output over stdout in CSV format.

usage: jdbc2csv [OPTION]... SQL
 -f,--csv-format <FORMAT>     Output CSV format with possibale values:
                              Default, Excel, InformixUnload,
                              InformixUnloadCsv, MongoDBCsv, MongoDBTsv,
                              MySQL, Oracle, PostgreSQLCsv,
                              PostgreSQLText, RFC4180 and TDF. Default
                              format is "Default".
 -h,--help                    show this help, then exit
 -H,--hide-headers            hide headers on output
 -u,--jdbc-url <URL STRING>   JDBC driver connection URL string

```


### 2. Postgresql connection example (jdbc required)
```sh
$ java -cp postgresql-9.3-1102-jdbc4.jar:jdbc2csv-2.0.jar com.azsoftware.jdbc2csv.Main \
    -f PostgreSQLText \
    -u 'jdbc:postgresql://localhost:5432/DBName?user=postgres&password=secretkey' \
    'select version()'

```



# jdbcsql-1.0.zip

[Documentation](http://jdbcsql.sourceforge.net/)
