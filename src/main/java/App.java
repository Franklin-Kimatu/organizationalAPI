import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oArticleDao;
import dao.Sql2oUserDao;
import exception.ApiExceptions;
import models.Article;
import models.Department;

import models.User;
import org.sql2o.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            int departmentId =Integer.parseInt(request.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if(departmentToFind ==null){
                throw new ApiExceptions(404,String.format("No department with the id: \"%s\" exists", request.params("id")));
            }else {
                response.type("application/json");
                return gson.toJson(departmentDao.findById(departmentId));
            }

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
        get("/departments/:id/users","application/json",(request, response) -> {
            int departmentId = Integer.parseInt(request.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            List<User> allUsers;
            if(departmentToFind ==null){
                throw new ApiExceptions(404,String.format("No department with the id: \"%s\" exists",request.params("id")));
            }
                response.type("application/json");
                allUsers=userDao.getAllUsersByDepartment(departmentId);
                return gson.toJson(allUsers);

        });
//adding routes for a many to many relationship
        post("/departments/:departmentId/article/:articleId","application/json",(request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            int articleId =Integer.parseInt(request.params("articleId"));
            Department department = departmentDao.findById(departmentId);
            Article article =articleDao.findById(articleId);
            System.out.println(department);
            if(department != null && article != null){
                articleDao.addArticleToDepartment(article,department);
                response.status(201);
                return gson.toJson(String.format("Department '%s' and Article '%s' have been associated",article.getContent(),department.getDepartmentName()));
            }else{
                throw new ApiExceptions(404,String.format("Department or Article does not exist"));
            }
        });
//getting or retrieving information for the posted many to many information
        get("/departments/:id/articles","application/json",(request, response) -> {
            int departmentId = Integer.parseInt(request.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if(departmentToFind ==null) {
                throw new ApiExceptions(404, String.format("No department with the id: \"&s\"exists", request.params("id")));
            }
                else if(departmentDao.getAllArticlesForADepartment(departmentId).size()==0){
                    return "{\"message:\"I'm sorry,but no articles are listed for this department.\"}";

                }else{
                    return gson.toJson(departmentDao.getAllArticlesForADepartment(departmentId));
            }

        });

        exception(ApiExceptions.class, (exc, req, res) -> {
            ApiExceptions err = (ApiExceptions) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json"); //after does not run in case of an exception.
            res.status(err.getStatusCode()); //set the status
            res.body(gson.toJson(jsonMap));  //set the output.
        });

        //filters
        after((request, response) -> {
            response.type("application/json");
        });
    }
}
