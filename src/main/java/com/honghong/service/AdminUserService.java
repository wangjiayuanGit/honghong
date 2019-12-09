package com.honghong.service;

import com.honghong.common.ResponseData;
import com.honghong.model.user.AdminUserDTO;
import com.honghong.util.PageUtils;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/11/1 14:00
 */
public interface AdminUserService {
    /**
     * 添加后台用户
     *
     * @param adminUserDTO
     * @return
     */
    ResponseData addUser(AdminUserDTO adminUserDTO);

    /**
     * 删除后台用户
     *
     * @param id
     * @return
     */
    ResponseData delUser(Long id);

    /**
     * 后台用户列表
     *
     * @param pageUtils
     * @return
     */
    ResponseData userList(PageUtils pageUtils);

    /**
     * 修改用户信息
     *
     * @param id
     * @param adminUserDTO
     * @return
     */
    ResponseData updateUser(Long id, AdminUserDTO adminUserDTO);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    ResponseData login(String username, String password);
}
