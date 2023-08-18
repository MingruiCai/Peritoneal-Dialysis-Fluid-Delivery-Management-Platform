package com.bcsd.project.enums;

public enum OrderStatusEnum {

    ONE("1","待确认","提交订单"),
    TOW("2","待审核","确认订单"),
    THREE("3","未配送","订单审核"),
    FOUR("4","配送中","订单配送中"),
    FIVE("5","部分配送中","订单部分配送中"),
    ZERO("0","已完成","订单完成"),
    BURDEN_ONE("-1","已关闭","关闭订单"),
    ;
    private final String type;
    private final String name;
    private final String text;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    OrderStatusEnum(String type, String name, String text) {
        this.type = type;
        this.name = name;
        this.text = text;
    }

}
