package ru.ea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserProfileDto {

    private String email;
    private String password;
    private String profileImageUrl;

    public  UserProfileDto(String email){
        this.email=email;
    }
}
