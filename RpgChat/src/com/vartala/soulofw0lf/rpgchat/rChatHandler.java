package com.vartala.soulofw0lf.rpgchat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class rChatHandler implements CommandExecutor {
	RpgChat Rpgc;
	public rChatHandler(RpgChat rpgc){
		this.Rpgc = rpgc;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player)sender;
		Boolean Password = false;
		if (args.length <= 0){
			Short wc = 0;
			Integer i = 0;
			Inventory inv = Bukkit.createInventory(null, 45, "RpgChat");
			for (String channels : RpgChat.channelConfig.getConfigurationSection("Channels").getKeys(false)){
				Password = false;
				if (RpgChat.channelConfig.getConfigurationSection("Channels." + channels).contains(player.getName())){
					String color = RpgChat.channelConfig.getString("Channels." + channels + "." + player.getName());
					if (color.equalsIgnoreCase("&0")){
						wc = 15;
					}
					if (color.equalsIgnoreCase("&1")){
						wc = 11;
					}
					if (color.equalsIgnoreCase("&2")){
						wc = 13;
					}
					if (color.equalsIgnoreCase("&3")){
						wc = 12;
					}
					if (color.equalsIgnoreCase("&4")){
						wc = 14;
					}
					if (color.equalsIgnoreCase("&5")){
						wc = 10;
					}
					if (color.equalsIgnoreCase("&6")){
						wc = 1;
					}
					if (color.equalsIgnoreCase("&7")){
						wc = 8;
					}
					if (color.equalsIgnoreCase("&8")){
						wc = 7;
					}
					if (color.equalsIgnoreCase("&9")){
						wc = 9;
					}
					if (color.equalsIgnoreCase("&a")){
						wc = 5;
					}
					if (color.equalsIgnoreCase("&b")){
						wc = 3;
					}
					if (color.equalsIgnoreCase("&c")){
						wc = 6;
					}
					if (color.equalsIgnoreCase("&d")){
						wc = 2;
					}
					if (color.equalsIgnoreCase("&e")){
						wc = 4;
					}
					if (color.equalsIgnoreCase("&f")){
						wc = 0;
					}
					ItemStack itmStack0 = new ItemStack(Material.WOOL, 1);
					ItemMeta ichatstack = itmStack0.getItemMeta();
					ichatstack.setDisplayName(channels);
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(this.Rpgc.getConfig().getString("Messages.Left Click"));
					lore.add(this.Rpgc.getConfig().getString("Messages.Right Click"));
					lore.add(this.Rpgc.getConfig().getString("Messages.Shift Click"));
					ichatstack.setLore(lore);
					itmStack0.setItemMeta(ichatstack);
					itmStack0.setDurability(wc);
					inv.setItem(i, itmStack0);
					i++;
				} else {
					for (String pw : RpgChat.passwordConfig.getConfigurationSection("Passwords").getKeys(false)){
						if (channels.equalsIgnoreCase(pw)){
							Password = true;
						}
					}
					if (Password == false){
						ItemStack itmStack0 = new ItemStack(Material.IRON_FENCE, 1);
						ItemMeta ichatstack = itmStack0.getItemMeta();
						ichatstack.setDisplayName(channels);
						ArrayList<String> lore = new ArrayList<String>();
						lore.add(this.Rpgc.getConfig().getString("Messages.Left Click"));
						lore.add(this.Rpgc.getConfig().getString("Messages.Right Click"));
						lore.add(this.Rpgc.getConfig().getString("Messages.Shift Click"));
						ichatstack.setLore(lore);
						itmStack0.setItemMeta(ichatstack);
						inv.setItem(i, itmStack0);
						i++;
					}
				}

			}
			player.openInventory(inv);
			return true;
		}
		if (args[0].equalsIgnoreCase("create")){
			if (!(player.hasPermission("rchat.create"))){
				player.sendMessage("You must have rchat.create permission to use this command!");
				return true;
			}
			if ((args.length <= 1) || (args.length >= 4)){
				player.sendMessage("Improper usage! Please use /rchat create ChannelName {password-optional}");
				return true;
			}
			Integer total = 0;
			for (String i : RpgChat.channelConfig.getConfigurationSection("Channels").getKeys(false)){
				if (!(RpgChat.passwordConfig.getConfigurationSection("Passwords").contains(i))){
				total++;
				}
				if (total >= 45){
					player.sendMessage("There are too many chat channels created to be able to create more.");
					return true;
				}
			}
			
			if (args.length == 2){
				if (RpgChat.channelConfig.getString("Channels." + args[1].replaceAll("_", " ")) != null){
					player.sendMessage("This channel already exists!");
					return true;
				}
				RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " ") + "." + player.getName(), "&f");
				RpgChat.playerConfig.set(player.getName() + ".Owned Channels." + args[1].replaceAll("_", " "), true);
				player.sendMessage("You have successfully created " + args[1].replaceAll("_", " ") + ".");
				try {
					RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
					RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
				} catch (IOException e) {}
				this.Rpgc.upDateChat();
				return true;
			}
			if (args.length == 3){
				if (!(player.hasPermission("rchat.create.password"))){
					player.sendMessage("You must have rchat.create.password permission to use this command!");
					return true;
				}
				if (RpgChat.channelConfig.getString("Channels." + args[1].replaceAll("_", " ")) != null){
					player.sendMessage("This channel already exists!");
					return true;
				}
				RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " ") + "." + player.getName(), "&f");
				RpgChat.playerConfig.set(player.getName() + ".Owned Channels." + args[1].replaceAll("_", " "), true);
				RpgChat.passwordConfig.set("Passwords." + args[1].replaceAll("_", " "), args[2]);
				player.sendMessage("You have successfully created " + args[1].replaceAll("_", " ") + ".");
				try {
					RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
					RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
					RpgChat.passwordConfig.save(new File("plugins/RpgChat/Passwords.yml"));
				} catch (IOException e) {}
				this.Rpgc.upDateChat();
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("delete")){
			if (!(player.hasPermission("rchat.delete"))){
				player.sendMessage("You must have rchat.delete permission to use this command!");
				return true;
			}
			if (args.length != 2){
				player.sendMessage("Improper usage! Please use /rchat delete ChannelName");
				return true;
			}
			if (RpgChat.channelConfig.getString("Channels." + args[1].replaceAll("_", " ") + "." + player.getName()) == null){
				player.sendMessage("This channel does not exist, or you are not currently in it!");
				return true;
			}
			if (!(RpgChat.playerConfig.getConfigurationSection(player.getName() + ".Owned Channels").contains(args[1]))){
				player.sendMessage("You do not own this Channel.");
				return true;
			}
			RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " "), null);
			RpgChat.playerConfig.set(player.getName() + ".Owned Channels." + args[1].replaceAll("_", " "), null);
			if (RpgChat.passwordConfig.getConfigurationSection("Passwords." + args[1].replaceAll("_", " ")) != null){
				RpgChat.passwordConfig.set("Passwords." + args[1].replaceAll("_", " "), null);
				try {
					RpgChat.passwordConfig.save(new File("plugins/RpgChat/Passwords.yml"));
				} catch (IOException e) {}
			}
			try {
				RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
				RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;
		}
		if (args[0].equalsIgnoreCase("promote")){
			if (!(player.hasPermission("rchat.promote"))){
				player.sendMessage("You must have rchat.promote permission to use this command!");
				return true;
			}
			if (args.length != 3){
				player.sendMessage("Improper usage! Please use /rchat promote ChannelName playername");
				return true;
			}
			if (RpgChat.channelConfig.getString("Channels." + args[1].replaceAll("_", " ") + "." + player.getName()) == null){
				player.sendMessage("This channel does not exist, or you are not currently in it!");
				return true;
			}
			if (!(RpgChat.playerConfig.getConfigurationSection(player.getName() + ".Owned Channels").contains(args[1].replaceAll("_", " ")))){
				player.sendMessage("You do not own this Channel.");
				return true;
			}
			if (Bukkit.getPlayer(args[2]) == null){
				player.sendMessage("This player could not be found!");
				return true;
			}
			Player n = Bukkit.getPlayer(args[2]);
			if (RpgChat.channelConfig.getString("Channels." + args[1].replaceAll("_", " ") + "." + n.getName()) == null){
				player.sendMessage("That player is not in that channel.");
				return true;
			}
			if (!(n.hasPermission("rchat.create"))){
				player.sendMessage(n.getName() + " does not have permission to own chat channels.");
				return true;
			}
			RpgChat.playerConfig.set(player.getName() + ".Owned Channels." + args[1].replaceAll("_", " "), null);
			RpgChat.playerConfig.set(n.getName() + ".Owned Channels." + args[1].replaceAll("_", " "), true);
			player.sendMessage(n.getName() + " is now the owner of " + args[1].replaceAll("_", " ") + ".");
			n.sendMessage(player.getName() + " has made you the owner of " + args[1].replaceAll("_", " ") + ".");
			try {
				RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;
		}
		if (args[0].equalsIgnoreCase("invite")){
			if (!(player.hasPermission("rchat.invite"))){
				player.sendMessage("You must have rchat.invite permission to use this command!");
				return true;
			}
			if (args.length != 3){
				player.sendMessage("Improper usage! Please use /rchat invite playername channelname");
				return true;
			}
			if (RpgChat.channelConfig.getString("Channels." + args[2].replaceAll("_", " ") + "." + player.getName()) == null){
				player.sendMessage("This channel does not exist, or you are not currently in it!");
				return true;
			}
			if (!(RpgChat.playerConfig.getConfigurationSection(player.getName() + ".Owned Channels").contains(args[2].replaceAll("_", " ")))){
				player.sendMessage("You do not own this Channel.");
				return true;
			}
			if (Bukkit.getPlayer(args[1]) == null){
				player.sendMessage("This player could not be found!");
				return true;
			}
			RpgChat.playerConfig.set("Invites." + args[1] +"."+ args[2].replaceAll("_", " "), true);
			player.sendMessage("You have invited " + args[1] + " to join your channel.");
			Player bob = Bukkit.getPlayer(args[1]);
			bob.sendMessage(player.getName() + " has invited you to join " + args[2].replaceAll("_", " ") + ". Please type {/rchat accept " + args[2] + "} to join this channel");
			try {
				RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;
		}
		if (args[0].equalsIgnoreCase("accept")){
			if (args.length != 2){
				player.sendMessage("Please use /rchat accept channelname");
				return true;
			}
			Boolean hasinvite = false;
			for (String channel : RpgChat.playerConfig.getConfigurationSection("Invites." + player.getName()).getKeys(false)){
				
				if (channel.equalsIgnoreCase(args[1].replaceAll("_"," "))){
					hasinvite = true;
				}
			}
			if (hasinvite == false){
				player.sendMessage("You do not have a pending invite from that channel or it does not exist!");
				return true;
			}
			RpgChat.playerConfig.set("Invites." + player.getName() + "." + args[1].replaceAll("_", " "), null);
			RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " ") + "." + player.getName(), "&f");
			player.sendMessage("You have successfully joined " + args[1].replaceAll("_", " ") + ".");
			try {
				RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
				RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;		
		}
		if (args[0].equalsIgnoreCase("Join")){
			if (!(player.hasPermission("rchat.join"))){
				player.sendMessage("You must have rchat.join permission to use this command!");
				return true;
			}
			if (RpgChat.channelConfig.getConfigurationSection("Channels." + args[1].replaceAll("_", " ")) == null){
				player.sendMessage("This channel does not exist!");
				return true;
			}
			if (RpgChat.passwordConfig.getConfigurationSection("Passwords").contains(args[1].replaceAll("_", " "))){
				if (player.hasPermission("rchat.bypass.password")){
					RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " ") + "." + player.getName(), "&f");
					player.sendMessage("You have successfully joined " + args[1].replaceAll("_", " ") + ".");
					try {
						RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
					} catch (IOException e) {}
					this.Rpgc.upDateChat();
					return true;
				}
				if (args.length != 3){
					player.sendMessage("You must include a password to join this channel!");
					return true;
				}
				if (!(args[2].equalsIgnoreCase(RpgChat.passwordConfig.getString("Passwords." + args[1].replaceAll("_", " "))))){
					player.sendMessage("You have supplied the wrong password to join this channel!");
					return true;
				}
				RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " ") + "." + player.getName(), "&f");
				player.sendMessage("You have successfully joined " + args[1].replaceAll("_", " ") + ".");
				try {
					RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
				} catch (IOException e) {}
				this.Rpgc.upDateChat();
				return true;
			}
			RpgChat.channelConfig.set("Channels." + args[1].replaceAll("_", " ") + "." + player.getName(), "&f");
			player.sendMessage("You have successfully joined " + args[1].replaceAll("_", " ") + ".");
			try {
				RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;
		}
		if (args[0].equalsIgnoreCase("ignore")){
			if (!(player.hasPermission("rchat.ignore"))){
				player.sendMessage("You must have rchat.ignore permission to use this command!");
				return true;
			}
			if (args.length != 2){
				player.sendMessage("Improper usage! Please use /rchat ignore playername");
				return true;
			}
			if (Bukkit.getPlayer(args[1]) == null){
				player.sendMessage("This player could not be found!");
				return true;
			}
			Player n = Bukkit.getPlayer(args[1]);
			if (n.isOp()){
				player.sendMessage("You cannot Ignore an op!");
				return true;
			}
			if (n.hasPermission("rchat.bypass.ignore")){
				player.sendMessage("This player cannot be ignored!");
				return true;
			}
			if (RpgChat.ignoreConfig.getConfigurationSection("Ignore Lists." + player.getName()).contains(args[1])){
				player.sendMessage("you already have this player ignored!");
				return true;
			}
			RpgChat.ignoreConfig.set("Ignore Lists." + player.getName() + "." + args[1], true);
			player.sendMessage("you have added " + args[1] + " to your ignore list.");
			n.sendMessage(player.getName() + " has added you to their ignore list.");
			try {
				RpgChat.ignoreConfig.save(new File("plugins/RpgChat/Ignore.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;
		}
		if (args[0].equalsIgnoreCase("Unignore")){
			if (args.length != 2){
				player.sendMessage("Improper usage! Please use /rchat ignore playername");
				return true;
			}
			if (Bukkit.getPlayer(args[1]) == null){
				player.sendMessage("This player could not be found!");
				return true;
			}
			Player n = Bukkit.getPlayer(args[1]);
			if (!(RpgChat.ignoreConfig.getConfigurationSection("Ignore Lists." + player.getName()).contains(args[1]))){
				player.sendMessage("This player is not on your ignore list.");
				return true;
			}
			RpgChat.ignoreConfig.set("Ignore Lists." + player.getName() + "." + args[1], null);
			player.sendMessage("you have removed " + args[1] + " from your ignore list.");
			n.sendMessage(player.getName() + " has removed you from their ignore list.");
			try {
				RpgChat.ignoreConfig.save(new File("plugins/RpgChat/Ignore.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
			return true;
		}
		if (args[0].equalsIgnoreCase("spy")){
			if (!(player.hasPermission("rchat.spy"))){
				player.sendMessage("You must have rchat.spy permission to use this command!");
				return true;
			}
			if (!(RpgChat.playerConfig.getConfigurationSection(player.getName()).contains("Spy"))){
				RpgChat.playerConfig.set(player.getName() + ".Spy", true);
				player.sendMessage("Social spy has been turned on.");
				try {
					RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
				} catch (IOException e) {}
				this.Rpgc.upDateChat();
				return true;
			}
			if (RpgChat.playerConfig.getBoolean(player.getName() + ".Spy") == false){
				RpgChat.playerConfig.set(player.getName() + ".Spy", true);
				player.sendMessage("Social spy has been turned on.");
				try {
					RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
				} catch (IOException e) {}
				this.Rpgc.upDateChat();
				return true;
				}
			if (RpgChat.playerConfig.getBoolean(player.getName() + ".Spy") == true){
				RpgChat.playerConfig.set(player.getName() + ".Spy", false);
				player.sendMessage("Social spy has been turned off.");
				try {
					RpgChat.playerConfig.save(new File("plugins/RpgChat/Players.yml"));
				} catch (IOException e) {}
				this.Rpgc.upDateChat();
				return true;	
			}
		}
		if (args[0].equalsIgnoreCase("kick")){
			if (!(player.hasPermission("rchat.kick"))){
				player.sendMessage("You must have rchat.kick permission to use this command!");
				return true;
			}
			if (args.length != 3){
				player.sendMessage("Improper usage! Please use /rchat kick playername channelname");
				return true;
			}
			if (RpgChat.channelConfig.getString("Channels." + args[2].replaceAll("_", " ") + "." + player.getName()) == null){
				player.sendMessage("This channel does not exist, or you are not currently in it!");
				return true;
			}
			if (!(RpgChat.playerConfig.getConfigurationSection(player.getName() + ".Owned Channels").contains(args[2].replaceAll("_", " ")))){
				player.sendMessage("You do not own this Channel.");
				return true;
			}
			if (Bukkit.getPlayer(args[1]) == null){
				player.sendMessage("This player could not be found!");
				return true;
			}
			Player n = Bukkit.getPlayer(args[1]);
			if (RpgChat.channelConfig.getString("Channels." + args[2].replaceAll("_", " ") + "." + n.getName()) == null){
				player.sendMessage("That player is not in that channel.");
				return true;
			}
			RpgChat.channelConfig.set("Channels." + args[2].replaceAll("_", " ") + "." + n.getName(), null);
			player.sendMessage("You have removed " + n.getName() + " from your chat.");
			n.sendMessage("You have been removed from " + args[2].replaceAll("_", " ") + ".");
			try {
				RpgChat.channelConfig.save(new File("plugins/RpgChat/Channels.yml"));
			} catch (IOException e) {}
			this.Rpgc.upDateChat();
		}
		return false;
	}
}
