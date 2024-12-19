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

import io.leangen.geantyref.TypeToken;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.util.NamingSchemes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@NullMarked
public final class ConfigHelper {

    public static HoconConfigurationLoader createLoader(final Path file) {
        final ObjectMapper.Factory factory = ObjectMapper.factoryBuilder()
            .defaultNamingScheme(NamingSchemes.SNAKE_CASE)
            .build();

        return HoconConfigurationLoader.builder()
            .defaultOptions(options -> options.serializers(build -> build.registerAnnotatedObjects(factory)))
            .path(file)
            .build();
    }


    public static <T> T loadConfig(final TypeToken<T> configType,
                                   final Path path,
                                   final Supplier<T> defaultConfigFactory) {
        try {
            if (Files.isRegularFile(path)) {
                final HoconConfigurationLoader loader = createLoader(path);
                final CommentedConfigurationNode node = loader.load();
                return Objects.requireNonNull(node.get(configType));
            } else {
                return defaultConfigFactory.get();
            }
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to load config of type '" + configType.getType()
                .getTypeName() + "' from file at '" + path + "'.", ex);
        }
    }

    // For @ConfigSerializable types with no args constructor
    public static <T> T loadConfig(final Class<T> configType, final Path path) {
        return loadConfig(TypeToken.get(configType), path, () -> {
            try {
                return configType.getConstructor().newInstance();
            } catch (final ReflectiveOperationException ex) {
                throw new RuntimeException("Failed to create instance of type " + configType.getName() + ", does it have a public no args constructor?");
            }
        });
    }

    public static <T> void saveConfig(final Path path,
                                      final TypeToken<T> configType,
                                      final T config) {
        saveConfig(path, config, configType);
    }

    // For @ConfigSerializable types
    public static void saveConfig(final Path path, final Object config) {
        saveConfig(path, config, null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void saveConfig(final Path path,
                                   final Object config,
                                   final @Nullable TypeToken<?> configType) {
        try {
            Files.createDirectories(path.getParent());
            final HoconConfigurationLoader loader = createLoader(path);
            final CommentedConfigurationNode node = loader.createNode();
            if (configType != null) {
                node.set((TypeToken) configType, config);
            } else {
                node.set(config);
            }
            loader.save(node);
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to save config of type '" + (configType != null ? configType.getType()
                .getTypeName() : config.getClass().getName()) + "' to file at '" + path + "'.", ex);
        }
    }

}
