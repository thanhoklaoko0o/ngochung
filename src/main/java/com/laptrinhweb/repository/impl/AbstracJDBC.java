package com.laptrinhweb.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Table;
import com.laptrinhweb.mapper.ResultSetMapper;
import com.laptrinhweb.paging.Pageble;
import com.laptrinhweb.paging.Sorter;
import com.laptrinhweb.repository.GenericJDBC;

public class AbstracJDBC<T> implements GenericJDBC<T>{
	
	private Class<T> zClass;
	@SuppressWarnings("unchecked")
	public AbstracJDBC() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
 	}
	
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // com.mysql.jdbc.Driver com.mysql.cj.jdbc.Driver
			String databaseURL = "jdbc:mysql://localhost:3306/estate2019";
			String user = "root";
			String password = "sa123456";
			return DriverManager.getConnection(databaseURL, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryJavaReflect(String sql,  Object... parameters) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {
			if (conn != null) {
				return resultSetMapper.mapRow(rs, this.zClass);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void update(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			// set commit = false
			conn.setAutoCommit(false);
			// return genertaed_keys
			statement = conn.prepareStatement(sql);
			if (conn != null) {
				// set parameter sang statement
				for (int i = 0; i < parameters.length; i++) {
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}
				statement.executeUpdate();
				// resultset lay ve generated ke
				// commit
				conn.commit();

			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
	}

	@SuppressWarnings("static-access")
	@Override
	public Long insertJDBC(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// set commit = false
			conn.setAutoCommit(false);
			// return genertaed_keys
			statement = conn.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				// set parameter sang statement
				for (int i = 0; i < parameters.length; i++) {
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}
				int rowindex = statement.executeUpdate();
				// resultset lay ve generated ke
				rs = statement.getGeneratedKeys();
				// commit
				conn.commit();
				if (rowindex > 0) {
					while (rs.next()) {
						long id = rs.getLong(1);
						return id;
					}
				}
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public Long insertJDBC(Object object) {
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// set commit = false
			conn.setAutoCommit(false);
			
			String sql = createSQLInsert();
			// return genertaed_keys
			statement = conn.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				Class<?> zClass = object.getClass();
				
				// set parameter sang statement				
				Field[] fields =zClass.getDeclaredFields();				
				for (int i = 0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					//quyen truy capk	
					field.setAccessible(true);
					statement.setObject(index, field.get(object));
				}
				// duyet them parent de lay gia tri
				Class<?> parentClass=zClass.getSuperclass();
				int indexParent=fields.length+1;
				while(parentClass !=null) {					
					//logic convert data
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						
						Field field = parentClass.getDeclaredFields()[i];
						//quyen truy cap
						field.setAccessible(true);
						statement.setObject(indexParent, field.get(object));
						indexParent=indexParent+1;
					}
					
					parentClass=parentClass.getSuperclass();
				}
				int rowindex = statement.executeUpdate();
				// resultset lay ve generated ke
				rs = statement.getGeneratedKeys();
				// commit
				conn.commit();
				if (rowindex > 0) {
					while (rs.next()) {
						long id = rs.getLong(1);
						return id;
					}
				}
			}

		} catch (SQLException | IllegalAccessException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	//		e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private String createSQLInsert() {
		String tableName="";
		if(zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName=table.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");
		for(Field field :zClass.getDeclaredFields()) {
			if(fields.length()>1) {
				fields.append(",");
				params.append(",");
			}
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);				
				fields.append(column.name());
				params.append("?");
				
			}
		}
		// check parent class
		Class<?> parentClass=zClass.getSuperclass();
		while(parentClass !=null) {
			
			//logic convert data
			for(Field field :parentClass.getDeclaredFields()) {
				if(fields.length()>1) {
					fields.append(",");
					params.append(",");
				}
				if(field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);		
					fields.append(column.name());
					params.append("?");
					
					
				}
			}
			
			parentClass=parentClass.getSuperclass();
		}
		String sql="INSERT INTO "+tableName+"("+fields.toString()+") VALUES("+params.toString()+")";
		return sql;
	}

	@Override
	public void update(Object object ) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			// set commit = false
			conn.setAutoCommit(false);
			
			String sql = createSQLUpdate();
			// return genertaed_keys
			statement = conn.prepareStatement(sql);
			if (conn != null) {
				Class<?> zClass = object.getClass();				
				// set parameter sang statement				
				Field[] fields =zClass.getDeclaredFields();				
				for (int i = 0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					//quyen truy capk	
					field.setAccessible(true);
					statement.setObject(index, field.get(object));
				}
				// duyet them parent de lay gia tri
				Class<?> parentClass=zClass.getSuperclass();
				int indexParent=fields.length+1;
				Object id =null;
				while(parentClass !=null) {					
					//logic convert data
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						
						Field field = parentClass.getDeclaredFields()[i];
						//quyen truy cap
						field.setAccessible(true);
						String name = field.getName();
						if(!name.equals("id")) {
							statement.setObject(indexParent, field.get(object));
							indexParent=indexParent+1;
						}else {
							id= field.get(object);
						}
						
					}
					
					parentClass=parentClass.getSuperclass();
				}
				statement.setObject(indexParent, id);
				 statement.executeUpdate();
			
				// commit
				conn.commit();
				
			}

		} catch (SQLException | IllegalAccessException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	//		e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		
	}

	private String createSQLUpdate() {
		String tableName="";
		if(zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName=table.name();
		}
		
		StringBuilder sets = new StringBuilder("");
		String where =null;
		for(Field field :zClass.getDeclaredFields()) {
			
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);	
				String coluomnName=column.name();
				String value=coluomnName + "=?";
				if(!coluomnName.equals("id")) {
					if(sets.length()>1) {
						sets.append(", ");
					}
					sets.append(value);
				}
								
			}
		}
		// check parent class
		Class<?> parentClass=zClass.getSuperclass();
		while(parentClass !=null) {
			//logic convert data
			for(Field field :parentClass.getDeclaredFields()) {
				
				if(field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);	
					String coluomnName=column.name();
					String value=coluomnName + "=?";
					if(!coluomnName.equals("id")) {
						if(sets.length()>1) {
							sets.append(", ");
						}
						sets.append(value);
					}else {
						where="WHERE"+ value;
					}
									
				}
			}
			
			parentClass=parentClass.getSuperclass();
		}
		String sql="UPDATE "+tableName+" SET "+sets.toString() + where;
		return sql;
	}

	@Override
	public List<T> findAll(Map<String, Object> properties,Pageble pageble,Object... where) {
		Connection conn=null;
		Statement statement = null;
		ResultSet rs = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		
		StringBuilder sql=createSQLfindAll(properties);
		if(where != null && where.length > 0) {
			sql.append(where[0]) ;
		}
		if(pageble !=null) {
			
			if(pageble.getSorter() != null) {
				Sorter sorter = pageble.getSorter();
				sql.append(" ORDER BY " +sorter.getSortName()+" "+sorter.getSortBy()+"");
			}
			if(pageble.getOffset() != null && pageble.getLimit() != null) {
				sql.append(" LIMIT " +pageble.getOffset()+", "+pageble.getLimit()+"");
			}
			
		}
		try {
				 conn = getConnection();
				 statement = conn.createStatement();
				 rs = statement.executeQuery(sql.toString());
			if (conn != null) {
				return resultSetMapper.mapRow(rs, this.zClass);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return new ArrayList<>();
	}


	private StringBuilder createSQLfindAll(Map<String, Object> properties) {
		
		String tableName="";
		if(zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName=table.name();
		}
		
		StringBuilder result = new StringBuilder("SELECT * FROM "+tableName+" A WHERE 1=1");
		if(properties != null && properties.size() > 0) {
			String[] params = new String [properties.size()];
			Object[] values = new Object[properties.size()];
			int i = 0;
			for(Map.Entry<?,?> item : properties.entrySet()) {
				params[i]=(String) item.getKey();
				values[i] = item.getValue();
				i++;
				
			}
			for(int i1=0;i1<params.length;i1++) {
			//	String temp = "%"+values[i1].toString().toLowerCase()+"%";
				if(values[i1] instanceof String) {
					result.append(" and LOWER("+params[i1]+") LIKE '%"+values[i1].toString().toLowerCase()+"%' ");
				//	result.append(" and LOWER("+params[i1]+") LIKE " + temp);
				} else if ( values[i1] instanceof Integer) {
					result.append(" and "+params[i1]+ " = "+ values[i1]+ " " );
				}else if ( values[i1] instanceof Long) {
					result.append(" and "+params[i1]+ " = "+ values[i1]+ " " );
				}
				
			}
		}
		return result;
	}

	@Override
	public void deleteByProperty(String where) {
		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = getConnection();
			// set commit = false
			conn.setAutoCommit(false);
			
			String tableName="";
			if(zClass.isAnnotationPresent(Table.class)) {
				Table table = zClass.getAnnotation(Table.class);
				tableName=table.name();
			}
			
			String sql = "DELETE FROM "+tableName+" "+ where;
			// return genertaed_keys
			statement = conn.createStatement();
			if (conn != null) {
				 statement.executeQuery(sql);
				 conn.commit();
				
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	//		e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
	}

	@Override
	public <T> T findById(long id) {
	Connection conn=null;
	PreparedStatement statement = null;
	ResultSet rs = null;
	ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
	
	String tableName="";
	if(zClass.isAnnotationPresent(Table.class)) {
		Table table = zClass.getAnnotation(Table.class);
		tableName=table.name();
	}
	
	String sql="SELECT * FROM "+ tableName+" WHERE id = ?";
	try {
			 conn = getConnection();
			 statement = conn.prepareStatement(sql);
			 statement.setObject(1, id);
			 rs = statement.executeQuery();
		if (conn != null) {
			return resultSetMapper.mapRow(rs, this.zClass).get(0);
		}
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}finally {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
		return null;
	}

	@Override
	public void delete(long id) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			// set commit = false
			conn.setAutoCommit(false);
			
			String tableName="";
			if(zClass.isAnnotationPresent(Table.class)) {
				Table table = zClass.getAnnotation(Table.class);
				tableName=table.name();
			}
			
			String sql = "DELETE FROM "+tableName+" WHERE id = ?";
			// return genertaed_keys
			statement = conn.prepareStatement(sql);
			if (conn != null) {
						
				 statement.setObject(1, id);
				 statement.executeUpdate();
				// commit
				conn.commit();
				
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	//		e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		
	}

	@Override
	public int countByProperty(Map<String, Object> properties,Object... where) {
		Connection conn=null;
		Statement statement = null;
		ResultSet rs = null;
		
		StringBuilder sql=createSQLCountByProperty(properties);
		if(where != null && where.length > 0) {
			sql.append(where[0]) ;
		}
		
		try {
				 conn = getConnection();
				 statement = conn.createStatement();
				 rs = statement.executeQuery(sql.toString());
			if (conn != null) {
				while(rs.next()) {
					return rs.getInt("COUNT(*)");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	private StringBuilder createSQLCountByProperty(Map<String, Object> properties) {
		String tableName="";
		if(zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName=table.name();
		}
		
		StringBuilder result = new StringBuilder("SELECT COUNT(*) FROM "+tableName+" A WHERE 1=1");
		if(properties != null && properties.size() > 0) {
			String[] params = new String [properties.size()];
			Object[] values = new Object[properties.size()];
			int i = 0;
			for(Map.Entry<?,?> item : properties.entrySet()) {
				params[i]=(String) item.getKey();
				values[i] = item.getValue();
				i++;
				
			}
			for(int i1=0;i1<params.length;i1++) {
			//	String temp = "%"+values[i1].toString().toLowerCase()+"%";
				if(values[i1] instanceof String) {
					result.append(" and LOWER("+params[i1]+") LIKE '%"+values[i1].toString().toLowerCase()+"%' ");
				//	result.append(" and LOWER("+params[i1]+") LIKE " + temp);
				} else if ( values[i1] instanceof Integer) {
					result.append(" and "+params[i1]+ " = "+ values[i1]+ " " );
				}else if ( values[i1] instanceof Long) {
					result.append(" and "+params[i1]+ " = "+ values[i1]+ " " );
				}
				
			}
		}
		return result;
		
	}

	
	}
	

