package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.model.Category;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.model.Trademark;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "ProductController", value = "/admin/product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ProductService productService;
    private CategoryService categoryService;
    private TrademarkService trademarkService;
    
    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        categoryService = new CategoryServiceImpl();
        trademarkService = new TrademarkServiceImpl();
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
                case "create":
                    create(request, response);
                    break;
                case "update":
                    update(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "get":
                    get(request, response);
                    break;
                case "changeActive":
                    changeActive(request, response);
                    break;
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    private void getMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        request.setAttribute("title", "PRODUCT MANAGEMENT");
        List<Product> products = productService.getList();
        request.setAttribute("products", products);
        List<Trademark> trademarks = trademarkService.getList();
        request.setAttribute("trademarks", trademarks);
        List<Category> categories = categoryService.getList();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher(request.getContextPath() + "/view/product.jsp").forward(request, response);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.get(id);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(product));
        out.close();
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int trademarkId = Integer.parseInt(request.getParameter("trademark"));
        String categorySku = request.getParameter("category");
        Trademark trademark = trademarkService.get(trademarkId);
        Category category = categoryService.get(categorySku);
        //productDAO.save(new Product(0, name, description, trademark, category, new Date(), new Date(), true));
        response.sendRedirect(request.getContextPath() + "/admin/product");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String size = request.getParameter("size");
        String description = request.getParameter("description-content");
        int trademarkId = Integer.parseInt(request.getParameter("trademark"));
        String categorySku = request.getParameter("category");
        Trademark trademark = trademarkService.get(trademarkId);
        Category category = categoryService.get(categorySku);
        String dateCreated = request.getParameter("dateCreated");
        String lastUpdated = request.getParameter("lastUpdated");
        //boolean active = Integer.parseInt(request.getParameter("active")) == 1;
        productService.update(new Product(id, name, size, description, trademark, category, dateFormat.parse(dateCreated), dateFormat.parse(lastUpdated), true));
        getMainPage(request, response);
        // Get part
        // Part part = request.getPart("image");
        // Get filename
        //String filename = part.getSubmittedFileName();
        // Setup path
        //String path = getFolderUpload().getAbsolutePath() + File.separator + System.currentTimeMillis() + "-" + filename;
        // Write file
        //part.write(path);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        getMainPage(request, response);
    }
    
    private void changeActive(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.changeActive(id);
        response.sendRedirect(request.getContextPath() + "/admin/product");
    }
    
    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Furniture Selling/images/product");
        if (!folderUpload.exists()) {
            if (folderUpload.mkdirs()) return folderUpload;
        }
        return folderUpload;
    }
}
