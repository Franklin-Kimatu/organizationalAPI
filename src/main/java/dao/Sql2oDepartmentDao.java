package dao;

import models.Article;
import models.Department;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {
    private final Sql2o sql2o;

    public  Sql2oDepartmentDao(Sql2o sql2o){
        this.sql2o =sql2o;
    }

    @Override
    public  void add(Department department){
      //  String sql ="INSERT INTO departments (departmentName, description,employeesNumber)VALUES (:departmentName,:description,:employeesNumber)";
        String sql ="INSERT INTO  departments (departmentName, description, employeesNumber) VALUES(:departmentName,:description,:employeesNumber)";
        try(Connection conn =sql2o.open()){
            int id =(int) conn.createQuery(sql,true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);

        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM  departments")
                    .executeAndFetch(Department.class);
        }
    }
//deleting from the join table
    @Override
    public Department findById(int id){
                try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM departments WHERE id =:id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public void update (int id,String newDepartmentName,String newDescription,int newEmployeesNumber){
        String sql = "UPDATE  departments SET (departmentName,description,employeesNumber) =(:departmentName,:description,:employeesNumber) WHERE id =:id";
        try(Connection conn =sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentName", newDepartmentName)
                    .addParameter("description",newDescription)
                    .addParameter("employeesNumber",newEmployeesNumber)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "DELETE  from departments WHERE id =:id";
        String deleteJoin = "DELETE  from departments_articles WHERE departmentId =:departmentId";

        try(Connection conn =sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
            conn.createQuery(deleteJoin)
                    .addParameter("departmentId",id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public  void clearAll(){
        String sql ="DELETE from departments";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    //methods  for having the many to many relationship for the departments and the articles
    @Override
    public void addDepartmentToArticle(Department department, Article article){
        String sql ="INSERT INTO departments_articles(departmentId,articleId) VALUES(:departmentId,:articleId)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentId",department.getId())
                    .addParameter("articleId",article.getId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Article> getAllArticlesForADepartment(int departmentId){
        ArrayList<Article> articles = new ArrayList<>();
        String joinQuery ="SELECT articleId FROM departments_articles WHERE departmentId =:departmentId";
        try(Connection conn= sql2o.open()){
            List<Integer> allArticlesId =conn.createQuery(joinQuery)
                    .addParameter("departmentId",departmentId)
                    .executeAndFetch(Integer.class);
            for(Integer articId:allArticlesId){
                String articleQuery = "SELECT * FROM articles WHERE id = :articleId";
                articles.add(
                        conn.createQuery(articleQuery)
                        .addParameter("articleId",articId)
                        .executeAndFetchFirst(Article.class));
            }
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
        return articles;
    }

}
