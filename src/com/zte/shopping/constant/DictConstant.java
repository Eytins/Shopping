package com.zte.shopping.constant;

/**
 * Created by Eytins
 * <p>
 * 分页固定数据
 */

public interface DictConstant {
    /**
     * 分页数据,当前页,默认为第一页
     */
    String PAGE_NO = "1";


    /**
     * 分页数据,每页显示条数,默认五条
     */
    String PAGE_SIZE = "5";

    /**
     * 首页分页数据,每页显示条数,默认4条
     */
    String INDEX_PAGE_SIZE = "4";

    /**
     * 商品编号前缀
     */
    String PRODUCT_NO_PREFIX = "SP";

    /**
     * 部门编号前缀
     */
    String DEPT_NO_PREFIX = "BM";

    /**
     * 订单编号前缀
     */
    String ORDER_NO_PREFIX = "DD";

    /**
     * 商品编号的序列号最小值为000001
     */
    String PRODUCT_NO_SEQUENCE_MIN = "000001";

    /**
     * 商品编号的序列号最大值为999999
     */
    String PRODUCT_NO_SEQUENCE_MAX = "999999";

    /**
     * 订单编号的序列号最小值为000001
     */
    String ORDER_NO_SEQUENCE_MIN = "000001";

    /**
     * 订单编号的序列号最大值为999999
     */
    String ORDER_NO_SEQUENCE_MAX = "999999";

    /**
     * 部门编号的序列号最小值为000001
     */
    String DEPT_NO_SEQUENCE_MIN = "000001";

    /**
     * 部门编号的序列号最大值为999999
     */
    String DEPT_NO_SEQUENCE_MAX = "999999";

    /**
     * 附件类型为头像
     */
    String ATTACHE_FILE_TYPE_HEAD_IMAGE = "1001";

    /**
     * 附件类型为生活照
     */
    String ATTACHE_FILE_TYPE_LIFE_IMAGE = "1002";

    /**
     * 员工权限为系统管理员,字典值:2001
     */
    String STAFF_ROLE_PREMISSION_SYSTEM_MANAGER = "2001";

    /**
     * 员工权限为普通管理员,字典值:2002
     */
    String STAFF_ROLE_PREMISSION_NORMAL_MANAGER = "2002";


}
