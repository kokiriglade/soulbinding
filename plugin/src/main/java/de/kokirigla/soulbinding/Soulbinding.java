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

import de.kokirigla.soulbinding.configuration.MainConfig;
import de.kokirigla.soulbinding.listener.DeathListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Soulbinding extends JavaPlugin {

    private final MainConfig config;

    public Soulbinding(final MainConfig config) {
        super();
        this.config = config;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }

    public MainConfig config() {
        return config;
    }

}
