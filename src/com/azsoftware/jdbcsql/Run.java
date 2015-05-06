package com.azsoftware.jdbcsql;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;

public class Run {

	private static int exit_status_err = 1;

	private static CLI cli = new CLI();

	private static CommandLine line;

	private static int port;

	private static boolean hideHeaders = false;

	private static String url;

	private static String driver;

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
			System.out.println( e.getMessage() );
			System.exit(exit_status_err);
		}

        if ( line.hasOption(cli.optionPort.getOpt()) )
        	port = Integer.parseUnsignedInt( line.getOptionValue(cli.optionPort.getOpt()) );

        if ( line.hasOption(cli.optionHideHeaders.getOpt()) )
        	hideHeaders = true;

        if ( "oracle".equals( line.getOptionValue(cli.optionMS.getOpt()).toLowerCase() ) && port == 0 )
        	port = 1526;

        try {
        	driver = JDBCConfig.getDriver( line.getOptionValue( cli.optionMS.getOpt() ) );
        	url = JDBCConfig.getUrl(
            		line.getOptionValue( cli.optionMS.getOpt() ),
            		line.getOptionValue( cli.optionHost.getOpt() ),
            		port,
            		line.getOptionValue( cli.optionDBName.getOpt() ) );
        } catch (Exception e) {
			System.out.println( "Can't find settings for database management system: " + line.getOptionValue( cli.optionMS.getOpt() ));
			System.out.println( "Please add settings in file "+JDBCConfig.class.getSimpleName() +".properties" );
			System.exit(exit_status_err);
        }

        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.out.println("Unable to load driver: " + driver);
			System.exit(exit_status_err);
        }

        try {
            DriverManager.getConnection(url,
            		line.getOptionValue( cli.optionUser.getOpt() ),
            		line.getOptionValue( cli.optionPass.getOpt() )
            );
        } catch (SQLException ex) {
            System.out.println( "Unable to create connection: " + url );
            System.out.println( ex.getMessage() );
            System.exit(exit_status_err);
        }

        System.exit(0);
    }
}
