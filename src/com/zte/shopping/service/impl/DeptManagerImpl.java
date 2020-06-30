package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.entity.Dept;
import com.zte.shopping.entity.Sequence;
import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.DeptExistException;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.mapper.IDeptManagerMapper;
import com.zte.shopping.mapper.ISequenceMapper;
import com.zte.shopping.service.IDeptManagerService;
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
public class DeptManagerImpl implements IDeptManagerService {
    @Autowired
    private IDeptManagerMapper iDeptManagerMapper;

    @Autowired
    private ISequenceMapper iSequenceMapper;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Dept> findAll() {
        return iDeptManagerMapper.selectAll();
    }

    @Override
    public Dept findById(String id) throws RequestParameterException {
        if (ParameterUtil.isnull(id)) {
            throw new RequestParameterException("部门id不能为空");
        }

        return iDeptManagerMapper.selectById(Integer.parseInt(id));
    }

    public void modifyDept(String redeptName, String deptId, String deptName, String remark) throws DeptExistException {
        Dept dept = iDeptManagerMapper.selectByIdAndName(Integer.parseInt(deptId), deptName);

        if (dept != null && !dept.getDeptName().equals(redeptName)) {
            throw new DeptExistException("该部门名称已经存在!");
        }

        // 修改部门信息
        iDeptManagerMapper.updateDept(Integer.parseInt(deptId), deptName, remark);
    }

    @Override
    public void addDept(String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException {
        Dept dept = iDeptManagerMapper.selectByName(deptName);

        if (dept != null) {
            throw new DeptExistException("该部门已经存在!");
        }

        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            throw new LoginDisabledException("登录失效, 请重新登录");
        }

        Dept sysDept = new Dept();
        sysDept.setCreateDate(new Date());
        sysDept.setDeptName(deptName);
        // 部门状态为有效,值为1
        sysDept.setIsValid(StatusConstant.DEPT_STATUS_ENABLE);
        sysDept.setRemark(remark);
        sysDept.setStaff(staff);

        // DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
        Sequence sequence = iSequenceMapper.selectByName(DictConstant.DEPT_NO_PREFIX);
        if (sequence == null) {
            Sequence sequ = new Sequence();

            sequ.setName(DictConstant.DEPT_NO_PREFIX); //  DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
            sequ.setValue(DictConstant.DEPT_NO_SEQUENCE_MIN);   // DictConstant.DEPT_NO_SEQUENCE_MIN: 部门编号的序列号最小值为000001

            // 添加序列
            iSequenceMapper.insertSequence(sequ);

            sysDept.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequ.getValue());
        } else {
            String value = sequence.getValue();

            // 若当前的序列号达到了最大值, 则初始化设置位最小值, 从头开始
            if (DictConstant.DEPT_NO_SEQUENCE_MAX.equals(value)) {
                value = DictConstant.DEPT_NO_SEQUENCE_MIN;
            } else {
                value = String.format("%06d", Integer.parseInt(value) + 1);
            }

            iSequenceMapper.updateSequenceValue(DictConstant.DEPT_NO_PREFIX, value);

            sysDept.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        }

        // 添加部门
        iDeptManagerMapper.insertDept(sysDept);
    }

    public void addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException {

        Dept dept = iDeptManagerMapper.selectByName(deptName);

        if (dept != null) {
            throw new DeptExistException("该部门已经存在!");
        }

        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            throw new LoginDisabledException("登录失效, 请重新登录");
        }

        Dept sysDept = new Dept();
        sysDept.setCreateDate(new Date());
        sysDept.setDeptName(deptName);
        // 部门状态为有效,值为1
        sysDept.setIsValid(StatusConstant.DEPT_STATUS_ENABLE);
        sysDept.setRemark(remark);
        sysDept.setStaff(staff);


        // 和添加父部门时不同  start
        Dept fatherDept = new Dept();
        fatherDept.setDeptId(Integer.parseInt(fatherDeptId));
        sysDept.setFatherDept(fatherDept);
        // 和添加父部门时不同  end

        // DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
        Sequence sequence = iSequenceMapper.selectByName(DictConstant.DEPT_NO_PREFIX);
        if (sequence == null) {
            Sequence sequ = new Sequence();

            sequ.setName(DictConstant.DEPT_NO_PREFIX); //  DictConstant.DEPT_NO_PREFIX: 部门编号前缀  BM
            sequ.setValue(DictConstant.DEPT_NO_SEQUENCE_MIN);   // DictConstant.DEPT_NO_SEQUENCE_MIN: 部门编号的序列号最小值为000001

            // 添加序列
            iSequenceMapper.insertSequence(sequ);

            sysDept.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequ.getValue());
        } else {
            String value = sequence.getValue();

            // 若当前的序列号达到了最大值, 则初始化设置位最小值, 从头开始
            if (DictConstant.DEPT_NO_SEQUENCE_MAX.equals(value)) {
                value = DictConstant.DEPT_NO_SEQUENCE_MIN;
            } else {
                value = String.format("%06d", Integer.parseInt(value) + 1);
            }

            iSequenceMapper.updateSequenceValue(DictConstant.DEPT_NO_PREFIX, value);

            sysDept.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        }

        // 添加子部门操作
        iDeptManagerMapper.insertSonDept(sysDept);
    }

    @Override
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
        iDeptManagerMapper.updateStatus(Integer.parseInt(deptId), status);
    }

    @Override
    public List<Dept> findEnabledDeptList() {
        return iDeptManagerMapper.selectEnabledDeptList(StatusConstant.DEPT_STATUS_ENABLE);
    }
}
