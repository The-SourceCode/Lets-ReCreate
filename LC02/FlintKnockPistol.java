package com.tscforum.lc02;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlintKnockPistol implements Listener {

    private ItemStack flintKnockItem;
    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    public FlintKnockPistol() {
        flintKnockItem = new ItemStack(Material.STICK);
        ItemMeta itemMeta = flintKnockItem.getItemMeta();

        itemMeta.setDisplayName(ChatColor.BLUE + "Flint-Knock Pistol");
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        flintKnockItem.setItemMeta(itemMeta);
    }

    private void shoot(Player player) {
        if (!player.isSneaking()) {
            Vector direction = player.getLocation().getDirection();
            player.setVelocity(direction.add(new Vector(0, -.2, 0)).multiply(-2D));
        }

        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, .5f, 1);
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 2);
        player.spawnParticle(Particle.REDSTONE,
                player.getLocation().add(0, 1, 0), 10, .5, .5, .5, dustOptions);

        Arrow arrow = player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(4D));
        cooldownMap.put(player.getUniqueId(), Instant.now());
    }


    @EventHandler
    public void flintKnockShoot(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking() && !player.getInventory().contains(flintKnockItem)) {
            player.getInventory().addItem(flintKnockItem);
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            if (!cooldownMap.containsKey(player.getUniqueId())) {
                shoot(player);
                event.setCancelled(true);
            } else {
                Instant lastCast = cooldownMap.get(player.getUniqueId());
                Instant currentTime = Instant.now();

                int reloadTime = 3;

                if (currentTime.isAfter(lastCast.plusSeconds(reloadTime))) {
                    shoot(player);
                    event.setCancelled(true);
                    return;
                }

                player.playSound(player.getLocation(),Sound.BLOCK_DISPENSER_FAIL,1,1);
                player.sendMessage(ChatColor.GRAY + "Reloading...");
            }
        }
    }

}
