package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.dto.cart.CartItem;
import vn.edu.hcmuaf.fit.dto.wishlist.Wishlist;
import vn.edu.hcmuaf.fit.helper.ResponseHandler;
import vn.edu.hcmuaf.fit.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@WebServlet(name = "HomeController", value = "/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private TrademarkService trademarkService;
    private CategoryService categoryService;
    private WarehouseService warehouseService;
    private CartService cartService;
    private WishlistService wishlistService;
    private AddressService addressService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        trademarkService = new TrademarkServiceImpl();
        categoryService = new CategoryServiceImpl();
        warehouseService = new WarehouseServiceImpl();
        cartService = new CartServiceImpl();
        wishlistService = new WishlistServiceImpl();
        addressService = new AddressServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String action = request.getParameter("action");
        try {
            if (action != null) {
                switch (action) {
                    case "addToCart":
                        addToCart(request, response);
                        break;
                    case "updateQuantity":
                        updateQuantity(request, response);
                        break;
                    case "removeFromCart":
                        removeFromCart(request, response);
                        break;
                    case "removeAllFromCart":
                        removeAllFromCart(request, response);
                        break;
                }
            }
            switch (path) {
                case "/product":
                    getProductPage(request, response);
                    break;
                case "/wishlist":
                    getWishlistPage(request, response);
                    break;
                case "/cart":
                    getCartPage(request, response);
                    break;
                case "/profile":
                    getProfilePage(request, response);
                    break;
                case "/contact":
                    getContactPage(request, response);
                    break;
                case "/about":
                    getAboutPage(request, response);
                    break;
                case "/faq":
                    getFAQPage(request, response);
                    break;
                default:
                    getHomePage(request, response);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    private void getHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
//        List<Product> products = productService.getList();
//        request.setAttribute("products", products);
//        
//        List<ProductDetail> productDetails = warehouseService.getProductList();
//        request.setAttribute("product-details", productDetails);
//        
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("auth");
//        
//        if (user != null) {
//            List<CartItem> carts = cartService.getList(user);
//            request.setAttribute("carts", carts);
//            
//            Wishlist wishlist = wishlistService.getList(user);
//            request.setAttribute("wishlist", wishlist);
//        }
        
        request.getRequestDispatcher("/view/index.jsp").forward(request, response);
    }
    
    private void getProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");

        List<Address> addresses = addressService.getListByUserId(Integer.parseInt(user.getId()));
        request.setAttribute("addresses", addresses);

        List<Order> orders = orderService.getListByUserId(user.getId());
        request.setAttribute("orders", orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/profile.jsp");
        dispatcher.forward(request, response);
    }
    
    private void getCartPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        if (user != null) {
            Wishlist wishlist = wishlistService.getList(user);
            request.setAttribute("wishlist", wishlist);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/cart.jsp");
        dispatcher.forward(request, response);
    }
    
    private void getWishlistPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");

        if (user != null) {
            List<CartItem> carts = cartService.getList(user);
            request.setAttribute("carts", carts);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/wishlist.jsp");
        dispatcher.forward(request, response);
    }

    private void getProductPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        if (request.getParameter("id") == null) {
            List<ProductDetail> products = warehouseService.getProductList();
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/product.jsp");
            dispatcher.forward(request, response);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productService.get(id);
            request.setAttribute("product", product);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/product-detail.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void getContactPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/contact.jsp").forward(request, response);
    }

    private void getAboutPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/about.jsp").forward(request, response);
    }

    private void getFAQPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/faqs.jsp").forward(request, response);
    }

    private void getListProductData(HttpServletRequest request, HttpServletResponse response, int countProduct) throws SQLException, ParseException {
        /* HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // get list product;
        List<Product> products = productService.getList(countProduct);
        request.setAttribute("products", products);

        // get products detail
        Map<Integer, Map<String, String>> productsDetails = productService.getListData(countProduct);
        request.setAttribute("product-details", productsDetails);

        // get cart
        Map<Integer, Map<String, String>> cart = productService.getCartOrWishlist(userId, true);
        request.setAttribute("cart", cart);

        // get wishlist
        Map<Integer, Map<String, String>> wishlist = productService.getCartOrWishlist(userId, false);
        request.setAttribute("wishlist", wishlist);*/
    }

    private void getListNewNDiscount(HttpServletRequest request, HttpServletResponse response, int countProduct) throws SQLException {
        /*// get list product new
        Map<Integer, Map<String, String>> listNew = productDAO.getListByCondition(true, countProduct);
        request.setAttribute("list_new", listNew);

        // get list product which has discount
        Map<Integer, Map<String, String>> listHasDiscount = productDAO.getListByCondition(false, countProduct);
        request.setAttribute("list_discount", listHasDiscount);*/
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        ProductDetail product = warehouseService.getProduct(request.getParameter("sku"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        PrintWriter out = response.getWriter();
        if (cartService.addToCart(new CartItem(user, product, quantity))) {
            out.println(new Gson().toJson(new ResponseHandler(1, "Thêm vào giỏ hàng thành công.")));
        } else {
            out.println(new Gson().toJson(new ResponseHandler(0, "Thêm vào giỏ hàng thất bại.")));
        }
    }
    
    private void updateQuantity(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException {
        User user = (User) request.getSession().getAttribute("user");
        ProductDetail product = warehouseService.getProduct(request.getParameter("sku"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        cartService.updateQuantity(new CartItem(user, product, quantity));
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException {
        User user = (User) request.getSession().getAttribute("user");
        ProductDetail product = warehouseService.getProduct(request.getParameter("sku"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        cartService.removeFromCart(new CartItem(user, product, quantity));
    }
    
    private void removeAllFromCart(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        cartService.removeAllFromCart(user.getId());
    }
}
