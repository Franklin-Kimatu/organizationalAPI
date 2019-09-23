package dao;

import models.Article;
import models.Department;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Sql2oArticleDao implements ArticleDao{

    private final Sql2o sql2o;
    public Sql2oArticleDao(Sql2o sql2o){
        this.sql2o =sql2o;
    }

    @Override
    public void add(Article article){
        String sql ="INSERT INTO articles (content) VALUES(:content)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(article)
                    .executeUpdate()
                    .getKey();
            article.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Article> getAll(){
        try (Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM articles")
                    .executeAndFetch(Article.class);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "DELETE  from articles WHERE id =:id";
        try(Connection conn =sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll(){
        String sql ="DELETE  from articles";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    //methods for implementing the many to many relationship for articles and departments

    @Override
    public  void addArticleToDepartment(Article article, Department department){
        String sql ="INSERT INTO departments_articles(departmentId,articleId) VALUES (:departmentId,:articleId)";
        try(Connection conn =sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentId",department.getId())
                    .addParameter("articleId",article.getId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public  List <Department> getAllDepartmentsForAnArticle(int articleId){
        ArrayList<Department> departments = new ArrayList<>();
        String joinQuery ="SELECT departmentId FROM departments_articles WHERE articleId =:articleId";

        try(Connection conn =sql2o.open()){
            List<Integer> allDepartmentIds = conn.createQuery(joinQuery)
                    .addParameter("articleId",articleId)
                    .executeAndFetch(Integer.class);
            for( Integer departmentId : allDepartmentIds){
                String departmentQuery ="SELECT * FROM departments WHERE id =:departmentId";
                departments.add(conn.createQuery(departmentQuery)
                .addParameter("departmentId",departmentId)
                .executeAndFetchFirst(Department.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public Article findById(int id) {
        try(Connection conn  = sql2o.open()){
            return conn.createQuery("SELECT * FROM articles WHERE id =:id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Article.class);
        }
    }
}
