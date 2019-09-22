package dao;

import models.Article;
import org.sql2o.*;

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
}
