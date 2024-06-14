package com.neotour.repository;

import com.neotour.entity.Role;
import com.neotour.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum roleEnum);
}
