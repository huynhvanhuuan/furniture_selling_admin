package vn.edu.hcmuaf.fit.controller.trademark;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.category.CategoryDto;
import vn.edu.hcmuaf.fit.service.CategoryService;
import vn.edu.hcmuaf.fit.service.TrademarkService;
import vn.edu.hcmuaf.fit.service.impl.CategoryServiceImpl;
import vn.edu.hcmuaf.fit.service.impl.TrademarkServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TrademarkAPIController", value = "/api/trademark/*")
public class TrademarkAPIController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Gson GSON = new GsonBuilder().create();
    private final IConnectionPool connectionPool = (IConnectionPool) getServletContext().getAttribute("connectionPool");
    private final TrademarkService trademarkService = new TrademarkServiceImpl(connectionPool);

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

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
