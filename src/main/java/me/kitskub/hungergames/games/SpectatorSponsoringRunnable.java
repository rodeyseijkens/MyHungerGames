package me.kitskub.hungergames.games;

import me.kitskub.hungergames.Defaults.Config;
import me.kitskub.hungergames.HungerGames;
import me.kitskub.hungergames.ItemConfig;
import me.kitskub.hungergames.utils.ChatUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class SpectatorSponsoringRunnable implements Runnable{
	public static final int pollEveryInTicks = 20 * 30;
	private final HungerGame game;
	private static final Map<String, Integer> spectatorTimes = new HashMap<String, Integer>(); // <player, ticksPolled>
	private BukkitTask task;
	
	public SpectatorSponsoringRunnable(HungerGame game) {
		this.game = game;
	}
	
	public void run() {
		for (String string : spectatorTimes.keySet()) {
			int time = pollEveryInTicks - spectatorTimes.get(string);
			if (time <= 0) {
				Player player = Bukkit.getPlayer(string);
				if (player == null) {
					spectatorTimes.remove(string);
					continue;
				}
				spectatorTimes.put(player.getName(), Config.SPECTATOR_SPONSOR_PERIOD.getInt(game.getSetup()) * 20);
				ChatUtils.send(player, ChatColor.GOLD, "You can now sponsor a player with an item.");
				ConversationFactory factory = new ConversationFactory(HungerGames.getInstance());
				factory.withEscapeSequence("quit");
				factory.withFirstPrompt(new SpectatorSponsorBeginPrompt());
				Map<Object, Object> sessionData = new HashMap<Object, Object>();
				Set<ItemStack> items = ItemConfig.getAllSponsorLootWithGlobal(game.getItemSets()).keySet();
				ItemStack item = (ItemStack) (items.toArray()[HungerGames.getRandom().nextInt(items.size())]);
				sessionData.put("item", item);
				sessionData.put("amount", item.getAmount());
				if (ItemConfig.useMatchMaterial()) {
					sessionData.put("name", item.getType().name());
				}
				else {
					sessionData.put("name", String.valueOf(item.getTypeId()));
				}
				factory.withInitialSessionData(sessionData);
				factory.withTimeout(30);
				factory.buildConversation(player);
			} else {
				spectatorTimes.put(string, pollEveryInTicks - time + 1);
			}
		}
	}
	
	public void setTask(BukkitTask task) {
		this.task = task;
	}
	
	public void cancel() {
		task.cancel();
		task = null;
		spectatorTimes.clear();
	}
	
	public void addSpectator(Player player) {
		spectatorTimes.put(player.getName(), Config.SPECTATOR_SPONSOR_PERIOD.getInt(game.getSetup()) * 20);
	}
	
	public void removeSpectator(Player player) {
		spectatorTimes.remove(player.getName());
	}
		
	private static class SpectatorSponsorBeginPrompt extends ValidatingPrompt {
		public String getPromptText(ConversationContext cc) {
			StringBuilder builder = new StringBuilder("The item is a stack of ");
			builder.append((Integer) cc.getSessionData("amount"));
			builder.append(" ");
			builder.append((String) cc.getSessionData("name"));
			builder.append(".");
			cc.getForWhom().sendRawMessage(ChatColor.GOLD + builder.toString());
			return "Please type the player you want to sponsor's name or quit.";
		}

		@Override
		protected boolean isInputValid(ConversationContext cc, String string) {
			if (Bukkit.getPlayer(string) != null) {
				return true;
			}
			return false;
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return "That player name is not valid. Please try again.";
		}
		
		 

		@Override
		protected Prompt acceptValidatedInput(ConversationContext cc, String string) {
			Player player = Bukkit.getPlayer(string);
			ItemStack item = (ItemStack) cc.getSessionData("item");
			for (ItemStack drop : player.getInventory().addItem(item).values()) {
				player.getWorld().dropItem(player.getLocation(),drop);
			}
			return END_OF_CONVERSATION;
		}
	}
}
