package net.syobonoon.plugin.micloscsmodels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.shampaggon.crackshot.CSUtility;

public class Config {
	private final Plugin plugin;
	private FileConfiguration config = null;
	private ItemStack gun_gui = null;
	private List<String> gun_item_all_list = new ArrayList<String>();
	private Map<String, ItemStack> gun_item_map = new LinkedHashMap<String, ItemStack>();
	public final static String GUN_GUI_NAME = "Gun_GUI";
	public final static int GUN_GUI_CUSTOM_NUM = 2;
	public final static Material GUN_GUI_MATERIAL = Material.SHULKER_SHELL;
	CSUtility cs = new CSUtility();

	public Config(Plugin plugin) {
		this.plugin = plugin;
		load();
		load_gunGUI();
	}

	public void load() {
		plugin.saveDefaultConfig();
		if (config != null) {
			plugin.reloadConfig();
		}
		config = plugin.getConfig();

		if (!config.contains("GunName")) {
			plugin.getLogger().info("GunName is not exist.");
		} else if (!config.isString("GunName")) {
			plugin.getLogger().info("GunName is not String type.");
		}

		for (String key : config.getConfigurationSection("GunName").getKeys(false)) {
			ItemStack gun = null;
			gun_item_all_list.add(key);

			try {
				gun = cs.generateWeapon(key);
			} catch (Exception e) {
				plugin.getLogger().info(key);
			}

			if (gun == null) continue;

			//gun.setAmount(amount_gun);
			ItemMeta metagun = gun.getItemMeta();
			metagun.setCustomModelData(config.getInt("GunName." + key));
			gun.setItemMeta(metagun);

			gun_item_map.put(key, gun);
		}
	}

	public void load_gunGUI() {
		gun_gui = new ItemStack(GUN_GUI_MATERIAL, 1);
		ItemMeta metagun = gun_gui.getItemMeta();
		metagun.setDisplayName(GUN_GUI_NAME);
		metagun.setCustomModelData(GUN_GUI_CUSTOM_NUM);
		gun_gui.setItemMeta(metagun);
	}

    public ItemStack getGunGUI() {
    	return this.gun_gui;
    }

	public List<String> getGunList() {
		return this.gun_item_all_list;
	}

	//銃の名前とItemStackのhashmapを取得する関数
	public Map<String, ItemStack> getGunItemStack() {
		return this.gun_item_map;
	}

}
