package com.codearp.application.users.services;

import com.codearp.application.users.dtos.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> findInNames(String[] names);
}
