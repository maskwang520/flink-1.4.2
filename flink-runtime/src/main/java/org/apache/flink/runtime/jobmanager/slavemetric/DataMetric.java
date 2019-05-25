package org.apache.flink.runtime.jobmanager.slavemetric;

/**
 * Created by Maskwang on 2019/1/2.
 */
public class DataMetric {

	private String ipAddr;
	private float cpuData;
	private float memData;
	private float weight;
	private float currentWeight;
	private float effectiveWeight;

	public float getCpuData() {
		return cpuData;
	}

	public void setCpuData(float cpuData) {
		this.cpuData = cpuData;
	}

	public float getMemData() {
		return memData;
	}

	public void setMemData(float memData) {
		this.memData = memData;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(float currentWeight) {
		this.currentWeight = currentWeight;
	}

	public float getEffectiveWeight() {
		return effectiveWeight;
	}

	public void setEffectiveWeight(float effectiveWeight) {
		this.effectiveWeight = effectiveWeight;
	}

	public DataMetric(String ipAddr, float cpuData, float memData) {
		this.ipAddr = ipAddr;
		this.cpuData = cpuData;
		this.memData = memData;
	}

	public DataMetric(MetricBuilder builder) {

		this.ipAddr = builder.ipAddr;
		this.memData = builder.memData;
		this.cpuData = builder.cpuData;
		this.currentWeight = builder.currentWeight;
		this.effectiveWeight = builder.effectiveWeight;
		this.weight = builder.weight;
	}

	/**
	 * Builder pattern to construct Metric
	 */
	public static class MetricBuilder {

		private String ipAddr;
		private float cpuData;
		private float memData;
		private float weight;
		private float currentWeight;
		private float effectiveWeight;

		public MetricBuilder(String ipAddr, float cpuData, float memData) {

			this.ipAddr = ipAddr;
			this.cpuData = cpuData;
			this.memData = memData;
		}

		public MetricBuilder weight(float weight) {

			this.weight = weight;
			return this;
		}

		public MetricBuilder currentWeight(float currentWeight) {

			this.currentWeight = currentWeight;
			return this;
		}

		public MetricBuilder effectWeight(float effectiveWeight) {

			this.effectiveWeight = effectiveWeight;
			return this;
		}

		public DataMetric build() {

			return new DataMetric(this);
		}

	}


}
