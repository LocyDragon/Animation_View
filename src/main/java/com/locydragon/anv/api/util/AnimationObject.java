package com.locydragon.anv.api.util;

import com.locydragon.anv.api.AnimationViewAPI;
import com.locydragon.anv.core.AnimationLand;
import com.locydragon.anv.core.tree.AnimationResolver;
import org.bukkit.entity.Player;

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
		List<AnimationJob> newList = new ArrayList<>();
		for (String obj : AnimationLand.land.getStringList(this.animationName+".jobs")) {
			newList.add(AnimationJob.toAnimationJob(obj));
		}
		this.jobList = newList;
		AnimationViewAPI.objectAche.replace(this.animationName, this); //更新缓存
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

	/**
	 * 判断动画对象是否有某个任务
	 * @param jobType 任务的标签
	 * @return 是否有这个任务
	 */
	public boolean hasJob(String jobType) {
		for (AnimationJob jobOnce : this.jobList) {
			if (jobOnce.getJobType().equals(jobType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据任务标签获取任务
	 * @param jobType 任务标签
	 * @return 任务对象集合
	 */
	public List<AnimationJob> getJobByType(String jobType) {
		List<AnimationJob> jobList = new ArrayList<>();
		for (AnimationJob jobOnce : this.jobList) {
			if (jobOnce.getJobType().equals(jobType)) {
				jobList.add(jobOnce);
			}
		}
		return jobList;
	}

	/**
	 * 给玩家播放这个动画组
	 * @param who 玩家对象
	 * @return 是否播放成功，如果玩家正在播放一个动画将不会成功.
	 */
	public boolean playFor(Player who) {
		return AnimationResolver.resolveAnimation(this, who);
	}
}
