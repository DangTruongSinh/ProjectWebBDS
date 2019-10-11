package com.laptrinhjavaweb.buildersearch;

import com.laptrinhjavaweb.anotation.Special;

public class BuildingSearchBuilder {
	private String name;
	private String district;
	private Integer buildingArea;
	private Integer numberOfBasement;
	private String ward;
	private String street;
	@Special
	private Long staffid;
	@Special
	private String builddingType[];
	@Special
	private Integer areaRentFrom;
	@Special
	private Integer areaRentTo;
	@Special
	private Integer costRentFrom;
	@Special
	private Integer costRentTo;
	
	private BuildingSearchBuilder(Builder builder)
	{
		this.name = builder.name;
		this.district = builder.district;
		this.buildingArea = builder.buildingarea;
		this.numberOfBasement = builder.numberofbasement;
		
		this.costRentFrom = builder.costRentfrom;
		this.costRentTo = builder.costRentTo;
		
		this.ward = builder.ward;
		this.street = builder.street;
		this.builddingType = builder.builddingType;
		this.areaRentFrom = builder.areaRentFrom;
		this.areaRentTo = builder.areaRentTo;
		this.staffid = builder.staffid;
	}
	
	
	
	
	public Long getStaffid() {
		return staffid;
	}




	public String[] getBuilddingType() {
		return builddingType;
	}




	public String getWard() {
		return ward;
	}


	

	public String getStreet() {
		return street;
	}




	public Integer getRentAreaFrom() {
		return areaRentFrom;
	}




	public Integer getRentAreaTo() {
		return areaRentTo;
	}




	public Integer getCostRentfrom() {
		return costRentFrom;
	}

	public Integer getCostRentTo() {
		return costRentTo;
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
	
	public static class Builder{
		private String name;
		private String district;
		private Integer buildingarea;
		private Integer numberofbasement;
		private Integer costRentfrom;
		private Integer costRentTo;
		private String ward;
		private String street;
		private String builddingType[];
		private Integer areaRentFrom;
		private Integer areaRentTo;
		private Long staffid;
		
		
		
		
		public Builder setStaffid(Long staffid) {
			this.staffid = staffid;
			return this;
		}
		public Builder setBuilddingType(String[] builddingType) {
			this.builddingType = builddingType;
			return this;
		}
		public Builder setAreaRentFrom(Integer rentAreaFrom) {
			this.areaRentFrom = rentAreaFrom;
			return this;
		}
		public Builder setAreaRentTo(Integer rentAreaTo) {
			this.areaRentTo = rentAreaTo;
			return this;
		}
		public Builder setWard(String ward) {
			this.ward = ward;
			return this;
		}
		public Builder setStreet(String street) {
			this.street = street;
			return this;
		}
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setDistrict(String district) {
			this.district = district;
			return this;
		}
		public Builder setBuildingarea(Integer buildingarea) {
			this.buildingarea = buildingarea;
			return this;
		}
		public Builder setNumberofbasement(Integer numberofbasement) {
			this.numberofbasement = numberofbasement;
			return this;
		}

		public BuildingSearchBuilder build(){
			return new BuildingSearchBuilder(this);
		}
		public Builder setCostRentfrom(Integer costRentfrom) {
			this.costRentfrom = costRentfrom;
			return this;
		}
		public Builder setCostRentTo(Integer costRentTo) {
			this.costRentTo = costRentTo;
			return this;
		}
		
	}
}
