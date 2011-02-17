/*
 * Copyright 2008 Troy Bourdon
 * 
 * This file is part of LJToolkit.
 *
 * LJToolkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LJToolkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LJToolkit.  If not, see <http://www.gnu.org/licenses/>.    
 */

package org.ljtoolkit.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ljtoolkit.utils.LJResourceBundle;

/**
 * The purpose of this class is to aid in the command line parsing for the 
 * {@link org.ljtoolkit.client.LJClient} class. This class will process the
 * command line arguments supplied by the user of LJClient and will provide
 * the values entered. If any of the required command line arguments are
 * missing this class will display the help message.
 * 
 * @author Troy Bourdon
 * @see LJClient
 *
 */
public class LJClientCmdLine {
	Options delegate = new Options();
	CommandLine line;
	CommandLineParser parser = new GnuParser();

	private LJResourceBundle bundle = LJResourceBundle.getInstance("org.ljtoolkit.client");
	private static Log logger = LogFactory.getLog(LJClientService.class);

	public LJClientCmdLine() {
		delegate.addOption(bundle.getResource("cmd.line.help"), false, bundle.getResource("cmd.line.help.message"));
		delegate.addOption(bundle.getResource("cmd.line.username"), true, bundle.getResource("cmd.line.username.message"));
		delegate.addOption(bundle.getResource("cmd.line.password"), true, bundle.getResource("cmd.line.password.message"));
		delegate.addOption(bundle.getResource("cmd.line.action"), true, bundle.getResource("cmd.line.action.message"));
		delegate.addOption(bundle.getResource("cmd.line.id"), true, bundle.getResource("cmd.line.id.message"));
		delegate.addOption(bundle.getResource("cmd.line.file"), true, bundle.getResource("cmd.line.file.message"));
	}
		
	/**
	 * Called by LJClient.
	 * 
	 * @param args
	 * @return true if the parse was successful
	 */
	public boolean parse(String[] args) {	
		try {
			line = parser.parse( delegate, args );
			Pair pair = verifyPostValidity(line);
			
			if(line.hasOption(bundle.getResource("cmd.line.help")) || !pair.isValid()) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(bundle.getResource("cmd.app.title"), delegate);
				return false;
			} else if(!pair.isValid()) {
				System.err.println(buildUsageError(pair.getReason()));
				return false;
			} else {
				return true;
			}
		} catch ( ParseException exp ) {
			logger.error("Parsing failed, cause: ", exp);
			return false;
		}					
	}
	
	public CommandLine getCommandLine() {
		return line;
	}
	
	public String getUsername() {
		return line.getOptionValue(bundle.getResource("cmd.line.username"));
	}
	
	public String getPassword() {
		return line.getOptionValue(bundle.getResource("cmd.line.password"));		
	}
	
	public LJClientAction getAction() {
		String action = line.getOptionValue(bundle.getResource("cmd.line.action"));
		
		if(action.equalsIgnoreCase("add")) return LJClientAction.ADD;
		if(action.equalsIgnoreCase("update")) return LJClientAction.UPDATE;
		if(action.equalsIgnoreCase("delete")) return LJClientAction.DELETE;
		if(action.equals("list")) return LJClientAction.LIST;
		
		return null;
	}
	
	public String getId() {
		return line.getOptionValue(bundle.getResource("cmd.line.id"));
	}
	
	public String getFilePath() {
		return line.getOptionValue(bundle.getResource("cmd.line.file"));				
	}

	private Pair verifyPostValidity(CommandLine line) {
		Pair pair = new Pair();
		pair.setValid(true);
		
		if(!line.hasOption(bundle.getResource("cmd.line.username"))) {
			pair.setValid(false);
			pair.setReason(bundle.getResource("cmd.line.username.error"));
			return pair;
		}
		if(!line.hasOption(bundle.getResource("cmd.line.password"))) {
			pair.setValid(false);
			pair.setReason(bundle.getResource("cmd.line.password.error"));
			return pair;
		}
		if(!line.hasOption(bundle.getResource("cmd.line.action"))) {
			pair.setValid(false);
			pair.setReason(bundle.getResource("cmd.line.action.error"));
			return pair;
		} else {
			if( getAction() == null) {
				pair.setValid(false);
				pair.setReason(bundle.getResource("cmd.line.defined.action"));
				return pair;
			}
		}
		if( (getAction().equals(LJClientAction.ADD) || getAction().equals(LJClientAction.UPDATE)) &&
			!line.hasOption(bundle.getResource("cmd.line.file"))) {
			pair.setValid(false);
			pair.setReason(bundle.getResource("cmd.line.add.requires.file"));
		}
		if( getAction().equals(LJClientAction.DELETE) &&
			!line.hasOption(bundle.getResource("cmd.line.id"))) {
			pair.setValid(false);
			pair.setReason(bundle.getResource("cmd.line.delete.requires.id"));
		}
		
		return pair;
	}
	
	private String buildUsageError(String err) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("!!! LJClient Usage Error !!!\n");
		buffer.append(err + "\n");
		buffer.append("Enter -h for options");
		
		return buffer.toString();
	}
	private class Pair {
		private boolean valid;
		private String reason;
		
		public void setValid(boolean _valid) { valid = _valid; }
		public boolean isValid() { return valid; }
		public void setReason(String _reason) { reason = _reason; }
		public String getReason() { return reason; }
	}

}
