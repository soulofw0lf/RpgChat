package com.vartala.soulofw0lf.rpgchat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class wHandler implements CommandExecutor {

	RpgChat Rpgc;
	public wHandler(RpgChat rpgc){
		this.Rpgc = rpgc;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		Player p = (Player)sender;
		if (args.length <= 1){
			p.sendMessage("/w playername message");
			return true;
		}
		if (Bukkit.getPlayer(args[0]) == null){
			p.sendMessage("Player not found");
			return true;
		}
		Player r = Bukkit.getPlayer(args[0]);
		if (!(RpgChat.ignoreConfig.getConfigurationSection("Ignore Lists." + r.getName()).contains(p.getName()))){
			StringBuilder buffer = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				buffer.append(' ').append(args[i]);
			}
			String s = buffer.toString();
			r.sendMessage("§2[From: " + p.getName() + "]§C " + s);
			p.sendMessage("§2[To: " + r.getName() + "]§C " + s);
			for (Player s1 : Bukkit.getOnlinePlayers()){
				if (s1.hasPermission("rchat.spy")){
					if (RpgChat.playerConfig.getConfigurationSection(s1.getName()).contains("Spy")){
						if (RpgChat.playerConfig.getBoolean(s1.getName() + ".Spy") == true){
							s1.sendMessage("§2[From: " + p.getName() + "][To: " + r.getName() + "]§C " + s);
						}
					}
				}
			}
			RpgChat.whisperlist.put(r.getName(), p.getName());
			return true;
		} else {
			p.sendMessage("This player has you on Ignore");
			return true;
		}
	}

}
