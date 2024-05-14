package com.easy.qq.web.user;

import com.easy.qq.conmon.Result;
import com.easy.qq.web.user.req.UserLoginReq;
import com.easy.qq.web.user.res.UserLoginRes;
import com.easy.qq.web.user.service.UserService;
import com.easy.qq.web.user.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<UserVo> registerUser(UserVo user) {
        return userService.registerUser(user);
    }

    /**
     * 登录
     *
     * @param req
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginRes> userLogin(@Valid @RequestBody UserLoginReq req) {
        return userService.userLogin(req);
    }
}
