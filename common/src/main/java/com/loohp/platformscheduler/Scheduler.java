/*
 * This file is part of PlatformScheduler.
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

package com.loohp.platformscheduler;

import com.loohp.platformscheduler.platform.PlatformProvider;
import com.loohp.platformscheduler.platform.PlatformScheduler;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class Scheduler {

    private static final PlatformScheduler platformScheduler = initializePlatformScheduler();

    private static PlatformScheduler initializePlatformScheduler() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("*").scan()) {
            ClassInfoList platformProviderClassList = scanResult.getSubclasses(PlatformProvider.class);
            List<Class<?>> platformProviderClasses = platformProviderClassList.loadClasses();
            Set<PlatformProvider> platformProviders = new TreeSet<>();
            for (Class<?> clazz : platformProviderClasses) {
                if (PlatformProvider.class.isAssignableFrom(clazz)) {
                    try {
                        platformProviders.add((PlatformProvider) clazz.getConstructor().newInstance());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
            for (PlatformProvider provider : platformProviders) {
                if (provider.isSupported()) {
                    return provider.provide();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Unable to find suitable implementation of PlatformScheduler");
    }

    public static PlatformScheduler getPlatform() {
        return platformScheduler;
    }

    public static boolean isPrimaryThread() {
        return platformScheduler.isPrimaryThread();
    }

    public static boolean isGlobalTickThread() {
        return platformScheduler.isGlobalTickThread();
    }

    public static boolean isOwnedByCurrentRegion(Entity entity) {
        return platformScheduler.isOwnedByCurrentRegion(entity);
    }

    public static boolean isOwnedByCurrentRegion(Location location) {
        return platformScheduler.isOwnedByCurrentRegion(location);
    }

    public static boolean isOwnedByCurrentRegion(Location location, int squareRadiusChunks) {
        return platformScheduler.isOwnedByCurrentRegion(location, squareRadiusChunks);
    }

    public static void executeOrScheduleSync(Plugin plugin, Runnable task, Entity entity) {
        platformScheduler.executeOrScheduleSync(plugin, task, entity);
    }

    public static void executeOrScheduleSync(Plugin plugin, Runnable task, Location location) {
        platformScheduler.executeOrScheduleSync(plugin, task, location);
    }

    public static ScheduledTask runTask(Plugin plugin, Runnable task, Entity entity) {
        return new ScheduledTask(platformScheduler.runTask(plugin, task, entity));
    }

    public static ScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay, Entity entity) {
        return new ScheduledTask(platformScheduler.runTaskLater(plugin, task, delay, entity));
    }

    public static ScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Entity entity) {
        return new ScheduledTask(platformScheduler.runTaskTimer(plugin, task, delay, period, entity));
    }

    public static ScheduledTask runTask(Plugin plugin, Runnable task, Location location) {
        return new ScheduledTask(platformScheduler.runTask(plugin, task, location));
    }

    public static ScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay, Location location) {
        return new ScheduledTask(platformScheduler.runTaskLater(plugin, task, delay, location));
    }

    public static ScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Location location) {
        return new ScheduledTask(platformScheduler.runTaskTimer(plugin, task, delay, period, location));
    }

    public static ScheduledTask runTask(Plugin plugin, Runnable task) {
        return new ScheduledTask(platformScheduler.runTask(plugin, task));
    }

    public static ScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay) {
        return new ScheduledTask(platformScheduler.runTaskLater(plugin, task, delay));
    }

    public static ScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {
        return new ScheduledTask(platformScheduler.runTaskTimer(plugin, task, delay, period));
    }
    
    public static ScheduledTask runTaskAsynchronously(Plugin plugin, Runnable task) {
        return new ScheduledTask(platformScheduler.runTaskAsynchronously(plugin, task));
    }

    public static ScheduledTask runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) {
        return new ScheduledTask(platformScheduler.runTaskLaterAsynchronously(plugin, task, delay));
    }
    
    public static ScheduledTask runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) {
        return new ScheduledTask(platformScheduler.runTaskTimerAsynchronously(plugin, task, delay, period));
    }
    
    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Entity entity) {
        return platformScheduler.callSyncMethod(plugin, task, entity);
    }
    
    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Location location) {
        return platformScheduler.callSyncMethod(plugin, task, location);
    }
    
    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
        return platformScheduler.callSyncMethod(plugin, task);
    }
    
}
