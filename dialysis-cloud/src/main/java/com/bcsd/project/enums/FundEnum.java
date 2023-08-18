package com.bcsd.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum FundEnum {

    FUND_TYPE_1(1,"planPcIssueFundList","计划-省市下达专项资金"),
    FUND_TYPE_2(2,"planPcIssueFundAdjustmentAmountList","计划-省市下达专项资金调整量"),
    FUND_TYPE_3(3,"planDcIssueFundList","计划-区县下达专项资金"),
    FUND_TYPE_4(4,"planDcIssueFundAdjustmentAmountList","计划-区县下达专项资金调整量"),
    FUND_TYPE_5(5,"paymentFundList","拨付-专项资金"),
    FUND_TYPE_6(6,"paymentAssortFundList","拨付-配套资金"),
    FUND_TYPE_7(7,"completeFundList","完成-专项资金"),
    FUND_TYPE_8(8,"completeAssortFundList","完成-配套资金"),
    FUND_TYPE_9(9,"useFundList","使用-专项资金"),
    FUND_TYPE_10(10,"useAssortFundList","使用-配套资金");

    private Integer code;
    private String value;
    private String text;

    @Getter
    private static final Map<String,Integer> valueMap = new HashMap<>();
    @Getter
    private static final Map<Integer,FundEnum> enumMap = new HashMap<>();
    @Getter
    private static final List<FundEnum> enumList = new ArrayList<>();
    static{
        for (FundEnum obj: EnumSet.allOf(FundEnum.class)){
            valueMap.put(obj.getValue(),obj.getCode());
            enumMap.put(obj.getCode(),obj);
            enumList.add(obj);
        }
    }

}
