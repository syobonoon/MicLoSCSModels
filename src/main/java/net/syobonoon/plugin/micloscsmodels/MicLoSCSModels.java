package net.syobonoon.plugin.micloscsmodels;

import org.bukkit.plugin.java.JavaPlugin;


public class MicLoSCSModels extends JavaPlugin {
	public static Config config;

    @Override
    public void onEnable() {
    	config = new Config(this);
    	new miclosgunEvent(this);
    	getCommand("ds").setExecutor(new GunCommand());
    	getCommand("dsg").setExecutor(new GunCommand());
    	getLogger().info("onEnable");
    }
}
