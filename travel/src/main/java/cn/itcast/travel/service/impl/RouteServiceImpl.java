package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.Route_ImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.Route_ImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();
    Route_ImgDao routeImgDao = new Route_ImgDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSize, int cid, String rname) {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        //调用routeDao查询总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //计算出总页码
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize +1;
        pb.setTotalPage(totalPage);

        //调用routeDao查询list集合
        //计算开始的记录数
        int start = (currentPage-1)*pageSize;
        List<Route> list = routeDao.findByPage(start,pageSize,cid,rname);
        pb.setList(list);

        return pb;
    }

    @Override
    public Route findOne(int rid) {
        //通过rid查询路线
        Route route = routeDao.findOne(rid);
        //通过路线route的rid查询关联的img
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        route.setRouteImgList(routeImgList);
        //通过route的rid查询关联的seller
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        return route;
    }
}
