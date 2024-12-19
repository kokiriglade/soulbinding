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
package de.kokirigla.soulbinding;

import de.kokirigla.soulbinding.configuration.ConfigHelper;
import de.kokirigla.soulbinding.configuration.MainConfig;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.set.RegistrySet;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NullMarked
public final class SoulbindingBootstrap implements PluginBootstrap {

    public static final Key SOULBOUND_ENCHANTMENT = Key.key("soulbinding:soulbound");
    public static final TagKey<ItemType> SOULBOUNDABLE_TAG = ItemTypeTagKeys.create(Key.key("soulbinding:soulboundable"));

    private @Nullable MainConfig config;

    @Override
    public void bootstrap(final BootstrapContext context) {
        this.config = ConfigHelper.loadConfig(MainConfig.class, context.getDataDirectory().resolve("config.conf"));
        ConfigHelper.saveConfig(context.getDataDirectory().resolve("config.conf"), this.config);

        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            event.registry().register(
                EnchantmentKeys.create(SOULBOUND_ENCHANTMENT),
                b -> b.description(config.soulboundDescription())
                    .supportedItems(event.getOrCreateTag(SOULBOUNDABLE_TAG))
                    .anvilCost(3)
                    .maxLevel(1)
                    .weight(1)
                    .exclusiveWith(
                        RegistrySet.keySet(
                            RegistryKey.ENCHANTMENT,
                            EnchantmentKeys.BINDING_CURSE,
                            EnchantmentKeys.VANISHING_CURSE
                        )
                    )
                    .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(1, 1))
                    .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(3, 1))
                    .activeSlots(EquipmentSlotGroup.ANY)
            );
        }));

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(
            RegistryKey.ITEM).newHandler(event -> {
            event.registrar().addToTag(
                SOULBOUNDABLE_TAG,
                Stream.of(
                    ItemTypeTagKeys.ENCHANTABLE_WEAPON,
                    ItemTypeTagKeys.ENCHANTABLE_MINING,
                    ItemTypeTagKeys.ENCHANTABLE_TRIDENT,
                    ItemTypeTagKeys.ENCHANTABLE_BOW,
                    ItemTypeTagKeys.ENCHANTABLE_CROSSBOW,
                    ItemTypeTagKeys.ENCHANTABLE_EQUIPPABLE,
                    ItemTypeTagKeys.LECTERN_BOOKS
                )
                    .map(TagEntry::tagEntry)
                    .collect(Collectors.toSet())
            );
        }));
    }

    @Override
    public Soulbinding createPlugin(final PluginProviderContext context) {
        Objects.requireNonNull(config);
        return new Soulbinding(config);
    }

}
