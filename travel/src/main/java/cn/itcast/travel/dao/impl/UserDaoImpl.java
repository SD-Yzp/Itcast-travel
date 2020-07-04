package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where username = ? ";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public void save(User u) {
        //定义sql
        String sql = "insert into " +
                "tab_user(username,password,name,birthday,sex,telephone,email,status,code) " +
                "values(?,?,?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,
                u.getUsername(),
                u.getPassword(),
                u.getName(),
                u.getBirthday(),
                u.getSex(),
                u.getTelephone(),
                u.getEmail(),
                u.getStatus(),
                u.getUid());
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where code = ?";
            //执行sql
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void setStatus(User u) {
        //定义sql
        String sql = "update tab_user set status = 'Y' where uid = ?";
        //执行sql
        template.update(sql,u.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where username = ? and password = ? ";
            //执行sql
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (Exception e) {
            //后期记录到日志中
        }
        return user;
    }

}
