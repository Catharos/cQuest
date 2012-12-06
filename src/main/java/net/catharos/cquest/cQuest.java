package net.catharos.cquest;


import java.util.logging.Level;

import net.catharos.cquest.cmd.CommandManager;
import net.catharos.cquest.cmd.cmds.CreateCommand;
import net.catharos.cquest.cmd.cmds.ReloadCommand;
import net.catharos.cquest.quest.QuestManager;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class cQuest extends JavaPlugin {
	private CommandManager cmd_manager;
	private QuestManager quest_manager;
	
	private static Permission permissions;
	
	
	/** Enables the plugin */
	@Override
	public void onEnable() {
		// Store default configuration
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		// Check for Vault integration
		if(!setupPermissions()) {
			getLogger().log(Level.SEVERE, "Disabled due to missing permission system!");
			getServer().getPluginManager().disablePlugin(this);
			
			return;
		}
		
		// Set up managers
		this.cmd_manager = new CommandManager();
		this.quest_manager = new QuestManager(this);
		
		// Add commands
		this.setupCommands();
		
		// Everything seems fine
		getLogger().info("Activated!");
	}
	
	/** Disables the plugin */
	@Override
	public void onDisable() {
		getLogger().info("Deactivated!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return getCommandManager().execute(sender, command.getName(), label, args);
	}
	
	
	/* ---------------- Get / Set ---------------- */
	
	/**
	 * Returns the command manager
	 * 
	 * @return {@link CommandManager}
	 */
	public final CommandManager getCommandManager() {
		return this.cmd_manager;
	}
	
	
	/**
	 * Returns the quest manager
	 * 
	 * @return {@link QuestManager}
	 */
	public final QuestManager getQuestManager() {
		return this.quest_manager;
	}

	
	/**
	 * Returns the vault permission handler
	 * 
	 * @return {@link Permission}
	 */
	public final static Permission getPermissions() {
		return cQuest.permissions;
	}
	
	
	/* ---------------- Set ups ---------------- */
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		cQuest.permissions = rsp.getProvider();
		
		return getPermissions() != null;
	}
	
	private void setupCommands() {
		getCommandManager().addCommand(new CreateCommand(this));
		getCommandManager().addCommand(new ReloadCommand(this));
	}
}
