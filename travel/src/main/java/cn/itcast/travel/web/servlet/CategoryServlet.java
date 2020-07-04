package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过service返回list
        CategoryService service = new CategoryServiceImpl();
        List<Category> list = service.findAll();
        //将数据写回页面
        writeValue(list,response);
    }


}
