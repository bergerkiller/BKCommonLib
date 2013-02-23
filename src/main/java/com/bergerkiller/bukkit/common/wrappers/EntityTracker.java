package com.bergerkiller.bukkit.common.wrappers;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.reflection.classes.EntityTrackerRef;

/**
 * Wrapper class for the Entity Tracker
 */
public class EntityTracker extends BasicWrapper {

	public EntityTracker(Object entityTrackerHandle) {
		setHandle(entityTrackerHandle);
	}

	/**
	 * Sends spawn packets to the player for all entities contained in the chunk
	 * 
	 * @param player to send spawn packets to
	 * @param chunk containing the entities to update
	 */
	public void spawnEntities(Player player, Chunk chunk) {
		EntityTrackerRef.spawnEntities(handle, player, chunk);
	}

	/**
	 * Adds an entity to this entity tracker, creating a new entity tracker entry if needed
	 * 
	 * @param entity to start tracking
	 */
	public void startTracking(Entity entity) {
		EntityTrackerRef.startTracking(handle, entity);
	}

	/**
	 * Removes an entity from this entity tracker.
	 * This call will result in entity destroy packets being sent to nearby players.
	 * 
	 * @param entity to remove
	 */
	public void stopTracking(Entity entity) {
		EntityTrackerRef.stopTracking(handle, entity);
	}

	/**
	 * Sets the entity tracker entry for an entity
	 * 
	 * @param entity to set the tracker entry for
	 * @param entityTrackerEntry to set to
	 * @return previously set entity tracker entry, null if there was none
	 */
	public Object setEntry(Entity entity, Object entityTrackerEntry) {
		return EntityTrackerRef.setEntry(handle, entity, entityTrackerEntry);
	}

	/**
	 * Gets the entity tracker entry of an entity
	 * 
	 * @param entity to get the entry of
	 * @return entity tracker entry
	 */
	public Object getEntry(Entity entity) {
		return EntityTrackerRef.getEntry(handle, entity);
	}
}
