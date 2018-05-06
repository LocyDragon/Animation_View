package com.locydragon.anv.api.util;

import com.locydragon.anv.core.AnimationLand;

import java.util.ArrayList;
import java.util.List;

public class AnimationObject {
	private String animationName = null;
	private List<AnimationJob> jobList = new ArrayList<>();
	private AnimationObject() {}
    public static AnimationObject emptyObject() {
		return new AnimationObject();
	}

	/**
	 * 设置AnimationObject的名字
	 * @param animationName 对象名字
	 * @return
	 */
	public AnimationObject setName(String animationName) {
		this.animationName = animationName;
		return this;
	}

	/**
	 * 重载保存插件配置
	 * @return 是否重载保存成功
	 */
	public boolean reloadSettings() {
		AnimationLand.land.set(this.animationName+".created", true);
		List<String> dese = new ArrayList<>();
		for (AnimationJob job : this.jobList) {
			dese.add(job.toString());
		}
		AnimationLand.land.set(this.animationName+".jobs", dese);
		AnimationLand.reloadLand();
		return true;
	}

	/**
	 * 获取任务List
	 * @return 任务列
	 */
	public List<AnimationJob> getJobList() {
		return this.jobList;
	}

	/**
	 * 添加一个任务
	 * @param job 任务
	 * @return
	 */
	public AnimationObject addJob(AnimationJob job) {
		this.jobList.add(job);
		reloadSettings();
		return this;
	}

	/**
	 * 在指定位置添加一个任务
	 * @param job 任务
	 * @param index 位置
	 * @return
	 */
	public AnimationObject addJob(AnimationJob job, int index) {
		this.jobList.add(index, job);
		reloadSettings();
		return this;
	}

	/**
	 * 移除一个任务
	 * @param job 任务
	 * @return
	 */
	public AnimationObject removeJob(AnimationJob job) {
		List<AnimationJob> newJobList = new ArrayList<>();
		for (AnimationJob jobOnce : this.jobList) {
			if (jobOnce.getJobType().equalsIgnoreCase(job.getJobType())) {
				continue;
			}
			newJobList.add(jobOnce);
		}
		this.jobList = newJobList;
		reloadSettings();
		return this;
	}
}
