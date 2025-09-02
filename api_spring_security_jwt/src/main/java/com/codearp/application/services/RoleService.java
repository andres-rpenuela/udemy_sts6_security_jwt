package com.codearp.application.services;

import com.codearp.application.dtos.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> findInNames(String[] names);
}
