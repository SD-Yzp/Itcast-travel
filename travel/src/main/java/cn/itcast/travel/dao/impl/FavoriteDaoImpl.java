package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByUidAndRid(int uid, int rid) {
        Favorite favorite = null;
        try {
            //定义sql
            String sql = "select * from tab_favorite where uid = ? and rid = ? ";
            //执行sql
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        } catch (DataAccessException e) {

        }

        return favorite;
    }

    @Override
    public void addFavorite(int uid, int rid) {
        //定义sql
        String sql1 = "insert into tab_favorite(uid,date,rid) values(?,?,?)";  //这里如果不加列的话可能出现外键约束错误，原因未知
        String sql2 = "update tab_route set count = count+1 where rid = ?";
        //执行sql
        System.out.println("uid="+uid+",rid="+rid);
        template.update(sql1,uid,new Date(),rid);
        template.update(sql2,rid);
    }
}
