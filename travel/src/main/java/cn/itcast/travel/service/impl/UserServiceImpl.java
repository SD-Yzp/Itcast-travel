package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();


    @Override
    public boolean regist(User user) {
        //通过用户名查找是否存在
        User u = userDao.findByUsername(user.getUsername());

        if(u!=null){
            //存在，返回false
            return false;
        }

        //不存在，存储用户
        //设置激活状态
        user.setStatus("N");
        //设置激活码
        user.setCode(UuidUtil.getUuid());
        userDao.save(user);

        //发送邮件
        //设置邮件内容
        String content = "<a href=localhost/travel/user/active?code="+user.getCode()+">点击激活</a>";
        MailUtils.sendMail("17854200056@163.com",content,"测试邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //通过userDao查询是否存在用户
        User u = userDao.findByCode(code);
        //判断结果
        if(u!=null){
            //存在该用户，修改状态为Y
            userDao.setStatus(u);
            return true;
        }else{
            //不存在该用户，返回false
            return false;
        }
    }

    @Override
    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        return userDao.findByUsernameAndPassword(username,password);
    }

}
