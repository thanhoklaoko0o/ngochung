package com.laptrinhweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhweb.paging.Pageble;

public interface GenericJDBC<T> {
	  List<T> queryJavaReflect(String sql, Object... parameters) ;
	  void update(String sql, Object... parameters) ;
	  Long insertJDBC(String sql, Object... parameters);
	  
	  
	  
	  Long insertJDBC(Object object);
	  void update (Object object );
	  List<T> findAll(Map<String, Object> properties,Pageble pageble, Object... where);
	  void delete(long id);
	  void deleteByProperty(String where);
	 
	  <T> T findById(long id);
	  
	  int countByProperty(Map<String, Object> properties,Object... where);
	  
	
}
