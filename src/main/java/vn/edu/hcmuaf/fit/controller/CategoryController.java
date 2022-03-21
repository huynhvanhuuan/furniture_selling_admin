package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.entity.Category;
import vn.edu.hcmuaf.fit.service.CategoryService;
import vn.edu.hcmuaf.fit.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

@WebServlet(name = "CategoryController", value = "/category")
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IConnectionPool connectionPool = (IConnectionPool) getServletContext().getAttribute("connectionPool");
    private final CategoryService categoryService = new CategoryServiceImpl(connectionPool);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if (action == null) {
                getMainPage(request, response);
            } else switch (action) {
                case "create":
                    create(request, response);
                    break;
                case "update":
                    update(request, response);
                    break;
                case "get":
                    get(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "changeActive":
                    changeActive(request, response);
                    break;
                case "checkExist":
                    checkExist(request, response);
                    break;
                case "getListSkuHasProduct":
                    getListSkuHasProduct(request, response);
                    break;
                default:
                    getMainPage(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        request.setAttribute("title", "QUẢN LÝ THỂ LOẠI");
        categoryService.getCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/view/admin/category.jsp").forward(request, response);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        String sku = request.getParameter("sku");
        Category category = categoryService.get(sku);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(category));
        out.close();
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        categoryService.create(new Category(sku.toUpperCase(), name, true));
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String oldSku = request.getParameter("old_sku");
        String newSku = request.getParameter("sku");
        String name = request.getParameter("name");
        String active = request.getParameter("active");
        Category category = new Category(newSku.toUpperCase(), name, active.equals("true"));
        categoryService.update(oldSku, category);
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        response.setContentType("application/json");
        String skus = request.getParameter("skus");
        StringTokenizer st = new StringTokenizer(skus, "[\",]");
        while (st.hasMoreTokens()) {
            String sku = st.nextToken();
            categoryService.delete(sku);
        }
        PrintWriter pw = response.getWriter();
        pw.println(new Gson().toJson(new ResponseHandler(200, "Xóa thành công.")));
        pw.close();
    }
    
    private void changeActive(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String sku = request.getParameter("sku");
        categoryService.changeActive(sku);
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
    
    private void checkExist(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.setContentType("application/json");
        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        PrintWriter out = response.getWriter();
        if (categoryService.isExist(sku.toUpperCase(), "")) 
            out.println(new Gson().toJson(new ResponseHandler(1, "Mã thể loại đã tồn tại.")));
        else if (categoryService.isExist("", name))
            out.println(new Gson().toJson(new ResponseHandler(2, "Tên thể loại đã tồn tại.")));
        else out.println(new Gson().toJson(new ResponseHandler(0, "Mã và tên thể loại hợp lệ.")));
        out.close();
    }
    
    private void getListSkuHasProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        List<String> skus = categoryService.getListSkuHasProduct();
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(skus));
        out.close();
    }
}
