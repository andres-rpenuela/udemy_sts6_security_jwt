package com.codearp.application.users.controllers;

import com.codearp.application.users.dtos.PersonDto;
import com.codearp.application.users.dtos.UserAccountDto;
import com.codearp.application.users.dtos.UserFormDto;
import com.codearp.application.users.facades.UserAccountFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createUserAccount(@Valid @RequestBody UserFormDto userFormDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach( fieldError -> {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

            Map<String, Object> response = new HashMap<>();
            response.put("message", "There are errors in the form");
            response.put("errors", errors);

            userFormDto.setPassword(null);// for security reasons
            response.put("data", userFormDto);

            return ResponseEntity.badRequest().body(response);
        }

        userFormDto.setPassword(null);// for security reasons

        return ResponseEntity.ok( userAccountFacade.createUserAccount( userFormDto ) );
    }
    @PutMapping("/{idAccount}")
    public ResponseEntity<?> updateUserAccount(@PathVariable String idAccount, @Valid @RequestBody UserFormDto userFormDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach( fieldError -> {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

            Map<String, Object> response = new HashMap<>();
            response.put("message", "There are errors in the form");
            response.put("errors", errors);

            userFormDto.setPassword(null);// for security reasons
            response.put("data", userFormDto);

            return ResponseEntity.badRequest().body(response);
        }

        userFormDto.setPassword(null);// for security reasons
        return ResponseEntity.ok( userAccountFacade.updateUserAccount(idAccount, userFormDto ) );
    }
}
