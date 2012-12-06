package net.catharos.cquest.cmd;

import net.catharos.cquest.cQuest;

import org.bukkit.command.CommandSender;

/**
 * AbstractCommand
 * 
 * Implements all basic features for a cRPG command
 * 
 * @version 1.0
 */
public abstract class AbstractCommand implements ICommand {
	private final cQuest plugin;
	
	private String name;
	private String description;
	private String usage;
	private String permission;
	
	private String[] aliases;
	private int min_args;
	private int max_args;
	
	public AbstractCommand(String name, cQuest plugin) {
		this.plugin = plugin;
		
		this.name = name;
		this.description = "";
		this.usage = "";
		this.permission = "";
		
		this.aliases = new String[0];
		this.min_args = 0;
		this.max_args = 0;
	}
	
	/** Returns the java plugin instance */
	protected cQuest getPlugin() {
		return this.plugin;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	/** Sets the name string */
	protected void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	/** Sets the description string */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	@Override
	public String getUsage() {
		return this.usage;
	}
	
	/** Sets the usage string */
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	@Override
	public String getPermission() {
		return this.permission;
	}
	
	/** Sets the permission string */
	public void setPermission(String perm) {
		this.permission = perm;
	}
	
	@Override
	public String[] getAliases() {
		return this.aliases;
	}
	
	/** Sets the identifiers for the command */
	public void setAliases(String... aliases) {
		String[] s = new String[aliases.length];
		
		for(int i = 0; i < aliases.length; i++)
			s[i] = aliases[i];
		
		this.aliases = s;
	}

	@Override
	public int getMinArguments() {
		return this.min_args;
	}
	
	@Override
	public int getMaxArguments() {
		return this.max_args;
	}
	
	/** Sets the argument range */
	public void setArgumentRage(int min, int max) {
		this.min_args = min;
		this.max_args = max;
	}

	@Override
	public boolean isConsoleCmd() {
		return false;
	}

	@Override
	public boolean isDisplayedInHelp() {
		return true;
	}
	
	@Override
	public boolean isAliasOf(CommandSender sender, String alias) {
		for(String a : getAliases()) {
			if(alias.equalsIgnoreCase(a)) return true;
		}
		
		return false;
	}
}
