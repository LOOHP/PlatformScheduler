/*
 * This file is part of PlatformScheduler-Bukkit.
 *
 * Copyright (C) 2020 - 2025. LoohpJames <jamesloohp@gmail.com>
 * Copyright (C) 2020 - 2025. Contributors
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
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.loohp.platformscheduler.platform.bukkit;

import com.loohp.platformscheduler.platform.PlatformScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class BukkitScheduler implements PlatformScheduler {

    @Override
    public void executeOrScheduleSync(Plugin plugin, Runnable task, Entity entity) {
        if (Bukkit.isPrimaryThread()) {
            task.run();
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    @Override
    public void executeOrScheduleSync(Plugin plugin, Runnable task, Location location) {
        if (Bukkit.isPrimaryThread()) {
            task.run();
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    @Override
    public BukkitScheduledTask runTask(Plugin plugin, Runnable task, Entity entity) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTask(plugin, task));
    }

    @Override
    public BukkitScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay, Entity entity) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskLater(plugin, task, delay));
    }

    @Override
    public BukkitScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Entity entity) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period));
    }

    @Override
    public BukkitScheduledTask runTask(Plugin plugin, Runnable task, Location location) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTask(plugin, task));
    }

    @Override
    public BukkitScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay, Location location) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskLater(plugin, task, delay));
    }

    @Override
    public BukkitScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Location location) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period));
    }

    @Override
    public BukkitScheduledTask runTask(Plugin plugin, Runnable task) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTask(plugin, task));
    }

    @Override
    public BukkitScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskLater(plugin, task, delay));
    }

    @Override
    public BukkitScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period));
    }

    @Override
    public BukkitScheduledTask runTaskAsynchronously(Plugin plugin, Runnable task) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskAsynchronously(plugin, task));
    }

    @Override
    public BukkitScheduledTask runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay));
    }

    @Override
    public BukkitScheduledTask runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) {
        return new BukkitScheduledTask(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period));
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Entity entity) {
        return Bukkit.getScheduler().callSyncMethod(plugin, task);
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Location location) {
        return Bukkit.getScheduler().callSyncMethod(plugin, task);
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
        return Bukkit.getScheduler().callSyncMethod(plugin, task);
    }

}
