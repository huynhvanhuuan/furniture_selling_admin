package vn.edu.hcmuaf.fit.constant;

public class QUERY {
    /* PRODUCT */
    public static class PRODUCT {
        public static final String FIND_ALL = "select * from product";
        public static final String FIND_PRODUCT_BY_ID = "select * from product where id = ?";
        public static final String FIND_LAST_PRODUCT = "select * from product order by id desc limit 1";
        public static final String CREATE = "insert into product(name, description, trademark_id, category_sku) " +
                "values(?,?,?,?)";
        public static final String UPDATE = "update product set name = ?, description = ?, trademark_id = ?, category_sku = ?, active = ? where id = ?";
        public static final String DELETE = "delete from product where id = ?";
        public static final String UPDATE_STATUS = "update product set active = (case active when 1 then 0 when 0 then 1 end) where id = ?";
        public static final String FIND_ALL_BY_COUNT = "select * from product limit ?";
        public static final String FIND_ALL_HAS_DISCOUNT = "SELECT p.id, p.name, w.unit_price, w.image from product p " +
                "join warehouse w on p.id = w.product_id WHERE w.discount > 0 limit ?";
        public static final String FIND_ALL_NEW_BY_COUNT = "SELECT p.id, p.name, w.unit_price, w.image from product p " +
                "join warehouse w on p.id = w.product_id ORDER BY p.date_created DESC limit ?";
    }

    /* PRODUCT DETAIL */
    public static class PRODUCT_DETAIL {
        public static final String FIND_PRODUCT_DETAILS = "select discount, unit_price from warehouse where product_id = ?";
        public static final String FIND_CART_FROM_USER = "SELECT p.name, p.id, w.image, w.unit_price " +
                "FROM cart c JOIN warehouse w ON c.product_sku = w.sku JOIN product p ON w.product_id = p.id WHERE c.user_id = ?";
        public static final String FIND_WISHLIST_FROM_USER = "SELECT p.name, p.id, w.image, w.unit_price " +
                "FROM cart wl JOIN warehouse w ON wl.product_sku = w.sku JOIN product p ON w.product_id = p.id WHERE wl.user_id = ?";
    }

    /* COLOR */
    public static class COLOR {
        public static final String FIND_ALL = "select * from color";
    }

    /* MATERIAL */
    public static class MATERIAL {
        public static final String FIND_ALL = "select * from material";
    }

    /* WAREHOUSE */
    public static class WAREHOUSE {
        public static final String FIND_PRODUCT_LIST = "select * from warehouse";
        public static final String FIND_PRODUCT_LIST_WITH_PRODUCT_ID = "select * from warehouse where product_id = ?";
        public static final String FIND_COLOR_LIST = "select * from color";
        public static final String FIND_MATERIAL_LIST = "select * from material";
        public static final String FIND_PRODUCT = "select * from warehouse where sku = ?";
        public static final String FIND_COLOR = "select * from color where id = ?";
        public static final String FIND_MATERIAL = "select * from material where sku = ?";
        public static final String CREATE = "insert into warehouse(sku, product_id, image, color_id, material_sku, unit_price, unit_in_stock, discount) values(?,?,?,?,?,?,?,?)";
        public static final String UPDATE = "update warehouse set sku = ?, product_id = ?, image = ?, color_id = ?, material_sku = ?, unit_price = ?, unit_in_stock = ?, discount = ? where sku = ? and date_created = ?";
        public static final String DELETE = "delete from warehouse where sku = ? and date_created = ?";
        public static final String CHANGE_ACTIVE = "update warehouse set active = (case active when 1 then 0 when 0 then 1 end) where sku = ?";
    }

    /* CATEGORY */
    public static class CATEGORY {
        public static final String FIND_ALL = "select * from category";
        public static final String FIND_CATEGORY_BY_ID = "select * from category where id = ?";
        public static final String FIND_CATEGORY_BY_SKU = "select * from category where sku like ?";
        public static final String FIND_CATEGORY_BY_NAME = "select * from category where name like ?";
        public static final String CREATE = "insert into category(sku, name) values(?,?)";
        public static final String UPDATE = "update category set sku = ?, name = ? where id = ?";
        public static final String DELETE = "delete from category where id = ?";
    }

