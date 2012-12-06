package net.catharos.cquest.quest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.catharos.cquest.cQuest;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class QuestManager {
	public static final String CONFIG_NAME = "quests";

	private final cQuest plugin;
	
	private FileConfiguration config;
	private File config_file = null;
	
	private List<QuestEntry> questList;
	
	
	public QuestManager(cQuest plugin) {
		this.plugin = plugin;
		
		getQuests();
	}
	
	
	/* ---------------- Get / Set ---------------- */
	
	/**
	 * Returns a quest entry by ID
	 * 
	 * @return {@link QuestEntry}
	 */
	public QuestEntry getQuestById(int id) {
		return getQuests().get(id);
	}
	
	/** Returns a list of stored quests */
	public List<QuestEntry> getQuests() {
		return getQuests(false);
	}
	
	/** Returns a list of stored quests */
	public List<QuestEntry> getQuests(boolean forceReload) {
		if (forceReload || questList == null) {
			this.reloadConfig();
			questList = new ArrayList<QuestEntry>();

			for (String key : config.getKeys(false)) {
				try {
					QuestEntry entry = new QuestEntry(config.getConfigurationSection(key));
					questList.add(entry);
				} catch (IllegalQuestException e) {
					plugin.getLogger().log(Level.SEVERE, "Could not add quest to list: " + e.getReason());
				}
			}
		}

		return this.questList;
	}
	
	/**
	 * Adds a new quest
	 * 
	 * @param	entry The quest entry to add
	 * @return	If the entry could be added, the index of the quest will be returned.
	 * 			If the entry already exists -1 will be returned
	 */
	public int addQuest(QuestEntry entry) {
		if (!questList.contains(entry)) {
			int index = questList.size();
			questList.add(index, entry);

			ConfigurationSection section = config.createSection(Integer.toString(index));
			section.set("name", entry.getName());
			section.set("description", entry.getDescription());

			if (entry.getLocation() != null && !entry.getLocation().isEmpty()) section.set("location", entry.getLocation());

			this.saveConfig();

			return index;
		}

		return -1;
	}

	public void removeQuest(int id) {
		if (questList.get(id) != null) {
			questList.remove(id);

			config.set(Integer.toString(id), null);
			this.saveConfig();
		}
	}
	
	
	/* ---------------- Configuration related ---------------- */
	
	public void reloadConfig() {
		if (config_file == null) config_file = new File(this.plugin.getDataFolder(), CONFIG_NAME + ".yml");

		config = YamlConfiguration.loadConfiguration(config_file);

		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource(CONFIG_NAME + ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
	}
	
	public void saveConfig() {
		try {
			config.save(config_file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save quest configuration!");
			e.printStackTrace();
		}
	}
}
