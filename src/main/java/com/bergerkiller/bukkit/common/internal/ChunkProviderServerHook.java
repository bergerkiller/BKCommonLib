package com.bergerkiller.bukkit.common.internal;

import java.util.List;

import com.bergerkiller.bukkit.common.conversion.Conversion;
import com.bergerkiller.bukkit.common.reflection.classes.ChunkProviderServerRef;
import com.bergerkiller.bukkit.common.reflection.classes.WorldServerRef;
import com.bergerkiller.bukkit.common.utils.CommonUtil;

import net.minecraft.server.BiomeMeta;
import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.EnumCreatureType;
import net.minecraft.server.IChunkLoader;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.WorldServer;

/**
 * A CPS Hook class that provides various new events, timings and other useful utilities.
 * This is here so that other plugins can safely control internal behavior (NoLagg mainly).
 */
public class ChunkProviderServerHook extends ChunkProviderServer {

	public ChunkProviderServerHook(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
		super(worldserver, ichunkloader, ichunkprovider);
	}

	public org.bukkit.World getWorld() {
		return super.world.getWorld();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, int x, int y, int z) {
		List<BiomeMeta> mobs = super.getMobsFor(enumcreaturetype, x, y, z);
		if (CommonPlugin.hasInstance()) {
			org.bukkit.World world = this.world.getWorld();
			return CommonPlugin.getInstance().getEventFactory().handleCreaturePreSpawn(world, x, y, z, mobs);
		} else {
			return mobs;
		}
	}

	private static <T> T getCPS(org.bukkit.World world, Class<T> type) {
		return CommonUtil.tryCast(WorldServerRef.chunkProviderServer.get(Conversion.toWorldHandle.convert(world)), type);
	}

	private static IChunkLoader getLoader(Object cps) {
		return (IChunkLoader) ChunkProviderServerRef.chunkLoader.get(cps);
	}

	public static void hook(org.bukkit.World world) {
		ChunkProviderServer oldCPS = getCPS(world, ChunkProviderServer.class);
		if (oldCPS instanceof ChunkProviderServerHook) {
			return;
		}
		ChunkProviderServerHook newCPS = new ChunkProviderServerHook(oldCPS.world, getLoader(oldCPS), oldCPS.chunkProvider);
		ChunkProviderServerRef.TEMPLATE.transfer(oldCPS, newCPS);
		WorldServerRef.chunkProviderServer.set(newCPS.world, newCPS);
	}

	public static void unhook(org.bukkit.World world) {
		ChunkProviderServerHook oldCPS = getCPS(world, ChunkProviderServerHook.class);
		if (oldCPS == null) {
			return;
		}
		ChunkProviderServer newCPS = new ChunkProviderServer(oldCPS.world, getLoader(oldCPS), oldCPS.chunkProvider);
		ChunkProviderServerRef.TEMPLATE.transfer(oldCPS, newCPS);
		WorldServerRef.chunkProviderServer.set(newCPS.world, newCPS);
	}
}
