package com.example.prova.dto;

import com.example.prova.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String cpf;
    private String phone;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.cpf = user.getCpf();
        this.phone = user.getPhone();
    }
}
