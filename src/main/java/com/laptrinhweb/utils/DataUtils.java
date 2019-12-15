package com.laptrinhweb.utils;

import java.util.HashMap;
import java.util.Map;

import com.laptrinhweb.enums.BuildingTypeEnum;
import com.laptrinhweb.enums.DistricEnum;

public class DataUtils {
	
	public static Map<String, String> getBuildingType(){
		Map<String, String> results = new HashMap<String, String>();
		
		for(BuildingTypeEnum item : BuildingTypeEnum.values()) {
			results.put(item.name(), item.getValue());
		}
		return results;
	}
	
	public static Map<String, String> getDistricts(){
		Map<String, String> results = new HashMap<String, String>();
		
		for(DistricEnum item : DistricEnum.values()) {
			results.put(item.name(), item.getValue());
		}
		return results;
	}
}
