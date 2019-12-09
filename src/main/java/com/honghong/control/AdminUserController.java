package com.honghong.control;

import com.honghong.common.ResponseData;
import com.honghong.model.user.AdminUserDTO;
import com.honghong.service.AdminUserService;
import com.honghong.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：wangjy
 * @description ：管理员control
 * @date ：2019/11/1 13:56
 */
@RestController
@RequestMapping("/admin")
@Api(description = "后台管理员用户)")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/add")
    @ApiOperation("添加后台用户")
    public ResponseData add(AdminUserDTO adminUserDTO) {
        return adminUserService.addUser(adminUserDTO);
    }

    @PostMapping("/del")
    @ApiOperation("删除后台用户")
    public ResponseData del(Long id) {
        return adminUserService.delUser(id);
    }

    @GetMapping("/list")
    @ApiOperation("后台用户列表")
    public ResponseData list(PageUtils pageUtils) {
        return adminUserService.userList(pageUtils);
    }

    @PostMapping("/update")
    @ApiOperation("更新后台用户")
    public ResponseData update(Long id, AdminUserDTO adminUserDTO) {
        return adminUserService.updateUser(id, adminUserDTO);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseData login(String username, String password) {
        return adminUserService.login(username, password);
    }
}
