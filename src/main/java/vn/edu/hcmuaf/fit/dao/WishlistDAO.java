package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.Wishlist;

import java.util.List;

public interface WishlistDAO extends BaseDAO<Wishlist> {
    List<Wishlist> findByUserId(Long userId);
    List<Wishlist> findByProductSku(String productSku);
    void remove(String userId, String productSku);
    void removeAll(String userId);
}
