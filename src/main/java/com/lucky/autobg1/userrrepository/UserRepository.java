
package com.lucky.autobg1.userrrepository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.autobg1.userregistrationmodle.UserRegistration;



public interface UserRepository extends JpaRepository<UserRegistration, Long> {

}
