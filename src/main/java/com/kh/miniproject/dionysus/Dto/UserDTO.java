package com.kh.miniproject.dionysus.Dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String pw;
    private String name;
    private String jumin;
    private String nick;
    private String phone;
    private String address;
}