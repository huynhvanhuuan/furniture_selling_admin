package vn.edu.hcmuaf.fit.controller.trademark;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.trademark.TrademarkCreate;
import vn.edu.hcmuaf.fit.dto.trademark.TrademarkDto;
import vn.edu.hcmuaf.fit.dto.trademark.TrademarkUpdate;
import vn.edu.hcmuaf.fit.service.TrademarkService;
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
            AppServiceResult<List<TrademarkDto>> result = trademarkService.getTrademarks();
            if (result.isSuccess()) {
                response.setStatus(200);
                response.getWriter().println(GSON.toJson(result.getData()));
            } else {
                response.sendError(result.getErrorCode(), result.getMessage());
            }
        } else {
            AppServiceResult<TrademarkDto> result = trademarkService.getTrademark(Long.parseLong(uri.substring("/api/trademark/".length())));
            if (result.isSuccess()) {
                response.setStatus(200);
                response.getWriter().println(GSON.toJson(result.getData()));
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
        String website = request.getParameter("website");

        TrademarkCreate newTrademark = new TrademarkCreate(name, website);

        AppServiceResult<TrademarkDto> result = trademarkService.createTrademark(newTrademark);

        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson(result.getData()));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String website = request.getParameter("website");

        AppBaseResult result = trademarkService.updateTrademark(new TrademarkUpdate(id, name, website));
        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson("Succeed!"));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        long id = Long.parseLong(action.substring(1));
        AppBaseResult result = trademarkService.deleteTrademark(id);
        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson("Succeed!"));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }
}
