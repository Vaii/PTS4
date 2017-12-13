package dal.contexts;

import models.storage.Category;
import models.storage.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class CategoryMongoContextTest {

    private CategoryContext categoryContext= new CategoryContext("CategoryText");

    @Before
    public void setup() {
        categoryContext.removeAll();
        Category newCategory = new Category("Scala");
        categoryContext.addCategory(newCategory);
    }

    @Test
    public void addCategory() throws Exception {
        Category newCategory = new Category("Java");
        categoryContext.addCategory(newCategory);

        List<Category> categories = categoryContext.getAllCategories();
        assertEquals(2, categories.size());
    }

    @Test
    public void getCategoryById(){
        Category newcategory = new Category("C#");
        categoryContext.addCategory(newcategory);
        Category category = categoryContext.getCategoryByName("C#");
        assertEquals(newcategory.getCategory(), category.getCategory());
    }

    @Test
    public void getCategoryByName(){
        Category newcategory = new Category("C#");
        categoryContext.addCategory(newcategory);
        Category category = categoryContext.getCategoryByName("C#");
        assertEquals(newcategory.getCategory(), category.getCategory());
    }

    @Test
    public void removeCategory(){
        Category toRemove = categoryContext.getCategoryByName("Scala");
        categoryContext.removeCategory(toRemove);
        assertEquals(0, categoryContext.getAllCategories().size());
    }
}