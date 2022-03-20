package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.model.Product;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ProductDAO {
    List<Product> getList() throws SQLException, ParseException;
    Product get(int id) throws SQLException, ParseException;
    void create(Product item) throws SQLException;
    void delete(int id) throws SQLException;
    void update(Product item) throws SQLException;
    void changeActive(int id) throws SQLException;

    Map<Integer, Map<String, String>> getListData() throws SQLException;
    Map<Integer, Map<String, String>> getListData(int countProduct) throws SQLException;
    List<Product> getList(int countProduct) throws SQLException, ParseException;
    Map<Integer, Map<String, String>> getCartOrWishlist(String userId, boolean getCart) throws SQLException;
    Map<Integer, Map<String, String>> getListByCondition(boolean getNewProduct, int countProduct) throws SQLException;

}
