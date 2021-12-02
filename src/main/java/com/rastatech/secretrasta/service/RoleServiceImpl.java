package com.rastatech.secretrasta.service;

import com.rastatech.secretrasta.model.Role;
import com.rastatech.secretrasta.model.UserEntity;
import com.rastatech.secretrasta.repository.RoleRepository;
import com.rastatech.secretrasta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }
}
