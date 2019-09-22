package dao;

import models.Department;

import java.util.List;

public interface DepartmentDao {

    //create
    void add (Department department);
    //void addDepartmentToArticle(Department department,Article article)

    //read
    List<Department> getAll();
    Department findById(int id);
    //List<Article> getAllArticlesForADepartment(int departmentId);

    //update
    void update (int id,String departmentName,String description,int employeesNumber);

    //delete
    void deleteById(int id);
    void clearAll();
}
