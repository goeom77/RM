package org.gyu.solution.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.global.base.Response;
import org.gyu.solution.user.dto.UserDto;
import org.gyu.solution.user.service.UserService;
import org.gyu.solution.user.vo.UserLoginOut;
import org.gyu.solution.user.vo.UserIn;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입을 합니다.", tags = {"User"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패")
    })
    public Response<?> signUp(@RequestBody UserIn userIn) {
        UserDto userDto = modelMapper.map(userIn, UserDto.class);
        UserLoginOut userLoginOut = userService.signUp(userDto);
        return Response.ofSuccess(userLoginOut);
    }

    @GetMapping("/duplicate")
    @Operation(summary = "아이디 중복체크", description = "아이디 중복체크를 합니다.", tags = {"User"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아이디 중복체크 성공"),
            @ApiResponse(responseCode = "403", description = "이미 존재하는 유저네임 존재"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> duplicateCheck(@RequestParam("loginId") String loginId) {
        // todo: 아이디에 대한 조건 추가
        userService.duplicateCheck(loginId);
        return Response.ofSuccess();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 합니다.", tags = {"User"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "403", description = "로그인 실패"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> login(@RequestBody UserIn userIn) {
        UserDto userDto = modelMapper.map(userIn, UserDto.class);
        UserLoginOut userLoginOut = userService.login(userDto);
        return Response.ofSuccess(userLoginOut);

    }

}
