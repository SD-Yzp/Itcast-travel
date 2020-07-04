package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService routeService = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 通过页码和cid查询路线信息,查询功能已经添加
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void routeQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求参数编码
        request.setCharacterEncoding("utf-8");

        //获取请求参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //增加搜索的参数
        String rname = request.getParameter("rname");

        //可能获取到的rname就是字符串null，判断一下
        if("null".equals(rname)){
            rname=null;
        }


        //处理参数
        int cid = 0;
        if(cidStr != null && cidStr.length()>0){
            cid = Integer.parseInt(cidStr);
        }else{
            cid=1;
        }

        int currentPage = 0;
        if(currentPageStr != null && currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else{
            currentPage=1;
        }
        int pageSize = 0;
        if(pageSizeStr != null && pageSizeStr.length()>0){
            pageSize=Integer.parseInt(pageSizeStr);
        }else{
            pageSize=5;
        }


       //通过service处理参数
        PageBean<Route> pb = routeService.pageQuery(currentPage,pageSize,cid,rname);


        System.out.println(pb);
        //写回页面
        writeValue(pb,response);
    }

    /**
     * 通过“点击详情”按钮访问，查询单个路线的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数rid(只有点击详情才会调用,所以一定有rid,不需要判断)
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);
        //2.通过service处理
        Route route = routeService.findOne(rid);
        //3.写回页面
        writeValue(route,response);
    }


    /**
     * 查询路线是否被用户收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数rid
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);

        //2.从session获取user
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if(user != null){
            uid = user.getUid();
        }

        //3.通过service处理
        boolean flag = favoriteService.isFavorite(uid,rid);

        //4.写回页面
        writeValue(flag,response);
    }

    /**
     * 添加收藏功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数rid
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);

        //2.从session获取user
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user == null){
            //用户未登录
            return;
        }else{
            //用户登录
            uid = user.getUid();
        }

        //3.通过service添加
        favoriteService.addFavorite(uid,rid);

    }



}

