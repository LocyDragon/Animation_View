package com.locydragon.anv.api;

import com.locydragon.anv.api.util.AnimationJob;
import com.locydragon.anv.api.util.AnimationObject;
import com.locydragon.anv.core.AnimationLand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationViewAPI {
	public static HashMap<String,AnimationObject> objectAche = new HashMap<>();
	/**
	 *根据动画名称获取对象
	 * @param animationName 动画名称
	 * @return 动画对象
	 */
	public static AnimationObject getAnimationObject(String animationName) {
		if (AnimationLand.land.getBoolean(animationName+"."+"created", false) == false) {
			return null;
		}
		if (objectAche.containsKey(animationName)) { //先从缓存中寻找
			return objectAche.get(animationName);
		} else {
			AnimationObject object = AnimationObject.emptyObject();
			object.setName(animationName);
			List<String> jobStringList = AnimationLand.land.getStringList(animationName+".jobs");
			for (String eachJob : jobStringList) {
				object.addJob(AnimationJob.toAnimationJob(eachJob));
			}
			objectAche.put(animationName, object);
			return object;
		}
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

	/**
	 * 获取在这个服务器内所有已经创建的动画对象
	 * @return 动画对象的名字集合
	 */
	public static List<String> objectList() {
    	List<String> list = new ArrayList<>();
    	for (String each : AnimationLand.land.getKeys(false)) {
    		list.add(each);
		}
		return list;
	}
}
