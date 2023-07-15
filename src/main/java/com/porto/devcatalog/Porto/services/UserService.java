package com.porto.devcatalog.Porto.services;

import com.porto.devcatalog.Porto.DTO.RoleDTO;
import com.porto.devcatalog.Porto.DTO.UserCreateDTO;
import com.porto.devcatalog.Porto.DTO.UserDTO;
import com.porto.devcatalog.Porto.entities.Category;
import com.porto.devcatalog.Porto.entities.Role;
import com.porto.devcatalog.Porto.entities.User;
import com.porto.devcatalog.Porto.repositories.UserRepository;
import com.porto.devcatalog.Porto.services.exceptions.DatabaseException;
import com.porto.devcatalog.Porto.services.exceptions.ResourceNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllUsers(Pageable pageable) {
        Page<User> categories = userRepository.findAll(pageable);

        return categories.map(x-> new UserDTO(x));
    }


    @Transactional(readOnly = true)
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundExceptions("Item not found!"));


        return new UserDTO(user);
    }


    @Transactional
    public UserDTO createUser(UserCreateDTO userCreateDTO){
        User user = new User();
        copyDTO2Entity(userCreateDTO, user);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user = userRepository.save(user);

        return new UserDTO(user);
    }



    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        try {
            User user = userRepository.getReferenceById(id);
            copyDTO2Entity(userDTO, user);

            user = userRepository.save(user);

            return new UserDTO(user);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundExceptions("User not found with ID: " + id);
        }

    }


    public void deleteUser(Long id){
        try {
            userRepository.deleteById(id);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Id not found " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundExceptions("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error Database Integration");
        }

    }

    private void copyDTO2Entity(UserDTO userDTO, User user) {

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        user.getRoles().clear();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
//            Category category = categoryRepository.getReferenceById(catDTO.getId());
            user.getRoles().add(new Role(roleDTO.getId(), roleDTO.getAuthority()));
        }
    }


}
