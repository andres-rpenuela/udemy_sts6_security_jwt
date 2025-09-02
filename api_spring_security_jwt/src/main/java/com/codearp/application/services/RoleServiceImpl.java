package com.codearp.application.services;

import com.codearp.application.dtos.RoleDto;
import com.codearp.application.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> findInNames(String[] names) {
        return roleRepository.findByNameIn(names)
                .stream()
                .map(role -> mapper.map(role, RoleDto.class))
                .toList();
    }

}
