package com.lucky.autobg1.userrrepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.lucky.autobg1.createdtempletedmodle.CreateTempletedEntity;
import com.lucky.autobg1.userregistrationmodle.UserEntity;
@EnableJpaRepositories
public interface UserTemplatesRepository  extends JpaRepository<UserEntity, Long>{

	CreateTempletedEntity save(CreateTempletedEntity createTempletedEntity);
	CreateTempletedEntity findById(long id);
	List<CreateTempletedEntity> findAllById(long userId);

}
