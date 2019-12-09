package com.honghong.repository;

import com.honghong.model.user.AdminUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/11/1 13:59
 */
public interface AdminUserRepository extends JpaRepository<AdminUserDO, Long> {
    /**
     * 根据电话号码和密码查找
     * @param username
     * @param password
     * @return
     */
    AdminUserDO findByUsernameAndPassword(String username, String password);
    /**
     * 根据电话号码查询
     *
     * @param phone
     * @return
     */
    List<AdminUserDO> findAllByPhone(String phone);

    /**
     * 根据姓名查询
     *
     * @param name
     * @return
     */
    List<AdminUserDO> findAllByName(String name);

    /**
     * 根据用户名查找
     *
     * @param username
     * @return
     */
    List<AdminUserDO> findAllByUsername(String username);
}
