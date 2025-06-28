/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.bukkit.util.report;

import com.sk89q.worldedit.util.report.DataReport;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WorldReport extends DataReport {

    public WorldReport() {
        super("Миры");

        List<World> worlds = Bukkit.getServer().getWorlds();

        append("Количество миров", worlds.size());

        for (World world : worlds) {
            DataReport report = new DataReport("Мир: " + world.getName());
            report.append("UUID", world.getUID());
            report.append("Тип мира", world.getWorldType());
            report.append("Окружение", world.getEnvironment());
            ChunkGenerator generator = world.getGenerator();
            report.append("Генератор чанков", generator != null ? generator.getClass().getName() : "<Стандартный>");

            DataReport spawning = new DataReport("Спавн");
            spawning.append("Животные разрешены?", world.getAllowAnimals());
            spawning.append("Монстры разрешены?", world.getAllowMonsters());
            spawning.append("Лимит спавна нейтральных мобов", world.getAmbientSpawnLimit());
            spawning.append("Лимит спавна животных", world.getAnimalSpawnLimit());
            spawning.append("Лимит спавна монстров", world.getMonsterSpawnLimit());
            spawning.append("Лимит спавна водных существ", world.getWaterAnimalSpawnLimit());
            report.append(spawning.getTitle(), spawning);

            DataReport config = new DataReport("Настройки");
            config.append("Сложность", world.getDifficulty());
            config.append("Максимальная высота", world.getMaxHeight());
            config.append("Уровень моря", world.getSeaLevel());
            report.append(config.getTitle(), config);

            DataReport state = new DataReport("Состояние");
            state.append("Точка появления", world.getSpawnLocation());
            state.append("Время мира", world.getFullTime());
            state.append("Длительность погоды", world.getWeatherDuration());
            state.append("Длительность грозы", world.getThunderDuration());
            report.append(state.getTitle(), state);

            DataReport protection = new DataReport("Защита");
            protection.append("PVP?", world.getPVP());
            protection.append("Игровые правила", Arrays.stream(world.getGameRules())
                    .map(name -> name + "=" + world.getGameRuleValue(name))
                    .collect(Collectors.joining(", ")));
            report.append(protection.getTitle(), protection);

            append(report.getTitle(), report);
        }
    }
}
