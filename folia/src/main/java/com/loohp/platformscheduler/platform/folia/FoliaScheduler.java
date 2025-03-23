/*
 * This file is part of PlatformScheduler-Folia.
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

package com.loohp.platformscheduler.platform.folia;

import com.loohp.platformscheduler.platform.PlatformScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FoliaScheduler implements PlatformScheduler {

    @Override
    public boolean isPrimaryThread() {
        return false;
    }

    @Override
    public boolean isGlobalTickThread() {
        return Bukkit.isGlobalTickThread();
    }

    @Override
    public boolean isOwnedByCurrentRegion(Entity entity) {
        return Bukkit.isOwnedByCurrentRegion(entity);
    }

    @Override
    public boolean isOwnedByCurrentRegion(Location location) {
        return Bukkit.isOwnedByCurrentRegion(location);
    }

    @Override
    public boolean isOwnedByCurrentRegion(Location location, int squareRadiusChunks) {
        return Bukkit.isOwnedByCurrentRegion(location, squareRadiusChunks);
    }

    @Override
    public void executeOrScheduleSync(Plugin plugin, Runnable task, Entity entity) {
        if (isOwnedByCurrentRegion(entity)) {
            task.run();
        } else {
            entity.getScheduler().run(plugin, st -> task.run(), null);
        }
    }

    @Override
    public void executeOrScheduleSync(Plugin plugin, Runnable task, Location location) {
        if (isOwnedByCurrentRegion(location)) {
            task.run();
        } else {
            Bukkit.getRegionScheduler().execute(plugin, location, task);
        }
    }

    @Override
    public FoliaScheduledTask runTask(Plugin plugin, Runnable task, Entity entity) {
        return runTask(plugin, task, null, entity);
    }

    @Override
    public FoliaScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay, Entity entity) {
        return runTaskLater(plugin, task, null, delay, entity);
    }

    @Override
    public FoliaScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Entity entity) {
        return runTaskTimer(plugin, task, null, delay, period, entity);
    }

    @Override
    public FoliaScheduledTask runTask(Plugin plugin, Runnable task, Runnable retired, Entity entity) {
        return new FoliaScheduledTask(entity.getScheduler().run(plugin, st -> task.run(), retired));
    }

    @Override
    public FoliaScheduledTask runTaskLater(Plugin plugin, Runnable task, Runnable retired, long delay, Entity entity) {
        return new FoliaScheduledTask(entity.getScheduler().runDelayed(plugin, st -> task.run(), retired, delay));
    }

    @Override
    public FoliaScheduledTask runTaskTimer(Plugin plugin, Runnable task, Runnable retired, long delay, long period, Entity entity) {
        return new FoliaScheduledTask(entity.getScheduler().runAtFixedRate(plugin, st -> task.run(), retired, Math.max(1, delay), period));
    }

    @Override
    public FoliaScheduledTask runTask(Plugin plugin, Runnable task, Location location) {
        return new FoliaScheduledTask(Bukkit.getRegionScheduler().run(plugin, location, st -> task.run()));
    }

    @Override
    public FoliaScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay, Location location) {
        return new FoliaScheduledTask(Bukkit.getRegionScheduler().runDelayed(plugin, location, st -> task.run(), delay));
    }

    @Override
    public FoliaScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period, Location location) {
        return new FoliaScheduledTask(Bukkit.getRegionScheduler().runAtFixedRate(plugin, location, st -> task.run(), Math.max(1, delay), period));
    }

    @Override
    public FoliaScheduledTask runTask(Plugin plugin, Runnable task) {
        return new FoliaScheduledTask(Bukkit.getGlobalRegionScheduler().run(plugin, st -> task.run()));
    }

    @Override
    public FoliaScheduledTask runTaskLater(Plugin plugin, Runnable task, long delay) {
        return new FoliaScheduledTask(Bukkit.getGlobalRegionScheduler().runDelayed(plugin, st -> task.run(), delay));
    }

    @Override
    public FoliaScheduledTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {
        return new FoliaScheduledTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, st -> task.run(), Math.max(1, delay), period));
    }

    @Override
    public FoliaScheduledTask runTaskAsynchronously(Plugin plugin, Runnable task) {
        return new FoliaScheduledTask(Bukkit.getAsyncScheduler().runNow(plugin, st -> task.run()));
    }

    @Override
    public FoliaScheduledTask runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) {
        return new FoliaScheduledTask(Bukkit.getAsyncScheduler().runDelayed(plugin, st -> task.run(), delay * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public FoliaScheduledTask runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) {
        return new FoliaScheduledTask(Bukkit.getAsyncScheduler().runAtFixedRate(plugin, st -> task.run(), Math.max(1, delay * 50), period * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Entity entity) {
        return callSyncMethod(plugin, task, task, entity);
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Callable<T> retired, Entity entity) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Runnable runnable = toFuture(future, task);
        Runnable runnableRetired = toFuture(future, retired);
        entity.getScheduler().run(plugin, st -> runnable.run(), runnableRetired);
        return future;
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task, Location location) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Runnable runnable = toFuture(future, task);
        Bukkit.getRegionScheduler().run(plugin, location, st -> runnable.run());
        return future;
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Runnable runnable = toFuture(future, task);
        Bukkit.getGlobalRegionScheduler().run(plugin, st -> runnable.run());
        return future;
    }

    private static <T> Runnable toFuture(CompletableFuture<T> future, Callable<T> task) {
        return () -> {
            try {
                future.complete(task.call());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        };
    }

}
