package net.catharos.cquest.util;

import java.util.regex.Pattern;

import org.bukkit.ChatColor;


public class MessageUtil {
	private static final Pattern COLOR_PATTERN = Pattern.compile("&([0-9a-fk-or])");
	
	
	public static String parseColors(String msg) {
		if (msg == null) return null;

		return COLOR_PATTERN.matcher(msg).replaceAll("\u00a7$1");
	}
	
	public static String parseError(String error, String... args) {
		return parseArguments(ChatColor.DARK_RED + "[Error] " + ChatColor.RED + error, args);
	}
	
	public static String parseArguments(String msg, String... args) {
		return parseArguments(msg, true, args);
	}

	public static String parseArguments(String msg, boolean parseColors, String... args) {
		for (int i = 0; i < args.length; i++)
			msg = msg.replaceAll("%" + i, args[i]);

		if(parseColors) return parseColors(msg);
		return msg;
	}
}
