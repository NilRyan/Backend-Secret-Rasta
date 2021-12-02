package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.model.Role;

public interface RoleService {
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
}
