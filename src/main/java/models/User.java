package models;

import java.util.Objects;

public class User {
    //getter and setter for the id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // getter and setter for the restaurantid used in for the one to many database relationship
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    //getter and setters for the user role
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    //getter and setter for the user company position
    public String getUserCompanyPosition() {
        return userCompanyPosition;
    }

    public void setUserCompanyPosition(String userCompanyPosition) {
        this.userCompanyPosition = userCompanyPosition;
    }

    //getter and setter for user name
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return departmentId == user.departmentId &&
                id == user.id &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userCompanyPosition, user.userCompanyPosition) &&
                Objects.equals(userRole, user.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userCompanyPosition, userRole, departmentId, id);
    }

    private String userName;
    private String userCompanyPosition;
    private String userRole;
    private int departmentId;
    private int id;

    public User(String userName,String userCompanyPosition,String userRole,int departmentId){
        this.userName =userName;
        this.userCompanyPosition =userCompanyPosition;
        this.userRole =userRole;
        this.departmentId =departmentId;
    }

}
