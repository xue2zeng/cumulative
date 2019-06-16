package com.xspace.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
	int id;
	String name;
	double height;
	double score;
}
