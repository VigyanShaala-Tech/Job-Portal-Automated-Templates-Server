package com.vigyanshaala.repository.jobPortalRepository;

import com.vigyanshaala.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String>{

    @Transactional
    @Query(value="SELECT * FROM user_role u where u.email_id=:emailId", nativeQuery = true)
    UserRole findByEmail (
            @Param("emailId") String emailId);



}

