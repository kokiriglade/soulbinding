/*
 * Soulbinding
 *
 * Copyright (C) 2024 kokiriglade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.kokirigla.soulbinding.listener;

import de.kokirigla.soulbinding.Soulbinding;
import de.kokirigla.soulbinding.SoulbindingBootstrap;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public final class DeathListener implements Listener {

    private final Soulbinding plugin;

    public DeathListener(final Soulbinding plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Set<ItemStack> soulboundItems = new HashSet<>();

        for (final ItemStack drop : event.getDrops()) {
            if(drop.getEnchantments().containsKey(this.soulboundEnchantment())) {
                soulboundItems.add(drop);
            }
        }

        soulboundItems.forEach(item -> {
            event.getDrops().remove(item);
            event.getItemsToKeep().add(item);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEnderDragonDeath(final EntityDeathEvent event) {
        if(event.getEntity() instanceof EnderDragon dragon) {
            if (Math.random() <= plugin.config().chance()) {
                dragon.getLocation().getWorld().dropItemNaturally(
                    dragon.getLocation(),
                    createSoulboundBook()
                );
            }
        }
    }

    private ItemStack createSoulboundBook() {
        final ItemStack book = ItemStack.of(Material.ENCHANTED_BOOK);
        book.setData(
            DataComponentTypes.STORED_ENCHANTMENTS,
            ItemEnchantments.itemEnchantments()
                .add(soulboundEnchantment(), 1)
                .build()
        );
        return book;
    }

    private Enchantment soulboundEnchantment() {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).getOrThrow(
            SoulbindingBootstrap.SOULBOUND_ENCHANTMENT);
    }

}
