package com.randude14.hungergames;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import static com.randude14.hungergames.Defaults.Message.*;
import static com.randude14.hungergames.Defaults.Config.*;


public class Config {
	private static final HungerGames plugin = HungerGames.getInstance();
	
	public static void reload() {
		plugin.reloadConfig();
	}
	
	private static boolean getGlobalBoolean(String config, boolean def) {
		return plugin.getConfig().getBoolean("global." + config, def);
	}
	
	private static String getGlobalString(String config, String def) {
		return plugin.getConfig().getString("global." + config, def);
	}
	
	private static int getGlobalInt(String config, int def) {
		return plugin.getConfig().getInt("global." + config, def);
	}
	
	private static List<String> getGlobalStringList(String config, List<String> def) {
		if (plugin.getConfig().contains("global." + config)) {
			return plugin.getConfig().getStringList("global." + config);

		}
		return def;
	}
	
	/** 
	 * For safe recursiveness 
	 * return boolean if found, null if not
	 */
	private static Boolean getBoolean(String config, String setup, Set<String> checked) {
		if (checked.contains(setup)) return null;
		if (plugin.getConfig().contains("setups." + setup + "." + config)) {
			return plugin.getConfig().getBoolean("setups." + setup + "." + config);
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			Boolean b = getBoolean(config, parent, checked);
			if (b != null) return b;
		}
		return null;
	}
	private static boolean getBoolean(String config, String setup, boolean def) {
		Boolean b = getBoolean(config, setup, new HashSet<String>());
		return b == null ? def : b;
	}
	
	/** 
	 * For safe recursiveness 
	 * return String if found, null if not
	 */	
	private static String getString(String config, String setup, Set<String> checked) {
		if (checked.contains(setup)) return null;
		if (plugin.getConfig().contains("setups." + setup + "." + config)) {
			return plugin.getConfig().getString("setups." + setup + "." + config);
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			String s = getString(config, parent, checked);
			if (s != null) return s;
		}
		return null;
	}
	private static String getString(String config, String setup, String def) {
		String s = getString(config, setup, new HashSet<String>());
		return s == null ? def : s;
	}
	
	/** 
	 * For safe recursiveness 
	 * return Integer if found, null if not
	 */
	private static Integer getInteger(String config, String setup, Set<String> checked) {
		if (checked.contains(setup)) return null;
		if (plugin.getConfig().contains("setups." + setup + "." + config)) {
			return plugin.getConfig().getInt("setups." + setup + "." + config);
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			Integer i = getInteger(config, parent, checked);
			if (i != null) return i;
		}
		return null;
	}
	private static int getInteger(String config, String setup, int def) {
		Integer i = getInteger(config, setup, new HashSet<String>());
		return i == null ? def : i;
	}
	
	/** 
	 * For safe recursiveness 
	 * return List if found, null if not
	 */
	private static List<String> getStringList(String config, String setup, Set<String> checked) {
		if (checked.contains(setup)) return null;
		if (plugin.getConfig().contains("setups." + setup + "." + config)) {
			return plugin.getConfig().getStringList("setups." + setup + "." + config);
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			List<String> list = getStringList(config, parent, checked);
			if (list != null) return list;
		}
		return null;
	}
	
	/** returns first available list */
	private static List<String> getStringList(String config, String setup, List<String> def) {
		List<String> list = getStringList(config, setup, new HashSet<String>());
		return list == null ? def : list;
	}

	
	// Global only
	public static String getDefaultGame() {
		return getGlobalString("default-game", DEFAULT_GAME.getString());
	}
	
	public static int getUpdateDelay() {
		return getGlobalInt("update-delay", UPDATE_DELAY.getInt());
	}
		
	public static boolean getForceInternalGlobal() {
		return getGlobalBoolean("force-internal", FORCE_INTERNAL.getBoolean());
	}
		
	public static boolean getAllowMinimalMessagesGlobal() {
		return getGlobalBoolean("allow-minimal-messages", ALLOW_MINIMAL_MESSAGES.getBoolean());
	}
	
