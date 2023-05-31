package com.example.petshelper.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_USER_TITLE = "USER";
    public static final Long ROLE_USER_ID = 1L;
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_ADMIN_TITLE = "ADMIN";
    public static final Long ROLE_ADMIN_ID = 2L;


    @Id
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}