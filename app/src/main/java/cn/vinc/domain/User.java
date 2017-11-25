package cn.vinc.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Integer id;

    private String name;

    private String phone;

    private String pass;

    private String email;

    private Integer gender;

    private Integer age;

}