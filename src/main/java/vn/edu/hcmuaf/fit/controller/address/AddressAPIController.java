package vn.edu.hcmuaf.fit.controller.address;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.address.AddressDto;
import vn.edu.hcmuaf.fit.dto.address.AddressUpdate;
import vn.edu.hcmuaf.fit.dto.category.CategoryCreate;
import vn.edu.hcmuaf.fit.dto.category.CategoryDto;
import vn.edu.hcmuaf.fit.entity.Ward;
import vn.edu.hcmuaf.fit.service.AddressService;
import vn.edu.hcmuaf.fit.service.impl.AddressServiceImpl;
import vn.edu.hcmuaf.fit.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "AddressAPIController", value = "/api/address/*")
public class AddressAPIController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Gson GSON = new GsonBuilder().create();
    private final IConnectionPool connectionPool = (IConnectionPool) getServletContext().getAttribute("connectionPool");

    private final AddressService addressService = new AddressServiceImpl(connectionPool);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String action = request.getPathInfo();
        long id;
        switch (action) {
            case "/trademark":
                id = Long.parseLong(request.getParameter("id"));
                AppServiceResult<List<AddressDto>> result = addressService.getAddressByTrademarkId(id);
                if (result.isSuccess()) {
                    response.setStatus(200);
                    response.getWriter().println(GSON.toJson(result.getData()));
                } else {
                    response.sendError(result.getErrorCode(), result.getMessage());
                }
                break;
            case "/user":
                id = Long.parseLong(request.getParameter("id"));

                AppServiceResult<List<AddressDto>> result = addressService.getAddressById(id);
                if (result.isSuccess()) {
                    response.setStatus(200);
                    response.getWriter().write(GSON.toJson(result.getData()));
                } else {
                    response.sendError(result.getErrorCode(), result.getMessage());
                }
                break;
            case "/":
                id = Long.parseLong(request.getParameter("id"));
                AppServiceResult<AddressDto> result = addressService.getAddressById(id);
        }
        if ("/list".equals(action)) {
        } else {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String sku = StringUtil.toStringWithoutSpaces(name).toUpperCase(Locale.ROOT);

        CategoryCreate newCategory = new CategoryCreate(sku, name);

        AppServiceResult<CategoryDto> result = addressService.createAddress(newCategory);
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
        String number = request.getParameter("number");
        String street = request.getParameter("street");
        Long wardId = Long.parseLong(request.getParameter("wardId"));
        Long districtId = Long.parseLong(request.getParameter("districtId"));

        Ward ward = wardService.getWardById(wardId);
        AppBaseResult result = addressService.updateAddress(new AddressUpdate(id, ));
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
        AppBaseResult result = addressService.deleteAddress(id);
        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson("Succeed!"));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }
}
