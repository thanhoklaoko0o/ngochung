package com.laptrinhweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhweb.dto.BuildingDTO;
import com.laptrinhweb.dto.UserDTO;
import com.laptrinhweb.entity.BuildingEntity;
import com.laptrinhweb.entity.UserEntity;

public class UserConverter {
	public UserDTO convertToDTO(UserEntity userEntity) {
		ModelMapper modelMapper = new ModelMapper();
		UserDTO result = modelMapper.map(userEntity, UserDTO.class);
		return result;
	}

	public UserEntity convertToEntity(UserDTO userDTO) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity result = modelMapper.map(userDTO, UserEntity.class);
		return result;
	}
}
