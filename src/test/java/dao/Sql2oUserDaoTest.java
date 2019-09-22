package dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {
    private Connection conn;
    private  Sql2oUserDao userDao;
    private Sql2oDepartmentDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        userDao =new Sql2oUserDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn =sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingUserSetsId() throws  Exception{
        User testUser = setupUser();
        assertEquals(1,testUser.getId());
    }

    @Test
    public void getAll() throws Exception{
        User user1 =setupUser();
        User user2 = setupUser();
        assertEquals(2,userDao.getAll().size());
    }

    @Test
    public void getAllUsersByDepartment() throws Exception{
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();

        User user1 =setupUserForADepartment(testDepartment);
        User user2 =setupUserForADepartment(testDepartment);
        User userForOtherDepartment =setupUserForADepartment(otherDepartment);

        assertEquals(2,userDao.getAllUsersByDepartment(testDepartment.getId()).size());
    }

    @Test
    public void deleteById()throws  Exception{
        User testUser =setupUser();
        User otherUser =setupUser();
        assertEquals(2,userDao.getAll().size());
        userDao.deleteById(testUser.getId());
        assertEquals(1,userDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception{
        User  testUser =setupUser();
        User otherUser = setupUser();
        userDao.clearAll();
        assertEquals(0,userDao.getAll().size());
    }

    //helper methods
    public User setupUser(){
        User user = new User("Frank","Manager","Signging cheques",1);
        userDao.add(user);
        return user;
    }
    public User setupUserForADepartment(Department department){
        User user =  new User("Frank","Manager","Signging cheques",department.getId());
        userDao.add(user);
        return user;
    }
    public Department setupDepartment(){
        Department department = new Department("Operations","daily errands",10);
        departmentDao.add(department);
        return department;
    }
}