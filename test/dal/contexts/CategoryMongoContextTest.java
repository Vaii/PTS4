package dal.contexts;

import models.storage.Category;
import models.storage.DateTime;
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

    private CategoryContext categoryContext= new CategoryContext("DateTimeTest");

    @Before
    public void setup() {

    }

    @Test
    public void addCategory() throws Exception {
        Category newCategory = new Category("Java");
        categoryContext.addCategory(newCategory);

        List<Category> categories = categoryContext.getAllCategories();

        assertEquals(, );
    }
}