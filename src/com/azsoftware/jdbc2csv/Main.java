package com.azsoftware.jdbc2csv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Main {

  public final static int exit_status_err = 1;

  public static void main(String[] args) throws ParseException, IOException {

    Cli cli = new Cli(args);
    try {
      Connection conn = DriverManager.getConnection(cli.getJdbcUrl());
      Statement stmt = conn.createStatement();
      ResultSet resSet = null;
      if (stmt.execute(cli.getQuery())) {
        resSet = stmt.getResultSet();

        CSVFormat csvFormat = cli.getCsvFormat();
        if ( !cli.isHideHeaders() )
          csvFormat.withHeader(resSet).print(System.out);

        CSVPrinter csvPrint=new CSVPrinter(System.out, csvFormat);
        csvPrint.printRecords(resSet);
        csvPrint.flush();
        csvPrint.close();
      }

      conn.close();
      System.exit(0);
    } catch (SQLException ex) {
        System.err.println( ex.getMessage() );
        System.exit(exit_status_err);
    }
  }
}
