package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.dao.IDeptDao;
import com.zte.shopping.dao.ISequenceDao;
import com.zte.shopping.entity.Dept;
import com.zte.shopping.entity.Sequence;
import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.DeptExistException;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.service.IDeptService;
import com.zte.shopping.util.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Eytins
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private IDeptDao deptDao;

    @Autowired
    private ISequenceDao sequenceDao;

    /**
     * 查询所有部门信息
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Dept> findAll() {
        return deptDao.selectAll();
    }

    /**
     * 添加父部门
     * 部门名称不能重复
     * 部门编号的创建规则:部门前缀BM + 当前年月日  + 序列号
     */
    public void addFatherDept(String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException {
        Dept dept = deptDao.selectByName(deptName);

        if (dept != null) {
            throw new DeptExistException("该部门已经存在!");
        }

        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            throw new LoginDisabledException("登录失效, 请重新登录");
        }

        Dept d = new Dept();
        d.setCreateDate(new Date());
        d.setDeptName(deptName);
        // 部门状态为有效,值为1
        d.setIsValid(StatusConstant.DEPT_STATUS_ENABLE);
        d.setRemark(remark);
        d.setStaff(staff);

        // DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
        Sequence sequence = sequenceDao.selectByName(DictConstant.DEPT_NO_PREFIX);
        if (sequence == null) {
            Sequence sequ = new Sequence();

            sequ.setName(DictConstant.DEPT_NO_PREFIX); //  DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
            sequ.setValue(DictConstant.DEPT_NO_SEQUENCE_MIN);   // DictConstant.DEPT_NO_SEQUENCE_MIN: 部门编号的序列号最小值为000001

            // 添加序列
            sequenceDao.insertSequence(sequ);

            d.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequ.getValue());
        } else {
            String value = sequence.getValue();

            // 若当前的序列号达到了最大值, 则初始化设置位最小值, 从头开始
            if (DictConstant.DEPT_NO_SEQUENCE_MAX.equals(value)) {
                value = DictConstant.DEPT_NO_SEQUENCE_MIN;
            } else {
                value = String.format("%06d", Integer.parseInt(value) + 1);
            }

            sequenceDao.updateSquenceValue(DictConstant.DEPT_NO_PREFIX, value);

            d.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        }

        // 添加父部门
        deptDao.insertFatherDept(d);
    }

    /**
     * 添加子部门
     * 部门名称不能重复
     * 部门编号创建规则:部门前缀BM + 当前年月日  + 序列号
     */
    public void addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException {

        Dept dept = deptDao.selectByName(deptName);

        if (dept != null) {
            throw new DeptExistException("该部门已经存在!");
        }

        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            throw new LoginDisabledException("登录失效, 请重新登录");
        }

        Dept d = new Dept();
        d.setCreateDate(new Date());
        d.setDeptName(deptName);
        // 部门状态为有效,值为1
        d.setIsValid(StatusConstant.DEPT_STATUS_ENABLE);
        d.setRemark(remark);
        d.setStaff(staff);


        // 和添加父部门时不同  start
        Dept fatherDept = new Dept();
        fatherDept.setDeptId(Integer.parseInt(fatherDeptId));
        d.setFatherDept(fatherDept);
        // 和添加父部门时不同  end

        // DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
        Sequence sequence = sequenceDao.selectByName(DictConstant.DEPT_NO_PREFIX);
        if (sequence == null) {
            Sequence sequ = new Sequence();

            sequ.setName(DictConstant.DEPT_NO_PREFIX); //  DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
            sequ.setValue(DictConstant.DEPT_NO_SEQUENCE_MIN);   // DictConstant.DEPT_NO_SEQUENCE_MIN: 部门编号的序列号最小值为000001

            // 添加序列
            sequenceDao.insertSequence(sequ);

            d.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequ.getValue());
        } else {
            String value = sequence.getValue();

            // 若当前的序列号达到了最大值, 则初始化设置位最小值, 从头开始
            if (DictConstant.DEPT_NO_SEQUENCE_MAX.equals(value)) {
                value = DictConstant.DEPT_NO_SEQUENCE_MIN;
            } else {
                value = String.format("%06d", Integer.parseInt(value) + 1);
            }

            sequenceDao.updateSquenceValue(DictConstant.DEPT_NO_PREFIX, value);

            d.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        }

        // 添加子部门操作
        deptDao.insertSonDept(d);
    }

    /**
     * 子部门的 启用/禁用
     */
    public void modifyStatus(String deptId, String isValid) throws RequestParameterException {
        if (ParameterUtil.isnull(deptId)) {
            throw new RequestParameterException("部门ID不能为空");
        }

        if (ParameterUtil.isnull(isValid)) {
            throw new RequestParameterException("部门状态不能为空");
        }

        Integer status = Integer.parseInt(isValid);


        if (status == StatusConstant.DEPT_STATUS_ENABLE)  // 启用  ---> 禁用
        {
            status = StatusConstant.DEPT_STATUS_DISABLE;
        } else                                             // 禁用  ---> 启用
        {
            status = StatusConstant.DEPT_STATUS_ENABLE;
        }

        // 子部门的 启用/禁用
        deptDao.updateStatus(Integer.parseInt(deptId), status);
    }

    @Override
    public Dept findById(String id) throws RequestParameterException {
        if (ParameterUtil.isnull(id)) {
            throw new RequestParameterException("部门id不能为空");
        }

        Dept dept = deptDao.selectById(Integer.parseInt(id));
        return dept;
    }

    /**
     * 修改部门信息
     */
    public void modifyDept(String deptId, String deptName, String remark) throws DeptExistException {
        Dept dept = deptDao.selectByIdAndName(Integer.parseInt(deptId), deptName);

        if (dept != null) {
            throw new DeptExistException("该部门名称已经存在!");
        }

        // 修改部门信息
        deptDao.updateDept(Integer.parseInt(deptId), deptName, remark);
    }

    /**
     * 查询出所有 有效的部门信息列表
     */
    @Override
    public List<Dept> findEnabledDeptList() {
        return deptDao.selectEnabledDeptList(StatusConstant.DEPT_STATUS_ENABLE);
    }


}
