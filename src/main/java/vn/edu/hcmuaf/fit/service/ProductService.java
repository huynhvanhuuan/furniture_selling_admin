package vn.edu.hcmuaf.fit.service;


import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.product.ProductDto;
import vn.edu.hcmuaf.fit.entity.Product;

import java.util.List;

public interface ProductService {
	AppServiceResult<List<ProductDto>> getProducts();
	AppServiceResult<ProductDto> getProductByStatus(long id);
	AppServiceResult<ProductDto> getProduct(int id);
	AppServiceResult<ProductDto> createProduct(Product item);
	AppServiceResult<ProductDto> updateProduct(Product item);
	AppBaseResult deleteProduct(int id);
	AppServiceResult<ProductDto> updateStatus(int id);
}
