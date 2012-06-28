package com.randude14.hungergames.commands;

import com.randude14.hungergames.Config;
import com.randude14.hungergames.Defaults.Commands;
import com.randude14.hungergames.HungerGames;
import com.randude14.hungergames.utils.ChatUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand{

	public ReloadCommand() {
		super(Commands.ADMIN_RELOAD);
	}

	@Override
	public boolean handle(CommandSender cs, Command cmd, String[] args) {
		Player player = (Player) cs;
			
		HungerGames.reload();
		Config.reload();
		ChatUtils.send(player, ChatUtils.getPrefix() + "Reloaded %s", HungerGames.getInstance().getDescription().getVersion());
		return true;
	}
    
}
