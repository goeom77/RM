package org.gyu.solution.user.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginOut {
    private String token;
}
