package dao;

import models.Article;

import java.util.List;

public interface ArticleDao {

    //create
    void add(Article article);
    //void addArticleToDepartment(Article article,Department department);

    //read
    List<Article> getAll();
    //List<Article> getAllDepartmentsForAnArticle(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}
