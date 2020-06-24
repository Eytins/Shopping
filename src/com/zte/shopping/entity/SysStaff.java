package com.zte.shopping.entity;


public class SysStaff {

    private long staffId;
    private String staffName;
    private String loginName;
    private String password;
    private String phone;
    private String email;
    private long deptId;
    private String role;
    private long isValid;
    private java.sql.Timestamp createDate;
    private long createStaffId;


    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }


    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public long getIsValid() {
        return isValid;
    }

    public void setIsValid(long isValid) {
        this.isValid = isValid;
    }


    public java.sql.Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.sql.Timestamp createDate) {
        this.createDate = createDate;
    }


    public long getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(long createStaffId) {
        this.createStaffId = createStaffId;
    }

}
