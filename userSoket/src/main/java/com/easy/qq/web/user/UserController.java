package com.easy.qq.web.user;

import com.easy.qq.conmon.Result;
import com.easy.qq.web.user.service.UserService;
import com.easy.qq.web.user.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result<UserVo> registerUser(UserVo user) {
        return userService.registerUser(user);
    }
}
