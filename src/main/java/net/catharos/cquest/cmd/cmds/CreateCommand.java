package net.catharos.cquest.cmd.cmds;

import net.catharos.cquest.cQuest;
import net.catharos.cquest.cmd.AbstractCommand;
import net.catharos.cquest.quest.QuestEntry;
import net.catharos.cquest.quest.QuestManager;
import net.catharos.cquest.util.ArrayUtil;
import net.catharos.cquest.util.MessageUtil;
import net.catharos.cquest.util.PermissionUtil;

import org.bukkit.command.CommandSender;

public class CreateCommand extends AbstractCommand {

	public CreateCommand(cQuest plugin) {
		super("Create", plugin);

		setAliases("quest create", "quests create");
		setDescription("Creates a new quest");
		setPermission(PermissionUtil.ADMIN);
		setArgumentRage(1, 99);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		QuestManager quest_mng = getPlugin().getQuestManager();
		
		QuestEntry entry = new QuestEntry(ArrayUtil.implode(" ", args));
		int index = quest_mng.addQuest(entry);
		
		MessageUtil.sendMessage(sender, "&2Success! &7Quest created: &6" + entry.getName() + " &d(ID: "+ index + ")");
		
		return true;
	}

}