	// Global
	public static String getGlobalJoinMessage() {
		return getGlobalString("join-message", JOIN.getMessage());
	}
	
	public static String getGlobalRejoinMessage() {
		return getGlobalString("rejoin-message", REJOIN.getMessage());
	}
	
	public static String getGlobalLeaveMessage() {
		return getGlobalString("leave-message", LEAVE.getMessage());
	}
	
	public static String getGlobalQuitMessage() {
		return getGlobalString("quit-message", QUIT.getMessage());
	}
	
	public static String getGlobalKillMessage() {
		return getGlobalString("kill-message", KILL.getMessage());
	}
	
	public static String getGlobalVoteMessage() {
		return getGlobalString("vote-message", VOTE.getMessage());
	}
	
	public static int getGlobalMinVote() {
		return getGlobalInt("min-vote", MIN_VOTE.getInt());
	}
	
	public static int getGlobalMinPlayers() {
		return getGlobalInt("min-players", MIN_PLAYERS.getInt());
	}

	public static int getGlobalDefaultTime() {
		return getGlobalInt("default-time", DEFAULT_TIME.getInt());
	}
	
	public static int getLivesGlobal() {
		return getGlobalInt("lives", LIVES.getInt());
	}

	public static boolean getGlobalAllowRejoin() {
		return getGlobalBoolean("allow-rejoin", ALLOW_REJOIN.getBoolean());
	}

	public static boolean getGlobalAllowJoinWhileRunning() {
		return getGlobalBoolean("allow-join-during-game", ALLOW_JOIN_WHILE_RUNNING.getBoolean());
	}

	public static boolean getGlobalWinnerKeepsItems(){
		return getGlobalBoolean("winner-keeps-items", WINNER_KEEPS_ITEMS.getBoolean());
	}
	
	public static boolean shouldRespawnAtSpawnPointGlobal() {
		return getGlobalBoolean("spawnpoint-on-death", RESPAWN_ON_DEATH.getBoolean());
	}
	
	public static boolean getRequireInvClear() {
		return getGlobalBoolean("require-inv-clear", REQUIRE_INV_CLEAR.getBoolean());
	}
	
	public static boolean getAllVoteGlobal() {
		return getGlobalBoolean("all-vote", ALL_VOTE.getBoolean());
	}
	
	public static boolean getAutoVoteGlobal() {
		return getGlobalBoolean("auto-vote", AUTO_VOTE.getBoolean());
	}
	
	public static boolean getCanPlaceBlockGlobal() {
		return getGlobalBoolean("can-place-block", CAN_PLACE_BLOCK.getBoolean());
	}
	
	public static boolean getCanBreakBlockGlobal() {
		return getGlobalBoolean("can-break-block", CAN_BREAK_BLOCK.getBoolean());
	}
	
	public static boolean getCanInteractBlockGlobal() {
		return getGlobalBoolean("can-interact-block", CAN_INTERACT_BLOCK.getBoolean());
	}
	
	public static boolean getCanTeleportGlobal() {
		return getGlobalBoolean("can-teleport", CAN_TELEPORT.getBoolean());
	}
	
	public static boolean getUseCommandGlobal() {
		return getGlobalBoolean("use-command", USE_COMMAND.getBoolean());
	}
	
	public static boolean getAutoAddGlobal() {
		return getGlobalBoolean("auto-add", AUTO_ADD.getBoolean());
	}
	
	public static boolean getReloadWorldGlobal() {
		return getGlobalBoolean("reload-world", RELOAD_WORLD.getBoolean());
	}
	
	public static String getReloadWorldNameGlobal() {
		return getGlobalString("reload-world-name", RELOAD_WORLD_NAME.getString());
	}
		
	public static boolean getResetChangesGlobal() {
		return getGlobalBoolean("reset-changes", RELOAD_WORLD.getBoolean());
	}
		
	public static boolean getForceSurvivalGlobal() {
		return getGlobalBoolean("force-survival", FORCE_SURVIVAL.getBoolean());
	}
		
	public static boolean getFreezePlayersGlobal() {
		return getGlobalBoolean("freeze-players", FREEZE_PLAYERS.getBoolean());
	}
		
