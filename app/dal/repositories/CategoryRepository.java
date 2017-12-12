package dal.repositories;

import dal.interfaces.CategoryContext;
import models.storage.Category;

import java.util.List;

public class CategoryRepository {

    private CategoryContext context;

    public CategoryRepository(CategoryContext context){
        this.context = context;
    }

    public List<Category> getAllCategories(){
        return context.getAllCategories();
    }

    public Boolean addCategory(Category category){
        return context.addCategory(category);
    }

    public Category getCategoryById(String categoryId) {
        return context.getCategoryById(categoryId);
    }
}
