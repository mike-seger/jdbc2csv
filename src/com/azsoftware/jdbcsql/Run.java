package com.azsoftware.jdbcsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Run {

	private static int exit_status_err = 1;

	private static CLI cli = new CLI();

	private static CommandLine line;

	private static int port;

	private static char separator = '\t';

	private static String csv;

	private static String qry;

	private static String url;

	private static String driver;

	private static Connection conn;

	private static ResultSet resSet;

	public static ResultSet execQry(Connection conn, String qry) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;

		try {
	        stmt = conn.createStatement();
	        if (stmt.execute(qry))
	        	rs = stmt.getResultSet();
		} finally {
            //if (stmt != null) stmt.close();
        }

		return rs;
	}

    public static void main(String[] args) {

        if (args.length == 0) {
        	cli.usage();
        	System.exit(0);
        }

        if (args.length == 1 &&
        		(args[0].equals("-"+cli.optionHelp.getOpt())
        		|| args[0].equals("--"+cli.optionHelp.getLongOpt()))) {
        	cli.usage();
        	System.exit(0);
        }

        try {
        	line = new GnuParser().parse( cli.options, args );
		} catch (ParseException e) {
			System.err.println( e.getMessage() );
			System.exit(exit_status_err);
		}

        if ( line.hasOption(cli.optionPort.getOpt()) )
        	port = Integer.parseUnsignedInt( line.getOptionValue(cli.optionPort.getOpt()) );

    	if ( line.hasOption(cli.optionSeparator.getOpt()) ) {
    		if ( line.getOptionValue(cli.optionSeparator.getOpt()).length() == 1 ) {
    			separator = line.getOptionValue(cli.optionSeparator.getOpt()).charAt(0);
    		} else {
    			System.err.println( "Uncorrect separator" );
    			System.exit(exit_status_err);
    		}
    	}

    	if ( line.hasOption(cli.optionCSV.getOpt()) ) {
    		List<String> csvFormats = Arrays.asList("EXCEL", "MYSQL", "RFC-4180", "TDF");
    		if ( csvFormats.contains(line.getOptionValue(cli.optionCSV.getOpt()).toUpperCase()) ) {
    			csv = line.getOptionValue(cli.optionCSV.getOpt()).toUpperCase();
    		} else {
    			System.err.println( "Uncorrect output csv format" );
    			System.exit(exit_status_err);
    		}
    	}

        if ( line.getArgs().length == 1 ) {
            qry = line.getArgs()[0];
		} else {
			System.err.println( line.getArgs().length < 1 ? "SQL is empty" : "Too many SQL" );
			System.exit(exit_status_err);
		}
/*
        if ( "oracle".equals( line.getOptionValue(cli.optionMS.getOpt()).toLowerCase() ) && port == 0 )
        	port = 1526;
*/
        try {
        	driver = JDBCConfig.getDriver( line.getOptionValue( cli.optionMS.getOpt() ) );
        	url = JDBCConfig.getUrl(
            		line.getOptionValue( cli.optionMS.getOpt() ),
            		line.getOptionValue( cli.optionHost.getOpt() ),
            		port,
            		line.getOptionValue( cli.optionDBName.getOpt() ) );
        } catch (Exception e) {
			System.err.println( "Can't find settings for database management system: " + line.getOptionValue( cli.optionMS.getOpt() ));
			System.err.println( "Please add settings in the file "+JDBCConfig.class.getSimpleName() +".properties" );
			System.exit(exit_status_err);
        }

        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Unable to load driver: " + driver);
			System.exit(exit_status_err);
        }

        try {
        	conn = DriverManager.getConnection(url,
            		line.getOptionValue( cli.optionUser.getOpt() ),
            		line.getOptionValue( cli.optionPass.getOpt() )
            );
        } catch (SQLException ex) {
            System.err.println( "Unable to create connection: " + url );
            System.err.println( ex.getMessage() );
            System.exit(exit_status_err);
        }

        try {
        	resSet = execQry(conn, qry);
        } catch (SQLException ex) {
            System.err.println( "Can't execute query: " + qry );
            System.err.println( ex.getMessage() );
            System.exit(exit_status_err);
        }

        if (resSet != null)
	        try {
	        	CSVFormat csvFormat;
	        	if ( "EXCEL".equals(csv) )
	        	  csvFormat=CSVFormat.EXCEL;
	        	else if ( "MYSQL".equals(csv) )
		          csvFormat=CSVFormat.MYSQL;
	        	else if ( "TDF".equals(csv) )
		          csvFormat=CSVFormat.TDF;
	        	else
		          csvFormat=CSVFormat.RFC4180;

	        	csvFormat=csvFormat.withDelimiter(separator);

	        	if ( !line.hasOption(cli.optionHideHeaders.getOpt()) )
	        		csvFormat.withHeader(resSet).print(System.out);

	        	CSVPrinter csvPrint=new CSVPrinter(System.out, csvFormat);
	        	csvPrint.printRecords(resSet);
	        	csvPrint.flush();
	        	csvPrint.close();
	        } catch (Exception e) {
	            System.err.println( "Can't output query result to stdout" );
	            System.err.println( e.getMessage() );
	            System.exit(exit_status_err);
	        }

        try {
            conn.close();
        } catch (SQLException ex) {
            System.err.println( "Can't close connection" );
            System.err.println( ex.getMessage() );
            System.exit(exit_status_err);
        }

        System.exit(0);
    }
}
