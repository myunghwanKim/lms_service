package com.example.lms.course.model;

import com.example.lms.admin.model.CommonParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseParam extends CommonParam {

	long id; // course.id
	long categoryId;
}
