package net.syobonoon.plugin.micloscsmodels;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GunCommand implements TabExecutor{
	private Map<String, ItemStack> gun_item_map = new LinkedHashMap<String, ItemStack>();

	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean isSuccess = false;
		if (command.getName().equalsIgnoreCase("ds")) {
			isSuccess = ds(sender, args);
		}
		else if (command.getName().equalsIgnoreCase("dsg")) {
			isSuccess = dsg(sender, args);
		}
		return isSuccess;
	}

	private boolean ds(CommandSender sender, String[] args) {
		if (!sender.hasPermission("micloscrackshot.ds")) return false;
		if (!(sender instanceof Player)) return false;
		Player p = (Player) sender;
		if (args.length == 0 || args.length >= 3) {
	        p.sendMessage(ChatColor.RED + "parameter error");
	        return false;
	    }

		gun_item_map = MicLoSCSModels.config.getGunItemStack();

		//銃が存在しなかったら
		if (!(gun_item_map.containsKey(args[0]))) {
			p.sendMessage(ChatColor.RED + args[0] + " is not exist.");
			return false;
		}

		int amount_gun = 1;
		if (args.length == 2) amount_gun = Integer.parseInt(args[1]);

		ItemStack gun = gun_item_map.get(args[0]);

		//ユーザーのインベントリに加える
		for (int i = 0; i < amount_gun; i++) p.getInventory().addItem(gun);

		p.sendMessage(ChatColor.GRAY + "Successfully grabbed " + args[0]);
		p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1F, 1F);
		return true;
	}

	private boolean dsg(CommandSender sender, String[] args) {
		if (!sender.hasPermission("micloscrackshot.dsg")) return false;
		if (!(sender instanceof Player)) return false;
		if (!(args.length == 0)) {
	        sender.sendMessage(ChatColor.RED + "parameter error");
	        return false;
	    }

		Player p = (Player) sender;

		p.getInventory().addItem(MicLoSCSModels.config.getGunGUI());
		p.sendMessage(ChatColor.AQUA + "You get a gun GUI.");

		return true;
	}

	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) return MicLoSCSModels.config.getGunList();
        return null;
    }
}
