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

	/**
	 * 获取任务类型
	 * @return 任务的类型
	 */
	public String getJobType() {
		return this.jobType;
	}

	/**
	 * 获取职业的描述
	 * @return 职业的描述
	 */
	public String[] getJobArgs() {
		return this.jobArgs;
	}

	/**
	 * 序列化
	 * @return 序列化的String
	 */
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

	/**
	 * 反序列化
	 * @param magicValue 序列化的String
	 * @return 对象实例
	 */
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
