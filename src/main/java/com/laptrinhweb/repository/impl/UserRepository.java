package com.laptrinhweb.repository.impl;

import com.laptrinhweb.entity.UserEntity;
import com.laptrinhweb.repository.IUserRepository;

public class UserRepository extends AbstracJDBC<UserEntity> implements IUserRepository{

	@Override
	public Long insert(UserEntity userEntity) {
		
		return null;
	}
	
}
