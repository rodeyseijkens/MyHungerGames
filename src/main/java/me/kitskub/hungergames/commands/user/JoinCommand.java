package me.kitskub.hungergames.commands.user;

import me.kitskub.hungergames.Defaults;
import me.kitskub.hungergames.Defaults.Perm;
import me.kitskub.hungergames.GameManager;
import me.kitskub.hungergames.HungerGames;
import me.kitskub.hungergames.Lang;
import me.kitskub.hungergames.commands.PlayerCommand;
import me.kitskub.hungergames.utils.ChatUtils;

import org.bukkit.entity.Player;

public class JoinCommand extends PlayerCommand {

	public JoinCommand() {
		super(Perm.USER_JOIN, "join", USER_COMMAND);
	}

	@Override
	public void handlePlayer(Player player, String cmd, String[] args) {
		String name = (args.length < 1) ? Defaults.Config.DEFAULT_GAME.getGlobalString() : args[0];
		if (name == null) {
			ChatUtils.helpCommand(player, getUsage(), HungerGames.CMD_USER);
			return;
		}

		game = HungerGames.getInstance().getGameManager().getRawGame(name);
		if (game == null) {
			ChatUtils.error(player, Lang.getNotExist().replace("<item>", name));
			return;
		}

		game.join(player);
	}

	@Override
	public String getInfo() {
		return "join a game";
	}

	@Override
	protected String getPrivateUsage() {
		return "join [game name]";
	}
}
