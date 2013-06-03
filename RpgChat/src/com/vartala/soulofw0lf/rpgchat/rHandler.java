package com.vartala.soulofw0lf.rpgchat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rHandler implements CommandExecutor {

	RpgChat Rpgc;
	public rHandler(RpgChat rpgc){
		this.Rpgc = rpgc;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player)sender;
		if (args.length <= 0){
			p.sendMessage("/r message");
			return true;
		}
		String respond = RpgChat.whisperlist.get(p.getName());
		if (respond == null){
			p.sendMessage("You have no one to respond to.");
			return true;
		}
		if (Bukkit.getPlayer(respond) == null){
			p.sendMessage("Player not found");
			return true;
		}
		Player r = Bukkit.getPlayer(respond);
		if (!(RpgChat.ignoreConfig.getConfigurationSection("Ignore Lists." + r.getName()).contains(p.getName()))){
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
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
