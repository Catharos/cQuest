package net.catharos.cquest.cmd;

import org.bukkit.command.CommandSender;

/**
 * ICommand
 * 
 * Basic command interface
 * 
 * @version 1.0
 */
public interface ICommand {
	/**
	 * Executes the command. Returns true on success, false on an error
	 * 
	 * @param sender The command sender
	 * @param label The command label
	 * @param args The command arguments
	 * @return Boolean
	 */
	public boolean execute(CommandSender sender, String label, String[] args);
	
	/**
	 * Returns the command name
	 * 
	 * @return String
	 */
	public String getName();
	
	/**
	 * Returns the command description
	 * 
	 * @return String
	 */
	public String getDescription();
	
	/**
	 * Returns the full command description string
	 * 
	 * @return String
	 */
	public String getUsage();
	
	/**
	 * Returns the permission string for the given command
	 * 
	 * @return String
	 */
	public String getPermission();
	
	/**
	 * Returns the list of command labels that are triggering
	 * the command
	 * 
	 * @return String[]
	 */
	public String[] getAliases();
	
	/**
	 * Returns the minimum argument count
	 * @return
	 */
	public int getMinArguments();
	
	/**
	 * Returns the maximum argument count
	 * 
	 * @return int
	 */
	public int getMaxArguments();
	
	/**
	 * Returns true if the command can be executed from console
	 * 
	 * @return Boolean
	 */
	public boolean isConsoleCmd();
	
	/**
	 * Returns true if command should be displayed in a help screen
	 * 
	 * @return Boolean
	 */
	public boolean isDisplayedInHelp();
	
	/**
	 * Returns true if command is an alias
	 * 
	 * @param sender The {@link CommandSender} that requested the command
	 * @param alias The alias to check
	 * @return Boolean
	 */
	public boolean isAliasOf(CommandSender sender, String alias);
	
}
