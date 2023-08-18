package com.bcsd.project.constants;

/**
 * 通用常量信息
 * 
 * @author bcsd
 */
public class Constants
{
    /**
     * 数据删除标识 0-正常 1-删除
     */
    public static final Integer DELETE_TAG_0 = 0;
    public static final Integer DELETE_TAG_1 = 1;


    public static final String ACHIEVEMENT_STATUS_DRAFT = "draft"; //草稿
    public static final String ACHIEVEMENT_STATUS_REVIEWING = "reviewing"; //待复核
    public static final String ACHIEVEMENT_STATUS_REVIEWED = "reviewed"; //已复核
    /**
     * 1-当年 2-累计
     */
    public static final Integer TIME_SCOPE_1 = 1;
    public static final Integer TIME_SCOPE_2 = 2;

    /**
     * 建设状态 1-待建 2-在建 3-完建 4-验收 5-取消
     */
    public static final int BUILD_TYPE_1 = 1;
    public static final int BUILD_TYPE_2 = 2;
    public static final int BUILD_TYPE_3 = 3;
    public static final int BUILD_TYPE_4 = 4;
    public static final int BUILD_TYPE_5 = 5;

    /**
     * 字典KEY
     */
    public static final String DICT_KEY_FANWEIQUYU = "fanweiquyu";
    public static final String DICT_KEY_145_TYPE_ADDR = "145_type_abbr";
    public static final String DICT_KEY_BOFULV_TARGET = "bofulv_target";
    public static final String DICT_KEY_WANCHENGLV_TARGET = "wanchenglv_target";
    public static final String DICT_KEY_RANK_WEIGHT = "rank_weight";
    public static final String DICT_KEY_PROJECT_LIBRARY_STATE = "project_library_state";
    public static final String DICT_KEY_SCHEDULING_REPORT_STATE = "scheduling_report_state";
    public static final String DICT_KEY_OVERDUE_NOT_STARTED = "overdue_not_started";
    public static final String DICT_KEY_PROJECT_IMPL_STATE = "project_impl_state";
    public static final String DICT_KEY_PROJECT_PLAN_STATE = "project_plan_state";

    /**
     * 审核类型 1-申请 2-审核 3-驳回 4-结束 5-撤回 6-申请未通过
     */
    public static final int VERIFY_TYPE_1 = 1;
    public static final int VERIFY_TYPE_2 = 2;
    public static final int VERIFY_TYPE_3 = 3;
    public static final int VERIFY_TYPE_4 = 4;
    public static final int VERIFY_TYPE_5 = 5;
    public static final int VERIFY_TYPE_6 = 6;

    /**
     * 审核状态
     */
    public static final String VERIFY_STATUS_PASS = "0";
    public static final String VERIFY_STATUS_REJECT = "-1";
    public static final String VERIFY_STATUS_DRAFT = "-2";
    public static final String VERIFY_STATUS_PENDING = "-3";
    public static final String VERIFY_STATUS_RELEASE = "-4";
    public static final String VERIFY_STATUS_QUXIAN = "1";
    public static final String VERIFY_STATUS_SHIJI = "2";
    public static final String VERIFY_STATUS_SANFANG = "3";

    /**
     * 角色标识
     */
    public static final String ROLE_KEY_QUXIANTIANBAO = "quxiantianbao";
    public static final String ROLE_KEY_SHIJITIANBAO = "shijitianbao";
    public static final String ROLE_KEY_SHENGJITIANBAO = "shengjitianbao";


}
