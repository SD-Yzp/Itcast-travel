package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        //验证激活码
        //获取输入的验证码
        String check = request.getParameter("check");
        //获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //去除域中的验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //比较
        if(!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            writeValue(info,response);

            return;
        }
        //接收参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //通过service处理
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        if(flag){
            //用户名不存在，可以创建
            info.setFlag(true);
        }else{
            //用户名存在，创建失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }

        writeValue(info,response);
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        //通过service处理查询到的结果
        UserService service = new UserServiceImpl();
        boolean flag = service.active(code);
        //判断是否成功激活
        String msg = null;
        if(flag){
            //成功激活
            msg = "激活成功，请<a href='login.html'>登陆</a>";
        }else{
            msg = "激活失败，请联系管理员!";
        }

        //将消息写回页面
        response.setContentType("html/text;charset=utf-8");
        response.getWriter().write(msg);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //检测验证码
        //从session中获取
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //销毁session中的checkcode，防止回退获取
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //从请求参数中获取check
        String check = request.getParameter("check");
        if(!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info,response);
            return;
        }

        //获取参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //处理对象,使用service
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);
        ResultInfo info = new ResultInfo();
        //判断是否可以登录,信息封装到ResultInfo中
        if(loginUser == null){
            //不存在
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }

        if(loginUser != null && "N".equals(loginUser.getStatus())){
            //用户存在，但未激活
            info.setFlag(false);
            info.setErrorMsg("请先激活");
        }

        if(loginUser != null && "Y".equals(loginUser.getStatus())){
            //将用户存入session中
            request.getSession().setAttribute("user",loginUser);
            //用户存在，已经激活
            info.setFlag(true);
        }
        //返回结果


        writeValue(info,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取user
        Object user = request.getSession().getAttribute("user");

        writeValue(user,response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
