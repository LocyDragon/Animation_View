package com.locydragon.anv.api.events;

import com.locydragon.anv.api.util.AnimationJob;
import com.locydragon.anv.api.util.AnimationObject;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AnimationResolveEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
    private AnimationObject animation = null;
    private AnimationJob thisJob = null;

	public AnimationResolveEvent(final Player player, AnimationObject animation, AnimationJob job) {
		this.player = player;
		this.animation = animation;
		this.thisJob = job;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public AnimationJob getThisJob() {
		return thisJob;
	}

	public AnimationObject getAnimation() {
		return animation;
	}
}