	public static boolean getForceDamageGlobal() {
		return getGlobalBoolean("force-damange", FORCE_DAMAGE.getBoolean());
	}
		
	public static boolean getIsolatePlayerChatGlobal() {
		return getGlobalBoolean("isolate-player-chat", ISOLATE_PLAYER_CHAT.getBoolean());
	}
		
	public static int getChatDistanceGlobal() {
		return getGlobalInt("chat-distance", CHAT_DISTANCE.getInt());
	}
	
	public static boolean getRemoveItemsGlobal() {
		return getGlobalBoolean("remove-items", REMOVE_ITEMS.getBoolean());
	}
	
	public static List<String> getSpecialBlocksPlaceGlobal() {
		return getGlobalStringList("special-blocks-place", new ArrayList<String>());
	}
	
	public static List<String> getSpecialBlocksBreakGlobal() {
		return getGlobalStringList("special-blocks-break", new ArrayList<String>());
	}
	
	public static List<String> getSpecialBlocksInteractGlobal() {
		return getGlobalStringList("special-blocks-interact", new ArrayList<String>());
	}
	
	public static Map<ItemStack, Float> getGlobalChestLoot() {
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		Map<ItemStack, Float> chestLoot = new HashMap<ItemStack, Float>();
		ConfigurationSection itemSection = config.getConfigurationSection("global.chest-loot");
		if(itemSection == null) return chestLoot;
		
		return readChestLoot(itemSection);
	}
	
	public static Map<ItemStack, Double> getGlobalSponsorLoot() {
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		Map<ItemStack, Double> sponsorLoot = new HashMap<ItemStack, Double>();
		ConfigurationSection itemSection = config.getConfigurationSection("global.sponsor-loot");
		if(itemSection == null) return sponsorLoot;
		
		return readSponsorLoot(itemSection);
	}
	
	// Setups
	public static String getJoinMessage(String setup) {
		return getString("join-message", setup, getGlobalJoinMessage());

	}
	
	public static String getRejoinMessage(String setup) {
		return getString("rejoin-message", setup, getGlobalRejoinMessage());
	}
	
	public static String getLeaveMessage(String setup) {
		return getString("leave-message", setup, getGlobalLeaveMessage());
	}
	
	public static String getQuitMessage(String setup) {
		return getString("quit-message", setup, getGlobalQuitMessage());
	}
	
	public static String getKillMessage(String setup) {
		return getString("kill-message", setup, getGlobalKillMessage());
	}
	
	public static String getVoteMessage(String setup) {
		return getString("vote-message", setup, getGlobalVoteMessage());
	}
	
	public static int getMinVote(String setup) {
		return getInteger("min-vote", setup, getGlobalMinVote());
	}
	
	public static int getMinPlayers(String setup) {
		return getInteger("min-players", setup, getGlobalMinPlayers());
	}
	
	public static int getDefaultTime(String setup) {
		return getInteger("default-time", setup, getGlobalDefaultTime());
	}
	
	public static int getLives(String setup) {
		return plugin.getConfig().getInt("setups." + setup + ".lives", getLivesGlobal());
	}

	public static boolean getAllowRejoin(String setup) {
		return getBoolean("allow-rejoin", setup, getGlobalAllowRejoin());
	}
	
	public static boolean getAllowJoinWhileRunning(String setup) {
		return getBoolean("allow-join-during-game", setup, getGlobalAllowJoinWhileRunning());
	}
	
	public static boolean getWinnerKeepsItems(String setup){
		return getBoolean("winner-keeps-items", setup, getGlobalWinnerKeepsItems());
	}
	
	public static boolean shouldRespawnAtSpawnPoint(String setup) {
		return getBoolean("spawnpoint-on-death", setup, shouldRespawnAtSpawnPointGlobal());
	}
	
	public static boolean getRequireInvClear(String setup) {
		return getBoolean("require-inv-clear", setup, getRequireInvClear());
	}
	
