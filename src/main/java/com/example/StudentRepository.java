package com.example;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class StudentRepository implements PanacheRepositoryBase<Student, String> {

    public Optional<Student> findByNimOptional(String nim) {
        return find("nim", nim).firstResultOptional();
    }
}
