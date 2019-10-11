package com.laptrinhjavaweb.buildersearch;

public class CustomerSearchBuilder {
	private String name;
	private String phone;
	private String email;
	private Long staffId;
	
	
	
	public CustomerSearchBuilder(Builder build) {
		this.name = build.name;
		this.phone = build.phone;
		this.email = build.email;
		this.staffId = build.staffId;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public long getStaffId() {
		return staffId;
	}
	
	public static class Builder{
		private String name;
		private String phone;
		private String email;
		private Long staffId;
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		public Builder setStaffId(Long staffId) {
			this.staffId = staffId;
			return this;
		}
		public CustomerSearchBuilder build()
		{
			return new CustomerSearchBuilder(this);
		}
		
	}
}
