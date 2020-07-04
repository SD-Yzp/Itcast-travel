package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findByUsername(String username);

    void save(User u);

    User findByCode(String code);

    void setStatus(User u);

    User findByUsernameAndPassword(String username, String password);
}
