package org.gyu.solution.user.vo;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Data
public class UserSignUpIn {
    private String loginId;
    private String password;
}
