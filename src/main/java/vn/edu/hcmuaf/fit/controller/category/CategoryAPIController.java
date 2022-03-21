package vn.edu.hcmuaf.fit.controller.category;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.category.CategoryCreate;
import vn.edu.hcmuaf.fit.dto.category.CategoryDto;
import vn.edu.hcmuaf.fit.entity.Category;
import vn.edu.hcmuaf.fit.service.CategoryService;
import vn.edu.hcmuaf.fit.service.impl.CategoryServiceImpl;
import vn.edu.hcmuaf.fit.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CategoryAPIController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Gson GSON = new GsonBuilder().create();
    private final IConnectionPool connectionPool = (IConnectionPool) getServletContext().getAttribute("connectionPool");
    private final CategoryService categoryService = new CategoryServiceImpl(connectionPool);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        if (uri.equals("/api/category/list")) {
            AppServiceResult<List<CategoryDto>> result = categoryService.getCategories();
            if (result.isSuccess()) {
                response.setStatus(200);
                response.getWriter().println(GSON.toJson(result.getData()));
            } else {
                response.sendError(result.getErrorCode(), result.getMessage());
            }
        } else {
            AppServiceResult<CategoryDto> result = categoryService.getCategory(Long.parseLong(uri.substring("/api/category/".length())));
            if (result.isSuccess()) {
                response.setStatus(200);
                response.getWriter().write(GSON.toJson(result.getData()));
            } else {
                response.sendError(result.getErrorCode(), result.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String sku = StringUtil.toStringWithoutSpaces(name).toUpperCase(Locale.ROOT);

        CategoryCreate newCategory = new CategoryCreate(sku, name);

        AppServiceResult<CategoryDto> result = categoryService.createCategory(newCategory);
        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson(result.getData()));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String action = request.getPathInfo();
        long id = Long.parseLong(request.getParameter("id"));
        switch (action) {
            case "/update-category":
                CategoryDto category = categoryService.getCategory(id);
                String name = request.getParameter("name");
                String sku = StringUtil.toStringWithoutSpaces(name).toUpperCase(Locale.ROOT);
                categoryService.updateCategory(new Category(id, sku, name, ));
                break;

            case "/update-status":
                categoryService.updateStatus(id);
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
    }
}