	public static boolean getAllVote(String setup) {
		return getBoolean("all-vote", setup, getAllVoteGlobal());
	}

	public static boolean getAutoVote(String setup) {
		return getBoolean("auto-vote", setup, getAutoVoteGlobal());
	}
	
	public static boolean getCanPlaceBlock(String setup) {
		return getBoolean("can-place-block", setup, getCanPlaceBlockGlobal());
	}
	
	public static boolean getCanBreakBlock(String setup) {
		return getBoolean("can-break-block", setup, getCanBreakBlockGlobal());
	}
	
	public static boolean getCanInteractBlock(String setup) {
		return getBoolean("can-interact-block", setup, getCanInteractBlockGlobal());
	}

	public static boolean getCanTeleport(String setup) {
		return getBoolean("can-teleport", setup, getCanTeleportGlobal());
	}

	public static boolean getUseCommand(String setup) {
		return getBoolean("use-command", setup, getUseCommandGlobal());
	}

	public static boolean getAutoAdd(String setup) {
		return getBoolean("auto-add", setup, getAutoAddGlobal());
	}

	public static boolean getReloadWorld(String setup) {
		return getBoolean("reload-world", setup, getReloadWorldGlobal());
	}

	public static String getReloadWorldName(String setup) {
		return getString("reload-world-name", setup, getReloadWorldNameGlobal());
	}

	public static boolean getResetChanges(String setup) {
		return getBoolean("reset-changes", setup, getResetChangesGlobal());
	}

	public static boolean getForceSurvival(String setup) {
		return getBoolean("force-survival", setup, getForceSurvivalGlobal());
	}

	public static boolean getFreezePlayers(String setup) {
		return getBoolean("freeze-players", setup, getFreezePlayersGlobal());
	}

	public static boolean getForceDamage(String setup) {
		return getBoolean("force-damage", setup, getForceDamageGlobal());
	}

	public static boolean getIsolatePlayerChat(String setup) {
		return getBoolean("isolate-player-chat", setup, getIsolatePlayerChatGlobal());
	}

	public static int getChatDistance(String setup) {
		return getInteger("chat-distance", setup, getChatDistanceGlobal());
	}

	public static boolean getRemoveItems(String setup) {
		return getBoolean("remove-items", setup, getRemoveItemsGlobal());
	}

