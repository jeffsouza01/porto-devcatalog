package com.porto.devcatalog.Porto.DTO;

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
