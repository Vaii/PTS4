package dal.interfaces;
import models.storage.Category;

import java.util.List;

public interface CategoryContext {

    List<Category> getAllCategories();
    Boolean addCategory(Category category);
    Category getCategoryById(String categoryId);
    Category getCategoryByName(String categoryName);
    Boolean removeCategory(Category category);

}
