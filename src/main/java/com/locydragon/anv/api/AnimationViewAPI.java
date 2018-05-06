package com.locydragon.anv.api;

import com.locydragon.anv.api.util.AnimationJob;
import com.locydragon.anv.api.util.AnimationObject;
import com.locydragon.anv.core.AnimationLand;

import java.util.List;

public class AnimationViewAPI {
	/**
	 *根据动画名称获取对象
	 * @param animationName 动画名称
	 * @return 动画对象
	 */
	public static AnimationObject getAnimationObject(String animationName) {
		if (AnimationLand.land.getBoolean(animationName+"."+"created", false) == false) {
			return null;
		}
		AnimationObject object = AnimationObject.emptyObject();
		object.setName(animationName);
		List<String> jobStringList = AnimationLand.land.getStringList(animationName+".jobs");
		for (String eachJob : jobStringList) {
			object.addJob(AnimationJob.toAnimationJob(eachJob));
		}
		return object;
	}

	/**
	 * 创建动画对象
	 * @param animationName 动画的名称
	 * @return 动画对象，如果无法创建或者已经存在就返回null
	 */
    public static AnimationObject createObject(String animationName) {
    	if (AnimationLand.land.getBoolean(animationName+"."+"created", false) == true) {
    		return null;
		}
		AnimationLand.land.set(animationName+"."+"created", true);
    	AnimationLand.reloadLand();
    	AnimationObject object = AnimationObject.emptyObject();
    	object.setName(animationName);
    	return object;
	}
}
