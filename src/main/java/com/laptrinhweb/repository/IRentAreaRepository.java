package com.laptrinhweb.repository;

import com.laptrinhweb.entity.RentAreaEntity;

public interface IRentAreaRepository extends GenericJDBC<RentAreaEntity>{
	void deleteByBuilding(long id);
}
