package com.laptrinhweb.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.laptrinhweb.builder.BuildingSearchBuilder;
import com.laptrinhweb.converter.BuildingConverter;
import com.laptrinhweb.dto.BuildingDTO;
import com.laptrinhweb.entity.BuildingEntity;
import com.laptrinhweb.entity.RentAreaEntity;
import com.laptrinhweb.paging.Pageble;
import com.laptrinhweb.repository.IBuildingRepository;
import com.laptrinhweb.repository.IRentAreaRepository;
import com.laptrinhweb.repository.impl.BuildingRepository;
import com.laptrinhweb.repository.impl.RentAreaRepository;
import com.laptrinhweb.service.IBuildingService;

public class BuildingService implements IBuildingService {

	//@Inject
	private  IBuildingRepository buildingRepository=new BuildingRepository();
	
	// @Inject
	private  BuildingConverter buildingConverter = new BuildingConverter();
	
//	@Inject
	private IRentAreaRepository rentAreaRepository = new RentAreaRepository();

	 
	@Override
	public BuildingDTO save(BuildingDTO buildingDTO) {
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		buildingEntity.setCreatedBy("");
		//chuyen tu mang thanh chuoi 
		
		buildingEntity.setType(StringUtils.join(buildingDTO.getBuildingTypes(), ","));
		Long id = buildingRepository.insertJDBC(buildingEntity);
		
		
		
		// save rent area
		for(String item : buildingDTO.getRentArea().split(",")) {
			RentAreaEntity rentArea = new RentAreaEntity();
			rentArea.setValue(item);
			rentArea.setBuildingid(id);
			rentAreaRepository.insertJDBC(rentArea);
		}
		
		return buildingConverter.convertToDTO(buildingRepository.findById(id));
	}
	
	  /*public BuildingDTO update(BuildingDTO buildingDTO,long id) { BuildingConverter
	  buildingConverter = new BuildingConverter(); BuildingEntity buildingEntity =
	  buildingConverter.convertToEntity(buildingDTO);
	  buildingEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
	  buildingRepository.update(buildingEntity); 
	  
	  return null;
	  
	  }*/
	 

	@Override
	public List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageble pageble) {
		
	/*	Map<String, Object> properties = buildMapSearch(builder);
		String whereClause="";*/
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(builder, pageble);
		List<BuildingDTO> result = buildingEntities.stream().map(item -> buildingConverter.convertToDTO(item))
				.collect(Collectors.toList());
		/*for(BuildingEntity item : buildingEntities) {
			BuildingDTO buildingDTO = buildingConverter.convertToDTO(item);
			result.add(buildingDTO);
		}*/
		
		return result;
	}

	private Map<String, Object> buildMapSearch(BuildingSearchBuilder builder) {
		Map<String, Object> result = new HashMap<>();
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field field : fields) {
				if(!field.getName().equals("buildingTypes") &&  !field.getName().startsWith("costRent") && 
						!field.getName().startsWith("areaRent")) {
					field.setAccessible(true);
					if(field.get(builder) != null) {
						result.put(field.getName().toLowerCase(), field.get(builder) );
					}
						
				}
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}		
		return result;
		
	}

	private Object getValue(Field field, BuildingSearchBuilder builder) {
		Object result = null;
		Method getter = getGetter(field, builder);
		if (getter != null) {
			try {
				result = getter.invoke(builder);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return result;
	}

	private Method getGetter(Field field, BuildingSearchBuilder builder) {

		String getterMethod = "get" + StringUtils.capitalize(field.getName());

		try {
			return builder.getClass().getMethod(getterMethod);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BuildingDTO findById(long id) {
		// TODO Auto-generated method stub
		return buildingConverter.convertToDTO(buildingRepository.findById(id));
	}

	@Override
	public void update(BuildingDTO updateBuilding, long id) {
		BuildingEntity oldBuilding = buildingRepository.findById(id);
		BuildingEntity newBuilding = buildingConverter.convertToEntity(updateBuilding);
		newBuilding.setCreatedBy(oldBuilding.getCreatedBy());
		newBuilding.setCreatedDate(oldBuilding.getCreatedDate());
		
		//rent area
		String rentArea = updateBuilding.getRentArea();
		updateRentArea(rentArea,id);
		
		//type
		newBuilding.setType(StringUtils.join(updateBuilding.getBuildingTypes(), ","));
		buildingRepository.update(newBuilding);
		
	}

	private void updateRentArea(String rentAreaStr, long buildingID) {
		//delete rent rentarea theo id
		rentAreaRepository.deleteByBuilding(buildingID);
		
		//insert rentarea 
		for(String item : rentAreaStr.split(",")) {
			RentAreaEntity rentArea= new RentAreaEntity();
			rentArea.setBuildingid(buildingID);
			rentArea.setValue(item);
			rentAreaRepository.insertJDBC(rentArea);
		}
		
	}

	@Override
	public void delete(Long[] ids) {
		for(long item : ids) {
			rentAreaRepository.deleteByBuilding(item);
			buildingRepository.delete(item);
		}
		
	}

	@Override
	public int getTotalItem(BuildingSearchBuilder builder) {
		
		return buildingRepository.countByProperty(builder);
	}

}
