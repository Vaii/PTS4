package dal.contexts;

import models.storage.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class CategoryMongoContextTest {

    private CategoryMongoContext categoryMongoContext = new CategoryMongoContext("CategoryText");

    @Before
    public void setup() {
        categoryMongoContext.removeAll();
        Category newCategory = new Category("Scala");
        categoryMongoContext.addCategory(newCategory);
    }

    @Test
    public void addCategory() throws Exception {
        Category newCategory = new Category("Java");
        categoryMongoContext.addCategory(newCategory);

        List<Category> categories = categoryMongoContext.getAllCategories();
        assertEquals(2, categories.size());
    }

    @Test
    public void getCategoryById(){
        Category newcategory = new Category("C#");
        categoryMongoContext.addCategory(newcategory);
        Category category = categoryMongoContext.getCategoryByName("C#");
        assertEquals(newcategory.getCategory(), category.getCategory());
    }

    @Test
    public void getCategoryByName(){
        Category newcategory = new Category("C#");
        categoryMongoContext.addCategory(newcategory);
        Category category = categoryMongoContext.getCategoryByName("C#");
        assertEquals(newcategory.getCategory(), category.getCategory());
    }

    @Test
    public void removeCategory(){
        Category toRemove = categoryMongoContext.getCategoryByName("Scala");
        categoryMongoContext.removeCategory(toRemove);
        assertEquals(0, categoryMongoContext.getAllCategories().size());
    }
}