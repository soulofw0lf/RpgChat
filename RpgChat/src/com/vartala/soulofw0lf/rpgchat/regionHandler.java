package com.vartala.soulofw0lf.rpgchat;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class regionHandler implements CommandExecutor {
	RpgChat Rpgc;
	public regionHandler(RpgChat rpgc){
		this.Rpgc = rpgc;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player)sender;
		if (args.length <=1){
			player.sendMessage("Improper usage, please use {/region create regionname radius} or {/region delete regionname}.");
			return true;
		}
		if (!(player.hasPermission("rchat.region"))){
			player.sendMessage("You must have the rchat.region permission to use this command.");
			return true;
		}
		if (args[0].equalsIgnoreCase("create")){
			if (args.length != 3){
				player.sendMessage("Improper usage, please use {/region create regionname radius}.");
				return true;
			}
			if (RpgChat.regionConfig.getConfigurationSection("Chat Regions." + args[1].replaceAll("_", " ")) != null){
				player.sendMessage("This region already exists!");
				return true;
			}
			Double X = player.getLocation().getX();
			Double Y = player.getLocation().getY();
			Double Z = player.getLocation().getZ();
			Double radius = Double.parseDouble(args[2]);
			String world = player.getLocation().getWorld().getName();
			RpgChat.regionConfig.set("Chat Regions." + args[1].replaceAll("_", " ") + ".X", X);
			RpgChat.regionConfig.set("Chat Regions." + args[1].replaceAll("_", " ") + ".Y", Y);
			RpgChat.regionConfig.set("Chat Regions." + args[1].replaceAll("_", " ") + ".Z", Z);
			RpgChat.regionConfig.set("Chat Regions." + args[1].replaceAll("_", " ") + ".World", world);
			RpgChat.regionConfig.set("Chat Regions." + args[1].replaceAll("_", " ") + ".Radius", radius);
			try {
				RpgChat.regionConfig.save(new File("plugins/RpgChat/Regions.yml"));
			} catch (IOException e) {}
			player.sendMessage("You have successfully created a chat region named " + args[1].replaceAll("_", " ") + " with a radius of " + args[2]);
			return true;
		}
		if (args[0].equalsIgnoreCase("delete")){
			if (args.length != 2){
				player.sendMessage("Improper usage, please use {/region delete regionname}.");
				return true;
			}
			if (RpgChat.regionConfig.getConfigurationSection("Chat Regions." + args[1].replaceAll("_", " ")) == null){
				player.sendMessage("This region does not exist!");
				return true;
			}
			RpgChat.regionConfig.set("Chat Regions." + args[1].replaceAll("_", " "), null);
			try {
				RpgChat.regionConfig.save(new File("plugins/RpgChat/Regions.yml"));
			} catch (IOException e) {}
			player.sendMessage("You have deleted " + args[1].replaceAll("_", " ") + ".");
			return true;
		}
		return false;
	}

}
