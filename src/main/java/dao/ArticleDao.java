package dao;

import models.Article;
import models.Department;

import java.util.List;

public interface ArticleDao {

    //create
    void add(Article article);
    void addArticleToDepartment(Article article, Department department);

    //read
    List<Article> getAll();
    List<Department> getAllDepartmentsForAnArticle(int id);
//find
    Article findById(int id);
    //delete
    void deleteById(int id);
    void clearAll();
}