    /* TRADEMARK */
    public static class TRADEMARK {
        public static final String FIND_ALL = "select * from trademark";
        public static final String FIND_ALL_NAME_HAS_PRODUCT = "select distinct trademark_id from product";
        public static final String FIND_TRADEMARK_BY_ID = "select * from trademark where id = ?";
        public static final String FIND_BY_NAME_OR_WEBSITE = "select * from trademark where name like ? or website like ?";
        public static final String CREATE = "insert into trademark(name, website) values(?,?)";
        public static final String CREATE_TRADEMARK_ADDRESS = "insert into address(number, street, ward_id, district_id, path) values(?,?,?,?,?)";
        public static final String UPDATE = "update trademark set name = ?, website = ? where id = ?";
        public static final String DELETE = "delete from trademark where id = ?";
        public static final String CHANGE_ACTIVE = "update trademark set active = (case active when 1 then 0 when 0 then 1 end) where id = ?";
    }

    /* USER */
    public static class USER {
        public static final String FIND_ALL = "select * from user";
        public static final String FIND_BY_ID = "select * from user where id = ?";
        public static final String FIND_BY_EMAIL = "select * from user where email = ?";
        public static final String CREATE = "insert into user(id, first_name, last_name, username, password, email, phone, female, profile_image_url, date_created, role) value(?,?,?,?,?,?,?,?,?,?,?)";
        public static final String UPDATE = "update user set first_name = ?, last_name = ?, password = ?, email = ?, phone = ? where id = ?";
        public static final String DELETE = "delete from user where id = ?";
        public static final String CHANGE_PASSWORD = "update user set password = ? where id = ?";
        public static final String UPDATE_LOCK_STATUS = "update user set locked = ? where id = ?";
        public static final String ADD_ADDRESS = "insert into user_address(user_id, address_id) values(?,?)";
        public static final String REMOVE_ADDRESS = "delete from trademark_address where address_id = ?";
    }

    /* ADDRESS */
    public static class ADDRESS {
        public static final String FIND_BY_TRADEMARK_ID = "select a.* from address a inner join trademark_address ta on a.id = ta.address_id where trademark_id = ?";
        public static final String FIND_BY_USER_ID = "select a.* from address a inner join user_address ua on a.id = ua.address_id where user_id = ?";
        public static final String FIND_BY_ID = "select * from address where id = ?";
        public static final String FIND_BY_PATH = "select * from address where path like ?";
        public static final String CREATE = "insert into address(number, street, ward_id, district_id, path) values(?,?,?,?,?)";
        public static final String UPDATE = "update address set number = ?, street = ?, ward_id = ?, district_id = ?, path = ? where id = ?";
        public static final String DELETE = "delete from address where id = ?";
        public static final String FIND_LAST_ID = "select id from address order by id desc limit 1";
    }
    
    /* PROVINCE */
    public static class PROVINCE {
        public static final String FIND_ALL = "select * from province";
        public static final String FIND_PROVINCE_BY_ID = "select * from province where id = ?";
    }
    
    /* DISTRICT */
    public static class DISTRICT {
        public static final String FIND_ALL = "select * from district";
        public static final String FIND_DISTRICT_BY_ID = "select * from district where id = ?";
        public static final String FIND_DISTRICT_BY_PROVINCE_ID = "select * from district where province_id = ?";
    }
    
    /* WARD */
    public static class WARD {
        public static final String FIND_ALL = "select * from ward";
        public static final String FIND_BY_ID = "select * from ward where id = ?";
        public static final String FIND_WARD_BY_DISTRICT_ID = "select * from ward where district_id = ?";
    }

    /* CART */
    public static class CART {
        public static final String FIND_ALL = "select * from cart where user_id like ?";
        public static final String ADD = "insert into cart values(?,?,?)";
        public static final String UPDATE = "update cart set quantity = ? where user_id like ? and product_sku like ?";
        public static final String REMOVE = "delete from cart where user_id like ? and product_sku like ?";
        public static final String REMOVE_ALL = "delete from cart where user_id like ?";
    }

    /* WISHLIST */
    public static class WISHLIST {
        public static final String FIND_ALL = "select * from wishlist where user_id like ?";
        public static final String ADD = "insert into wishlist values(?,?)";
        public static final String REMOVE = "delete from wishlist where user_id like ? and product_sku like ?";
        public static final String REMOVE_ALL = "delete from wishlist where user_id like ?";
    }

    /* ORDER */
    public static class ORDER {
        public static final String FIND_ALL = "select * from order where user_id like ?";
        public static final String FIND_DETAIL = "select * from order_detail where order_id like ?";
    }
}
