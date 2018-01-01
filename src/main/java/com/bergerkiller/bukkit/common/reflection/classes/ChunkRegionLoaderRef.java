package com.bergerkiller.bukkit.common.reflection.classes;

import org.bukkit.World;

import com.bergerkiller.bukkit.common.conversion.Conversion;
import com.bergerkiller.bukkit.common.reflection.ClassTemplate;
import com.bergerkiller.bukkit.common.reflection.MethodAccessor;
import com.bergerkiller.bukkit.common.reflection.NMSClassTemplate;

public class ChunkRegionLoaderRef {
	public static final ClassTemplate<?> TEMPLATE = NMSClassTemplate.create("ChunkRegionLoader");
	private static final MethodAccessor<Boolean> chunkExists = TEMPLATE.getMethod("chunkExists", WorldRef.TEMPLATE.getType(), int.class, int.class);
	public static boolean chunkExists(Object chunkRegionLoader, World world, int x, int z) {
		return chunkExists.invoke(chunkRegionLoader, Conversion.toWorldHandle.convert(world), x, z);
	}
}
