package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUserName(){
        User testUser = setupUser();
        assertEquals("Frank",testUser.getUserName());
    }
    @Test
    public void getUserCompanyPosition(){
        User testUser = setupUser();
        assertEquals("Manager",testUser.getUserCompanyPosition());
    }
    @Test
    public void getUserRole(){
        User testUser = setupUser();
        assertEquals("Signing cheques",testUser.getUserRole());
    }
    @Test
    public  void getDepartmentId(){
        User testUser = setupUser();
        assertEquals(1,testUser.getDepartmentId());
    }
    @Test
    public void setUserName(){
        User testUser = setupUser();
        testUser.setUserName("Yvonne");
        assertNotEquals("Frank",testUser.getUserName());
    }
    @Test
    public void setUserCompanyPosition(){
        User testUser = setupUser();
        testUser.setUserCompanyPosition("Director");
        assertNotEquals("Manager",testUser.getUserCompanyPosition());
    }
    @Test
    public void setUserRole(){
        User testUser = setupUser();
        testUser.setUserRole("Chairing meetings");
        assertNotEquals("Signing cheques",testUser.getUserRole());
    }
    @Test
    public void setDepartmentId(){
        User testUser = setupUser();
        testUser.setDepartmentId(2);
        assertNotEquals(1,testUser.getDepartmentId());
    }

    //helper
    public User setupUser(){
        return new User("Frank","Manager","Signing cheques",1);
    }
}