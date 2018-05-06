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
	public AnimationObject setName(String animationName) {
		this.animationName = animationName;
		return this;
	}
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
	public List<AnimationJob> getJobList() {
		return this.jobList;
	}
	public AnimationObject addJob(AnimationJob job) {
		this.jobList.add(job);
		reloadSettings();
		return this;
	}
	public AnimationObject addJob(AnimationJob job, int index) {
		this.jobList.add(index, job);
		reloadSettings();
		return this;
	}
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