	public static List<ItemStack> getSpecialBlocksPlace(String setup) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : getStringList("special-blocks-place", setup, getSpecialBlocksPlaceGlobal())){
			list.add(getItemStack(s, 1));
		}
		return list;
	}
	
	public static List<ItemStack> getSpecialBlocksBreak(String setup) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : getStringList("special-blocks-break", setup, getSpecialBlocksBreakGlobal())){
			list.add(getItemStack(s, 1));
		}
		return list;
	}
	
	public static List<ItemStack> getSpecialBlocksInteract(String setup) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : getStringList("special-blocks-interact", setup, getSpecialBlocksPlaceGlobal())){
			list.add(getItemStack(s, 1));
		}
		return list;
	}

	private static Boolean getCanPlaceBlock(String setup, Block block, Set<String> checked) {
		boolean can = false;
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : plugin.getConfig().getStringList("setups." + setup + "." + "special-blocks-place")){
			list.add(getItemStack(s, 1));
		}
		if (plugin.getConfig().contains("setups." + setup + "." + "can-place-block")) {
			can |= plugin.getConfig().getBoolean("setups." + setup + "." + "can-place-block") ^ list.contains(getItemStack(block));
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			Boolean b = getCanPlaceBlock(parent, block, checked);
			can |= b;
		}
		return can;
	}
	public static boolean getCanPlaceBlock(String setup, Block block) {
		boolean can = false;
		Boolean b = getCanPlaceBlock(setup, block, new HashSet<String>());
		if (b != null) can |= b;
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : plugin.getConfig().getStringList("global.special-blocks-place")){
			list.add(getItemStack(s, 1));
		}
		can |= getCanPlaceBlockGlobal() ^ list.contains(getItemStack(block));
		return can;
	}

	private static Boolean getCanBreakBlock(String setup, Block block, Set<String> checked) {
		boolean can = false;
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : plugin.getConfig().getStringList("setups." + setup + "." + "special-blocks-break")){
			list.add(getItemStack(s, 1));
		}
		if (plugin.getConfig().contains("setups." + setup + "." + "can-break-block")) {
			can |= plugin.getConfig().getBoolean("setups." + setup + "." + "can-break-block") ^ list.contains(getItemStack(block));
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			Boolean b = getCanBreakBlock(parent, block, checked);
			can |= b;
		}
		return can;
	}
	public static boolean getCanBreakBlock(String setup, Block block) {
		boolean can = false;
		Boolean b = getCanBreakBlock(setup, block, new HashSet<String>());
		if (b != null) can |= b;
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : plugin.getConfig().getStringList("global.special-blocks-break")){
			list.add(getItemStack(s, 1));
		}
		can |= getCanBreakBlockGlobal() ^ list.contains(getItemStack(block));
		return can;
	}

	private static Boolean getCanInteractBlock(String setup, Block block, Set<String> checked) {
		boolean can = false;
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : plugin.getConfig().getStringList("setups." + setup + "." + "special-blocks-interact")){
			list.add(getItemStack(s, 1));
		}
		if (plugin.getConfig().contains("setups." + setup + "." + "can-interact-block")) {
			can |= plugin.getConfig().getBoolean("setups." + setup + "." + "can-interact-block") ^ list.contains(getItemStack(block));
		}
		checked.add(setup);
		for (String parent : plugin.getConfig().getStringList("setups." + setup + ".inherits")) {
			Boolean b = getCanInteractBlock(parent, block, checked);
			can |= b;
		}
		return can;
	}
	public static boolean getCanInteractBlock(String setup, Block block) {
		boolean can = false;
		Boolean b = getCanInteractBlock(setup, block, new HashSet<String>());
		if (b != null) can |= b;
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (String s : plugin.getConfig().getStringList("global.special-blocks-interact")){
			list.add(getItemStack(s, 1));
		}
		can |= getCanInteractBlockGlobal() ^ list.contains(getItemStack(block));
		return can;
	}

	public static List<String> getSetups(){
	    ConfigurationSection section = plugin.getConfig().getConfigurationSection("setups");
	    if(section == null) return Collections.emptyList();
	    List<String> list = (List<String>) section.getKeys(false);
	    return (list == null) ? new ArrayList<String>() : list;
	}
	
	// Itemsets
	public static List<String> getItemSets(){
	    ConfigurationSection section = plugin.getConfig().getConfigurationSection("itemsets");
	    if(section == null) return Collections.emptyList();
	    List<String> list = new ArrayList<String>(section.getKeys(false));
	    return (list == null) ? new ArrayList<String>() : list;
	}
	
	public static Map<ItemStack, Float> getAllChestLootWithGlobal(List<String> itemsets){
	    Map<ItemStack, Float> toRet = new HashMap<ItemStack, Float>();
	    if(itemsets != null) {
		for(String s : itemsets){
			toRet.putAll(getChestLoot(s));
		}
	    }
	    toRet.putAll(getGlobalChestLoot());
	    return toRet;
	}
	
	public static Map<ItemStack, Double> getAllSponsorLootWithGlobal(List<String> itemsets){
	    Map<ItemStack, Double> toRet = new HashMap<ItemStack, Double>();
	    if(itemsets != null){
		for(String s : itemsets){
			toRet.putAll(getSponsorLoot(s));
		}
	    }
	    toRet.putAll(getGlobalSponsorLoot());
	    return toRet;
	}
	
	/** For safe recursiveness */
	private static Map<ItemStack, Float> getChestLoot(String itemset, Set<String> checked) {
		Map<ItemStack, Float> chestLoot = new HashMap<ItemStack, Float>();
		if (checked.contains(itemset)) return chestLoot;
		chestLoot.putAll(readChestLoot(plugin.getConfig().getConfigurationSection("itemsets." + itemset + ".chest-loot")));
		checked.add(itemset);
		for (String parent : plugin.getConfig().getStringList("itemsets." + itemset + ".inherits")) {
			chestLoot.putAll(getChestLoot(parent, checked));
		}
		return chestLoot;
	}
	public static Map<ItemStack, Float> getChestLoot(String itemset){
		return getChestLoot(itemset, new HashSet<String>());
	}
	
	/** For safe recursiveness */
	private static Map<ItemStack, Double> getSponsorLoot(String itemset, Set<String> checked) {
		Map<ItemStack, Double> chestLoot = new HashMap<ItemStack, Double>();
		if (checked.contains(itemset)) return chestLoot;
		chestLoot.putAll(readSponsorLoot(plugin.getConfig().getConfigurationSection("itemsets." + itemset + ".sponsor-loot")));
		checked.add(itemset);
		for (String parent : plugin.getConfig().getStringList("itemsets." + itemset + ".inherits")) {
			checked.add(parent);
			chestLoot.putAll(getSponsorLoot(parent, checked));
		}
		return chestLoot;
	}
	public static Map<ItemStack, Double> getSponsorLoot(String itemset){
		return getSponsorLoot(itemset, new HashSet<String>());
	}
	
	
	public static void addChestLoot(ItemStack item, float f){
	    // TODO chest loot by command
	}
	
	public static void addSponsorLoot(ItemStack item, double f){
	    // TODO sponsor loot by command
	}
	
	private static Map<ItemStack, Float> readChestLoot(ConfigurationSection itemSection){
	    Map<ItemStack, Float> toRet = new HashMap<ItemStack, Float>();
	    if(itemSection == null) return toRet;
	    
	    for(String key : itemSection.getKeys(false)) {
		    ConfigurationSection section = itemSection.getConfigurationSection(key);
		    int stackSize = section.getInt("stack-size", 1);
		    ItemStack item = getItemStack(key, stackSize);
		    if(item == null) continue;
		    
		    for(String str : section.getKeys(false)) {
			    Enchantment enchant = Enchantment.getByName(str);
			    if(enchant == null || !enchant.canEnchantItem(item)) {
				    continue;
			    }
			    int level = section.getInt(str, 1);
			    try {
				    item.addEnchantment(enchant, level);
			    } catch (Exception ex) {
			    }
		    }
		    
		    float chance = new Double(section.getDouble("chance", 0.3333337)).floatValue();
		    toRet.put(item, chance);
	    }
	    return toRet;
	}
	
	private static Map<ItemStack, Double> readSponsorLoot(ConfigurationSection itemSection){
	    Map<ItemStack, Double> toRet = new HashMap<ItemStack, Double>();
	    if(itemSection == null) return toRet;
	    
	    for(String key : itemSection.getKeys(false)) {
		    ConfigurationSection section = itemSection.getConfigurationSection(key);
		    int stackSize = section.getInt("stack-size", 1);
		    ItemStack item = getItemStack(key, stackSize);
		    if(item == null) continue;

		    for(String str : section.getKeys(false)) {
			    Enchantment enchant = Enchantment.getByName(str);
			    if(enchant == null || !enchant.canEnchantItem(item)) {
				    continue;
			    }
			    int level = section.getInt(str, 1);
			    try {
				    item.addEnchantment(enchant, level);
			    } catch (Exception ex) {
			    }

		    }

		    double money = section.getDouble("money", 10.00);
		    toRet.put(item, money);
	    }
	    return toRet;
	}
	
	private static ItemStack getItemStack(Block block) {
		return new ItemStack(block.getType(), 1, block.getData());
	}
	
	private static ItemStack getItemStack(String s, int stackSize){
		String[] keyParts = s.split(":");
		Material mat = Material.matchMaterial(keyParts[0]);
		if(mat == null) return null;
		MaterialData data = new MaterialData(mat);
		if(keyParts.length == 2){
			try{
				data.setData(Integer.valueOf(keyParts[1]).byteValue());
			}
			catch(NumberFormatException e){}
		}
		ItemStack item = new ItemStack(mat, stackSize);
		item.setData(data);
	    
		return item;
	}
	
}