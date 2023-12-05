package com.spring.boot.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.spring.boot.application.model.UserDetails;

public interface UserRepository extends CrudRepository<UserDetails, String> {

}
