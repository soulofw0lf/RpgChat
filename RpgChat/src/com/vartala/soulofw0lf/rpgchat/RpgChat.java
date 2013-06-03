package com.vartala.soulofw0lf.rpgchat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class RpgChat extends JavaPlugin implements Listener {
	public static Map<String, String> activechat = new HashMap<>();
	public static Map<String, List<String>> channels = new HashMap<>();
	public static List<String> playerlist = new ArrayList<String>();
	public static Map<String, String> whisperlist = new HashMap<>();
	public static YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Players.yml"));
	public static YamlConfiguration channelConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Channels.yml"));
	public static YamlConfiguration languageConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Languages.yml"));
	public static YamlConfiguration regionConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Regionss.yml"));
	public static YamlConfiguration ignoreConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Ignore.yml"));
	public static YamlConfiguration passwordConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Passwords.yml"));
	@Override
	public void onEnable(){
		saveDefaultConfig();
		getCommand("rchat").setExecutor(new rChatHandler(this));
		getCommand("w").setExecutor(new wHandler(this));
		getCommand("r").setExecutor(new rHandler(this));
		getCommand("chatregion").setExecutor(new regionHandler(this));
		Bukkit.getPluginManager().registerEvents(this, this);
		playerConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Players.yml"));
		channelConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Channels.yml"));
		languageConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Languages.yml"));
		regionConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Regionss.yml"));
		ignoreConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Ignore.yml"));
		passwordConfig = YamlConfiguration.loadConfiguration(new File("plugins/RpgChat/Passwords.yml"));
		playerConfig.set("Players.Player Name", "This file is for storing player data!");
		channelConfig.set("Channels.World Chat.Player Name", "This file is for saving channel info!");
		languageConfig.set("Languages.Language Name", "This is for storing language filters!");
		regionConfig.set("Regions.Region Name", "This file stores your region configuration data.");
		ignoreConfig.set("Ignore Lists.Player Name", "This file saves the list of all players ignored by other players");
		passwordConfig.set("Passwords.Chat Name", "This file saves the passwords for all private chat channels");
		List<String> lstring = new ArrayList<String>();
		for (String chats : getConfig().getConfigurationSection("Join Chats").getKeys(false)){
			lstring.clear();
			if (getConfig().getBoolean("Join Chats." + chats) == true){
				channelConfig.set("Channels." + chats + ".ghost", "&2");
				lstring.add("ghost");
				channels.put(chats, lstring);
			}
		}
		try {
			playerConfig.save(new File("plugins/RpgChat/Players.yml"));
			channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
			languageConfig.save(new File("plugins/RpgChat/Languages.yml"));
			regionConfig.save(new File("plugins/RpgChat/Regions.yml"));
			ignoreConfig.save(new File("plugins/RpgChat/Ignore.yml"));
			passwordConfig.save(new File("plugins/RpgChat/Passwords.yml"));
		} catch (IOException e) {}
	upDateChat();
}
public void upDateChat(){	
	playerlist.clear();
	channels.clear();
	for (String key : channelConfig.getConfigurationSection("Channels").getKeys(false)){
		List<String> playersinchat = new ArrayList<String>();
		for (String players : channelConfig.getConfigurationSection("Channels." + key).getKeys(false)){

			playersinchat.add(players);

		}
		playerlist = playersinchat;
		channels.put(key, playerlist);

	}
	for (Player p : Bukkit.getOnlinePlayers()){
		String channel = playerConfig.getString(p.getName() + ".Active Chat");
		activechat.put(p.getName(), channel);
	}
}

@Override
public void onDisable(){
	reloadConfig();
	saveConfig();
}
@EventHandler
public void onplayerClick(InventoryClickEvent event){
	Player p = (Player)event.getWhoClicked();
	if (!(event.getInventory().getTitle().equalsIgnoreCase("RpgChat"))){
		return;
	}
	ItemStack item = event.getCurrentItem();
	event.setCancelled(true);
	if (item != null){
		ItemMeta im = item.getItemMeta();
		if (im != null){
			String channel = im.getDisplayName();
			if (event.isShiftClick()){
				event.setResult(Result.DENY);
				playerConfig.set(p.getName() + ".Active Chat", channel);
				activechat.remove(p.getName());
				activechat.put(p.getName(), channel);
				p.closeInventory();
				return;
			}
			if (event.isLeftClick()){
				event.setResult(Result.DENY);
				String color = "&f";
				if (item.getType() == Material.WOOL){
					Short i = item.getDurability();
					i++;
					if (i > 15){
						i = 0;
					}
					item.setDurability(i);
					if (i == 15){
						color = "&0";
					}
					if (i == 11){
						color = "&1";
					}
					if (i == 13){
						color = "&2";
					}
					if (i == 13){
						color = "&3";
					}
					if (i == 14){
						color = "&4";
					}
					if (i == 10){
						color = "&5";
					}
					if (i == 1){
						color = "&6";
					}
					if (i == 8){
						color = "&7";
					}
					if (i == 7){
						color = "&8";
					}
					if (i == 9){
						color = "&9";
					}
					if (i == 5){
						color = "&a";
					}
					if (i == 3){
						color = "&b";
					}
					if (i == 6){
						color = "&c";
					}
					if (i == 2){
						color = "&d";
					}
					if (i == 4){
						color = "&e";
					}
					if (i == 0){
						color = "&f";
					}
					channelConfig.set("Channels." + channel + "." + p.getName(), color);
				}
				if (item.getType() == Material.IRON_FENCE){
					item.setType(Material.WOOL);
					channelConfig.set("Channels." + channel + "." + p.getName(), color);

				}
			}
			if (event.isRightClick()){
				event.setResult(Result.DENY);
				item.setType(Material.IRON_FENCE);
				playerConfig.set(p.getName() + ".Active Chat", null);
				activechat.remove(p.getName());
				activechat.put(p.getName(), getConfig().getString("First Active"));
				channelConfig.set("Channels." + channel + "." + p.getName(), null);
			}

		}
	}
}
@EventHandler
public void onInvClose(InventoryCloseEvent event){
	if (!(event.getInventory().getTitle().equalsIgnoreCase("RpgChat"))){
		return;
	}
	try {
		playerConfig.save(new File("plugins/RpgChat/Players.yml"));
		channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
	} catch (IOException e) {}
	upDateChat();
}
@EventHandler
public void onPlayerLogin(PlayerJoinEvent event){
	Player p = event.getPlayer();
	if (ignoreConfig.getConfigurationSection("Ignore Lists." + p.getName()) == null){
		ignoreConfig.set("Ignore Lists." + p.getName() + "." + "player to ignore", true);
	}
	if (playerConfig.getConfigurationSection(p.getName()) != null){
		activechat.put(p.getName(), playerConfig.getString(p.getName() + ".Active Chat"));
	} else {
		playerConfig.set(p.getName() + ".Active Chat", getConfig().getString("First Active"));
		activechat.put(p.getName(), playerConfig.getString(p.getName() + ".Active Chat"));
		for (String chats : getConfig().getConfigurationSection("Join Chats").getKeys(false)){
			if (getConfig().getBoolean("Join Chats." + chats) == true){
				channelConfig.set("Channels." + chats + "." + p.getName(), "&2");
				List<String> lstring = channels.get(chats);
				lstring.add(p.getName());
				channels.put(chats, lstring);
			}
		}
	}
	try {
		playerConfig.save(new File("plugins/RpgChat/Players.yml"));
		channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
		ignoreConfig.save(new File("plugins/RpgChat/Ignore.yml"));
	} catch (IOException e) {}
	upDateChat();
}
@EventHandler
public void onPlayerQuit(PlayerQuitEvent event){
	Player p = event.getPlayer();
	activechat.remove(p.getName());
	channels.remove(p.getName());
}
@EventHandler
public void onPlayerChat(AsyncPlayerChatEvent event){
	Player p = event.getPlayer();
	Set<Player> recipients = event.getRecipients();
	String channel = activechat.get(p.getName());
	event.setCancelled(true);
	List<String> newlist = new ArrayList<String>();
	if (getConfig().getBoolean("Console Chat") == true){
		getLogger().info(p.getName() + ": " + event.getMessage());
	}
	if (!(channels.containsKey(channel))){
		p.sendMessage("This channel Does not exist!");
		return;
	}
	Boolean spy = false;
	if (channel.equalsIgnoreCase("World Chat")){
		newlist.clear();
		newlist = channels.get(channel);
		String pworld = p.getLocation().getWorld().getName();
		for (Player r : recipients){
			spy = false;
			if (r.hasPermission("rchat.spy")){
				if (playerConfig.getConfigurationSection(r.getName()).contains("Spy")){
					if (playerConfig.getBoolean(r.getName() + ".Spy") == true){
						String color = channelConfig.getString("Channels." + channel + "." + r.getName());
						if (color == null){
							color = "&f";
						}
						r.sendMessage("§4Spy: " + "§F[" + color.replaceAll("&", "§") + pworld + "§F] " + p.getDisplayName() + "§F:" + color.replaceAll("&", "§") + " " + event.getMessage());
						spy = true;
					}
				}
			}
			if (newlist.contains(r.getName())){
				if (!(ignoreConfig.getConfigurationSection("Ignore Lists." + r.getName()).contains(p.getName()))){
					if (pworld.equalsIgnoreCase(r.getLocation().getWorld().getName())){
						if (spy == false){
							String color = channelConfig.getString("Channels." + channel + "." + r.getName());
							if (color == null){
								color = "&f";
							}
							r.sendMessage("§F[" + color.replaceAll("&", "§") + pworld + "§F] " + p.getDisplayName() + "§F:" + color.replaceAll("&", "§") + " " + event.getMessage());
						}
					}
				}
			}
		}
		return;
	}
	if (channel.equalsIgnoreCase("Region Chat")){
		newlist.clear();
		newlist = channels.get(channel);
		Location loc = p.getLocation();
		String activeregion = null;
		Double X = 0.0;
		Double Y = 0.0;
		Double Z = 0.0;
		String world = null;
		Double radius = 0.0;
		Location region = null;
		Location check = null;
		Double radiuscheck = 0.0;
		for (String regions : regionConfig.getConfigurationSection("Chat Regions").getKeys(false)){
			X = regionConfig.getDouble("Chat Regions." + regions + ".X");
			Y = regionConfig.getDouble("Chat Regions." + regions + ".Y");
			Z = regionConfig.getDouble("Chat Regions." + regions + ".Z");
			world = regionConfig.getString("Chat Regions." + regions + ".World");
			radius = regionConfig.getDouble("Chat Regions." + regions + ".Radius");
			if (p.getWorld().getName().equalsIgnoreCase(world)){
				region = new Location(Bukkit.getWorld(world), X, Y, Z);
				if (loc.distance(region) <= radius){
					activeregion = regions;
					check = region;
					radiuscheck = radius;
				}
			}
		}
		if (check == null){
			p.sendMessage("You are not currently in a region");
			return;
		}
		for (Player r : recipients){
			spy = false;
			if (r.hasPermission("rchat.spy")){
				if (playerConfig.getConfigurationSection(r.getName()).contains("Spy")){
					if (playerConfig.getBoolean(r.getName() + ".Spy") == true){
						String color = channelConfig.getString("Channels." + channel + "." + r.getName());
						if (color == null){
							color = "&f";
						}
						r.sendMessage("§4Spy: " + "§F[" + color.replaceAll("&", "§") + channel + "§F] " + p.getDisplayName() + "§F:" + color.replaceAll("&", "§") + " " + event.getMessage());
						spy = true;
					}
				}
			}
			if (newlist.contains(r.getName())){
				if (!(ignoreConfig.getConfigurationSection("Ignore Lists." + r.getName()).contains(p.getName()))){
					if (r.getWorld().getName().equalsIgnoreCase(p.getWorld().getName())){
						if (r.getLocation().distance(check) <= radiuscheck){
							if (spy == false){
								String color = channelConfig.getString("Channels." + channel + "." + r.getName());
								if (color == null){
									color = "&f";
								}
								r.sendMessage("§F[" + color.replaceAll("&", "§") + channel + " " + activeregion + "§F] " + p.getDisplayName() + "§F:" + color.replaceAll("&", "§") + " " + event.getMessage());
							}
						}
					}
				}
			}
		}
		return;
	}
	if (channels.containsKey(channel)){
		newlist.clear();
		newlist = channels.get(channel);
		for (Player r : recipients){
			spy = false;
			if (r.hasPermission("rchat.spy")){
				if (playerConfig.getConfigurationSection(r.getName()).contains("Spy")){
					if (playerConfig.getBoolean(r.getName() + ".Spy") == true){
						String color = channelConfig.getString("Channels." + channel + "." + r.getName());
						if (color == null){
							color = "&f";
						}
						r.sendMessage("§4Spy: " + "§F[" + color.replaceAll("&", "§") + channel + "§F] " + p.getDisplayName() + "§F:" + color.replaceAll("&", "§") + " " + event.getMessage());
						spy = true;
					}
				}
			}
			if (newlist.contains(r.getName())){
				if (spy == false){
					if (!(ignoreConfig.getConfigurationSection("Ignore Lists." + r.getName()).contains(p.getName()))){
						String color = channelConfig.getString("Channels." + channel + "." + r.getName());
						if (color == null){
							color = "&f";
						}
						r.sendMessage("§F[" + color.replaceAll("&", "§") + channel + "§F] " + p.getDisplayName() + "§F:" + color.replaceAll("&", "§") + " " + event.getMessage());
					}
				}
			}
		}
	}
}
}