package com.honghong.service.impl;

import com.honghong.common.ResponseData;
import com.honghong.model.user.AdminUserDO;
import com.honghong.model.user.AdminUserDTO;
import com.honghong.repository.AdminUserRepository;
import com.honghong.service.AdminUserService;
import com.honghong.util.PageUtils;
import com.honghong.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：wangjy
 * @description ：后台用户service
 * @date ：2019/11/1 14:14
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public ResponseData addUser(AdminUserDTO adminUserDTO) {
        isExist(adminUserDTO);
        AdminUserDO adminUserDO = new AdminUserDO();
        adminUserDO.addAdminUser(adminUserDTO);
        adminUserDO = adminUserRepository.save(adminUserDO);
        return ResultUtils.success(adminUserDO);
    }

    @Override
    public ResponseData delUser(Long id) {
        if (id == null) {
            return ResultUtils.paramError();
        }
        Optional<AdminUserDO> optional = adminUserRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("数据异常，未找到该用户，请刷新页面");
        }
        adminUserRepository.delete(optional.get());
        return ResultUtils.success();
    }

    @Override
    public ResponseData userList(PageUtils pageUtils) {
        Page<AdminUserDO> page = adminUserRepository.findAll(pageUtils.getPageRequest());
        return ResultUtils.success(page);
    }

    @Override
    public ResponseData updateUser(Long id, AdminUserDTO adminUserDTO) {
        if (id == null) {
            return ResultUtils.paramError();
        }
        Optional<AdminUserDO> optional = adminUserRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("数据异常，未找到该用户，请刷新页面");
        }
        isExist(adminUserDTO);
        AdminUserDO adminUserDO = optional.get();
        adminUserDO.updateAdminUser(adminUserDTO);
        adminUserDO = adminUserRepository.saveAndFlush(adminUserDO);
        return ResultUtils.success(adminUserDO);
    }

    @Override
    public ResponseData login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            return ResultUtils.paramError();
        }
        AdminUserDO adminUserDO = adminUserRepository.findByUsernameAndPassword(username, password);
        if (adminUserDO == null) {
            throw new RuntimeException("账号或密码错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", adminUserDO);
        String token = adminUserDO.getId() + System.currentTimeMillis() + "";
        map.put("token",token);
        return ResultUtils.success(map);
    }


    private void isExist(AdminUserDTO adminUserDTO) {
        List<AdminUserDO> usernameList = adminUserRepository.findAllByUsername(adminUserDTO.getUsername());
        if (usernameList.size() > 0) {
            throw new RuntimeException("用户名已存在");
        }
        List<AdminUserDO> nameList = adminUserRepository.findAllByName(adminUserDTO.getName());
        if (nameList.size() > 0) {
            throw new RuntimeException("姓名已存在");
        }
        List<AdminUserDO> list = adminUserRepository.findAllByPhone(adminUserDTO.getPhone());
        if (list.size() > 0) {
            throw new RuntimeException("电话号码已存在");
        }
    }
}
