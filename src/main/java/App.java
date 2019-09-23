import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oArticleDao;
import dao.Sql2oUserDao;
import models.Department;

import models.User;
import org.sql2o.*;
import static spark.Spark.*;
public class App {
    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oArticleDao articleDao;
        Sql2oUserDao userDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString ="jdbc:h2:~/organizationalAPI.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";

        Sql2o sql2o = new Sql2o(connectionString, "","");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        articleDao = new Sql2oArticleDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn =sql2o.open();

        post("/departments/new","application/json",(request, response) -> {
            Department department =gson.fromJson(request.body(),Department.class);
            departmentDao.add(department);
            response.status(201);
            response.type("application/json");
            return gson.toJson(department);
        });

        get("/departments","application/json",(request, response) -> {
            response.type("application/json");
            return gson.toJson(departmentDao.getAll());
        });

        get("/departments/:id","application/json",(request, response) -> {
            response.type("application/json");
            int departmentId =Integer.parseInt(request.params("id"));
            response.type("application/json");
            return gson.toJson(departmentDao.findById(departmentId));
        });

        //posting users for a specific department
        post("/departments/:departmentId/users/new","application/json",(request, response) -> {
            int departmentId =Integer.parseInt(request.params("departmentId"));
            User user =gson.fromJson(request.body(),User.class);

            user.setDepartmentId(departmentId);
            userDao.add(user);
            response.status(201);
            return gson.toJson(user);
        });

        //filters
        after((request, response) -> {
            response.type("application/json");
        });
    }
}
