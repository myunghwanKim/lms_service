package com.example.lms.course.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.lms.course.domain.Course;
import com.example.lms.course.dto.CourseDto;
import com.example.lms.course.mapper.CourseMapper;
import com.example.lms.course.model.CourseInput;
import com.example.lms.course.model.CourseParam;
import com.example.lms.course.repository.CourseRepository;
import com.example.lms.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	
	private LocalDate getLocalDate(String value) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		try {
			return LocalDate.parse(value, formatter);
		} catch (Exception e) {

		}
		
		return null;
	}
	
	@Override
	public boolean add(CourseInput courseInput) {
		
		LocalDate saleEndAt = getLocalDate(courseInput.getSaleEndAtText());
			
		Course course = Course.builder()
							.categoryId(courseInput.getCategoryId())
							.subject(courseInput.getSubject())
							.keyword(courseInput.getKeyword())
							.summary(courseInput.getSummary())
							.content(courseInput.getContent())
							.price(courseInput.getPrice())
							.salePrice(courseInput.getSalePrice())
							.saleEndAt(saleEndAt)
							.createAt(LocalDateTime.now())
							.build();
		courseRepository.save(course);
		
		return true;
	}
	
	@Override
	public boolean set(CourseInput courseInput) {
		
		LocalDate saleEndAt = getLocalDate(courseInput.getSaleEndAtText());
		
		Optional<Course> optionalCourse = courseRepository.findById(courseInput.getId());
		if (!optionalCourse.isPresent()) {
			return false;
		}
		
		Course course = optionalCourse.get();
		course.setCategoryId(courseInput.getCategoryId());
		course.setSubject(courseInput.getSubject());
		course.setKeyword(courseInput.getKeyword());
		course.setSummary(courseInput.getSummary());
		course.setContent(courseInput.getContent());
		course.setPrice(courseInput.getPrice());
		course.setSalePrice(courseInput.getSalePrice());
		course.setSaleEndAt(saleEndAt);
		course.setUpdateAt(LocalDateTime.now());
		courseRepository.save(course);
		
		return true;
		}

	@Override
	public List<CourseDto> list(CourseParam courseParam) {

		long totalCount = courseMapper.selectListCount(courseParam);
		List<CourseDto> list = courseMapper.selectList(courseParam);
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for(CourseDto x: list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - courseParam.getPageStart() - i);
				i++;
			}
		}
		return list;
	}

	@Override
	public CourseDto getById(long id) {
		
		return courseRepository.findById(id).map(CourseDto::of).orElse(null);
	}
}
