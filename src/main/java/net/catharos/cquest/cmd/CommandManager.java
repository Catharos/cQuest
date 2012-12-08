package net.catharos.cquest.cmd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NoPermissionException;

import net.catharos.cquest.cQuest;
import net.catharos.cquest.util.ArrayUtil;
import net.catharos.cquest.util.MessageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


/**
 * CommandManager
 * 
 * Handles commands, their permissions and help pages
 */
public class CommandManager {
	protected Map<String, ICommand> commands;
	protected Map<String, ICommand> aliases;
	
	
	public CommandManager() {
		this.commands = new HashMap<String, ICommand>();
		this.aliases= new HashMap<String, ICommand>();
	}
	
	public CommandManager addCommand(ICommand cmd) {
		this.commands.put(cmd.getName(), cmd);
		
		for(String alias : cmd.getAliases())
			this.aliases.put(alias.toLowerCase(), cmd);
		
		return this;
	}
	
	public Map<String, ICommand> getCommands() {
		return commands;
	}

	public boolean execute(CommandSender sender, String command, String label, String[] args) {
		ICommand cmd = null;
		
		for(int args_left = args.length; args_left >= 0; args_left--) {
			// Build the argument list
			StringBuilder builder = new StringBuilder(label);
			
			for(int i = 0; i < args_left; i++)
				builder.append(" ").append(args[i]);
			
			// Get the command
			cmd = this.aliases.get(builder.toString());
			
			if(cmd == null) {
				for(ICommand c : this.commands.values()) {
					if(c.isAliasOf(sender, builder.toString())) {
						cmd = c;
						break;
					}
				}
			}
			
			// Execute if command exists
			if(cmd != null) {
				try {
					if(sender instanceof ConsoleCommandSender && !cmd.isConsoleCmd()) {
						MessageUtil.sendError(sender, "The command cannot be executed from console.");
						return true;
					}
						
					if(!hasPermission(sender, cmd.getPermission()))
						throw new NoPermissionException(cmd.getPermission());
					
					String[] cmd_args = Arrays.copyOfRange(args, args_left, args.length);
					
					if(cmd_args.length >= cmd.getMinArguments() && cmd_args.length <= cmd.getMaxArguments()) {
						if( cmd.execute(sender, label, cmd_args) ) return true;
					}
					
					// Show help box
					MessageUtil.sendMessage(sender, "&6------------=[ &cHelp &6]=------------");
					MessageUtil.sendMessage(sender, "Name: &6" + cmd.getName() + "&7(" + cmd.getUsage() + ")");
					MessageUtil.sendMessage(sender, "Description: &6" + cmd.getDescription());
					
				} catch(NoPermissionException np_ex) {
					MessageUtil.sendError(sender, "You are not allowed to use that command!");
				}
				
				return true;
			}
		}
		
		MessageUtil.sendError(sender, "Command not found:&6 /%0 %1", command, ArrayUtil.implode(" ", args));
		
		return true;
	}
	
	public static boolean hasPermission(CommandSender sender, String perm) {
		if ((!(sender instanceof Player)) || (perm == null) || (perm.isEmpty()))
			return true;

		Player player = (Player)sender;

		return (player.isOp()) || (cQuest.getPermissions().has(player, perm));
	}
}
