package dao;

import models.Article;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sql2o.*;

public class Sql2oArticleDaoTest {
    private  Sql2oArticleDao articleDao;
    private Sql2oDepartmentDao departmentDao;
    private Connection conn;


    @Before
    public void setUp() throws Exception {

        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        articleDao =new Sql2oArticleDao(sql2o);
        conn =sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingUserSetsId() throws  Exception{
        Article testArticle =setupNewArticle();
        int originalArticleId = testArticle.getId();
        articleDao.add(testArticle);
        assertNotEquals(originalArticleId,testArticle.getId());
    }

    @Test
    public void addedArticlesAreReturnedFromGetAll() throws  Exception{
        Article testArticle = setupNewArticle();
        articleDao.add(testArticle);
        assertEquals(1,articleDao.getAll().size());
    }

    @Test
    public void noArticlesReturnedEmptyList() throws Exception{
        assertEquals(0,articleDao.getAll().size());
    }

    @Test
    public void deleteByIdDeeletesCorrectArticle() throws Exception{
        Article article =setupNewArticle();
        articleDao.add(article);
        articleDao.deleteById(article.getId());
        assertEquals(0,articleDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception{
        Article testArticle = setupNewArticle();
        Article otherArticle = setupNewArticle();
        articleDao.clearAll();
        assertEquals(0,articleDao.getAll().size());
    }

    //helper method
    public Article setupNewArticle(){
        return  new Article("Raila");
    }
}