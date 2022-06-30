package net.syobonoon.plugin.micloscsmodels;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class miclosgunEvent implements Listener{
	private Plugin plugin;

	public miclosgunEvent(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	//スマホを右クリックで開く関数
	@EventHandler
	public void gunGUI(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;

		//右クリックではなかったら
		if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) return;

		if (!judgeGun(p.getInventory().getItemInMainHand())) return; //GUIかどうか
		e.setCancelled(true);

		Inventory inv = Bukkit.createInventory(null, 54, Config.GUN_GUI_NAME);

		//インベントリにアイテムを埋めていく
		int i = 0;
		for (ItemStack gun_itemstack : MicLoSCSModels.config.getGunItemStack().values()) {

			if (i > 53) break;
			inv.setItem(i, gun_itemstack);//銃一覧のhashmapからセットする
			i++;
		}

		p.openInventory(inv);
		return;
	}

	//銃GUIから家具を取り出す関数
	@EventHandler
	public void addgunInventory(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();

		if (!(e.isLeftClick() || e.isRightClick())) return;

		//銃GUIではなかった場合
		if (!e.getView().getTitle().equals(Config.GUN_GUI_NAME)) return;

		e.setCancelled(true);//銃GUIでは全てのアイテムの移動を禁止する

		//魔法設定GUIではないところをクリックした場合
		if (!(0 <= e.getRawSlot() && e.getRawSlot() <= 53)) return;

		//クリックしたアイテムが銃ではなかった場合
		ItemStack clicked_item = e.getCurrentItem();

		//if (!judgeGun(clicked_item)) return;

		//クリックした銃をインベントリに追加する処理
		p.getInventory().addItem(clicked_item);
		p.sendMessage(ChatColor.GRAY+"You get " + clicked_item.getItemMeta().getDisplayName());
		return;
	}

	private boolean judgeGun(ItemStack item) {
		if (item.getType().equals(Material.AIR)) return false;

		String item_name = item.getItemMeta().getDisplayName();
		if (!item_name.equals(Config.GUN_GUI_NAME)) return false; //カタログの名前ではない
		if (!item.getItemMeta().hasCustomModelData()) return false; //CustomModelDataがセットされてない
		if (!item.getType().equals(Config.GUN_GUI_MATERIAL)) return false; //Material型が一致しない

		return true;
	}

}
