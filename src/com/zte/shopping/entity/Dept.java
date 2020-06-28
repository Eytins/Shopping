package com.zte.shopping.entity;


import java.util.Date;

/**
 * Created by Eytins
 */

public class Dept {

    private Integer deptId;
    private String  deptName;
    private String  deptNo;
    private long    fatherDeptId;
    private String  remark;
    private Date    createDate;
    private long    createStaffId;
    private long    isValid;
    private Dept    fatherDept;
    private Staff   staff;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public long getFatherDeptId() {
        return fatherDeptId;
    }

    public void setFatherDeptId(long fatherDeptId) {
        this.fatherDeptId = fatherDeptId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(long createStaffId) {
        this.createStaffId = createStaffId;
    }

    public long getIsValid() {
        return isValid;
    }

    public void setIsValid(long isValid) {
        this.isValid = isValid;
    }

    public Dept getFatherDept() {
        return fatherDept;
    }

    public void setFatherDept(Dept fatherDept) {
        this.fatherDept = fatherDept;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }
}
