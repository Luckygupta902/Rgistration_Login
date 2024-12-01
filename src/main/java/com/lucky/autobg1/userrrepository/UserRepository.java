
package com.lucky.autobg1.userrrepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.lucky.autobg1.userregistrationmodle.RegistrationRequest;
import com.lucky.autobg1.userregistrationmodle.UserEntity;


@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	
	

	public UserEntity findByEmail(String email);

	public UserEntity findByActiveCode(String token);

	public UserEntity findByUsername(String username);

	

	
}
