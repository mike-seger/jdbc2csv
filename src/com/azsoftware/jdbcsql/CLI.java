package com.azsoftware.jdbcsql;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class CLI {

	// create the Options
	protected Options options = new Options();

	// JDBC url
	@SuppressWarnings("static-access")
	protected Option optionHost = OptionBuilder
			.withLongOpt("host")
			.withDescription("database server host")
			.hasArg()
			.withArgName("HOSTNAME")
			.isRequired()
			.create("h");

	@SuppressWarnings("static-access")
	protected Option optionPort = OptionBuilder
			.withLongOpt("port")
			.withDescription("database server port")
			.hasArg()
			.withArgName("PORT")
//			.isRequired()
			.create("p");

	@SuppressWarnings("static-access")
	protected Option optionDBName = OptionBuilder
			.withLongOpt("dbname")
			.withDescription("database name to connect")
			.hasArg()
			.withArgName("DBNAME")
			.isRequired()
			.create("d");

	@SuppressWarnings("static-access")
	protected Option optionMS = OptionBuilder
			.withLongOpt("management-system")
			.withDescription("database management system (mysql, oracle, postgresql ...)")
			.hasArg()
			.withArgName("SYSTEM")
			.isRequired()
			.create("m");

	// Authentication
	@SuppressWarnings("static-access")
	protected Option optionUser = OptionBuilder
			.withLongOpt("usernme")
			.withDescription("database user name")
			.hasArg()
			.withArgName("USERNAME")
			.isRequired()
			.create("U");

	@SuppressWarnings("static-access")
	protected Option optionPass = OptionBuilder
			.withLongOpt("password")
			.withDescription("database password")
			.hasArg()
			.withArgName("PASSWORD")
			.isRequired()
			.create("P");

	// stdout
	protected Option optionHideHeaders = new Option("H", "hide-headers", false, "hide headers on output");

	@SuppressWarnings("static-access")
	protected Option optionSeparator = OptionBuilder
			.withLongOpt("separator")
			.withDescription("column separator (default: \"\\t\")")
			.hasArg()
			.withArgName("SEPARATOR")
			.withType(Character.class)
			.create("s");

	// help
	protected Option optionHelp = new Option("?", "help", false, "show this help, then exit");

	public CLI() {
		// JDBC url
		options.addOption( optionHost );
		options.addOption( optionPort );
		options.addOption( optionDBName );
		options.addOption( optionMS );

		// Authentication
		options.addOption( optionUser );
		options.addOption( optionPass );

		// stdout
		options.addOption( optionHideHeaders );
		options.addOption( optionSeparator );

		// help
		options.addOption( optionHelp );
	}

	public void usage() {
		System.out.println(
			"jdbcsql execute queries in diferent databases such as mysql, oracle, postgresql and etc.\n"
			+ "Query with resultset output over stdout in format CSV.\n");

		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "jdbcsql [OPTION] sql", options );
		System.out.println();
	}
}
