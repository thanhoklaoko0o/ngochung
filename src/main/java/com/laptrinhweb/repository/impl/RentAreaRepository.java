package com.laptrinhweb.repository.impl;

import com.laptrinhweb.entity.RentAreaEntity;
import com.laptrinhweb.repository.IRentAreaRepository;

public class RentAreaRepository extends AbstracJDBC<RentAreaEntity> implements IRentAreaRepository{

	@Override
	public void deleteByBuilding(long id) {
		String where = "WHERE buildingid = "+id+"";
		this.deleteByProperty(where);
	}

	

}
