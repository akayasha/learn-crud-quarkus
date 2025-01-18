package com.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentService {

    private final StudentRepository studentRepository;

    @Inject
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> findAll() {
        return studentRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<StudentDto> findByNim(String nim) {
        return studentRepository.findByNimOptional(nim)
                .map(this::toDomain);
    }

    public  Optional<StudentDto> findByClasses(String classes) {
        return studentRepository.findByClass(classes)
                .map(this::toDomain);
    }

    @Transactional
    public StudentDto save(StudentDto student) {
        validateStudent(student);
        if (isDuplicate(student.getNim(), student.getEmail(), student.getPhoneNo())) {
            throw new ServiceException("Duplicate NIM, Email, or Phone Number found");
        }
        Student entity = toEntity(student);
        studentRepository.persist(entity);
        return toDomain(entity);
    }

    @Transactional
    public StudentDto update(StudentDto studentDto) {
        if (studentDto.getNim() == null) {
            throw new ServiceException("Not Found for Nim Student");
      }

        Optional<Student> optionalStudent = studentRepository.findByNimOptional(studentDto.getNim());
        if (optionalStudent.isEmpty()) {
            throw new ServiceException("No teacher found with NIP: " + studentDto.getNim());
        }

        if (isDuplicate(studentDto.getNim(), studentDto.getEmail(), studentDto.getPhoneNo())) {
            throw new ServiceException("Duplicate NIM, Email, or Phone Number found");
        }
        Student studentEntity = optionalStudent.get();
        studentEntity.setNim(studentDto.getNim());
        studentEntity.setName(studentDto.getName());
        studentEntity.setClasses(studentDto.getClasses());
        studentEntity.setDob(studentDto.getDob());
        studentEntity.setEmail(studentDto.getEmail());
        studentEntity.setPhoneNo(studentDto.getPhoneNo());
        return toDomain(studentEntity);
    }

    // Manual mapping from Student to StudentDto
    private StudentDto toDomain(Student student) {
        StudentDto dto = new StudentDto();
        dto.setNim(student.getNim());
        dto.setName(student.getName());
        dto.setClasses(student.getClasses());
        dto.setDob(student.getDob());
        dto.setPhoneNo(student.getPhoneNo());
        dto.setEmail(student.getEmail());
        return dto;
    }

    // Manual mapping from StudentDto to Student
    private Student toEntity(StudentDto dto) {
        Student entity = new Student();
        entity.setNim(dto.getNim());
        entity.setName(dto.getName());
        entity.setClasses(dto.getClasses());
        entity.setDob(dto.getDob());
        entity.setPhoneNo(dto.getPhoneNo());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public boolean deleteByNim(String nim) {
        Optional<Student> student = studentRepository.findByNimOptional(nim);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return true;
        }
        return false;
    }

    private void validateStudent(StudentDto student) {
        if (student.getNim() == null || student.getEmail() == null || student.getPhoneNo() == null) {
            throw new ServiceException("NIM, Email, and Phone Number must not be null");
        }
    }

    private boolean isDuplicate(String nim, String email, String phoneNo) {
        return studentRepository.find("nim = ?1 or email = ?2 or phoneNo = ?3", nim, email, phoneNo)
                .firstResultOptional()
                .isPresent();
    }
}