package com.locydragon.anv.api.util;

public class AnimationJob {
	private String jobType = null;
	private String[] jobArgs = null;
	public AnimationJob(String jobType, String[] jobArgs) {
		if (jobType == null || jobArgs == null) {
			throw new NullPointerException("Null args");
		}
		this.jobArgs = jobArgs;
		if (jobType.contains(":")) {
			throw new IllegalArgumentException("Illegal char ':' in job Type.");
		}
		this.jobType = jobType;
		for (String each : jobArgs) {
			if (each.contains(",")) {
				throw new IllegalArgumentException("Illegal char ',' in job Args.");
			}
		}
	}

	public String getJobType() {
		return this.jobType;
	}

	public String[] getJobArgs() {
		return this.jobArgs;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.jobType);
		builder.append(":");
		for (String each : this.jobArgs) {
			builder.append(each);
			builder.append(",");
		}
		String toString = builder.toString();
		if (toString.endsWith(",")) {
			toString = toString.substring(0, toString.length()-1);
		}
		return toString;
	}
	public static AnimationJob toAnimationJob(String magicValue) {
		try {
			String[] splitT = magicValue.split(":");
			String jobType = splitT[0];
			String jobValue = splitT[1];
			String[] jobArgs = jobValue.split(",");
			return new AnimationJob(jobType, jobArgs);
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new IllegalArgumentException("Illegal value in method 'toAnimationJob()' !");
		}
	}
}
