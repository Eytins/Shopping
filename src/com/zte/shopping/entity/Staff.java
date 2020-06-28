package com.zte.shopping.entity;

import java.util.Date;

/**
 * Created by Eytins
 */

public class Staff {

    private static final long    serialVersionUID = 1L;
    private              Integer staffId;
    private              String  staffName;
    private              String  loginName;
    private              String  password;
    private              String  phone;
    private              String  email;
    private              Dept    dept;
    private              String  role;
    private              Integer isValid;
    private              Date    createDate;
    private              Staff   createStaff;

    public Staff() {
        super();
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
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

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Staff getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Staff createStaff) {
        this.createStaff = createStaff;
    }

    @Override
    public String toString() {
        return "Staff [staffId=" + staffId + ", staffName=" + staffName
                + ", loginName=" + loginName + ", password=" + password
                + ", phone=" + phone + ", email=" + email + ", dept=" + dept
                + ", role=" + role + ", isValid=" + isValid + ", createDate="
                + createDate + ", createStaff=" + createStaff + "]";
    }

}
