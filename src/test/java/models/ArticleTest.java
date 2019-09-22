package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContent(){
        Article testArticle = setupArticle();
        assertEquals("Raila",testArticle.getContent());

    }
    @Test
    public void setContent(){
        Article testArticle = setupArticle();
        testArticle.setContent("Uhuru");
        assertNotEquals("Raila",testArticle.getContent());
    }
    @Test
    public void setId(){
        Article testArticle = setupArticle();
        testArticle.setId(2);
        assertNotEquals(1,testArticle.getId());
    }

    //helper
    public Article setupArticle(){
        return new Article("Raila");

    }
}