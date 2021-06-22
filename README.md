# jdbc2csv-2.1.jar
### Possibility for reading SQL from stdin

THIS IS A FORK FROM https://sourceforge.net/projects/jdbcsql/ PORTED TO GRADLE

### 1. Reading SQL from stdin
```sh
java -cp postgresql-9.3-1102-jdbc4.jar:jdbc2csv-2.1.jar com.azsoftware.jdbc2csv.Main \
    -u 'jdbc:postgresql://host:port/dbname?user=postgres&password=secretkey' <<EOF

    select version()

EOF
```


### 2. Reading SQL from stdin
```sh
echo 'select version()' | java -cp postgresql-9.3-1102-jdbc4.jar:jdbc2csv-2.1.jar com.azsoftware.jdbc2csv.Main \
    -f PostgreSQLText \
    -u 'jdbc:postgresql://host:port/dbname?user=postgres&password=secretkey'
```


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


### 2. Postgresql connection example (postgresql-9.3-1102-jdbc4.jar required)
```sh
$ java -cp postgresql-9.3-1102-jdbc4.jar:jdbc2csv-2.0.jar com.azsoftware.jdbc2csv.Main \
    -f PostgreSQLText \
    -u 'jdbc:postgresql://host:port/dbname?user=postgres&password=secretkey' \
    'select version()'

```


### 3. Oracle connection example (ojdbc8-12.2.0.1.jar required)
```sh
$ java -cp ojdbc8-12.2.0.1.jar:jdbc2csv-2.0.jar com.azsoftware.jdbc2csv.Main \
    -u 'jdbc:oracle:thin:<user>/<password>@host:port:dbname' \
    'select * from V$VERSION'

```


### 4. mysql connection example (mysql-connector-java-8.0.18.jar required)
```sh
$ java -cp mysql-connector-java-8.0.18.jar:jdbc2csv-2.0.jar com.azsoftware.jdbc2csv.Main \
    -u 'jdbc:mysql://user:password@host:port/dbname' \
    'SHOW VARIABLES LIKE "%version%"'

```


# jdbcsql-1.0.zip

[Documentation](http://jdbcsql.sourceforge.net/)
