package com.tscforum.lc01;

import org.bukkit.plugin.java.JavaPlugin;

public final class LC01 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new EventManager(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
