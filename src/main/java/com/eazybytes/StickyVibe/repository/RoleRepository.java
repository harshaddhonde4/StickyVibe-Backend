package com.eazybytes.StickyVibe.repository;

import com.eazybytes.StickyVibe.entity.Product;
import com.eazybytes.StickyVibe.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    @Cacheable("roles")
//    ROLE_USER -> CACHE MISS(Initially) -> DB Call -> Cache Store(Key(ROLE_USER)) -> Customer 1
//    ROLE_USER -> CACHE HIT -> CUSTOMER 2
//    ROLE_ADMIN -> CACHE_MISS -> DB Call -> Cache store (ROLE_ADMIN) -> Customer
    Optional<Role> findByName(String name);
}