package com.example;

import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface StudentMapper {

    Student toEntity(StudentDto domain);

    StudentDto toDomain(Student entity);

}
