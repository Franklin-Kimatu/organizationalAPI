package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDepartmentNameReturnsCorrectName() throws Exception{
        Department testDepartment =setupDepartment();
        assertEquals("Operations",testDepartment.getDepartmentName());
    }

    @Test
    public void getDepartmentDescriptionReturnDescriptionCorrectDescription(){
        Department testDepartment =setupDepartment();
        assertEquals("Daily errands",testDepartment.getDepartmentDescription());
    }

    @Test
    public void getEmployeesNumberReturnsCorrectNumber() throws Exception{
        Department testDepartment =setupDepartment();
        assertEquals(10,testDepartment.getEmployeesNumber());
    }

    //testing setters
    @Test
    public void setDepartmentNameSetCorrectAddress() throws Exception{
        Department testDepartment =setupDepartment();
        testDepartment.setDepartmentName("Managerial");
        assertNotEquals("Operations",testDepartment.getDepartmentName());
    }
    @Test
    public  void setDepartmentDescriptionSetsCorrectDescription() throws Exception{
        Department testDepartment =setupDepartment();
        testDepartment.setDescription("Manage people");
        assertNotEquals("Daily errands",testDepartment.getDepartmentDescription());
    }
    @Test
    public void setEmployeesNumberSetEmpolyeesCorrectly() throws Exception{
        Department testDepartment =setupDepartment();
        testDepartment.setEmployeesNumber(20);
        assertNotEquals(10,testDepartment.getEmployeesNumber());
    }

    //helper department methods
    public Department setupDepartment(){
        return new Department("Operations","Daily errands",10);

    }
}