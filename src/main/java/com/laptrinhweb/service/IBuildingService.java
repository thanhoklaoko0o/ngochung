package com.laptrinhweb.service;

import java.util.List;

import com.laptrinhweb.builder.BuildingSearchBuilder;
import com.laptrinhweb.dto.BuildingDTO;
import com.laptrinhweb.paging.Pageble;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO newBuilding);
	void update(BuildingDTO updateBuilding,long id);
	List<BuildingDTO> findAll(BuildingSearchBuilder builder ,Pageble pageble);
	BuildingDTO findById(long id);
	void delete(Long[] ids);
	
	int getTotalItem(BuildingSearchBuilder builder);
}
