package de.strubel.gravitygun;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GravityGunCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (args.length <= 0) {
			
			sender.sendMessage("&4This command requires minimum one argument!");
			return true;
		}
		
		if (sender instanceof Player) {
			
			if (args.length == 1) {
				
				GravityGun g = GravityGunMain.reg.get(args[0]);
				
				if (g == null) {
					
					sender.sendMessage("§4The GravityGun " + args[0] + " does not exists!");
					
					return true;
				}
				
				((Player)sender).getInventory().addItem(g.getItemStack());
				
				sender.sendMessage("§6Move the Block with your GravityGun!");
				
				return true;
			}
			
			if (args.length >= 2) {
				
				GravityGun g = GravityGunMain.reg.get(args[0]);
				
				if (g == null) {
					
					sender.sendMessage("§4The GravityGun " + args[0] + " does not exists!");
					
					return true;
				}
				
				if (Bukkit.getPlayer(args[1]) == null) {
					
					sender.sendMessage("§4The Player " + args[1] + " is not online!");
					
					return true;
				}
				
				Bukkit.getPlayer(args[1]).getInventory().addItem(g.getItemStack());
				
				sender.sendMessage("§6The GravityGun " + args[0] + " was given to the player " + Bukkit.getPlayer(args[1]).getName());
				
				return true;
				
			}
			
		}else {
			
			if (args.length < 2) {
				
				sender.sendMessage("When you run this command from the console (or a CommandBlock) it requires minimum two arguments"); 
				
				return true;
			}
				
				GravityGun g = GravityGunMain.reg.get(args[0]);
				
				if (g == null) {
					
					sender.sendMessage("The GravityGun " + args[0] + " does not exists!");
					
					return true;
				}
				
				if (Bukkit.getPlayer(args[1]) == null) {
					
					sender.sendMessage("The Player " + args[1] + " is not online!");
					
					return true;
				}
				
				Bukkit.getPlayer(args[1]).getInventory().addItem(g.getItemStack());
				
				sender.sendMessage("The GravityGun " + args[0] + " was given to the player " + Bukkit.getPlayer(args[1]).getName());
				
				return true;
				
			}
			
		
		return true;
	}

}
