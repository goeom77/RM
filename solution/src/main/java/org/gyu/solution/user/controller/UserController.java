package org.gyu.solution.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.service.UserService;
import org.gyu.solution.user.vo.UserSignUpIn;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입을 합니다.", tags = {"User"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    public void signUp(@RequestBody UserSignUpIn userSignUpIn) {
        userService.signUp(userSignUpIn);
    }
    @GetMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }
}
