package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 通过激活码激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 通过用户名和密码登录
     * @param user
     * @return
     */
    User login(User user);
}
