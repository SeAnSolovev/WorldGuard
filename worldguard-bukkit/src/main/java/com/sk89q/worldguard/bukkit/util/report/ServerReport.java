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
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ServerReport extends DataReport {

    public ServerReport() {
        super("Информация о сервере");

        Server server = Bukkit.getServer();

        append("Версия Bukkit", server.getBukkitVersion());
        append("Реализация", server.getName() + " " + server.getVersion());
        append("Игроков онлайн", "%d/%d", Bukkit.getOnlinePlayers().size(), server.getMaxPlayers());
        append("Источник класса сервера", server.getClass().getProtectionDomain().getCodeSource().getLocation());

        DataReport onlineMode = new DataReport("Онлайн-режим");
        onlineMode.append("включён?", server.getOnlineMode());
        if (PaperLib.isSpigot()) {
            onlineMode.append("Поддержка BungeeCord?", Bukkit.spigot().getConfig().getBoolean("settings.bungeecord", false));
        }
        if (PaperLib.isPaper()) {
            onlineMode.append("Поддержка Velocity?", Bukkit.spigot().getPaperConfig().getBoolean("proxies.velocity.enabled", false));
        }
        append(onlineMode.getTitle(), onlineMode);

        DataReport spawning = new DataReport("Спавн");
        spawning.append("Лимит спавна для окружающих мобов", server.getAmbientSpawnLimit());
        spawning.append("Лимит спавна животных", server.getAnimalSpawnLimit());
        spawning.append("Лимит спавна монстров", server.getMonsterSpawnLimit());
        spawning.append("Тики между спавном животных", server.getTicksPerAnimalSpawns());
        spawning.append("Тики между спавном монстров", server.getTicksPerMonsterSpawns());
        append(spawning.getTitle(), spawning);

        DataReport config = new DataReport("Конфигурация");
        config.append("Ад разрешён?", server.getAllowNether());
        config.append("Край разрешён?", server.getAllowEnd());
        config.append("Генерировать структуры?", server.getGenerateStructures());
        config.append("Разрешён полёт?", server.getAllowFlight());
        config.append("Задержка соединения", server.getConnectionThrottle());
        config.append("Таймаут простоя", server.getIdleTimeout());
        config.append("Сообщение при выключении", server.getShutdownMessage());
        config.append("Игровой режим по умолчанию", server.getDefaultGameMode());
        config.append("Тип главного мира", server.getWorldType());
        config.append("Дистанция прорисовки", server.getViewDistance());
        append(config.getTitle(), config);

        DataReport protection = new DataReport("Защита");
        protection.append("Радиус спавна", server.getSpawnRadius());
        append(protection.getTitle(), protection);
    }

}