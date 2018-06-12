package com.locydragon.anv.api.util.maths;


import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class Point2D {
	public static Set<Location> point2D(Location location1, Location location2) {
		Set<Location> returnType = new HashSet<>();
		returnType.add(location1);
		returnType.add(location2);
		if (location1.getY() != location2.getY()) {
			throw new IllegalArgumentException("Not a Point 2D arg.");
		}
		if (location1.distance(location2) <= 1) {
			return returnType;
		} else {
			if (Math.abs(location1.getBlockX() - location2.getBlockX())
					> Math.abs(location1.getBlockZ() - location2.getBlockZ())) {
				if (location1.getBlockX() > location2.getBlockX()) {
					for (int i = location2.getBlockX();i < location1.getBlockX();i++) {
						double y = x_point2D(location2.getBlockX(), location1.getBlockX()
								, location2.getBlockY(), location1.getBlockY(), i);
						Location newLocation = new Location(location1.getWorld(), (double)i, location1.getBlockY(), y);
						returnType.add(newLocation);
						return returnType;
					}
				} else {
					for (int i = location1.getBlockX();i < location2.getBlockX();i++) {
						double y = x_point2D(location2.getBlockX(), location1.getBlockX()
								, location2.getBlockY(), location1.getBlockY(), i);
						Location newLocation = new Location(location1.getWorld(), (double)i, location1.getBlockY(), y);
						returnType.add(newLocation);
						return returnType;
					}
				}
			} else {
				if (location1.getBlockZ() > location2.getBlockZ()) {
					for (int z = location2.getBlockZ();z < location1.getBlockZ();z++) {
						double x = y_point2D(location2.getBlockX(), location1.getBlockX()
								, location2.getBlockY(), location1.getBlockY(),z);
						Location newLocation = new Location(location1.getWorld(), x, location1.getBlockY(), (double)z);
						returnType.add(newLocation);
						return returnType;
					}
				} else {
					for (int z = location1.getBlockZ();z < location2.getBlockZ();z++) {
						double x = y_point2D(location2.getBlockX(), location1.getBlockX()
								, location2.getBlockY(), location1.getBlockY(),z);
						Location newLocation = new Location(location1.getWorld(), x, location1.getBlockY(), (double)z);
						returnType.add(newLocation);
						return returnType;
					}
				}
			}
		}
		return returnType;
	}
	public static double x_point2D(int x1, int x2, int y1, int y2, int x) {
		return (x - x2)/(x1 - x2)*(y1 - y2)+y2;
	}
	public static double y_point2D(int x1, int x2, int y1, int y2, int y) {
		return (y - y2)/(y1 - y2)*(x1 - x2)+x2;
	}
}
