package dao;

import models.Department;
import org.sql2o.*;

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
        String sql ="DELETE  from departments  WHERE id =:id";
        try(Connection conn =sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
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
}
