package com.example.lms.course.service;

import java.util.List;

import com.example.lms.course.dto.CourseDto;
import com.example.lms.course.model.CourseInput;
import com.example.lms.course.model.CourseParam;
import com.example.lms.course.model.ServiceResult;
import com.example.lms.course.model.TakeCourseInput;

public interface CourseService {

	/*
	 * 강좌 등록
	 */
	boolean add(CourseInput courseInput);

	/*
	 * 강좌 정보 수정
	 */
	boolean set(CourseInput courseInput);
	
	/*
	 * 강좌 목록
	 */
	List<CourseDto> list(CourseParam courseParam);

	/*
	 * 강좌 상세 정보
	 */
	CourseDto getById(long id);


	/*
	 * 강좌 내용 삭제
	 */
	boolean del(String idList);

	/*
	 * 프론트 강좌 목록
	 */
	List<CourseDto> frontList(CourseParam courseParam);

	/*
	 * 프론트 강좌 상세 정보
	 */
	CourseDto frontDetail(long id);

	/*
	 * 수강 신청
	 */
	ServiceResult req(TakeCourseInput takeCourseInput);

	/*
	 * 전체 강좌 목록
	 */
	List<CourseDto> listAll();


}
