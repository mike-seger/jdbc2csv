package com.azsoftware.jdbc2csv;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;

public class Cli {
  private String jdbcUrl;

  private String query;

  private CSVFormat csvFormat = CSVFormat.DEFAULT;

  private boolean hideHeaders = false;

  private Options options = new Options();

  private Option optionHelp = new Option("h", "help", false, "show this help, then exit");

  private Option optionHideHeaders = new Option("H", "hide-headers", false, "hide headers on output");

  private Option optionJdbcUrl = Option.builder("u")
      .longOpt("jdbc-url")
      .hasArg()
      .required()
      .argName("URL STRING")
      .desc("JDBC driver connection URL string")
      .build();

  private Option optionCsvFormat = Option.builder("f")
      .longOpt("csv-format")
      .hasArg()
      .argName("FORMAT")
      .desc("Output CSV format with possibale values: Default, Excel, InformixUnload, InformixUnloadCsv, MongoDBCsv, MongoDBTsv, MySQL, Oracle, PostgreSQLCsv, PostgreSQLText, RFC4180 and TDF. Default format is \"Default\". For more info visit link 'https://javadoc.io/doc/org.apache.commons/commons-csv/latest/org/apache/commons/csv/CSVFormat.html'")
      .build();

  public Cli(String[] args) throws ParseException {
    options.addOption( optionHelp );
    options.addOption( optionHideHeaders );
    options.addOption( optionJdbcUrl );
    options.addOption( optionCsvFormat );

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse( options, args);

    if (cmd.getArgs().length ==0 || cmd.hasOption("h")) {
      usage();
      System.exit(0);
    }

    if (cmd.hasOption(optionJdbcUrl.getOpt())) {
      jdbcUrl = cmd.getOptionValue(optionJdbcUrl.getOpt());
    }

    if (cmd.hasOption(optionCsvFormat.getOpt())) {
      csvFormat = CSVFormat.Predefined.valueOf(cmd.getOptionValue(optionCsvFormat.getOpt())).getFormat();
    }

    if (cmd.hasOption(optionHideHeaders.getOpt())) {
      hideHeaders = true;
    }

    if ( cmd.getArgs().length == 1 ) {
      query = cmd.getArgs()[0];
    } else {
      System.err.println( cmd.getArgs().length < 1 ? "SQL is empty" : "Too many SQL" );
      System.exit(Main.exit_status_err);
    }
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getQuery() {
    return query;
  }


  public CSVFormat getCsvFormat() {
    return csvFormat;
  }

  public boolean isHideHeaders() {
    return hideHeaders;
  }

  public void usage() {
    System.out.println(
      "jdbc2csv execute queries in diferent databases such as mysql, oracle, postgresql and etc.\n"
      + "Query with resultset output over stdout in CSV format.\n");

    // automatically generate the help statement
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp( "jdbc2csv [OPTION]... SQL", options );
    System.out.println();
  }
}
