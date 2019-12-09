package com.honghong.repository;

import com.honghong.model.user.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/9/6 13:51
 */
public interface UserRepository extends JpaRepository<UserDO, Long> {

    UserDO findByWechatOpenid(String openId);

    /**
     * 当天登录的人
     *
     * @param start
     * @param end
     * @return
     */
    List<UserDO> findAllByLastLoginTimeAfterAndLastLoginTimeBefore(Date start, Date end);

    /**
     * 授权登录的人数
     *
     * @return
     */
    Integer countByWechatOpenidIsNotNull();
}
