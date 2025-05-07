package com.modern.security.spring.support.controller;

import com.modern.security.NoAuth;
import com.modern.security.SecurityService;
import com.modern.security.spring.support.controller.param.LoginParam;
import com.modernframework.base.vo.Rs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 安全Controller
 *
 * @author zhangj
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

//    /**
//     * 用户注册
//     *
//     * @param username 用户名
//     * @param password 密码
//     * @return SysUser
//     */
//    @NoAuth
//    @PostMapping("/register")
//    public Rs<Boolean> register(String username, String password) {
//        return Rs.ok(authService.register(username, password));
//    }

    /**
     * 用户登录
     */
    @NoAuth
    @PostMapping("/login")
    public Rs<Object> login(@RequestBody LoginParam param) {
        return Rs.ok(securityService.login(param.getUsername(), param.getPassword()));
    }

    /**
     * 用户登出
     *
     * @return Boolean
     */
    @PostMapping("/logout")
    public Rs<Boolean> logout() {
        return Rs.ok(securityService.logout());
    }

//    /**
//     * 查看当前用户的所有权限
//     *
//     * @param user 当前用户
//     * @return List<String>
//     */
//    @GetMapping("/permissions/user")
//    public ApiResult<List<String>> permissionsByUser(/*@AutoUser*/ SysUser user) {
//        if (user == null) {
//            throw new BusinessException("没有当前登录用户信息");
//        }
//        return ApiResult.ok(permissionService.permissions(user));
//    }
}
