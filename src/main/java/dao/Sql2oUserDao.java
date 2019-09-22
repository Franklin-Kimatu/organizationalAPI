package dao;

import models.User;
import org.sql2o.*;

import java.util.List;

public class Sql2oUserDao implements UserDao {
    private final Sql2o sql2o;
    public Sql2oUserDao(Sql2o sql2o){
        this.sql2o =sql2o;
    }

    @Override
    public void add(User user){
        String sql = "INSERT INTO users(userName,userCompanyPosition,userRole,departmentId) VALUES (:userName,:userCompanyPosition,:userRole,:departmentId)";
        try(Connection conn  =sql2o.open()){
            int  id =(int) conn.createQuery(sql,true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAll(){
        try(Connection conn =sql2o.open()){
            return conn.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public List<User>getAllUsersByDepartment(int departmentId){
        try (Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM users WHERE departmentId = :departmentId")
                    .addParameter("departmentId",departmentId)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public  void deleteById(int id){
        String sql = "DELETE from users WHERE id =:id";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll(){
        String sql ="DELETE from users";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
