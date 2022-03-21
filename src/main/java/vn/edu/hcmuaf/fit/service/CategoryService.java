package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.domain.AppBaseResult;
import vn.edu.hcmuaf.fit.domain.AppServiceResult;
import vn.edu.hcmuaf.fit.dto.category.CategoryCreate;
import vn.edu.hcmuaf.fit.dto.category.CategoryDto;
import vn.edu.hcmuaf.fit.entity.Category;

import java.util.List;

public interface CategoryService {
	AppServiceResult<List<CategoryDto>> getCategories();
	AppServiceResult<CategoryDto> getCategory(Long id);
	AppServiceResult<CategoryDto> getCategoryBySku(String sku);
	AppServiceResult<CategoryDto> getCategoryByName(String name);
	AppServiceResult<CategoryDto> createCategory(CategoryCreate category);
	AppServiceResult<CategoryDto> updateCategory(Category category);
	AppBaseResult deleteCategory(Long id);
	AppServiceResult<CategoryDto> updateStatus(Long id);
}
