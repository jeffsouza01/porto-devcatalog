package com.porto.devcatalog.Porto.controllers;

import com.porto.devcatalog.Porto.DTO.UserCreateDTO;
import com.porto.devcatalog.Porto.DTO.UserDTO;
import com.porto.devcatalog.Porto.DTO.UserUpdateDTO;
import com.porto.devcatalog.Porto.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public Page<UserDTO> findAllUsers(Pageable page){

        return userService.findAllUsers(page);
    }

    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }


    @PostMapping
    public ResponseEntity<UserDTO> insertNewUser(@Valid @RequestBody UserCreateDTO dto) {
        UserDTO newDto = userService.createUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newDto.getId()).toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserDTO newDto = userService.updateUser(id, dto);

        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
