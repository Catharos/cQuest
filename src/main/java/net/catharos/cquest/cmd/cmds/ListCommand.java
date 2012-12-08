package net.catharos.cquest.cmd.cmds;

import java.util.List;

import net.catharos.cquest.cQuest;
import net.catharos.cquest.cmd.AbstractCommand;
import net.catharos.cquest.quest.QuestEntry;
import net.catharos.cquest.util.MessageUtil;
import net.catharos.cquest.util.PermissionUtil;

import org.bukkit.command.CommandSender;

public class ListCommand extends AbstractCommand {
	private static final int PER_PAGE = 8;
	
	public ListCommand(cQuest plugin) {
		super("List", plugin);

		setAliases("quest list", "quests", "quests list");
		setDescription("Lists all available quests");
		setUsage("/quest list [page]");
		setPermission(PermissionUtil.LIST);
		setArgumentRage(0, 1);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		int page = 1;
		if(args.length > 0) page = Integer.parseInt(args[0]);
		
		List<QuestEntry> quests = getPlugin().getQuestManager().getQuests();
		String page_tag = page + "/" + (int) (Math.ceil(quests.size() / PER_PAGE)+1);
		
		MessageUtil.sendMessage(sender, "&6------------=[ &cQuests " + page_tag + "&6]=------------");
		
		for(int i = 0; i < PER_PAGE ; i++) {
			int index = i + ((page - 1) * PER_PAGE);
			
			if(index < quests.size()) {
				QuestEntry quest = quests.get(index);
				MessageUtil.sendMessage(sender, "&6" + (index+1) + ": &f" + quest.getName());
			}
		}
		
		return true;
	}

}