package com.locydragon.anv.core.tree;

import com.locydragon.anv.AnimationView;
import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.api.util.AnimationJob;
import com.locydragon.anv.api.util.AnimationObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class AnimationResolver {
	public static Vector<String> runningPlayers = new Vector<>();
public static ReentrantLock lock = new ReentrantLock();
public static boolean resolveAnimation(AnimationObject object, Player who) {
	if (object == null || who == null) {
		throw new NullPointerException("Null args");
	}
	for (String user : runningPlayers) {
		if (user.equalsIgnoreCase(who.getName())) {
			return false;
		}
	}
	Thread resolveThread = new Thread(new Runnable() {
		@Override
		public void run() {
			lock.lock();

			runningPlayers.add(who.getName());
			for (AnimationJob job : object.getJobList()) {
				if (job.getJobType().equalsIgnoreCase("等待")) {
					try {
						Thread.sleep(Integer.valueOf(job.getJobArgs()[0]) * 1000);
					} catch (InterruptedException exc) {
						exc.printStackTrace();
					} finally {
						continue;
					}
				}
				Bukkit.getScheduler().runTask(AnimationView.getInstance(), new Runnable() {
					@Override
					public void run() {
						Bukkit.getPluginManager().callEvent(new AnimationResolveEvent(who, object, job));
					}
				});
			}
			for (int i = 0;i < runningPlayers.size();i++) {
				if (runningPlayers.get(i).equalsIgnoreCase(who.getName())) {
					runningPlayers.remove(i);
				}
			}

			lock.unlock();
		}
	});
	resolveThread.start();
	return true;
}
}
