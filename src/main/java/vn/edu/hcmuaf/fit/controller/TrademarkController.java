package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.dto.address.Province;
import vn.edu.hcmuaf.fit.entity.Address;
import vn.edu.hcmuaf.fit.entity.Province;
import vn.edu.hcmuaf.fit.entity.Trademark;
import vn.edu.hcmuaf.fit.helper.ResponseHandler;
import vn.edu.hcmuaf.fit.model.Address;
import vn.edu.hcmuaf.fit.model.Trademark;
import vn.edu.hcmuaf.fit.service.AddressService;
import vn.edu.hcmuaf.fit.service.impl.AddressServiceImpl;
import vn.edu.hcmuaf.fit.service.TrademarkService;
import vn.edu.hcmuaf.fit.service.impl.TrademarkServiceImpl;

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

@WebServlet(name = "TrademarkController", value = "/admin/trademark")
public class TrademarkController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TrademarkService trademarkService;
    private AddressService addressService;

    @Override
    public void init() throws ServletException {
        trademarkService = new TrademarkServiceImpl();
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
        String action = request.getParameter("action");
        try {
            if (action == null) {
                getMainPage(request, response);
            } else switch (action) {
                case "get":
                    get(request, response);
                    break;
                case "create":
                    create(request, response);
                    break;
                case "update":
                    update(request, response);
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
                case "getListNameHasProduct":
                    getListNameHasProduct(request, response);
                    break;
                default:
                    getMainPage(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void getMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        request.setAttribute("title", "QUẢN LÝ THƯƠNG HIỆU");
        List<Trademark> trademarks = trademarkService.getList();
        List<Province> provinces = addressService.getProvinceList();
        request.setAttribute("trademarks", trademarks);
        request.setAttribute("provinces", provinces);
        request.getRequestDispatcher("/view/admin/trademark.jsp").forward(request, response);
    }

    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Trademark trademark = trademarkService.get(id);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(trademark));
        out.close();
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String name = request.getParameter("name");
        String website = request.getParameter("website");
        trademarkService.create(new Trademark(0, name.trim(), website.trim()));
        response.sendRedirect(request.getContextPath() + "/admin/trademark");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String website = request.getParameter("website");
        trademarkService.update(new Trademark(id, name.trim(), website.trim()));
        response.sendRedirect(request.getContextPath() + "/admin/trademark");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        response.setContentType("application/json");
        String ids = request.getParameter("ids");
        StringTokenizer st = new StringTokenizer(ids, "[\",]");
        while (st.hasMoreTokens()) {
            int id = Integer.parseInt(st.nextToken());
            List<Address> addresses = addressService.getListByTrademarkId(id);
            for (Address address : addresses) {
                addressService.delete(address.getId());
            }
            trademarkService.delete(id);
        }
        PrintWriter pw = response.getWriter();
        pw.println(new Gson().toJson(new ResponseHandler(200, "Xóa thành công.")));
        pw.close();
    }
    
    private void changeActive(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        trademarkService.changeActive(id);
        response.sendRedirect(request.getContextPath() + "/admin/trademark");
    }
    
    private void checkExist(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.setContentType("application/json");
        String name = request.getParameter("name");
        String website = request.getParameter("website");
        PrintWriter out = response.getWriter();
        if (trademarkService.isExist(name.equals("") ? null : name.trim(), null))
            out.println(new Gson().toJson(new ResponseHandler(1, "Tên thương hiệu đã tồn tại.")));
        else if (trademarkService.isExist(null, website.equals("") ? null : website.trim()))
            out.println(new Gson().toJson(new ResponseHandler(2, "Website đã tồn tại.")));
        else out.println(new Gson().toJson(new ResponseHandler(0, "Tên thương hiệu và website hợp lệ.")));
        out.close();
    }
    
    private void getListNameHasProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json");
        List<String> names = trademarkService.getListNameHasProduct();
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(names));
        out.close();
    }
}
