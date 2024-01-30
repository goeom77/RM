package org.gyu.solution.user.vo;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Data
public class UserIn {
    private String loginId;
    private String password;
}
