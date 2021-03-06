package com.example.lms.admin.service;

import java.util.List;

import com.example.lms.admin.dto.CategoryDto;
import com.example.lms.admin.model.CategoryInput;

public interface CategoryService {

	List<CategoryDto> list();
	
	/*
	 * 카테고리 신규 추가
	 */
	boolean add(String categoryName);
	
	/*
	 * 카테고리 수정
	 */
	boolean update(CategoryInput categoryInput);
	
	/*
	 * 카테고리 삭제
	 */
	boolean del(long id);
	
	/*
	 * 프론트 카테고리 정보
	 */
	List<CategoryDto> frontList(CategoryDto categoryDto);
}
