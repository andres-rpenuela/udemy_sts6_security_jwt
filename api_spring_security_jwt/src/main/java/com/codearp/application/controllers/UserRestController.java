package com.codearp.application.controllers;

import com.codearp.application.dtos.PersonDto;
import com.codearp.application.dtos.UserAccountDto;
import com.codearp.application.dtos.UserFormDto;
import com.codearp.application.facades.UserAccountFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {

    private final UserAccountFacade userAccountFacade;

    @GetMapping
    public ResponseEntity<List<PersonDto>> recoverUsers(){
        return  ResponseEntity.ok( userAccountFacade.getAllPerson() );
    }

    @PostMapping
    public ResponseEntity<UserAccountDto> createUser(@RequestBody UserFormDto userFormDto){
        return ResponseEntity.ok( userAccountFacade.createUserAccount( userFormDto ) );
    }
    @PutMapping("/{idAccount}")
    public ResponseEntity<UserAccountDto> createUser(@PathVariable String idAccount, @RequestBody UserFormDto userFormDto){
        return ResponseEntity.ok( userAccountFacade.updateUserAccount(idAccount, userFormDto ) );
    }
}
