package com.laptrinhjavaweb.builder;

public class BuilderBuilding {
	private String name;
	private String district;
	private Integer buildingArea;
	private Integer numberOfBasement;
	private String type ;
	
	private BuilderBuilding(Builder builder)
	{
		this.name = builder.name;
		this.district = builder.district;
		this.buildingArea = builder.buildingarea;
		this.numberOfBasement = builder.numberofbasement;
		this.type = builder.type;
	}
	public String getName() {
		return name;
	}
	public String getDistrict() {
		return district;
	}
	public Integer getBuildingarea() {
		return buildingArea;
	}
	public Integer getNumberofbasement() {
		return numberOfBasement;
	}
	public String getType() {
		return type;
	}
	public static class Builder{
		private String name;
		private String district;
		private Integer buildingarea;
		private Integer numberofbasement;
		private String type;
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setDistrict(String district) {
			this.district = district;
			return this;
		}
		public Builder setBuildingarea(int buildingarea) {
			this.buildingarea = buildingarea;
			return this;
		}
		public Builder setNumberofbasement(int numberofbasement) {
			this.numberofbasement = numberofbasement;
			return this;
		}
		public Builder setType(String type) {
			this.type = type;
			return this;
		}
		public BuilderBuilding build(){
			return new BuilderBuilding(this);
		}
	}
}
