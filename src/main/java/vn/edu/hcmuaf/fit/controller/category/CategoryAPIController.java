package vn.edu.hcmuaf.fit.controller.category;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.category.CategoryDto;
import vn.edu.hcmuaf.fit.entity.Category;
import vn.edu.hcmuaf.fit.service.CategoryService;
import vn.edu.hcmuaf.fit.service.impl.CategoryServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

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
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/todos/".length()));

        if (Todos.todos.containsKey(id)) {
            resp.setStatus(422);
            resp.getOutputStream().println("You cannot created Todo with id " + id + " because it exists!");
        }

        String json = Util.readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);
        todo.setId(id);

        Todos.todos.put(todo.getId(), todo);

        resp.setStatus(201);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(todo));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/todos/".length()));

        if (!Todos.todos.containsKey(id)) {
            resp.setStatus(422);
            resp.getOutputStream().println("You cannot update Todo with id " + id + " because it doesn't exists!");
        }

        String json = Util.readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);
        todo.setId(id);

        Todos.todos.put(todo.getId(), todo);

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(todo));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/todos/".length()));

        Todo todo = Todos.todos.remove(id);
        String json = GSON.toJson(todo);

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }
}
