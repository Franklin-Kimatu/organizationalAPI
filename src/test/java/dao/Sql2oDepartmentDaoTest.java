package dao;

import com.sun.org.apache.bcel.internal.ExceptionConst;
import models.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;


import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private Sql2oDepartmentDao departmentDao;
    private Sql2oArticleDao articleDao;
    private Connection conn;
    private Sql2oUserDao userDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        articleDao = new Sql2oArticleDao(sql2o);
        userDao =new Sql2oUserDao(sql2o);
        conn =sql2o.open();
    }


    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetId() throws Exception{
        Department testDepartment =setupDepartment();
        assertNotEquals(0,testDepartment.getId());
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception{
        Department testDepartment = setupDepartment();
        assertEquals(1,departmentDao.getAll().size());
    }

    @Test
    public  void noDepartmentReturnEmptyList() throws Exception{
        assertEquals(0,departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception{
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        assertEquals(testDepartment,departmentDao.findById(testDepartment.getId()));
    }

    @Test
    public  void updateCorrectlyUpdateAllFields() throws Exception{
        Department testDepartment = setupDepartment();
        departmentDao.update(testDepartment.getId(),"Packing","putting cartons",20);
        Department foundDepartment = departmentDao.findById(testDepartment.getId());
        assertEquals("Packing",foundDepartment.getDepartmentName());
        assertEquals("putting cartons",foundDepartment.getDepartmentDescription());
        assertEquals(20,foundDepartment.getEmployeesNumber());
    }

    @Test
    public void deleteByIdDeleteCorrectlyRestaurant() throws  Exception{
        Department testDepartment = setupDepartment();
        Department otherDepartment =setupDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(1,departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception{
        Department testDepartment =setupDepartment();
        Department otherDepartment =setupDepartment();
        departmentDao.clearAll();
        assertEquals(0,departmentDao.getAll().size());
    }
    //helper Methods
    public Department setupDepartment(){
        Department department = new Department("Operations","Daily errands",10);
        departmentDao.add(department);
        return department;
    }
}