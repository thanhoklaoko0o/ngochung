package com.laptrinhweb.entity;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Entity;
import com.laptrinhweb.annotation.Table;

@Entity
@Table(name="rentarea")
public class RentAreaEntity extends BaseEntity {
	
	@Column(name="value")
	private String value;
	
	@Column(name="buildingid")
	private Long buildingId;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getBuildingid() {
		return buildingId;
	}

	public void setBuildingid(Long buildingid) {
		this.buildingId = buildingid;
	}
	
	
	
	
}
