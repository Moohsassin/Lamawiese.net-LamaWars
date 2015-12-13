package de.Moohsassin.LamaWars.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.PlayerDatas;
import de.Moohsassin.LamaWars.Stats;

public class Command_stats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			String u = p.getUniqueId().toString();
			String name = p.getName();
			
			if(args.length >= 1) {
				name = args[0];
				Player t = Bukkit.getPlayer(name);
				if(t != null) u = t.getUniqueId().toString();
				else {
					
					u = PlayerDatas.getUUID(name);
					if(u == null) {
						p.sendMessage(LamaWars.pr + "§e" + name + " §7hat noch nie LamaWars gespielt!");
						return true;
					}
				}
			}
			
			UUID uuid = UUID.fromString(u);
			
			if(Stats.getKills(uuid) < 0) {
				p.sendMessage(LamaWars.pr + "§e" + name + " §7hat noch nie LamaWars gespielt!");
				return true;
			}
			
			int wins = Stats.getWins(uuid);
			int looses = Stats.getLooses(uuid);
			double winChance = 100 / ((double) wins + (double) looses) * (double) wins;
			
			int kills = Stats.getKills(uuid);
			int deaths = Stats.getDeaths(uuid);
			double kdr = (double) kills / (double) deaths;
			
			p.sendMessage("  ");
			p.sendMessage(LamaWars.pr + "Statistik von §e" + name);
			p.sendMessage("  ");
			p.sendMessage("§6» §7Morde: §e" + kills);
			p.sendMessage("§6» §7Tode: §e" + deaths);
			p.sendMessage("§6» §7KDr: §e" + String.format("%.2f", kdr));
			p.sendMessage("  ");
			p.sendMessage("§6» §7Zerstörte Betten: §e" + Stats.getBrokenBeds(uuid));
			p.sendMessage("  ");
			p.sendMessage("§6» §7Siege: §e" + wins);
			p.sendMessage("§6» §7Niederlagen: §e" + looses);
			p.sendMessage("§6» §7Siegwahrscheinlichkeit: §e" + String.format("%.2f", winChance) + "%");
			p.sendMessage("  ");
			
		}
		
		return true;
	}
	
}
