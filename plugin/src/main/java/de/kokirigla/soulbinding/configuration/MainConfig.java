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
package de.kokirigla.soulbinding.configuration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NullMarked;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@NullMarked
public final class MainConfig {

    private String soulboundDescription = "<lang:enchantment.soulbinding.soulbound>";

    @Comment("Chance of a Soulbound enchantment book being dropped upon killing the ender dragon. 10% by default")
    private double chance = 0.10d;

    public MainConfig() {
    }

    public Component soulboundDescription() {
        return MiniMessage.miniMessage().deserialize(this.soulboundDescription);
    }

    public double chance() {
        return this.chance;
    }

}
