package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.dto.address.District;
import vn.edu.hcmuaf.fit.dto.address.Province;
import vn.edu.hcmuaf.fit.dto.address.Ward;
import vn.edu.hcmuaf.fit.helper.ResponseHandler;
import vn.edu.hcmuaf.fit.model.Address;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.AddressService;
import vn.edu.hcmuaf.fit.service.impl.AddressServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AddressController", value = "/admin/address")
public class AddressController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AddressService addressService;
    
    @Override
    public void init() throws ServletException {
        addressService = new AddressServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "getProvinceList":
                    getProvinceList(response);
                    break;
                case "getDistrictList":
                    getDistrictList(request, response);
                    break;
                case "getWardList":
                    getWardList(request, response);
                    break;
                case "get":
                    get(request, response);
                    break;
                case "createTrademarkAddress":
                    createTrademarkAddress(request, response);
                    break;
                case "createUserAddress":
                    createUserAddress(request, response);
                    break;
                case "update":
                    update(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "checkExist":
                    checkExist(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void getProvinceList(HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        List<Province> provinces = addressService.getProvinceList();
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(provinces));
        out.close();
    }
    
    private void getDistrictList(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        List<District> districts = addressService.getDistrictListByProvinceId(id);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(districts));
        out.close();
    }

    private void getWardList(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        List<Ward> wards = addressService.getWardListByDistrictId(id);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(wards));
        out.close();
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        Address address = addressService.getAddress(id);
        PrintWriter pw = response.getWriter();
        pw.println(new Gson().toJson(address));
        pw.close();
    }
    
    private void checkExist(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        String path = request.getParameter("path");
        PrintWriter out = response.getWriter();
        if (addressService.checkExist(path)) 
            out.println(new Gson().toJson(new ResponseHandler(1, "Địa chỉ đã tồn tại.")));
        else out.println(new Gson().toJson(new ResponseHandler(0, "Địa chỉ hợp lệ.")));
        out.close();
    }
    
    private void createTrademarkAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String redirect = request.getParameter("redirect");
        int trademarkId = Integer.parseInt(request.getParameter("trademarkId"));
        int districtId = Integer.parseInt(request.getParameter("district"));
        String wardId = request.getParameter("ward");
        String street = request.getParameter("street");
        String number = request.getParameter("number");
        if (number.equals("")) number = null;
        if (wardId.equals("")) {
            District district = addressService.getDistrict(districtId);
            addressService.createTrademarkAddress(trademarkId, new Address(0, number, street, district));
        } else {
            Ward ward = addressService.getWard(Integer.parseInt(wardId));
            addressService.createTrademarkAddress(trademarkId, new Address(0, number, street, ward, ward.getDistrict()));
        }
        response.sendRedirect(request.getContextPath() + "/" + redirect);
    }
    
    private void createUserAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String redirect = request.getParameter("redirect");
        User user = (User) request.getSession().getAttribute("USER");
        int districtId = Integer.parseInt(request.getParameter("district"));
        String wardId = request.getParameter("ward");
        String street = request.getParameter("street");
        String number = request.getParameter("number");
        if (number.equals("")) number = null;
        if (wardId.equals("")) {
            District district = addressService.getDistrict(districtId);
            addressService.createUserAddress(user.getId(), new Address(0, number, street, district));
        } else {
            Ward ward = addressService.getWard(Integer.parseInt(wardId));
            addressService.createUserAddress(user.getId(), new Address(0, number, street, ward, ward.getDistrict()));
        }
        response.sendRedirect(request.getContextPath() + "/" + redirect);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String redirect = request.getParameter("redirect");
        System.out.println(redirect);
        int id = Integer.parseInt(request.getParameter("id"));
        String path = request.getParameter("path");
        int districtId = Integer.parseInt(request.getParameter("district"));
        String wardId = request.getParameter("ward");
        String street = request.getParameter("street");
        String number = request.getParameter("number");
        if (number.equals("")) number = null;
        if (wardId.equals("")) {
            District district = addressService.getDistrict(districtId);
            addressService.update(new Address(id, number, street,null, district));
        } else {
            Ward ward = addressService.getWard(Integer.parseInt(wardId));
            addressService.update(new Address(id, number, street, ward, ward.getDistrict()));
        }
        response.sendRedirect(request.getContextPath() + "/" + redirect);
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String redirect = request.getParameter("redirect");
        int id = Integer.parseInt(request.getParameter("id"));
        addressService.delete(id);
        response.sendRedirect(request.getContextPath() + "/" + redirect);
    }
}
