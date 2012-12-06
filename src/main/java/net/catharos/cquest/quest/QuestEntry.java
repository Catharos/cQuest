package net.catharos.cquest.quest;

import org.bukkit.configuration.ConfigurationSection;

public class QuestEntry {
	/** Defines in which state the quest currently is */
	public enum QuestState {
		UNKNOWN, OPEN, FINISHED
	}

	/** Defines the priority for the quest */
	public enum Priority {
		UNKNOWN, DEFAULT, NEEDED
	}

	private String name;
	private String desc;
	private String loc;

	private QuestState state;

	private Priority priority;


	public QuestEntry(String name) {
		this(name, QuestState.UNKNOWN);
	}

	public QuestEntry(String name, QuestState state) {
		this.name = name;
		this.desc = "";
		this.priority = Priority.DEFAULT;
		this.state = state;
	}

	public QuestEntry(ConfigurationSection section) throws IllegalQuestException {
		if (!section.contains("name")) throw new IllegalQuestException("No quest name found");
		this.name = section.getString("name");

		if (!section.contains("description")) throw new IllegalQuestException("No quest description found");
		this.desc = section.getString("description");

		this.loc = section.getString("location", "");

		this.state = QuestState.UNKNOWN;
	}

	/** Returns the quests' name */
	public String getName() {
		return name;
	}

	/** Returns the quests' description */
	public String getDescription() {
		return desc;
	}

	/** Returns the quests' locaction string */
	public String getLocation() {
		return loc;
	}

	/** Returns the quests' state */
	public QuestState getState() {
		return state;
	}

	/** Returns the quests' priority */
	public Priority getPriority() {
		return priority;
	}

	/** Sets the quests' name */
	public void setName(String n) {
		this.name = n;
	}

	/** Sets the quests' description */
	public void setDescription(String d) {
		this.desc = d;
	}

	/** Sets the quests' location string */
	public void setLocation(String l) {
		this.loc = l;
	}

	/** Sets the quests' state */
	public void setState(QuestState s) {
		this.state = s;
	}

	/** Sets the quests' priority */
	public void setPriority(Priority p) {
		this.priority = p;
	}
}