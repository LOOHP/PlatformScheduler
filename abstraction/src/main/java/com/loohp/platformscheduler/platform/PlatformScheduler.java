/*
 * This file is part of PlatformScheduler-Abstraction.
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

package com.loohp.platformscheduler.platform;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface PlatformScheduler {

    boolean isPrimaryThread();

    boolean isGlobalTickThread();

    boolean isOwnedByCurrentRegion(Entity entity);

    boolean isOwnedByCurrentRegion(Location location);

    boolean isOwnedByCurrentRegion(Location location, int squareRadiusChunks);

    void executeOrScheduleSync(Plugin plugin, Runnable task, Entity entity);

    void executeOrScheduleSync(Plugin plugin, Runnable task, Location location);

    PlatformScheduledTask<?> runTask(Plugin plugin, Runnable task, Entity entity);

    PlatformScheduledTask<?> runTaskLater(Plugin plugin, Runnable task, long delay, Entity entity);

    PlatformScheduledTask<?> runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Entity entity);

    PlatformScheduledTask<?> runTask(Plugin plugin, Runnable task, Location location);

    PlatformScheduledTask<?> runTaskLater(Plugin plugin, Runnable task, long delay, Location location);

    PlatformScheduledTask<?> runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Location location);

    PlatformScheduledTask<?> runTask(Plugin plugin, Runnable task);

    PlatformScheduledTask<?> runTaskLater(Plugin plugin, Runnable task, long delay);

    PlatformScheduledTask<?> runTaskTimer(Plugin plugin, Runnable task, long delay, long period);

    PlatformScheduledTask<?> runTaskAsynchronously(Plugin plugin, Runnable task);

    PlatformScheduledTask<?> runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay);

    PlatformScheduledTask<?> runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period);

    <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Entity entity);

    <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Location location);

    <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task);
    
}
