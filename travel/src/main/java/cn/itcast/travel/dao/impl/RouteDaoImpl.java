package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        int totalCount = 0;
        try {
            //定义sql
            String sql = "select count(*) from tab_route where 1 = 1  ";
            StringBuilder sb = new StringBuilder(sql);
            List<Object> params = new ArrayList<Object>();
            sb.append(" and cid = ? ");
            params.add(cid);
            if(rname!=null && rname.length()>0){
                sb.append(" and rname like ? ");
                params.add("%"+rname+"%");
            }
            //执行sql
            sql = sb.toString();
            totalCount = template.queryForObject(sql, Integer.class,params.toArray());
        } catch (DataAccessException e) {

        }
        return totalCount;
    }

    @Override
    public List<Route> findByPage(int start, int pageSize, int cid, String rname) {
        List<Route> list = null;
        try {
            //定义sql
            String sql = "select * from tab_route where 1=1 ";
            StringBuilder sb = new StringBuilder(sql);
            List<Object> params = new ArrayList<Object>();
            sb.append(" and cid = ? ");
            params.add(cid);
            //添加rname选项
            if(rname != null && rname.length() > 0){
                sb.append(" and rname like ? ");
                params.add("%"+rname+"%");
            }

            sb.append(" limit ? , ? ");//limit第一个数据是不包含的，例如limit 3,5 是从id为4开始找5条记录
            params.add(start);
            params.add(pageSize);
            //执行sql
            sql = sb.toString();
            list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        } catch (DataAccessException e) {

        }

        return list;
    }

    @Override
    public Route findOne(int rid) {
        //定义sql
        String sql = "select * from tab_route where rid = ? ";
        //执行sql
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);

        return route;
    }
}
