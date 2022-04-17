package vn.edu.hcmuaf.fit.controller.address;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.address.AddressCreate;
import vn.edu.hcmuaf.fit.dto.address.AddressDto;
import vn.edu.hcmuaf.fit.dto.address.AddressUpdate;
import vn.edu.hcmuaf.fit.service.AddressService;
import vn.edu.hcmuaf.fit.service.impl.AddressServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        Long id = Long.parseLong(request.getPathInfo().substring(1));
        AppServiceResult<AddressDto> result = addressService.getAddressById(id);
        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson(result.getData()));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String number = request.getParameter("number");
        String street = request.getParameter("street");
        Long wardId = Long.parseLong(request.getParameter("wardId"));
        Long districtId = Long.parseLong(request.getParameter("districtId"));
        Long provinceId = Long.parseLong(request.getParameter("provinceId"));

        AddressCreate newAddress = new AddressCreate(number, street, wardId, districtId, provinceId);

        AppServiceResult<AddressDto> result = addressService.createAddress(newAddress);
        if (result.isSuccess()) {
            response.setStatus(200);
            response.getWriter().println(GSON.toJson(result.getData()));
        } else {
            response.sendError(result.getErrorCode(), result.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Long id = Long.parseLong(request.getParameter("id"));
        String number = request.getParameter("number");
        String street = request.getParameter("street");
        Long wardId = Long.parseLong(request.getParameter("wardId"));
        Long districtId = Long.parseLong(request.getParameter("districtId"));
        Long provinceId = Long.parseLong(request.getParameter("provinceId"));

        AddressUpdate updatedAddress = new AddressUpdate(id, number, street, wardId, districtId, provinceId);

        AppBaseResult result = addressService.updateAddress(updatedAddress);
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
