package com.porto.devcatalog.Porto.DTO;

import com.porto.devcatalog.Porto.services.validation.UserInsertValid;

@UserInsertValid
public class UserCreateDTO extends UserDTO {

    private String password;

    public UserCreateDTO(){
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
