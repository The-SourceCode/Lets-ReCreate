package dev.thesourcecode.lc03;

import org.bukkit.plugin.java.JavaPlugin;

public final class LC03 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new TeemoShroom(this),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
