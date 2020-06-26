package com.zte.shopping.entity;

import java.util.Date;

/**
 * Created by Eytins
 */

public class Dept {

    private static final long serialVersionUID = 1L;

    // 部门表主键
    private Integer deptId;

    // 部门名称  不能重复
    private String deptName;

    // 部门编号   部门前缀BM + 当前的年月日  + 序列号
    private String deptNo;

    // 父级部门
    private Dept fatherDept;

    // 部门描述/职能
    private String remark;

    // 部门创建时间
    private Date createDate;

    // 部门的创建人
    private Staff staff;

    // 部门是否有效
    private Integer isValid;

    public Dept() {
        super();
    }

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

    public Dept getFatherDept() {
        return fatherDept;
    }

    public void setFatherDept(Dept fatherDept) {
        this.fatherDept = fatherDept;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

}
