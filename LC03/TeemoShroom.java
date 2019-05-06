package dev.thesourcecode.lc03;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TeemoShroom implements Listener {

    private final Plugin plugin;

    public TeemoShroom(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void shroomDetect(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Material blockType = player.getLocation().getBlock().getType();

        switch (blockType) {
    case RED_MUSHROOM, BROWN_MUSHROOM -> shroomExplode(player);
    default -> return;
}
    }

    private void shroomExplode(Player player) {
        final Particle.DustOptions
                purpleDust = new Particle.DustOptions(Color.PURPLE, 2),
                lightPurpleDust = new Particle.DustOptions(Color.FUCHSIA, 1);

        final PotionEffect
                poison = new PotionEffect(PotionEffectType.POISON, 80, 1),
                slow = new PotionEffect(PotionEffectType.SLOW, 80, 1);

        final Location location = player.getLocation();
        final World world = location.getWorld();

        world.spawnParticle(Particle.REDSTONE, location, 20,1,1,1, purpleDust);
        world.spawnParticle(Particle.REDSTONE, location, 20,1,1,1, lightPurpleDust);

        player.playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,.5f,10);

        location.getBlock().setType(Material.AIR);

        player.addPotionEffect(poison,true);
        player.addPotionEffect(slow,true);
    }

}
