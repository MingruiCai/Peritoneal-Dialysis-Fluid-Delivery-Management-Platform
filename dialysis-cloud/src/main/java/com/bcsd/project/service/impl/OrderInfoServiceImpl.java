package com.bcsd.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.project.domain.*;
import com.bcsd.project.domain.dto.OrderDrugDTO;
import com.bcsd.project.domain.dto.OrderInfoDTO;
import com.bcsd.project.domain.dto.OrderLogisticsDrugAddDTO;
import com.bcsd.project.domain.dto.OrderLogisticsDrugDTO;
import com.bcsd.project.domain.vo.OrderDrugVO;
import com.bcsd.project.domain.vo.OrderInfoVO;
import com.bcsd.project.domain.vo.ThirdSession;
import com.bcsd.project.enums.OrderStatusEnum;
import com.bcsd.project.mapper.*;
import com.bcsd.project.service.OrderInfoService;
import com.bcsd.project.utils.ThirdSessionHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcsd.common.utils.SecurityUtils.*;


/**
 * 订单实现类
 *
 * @author zhaofei
 * @date 2023-07-13 15:39:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderOperationInfoMapper operationInfoMapper;
    @Autowired
    private OrderDrugMapper orderDrugMapper;

    @Autowired
    private OrderLogisticsDrugMapper orderLogisticsDrugMapper;
    @Autowired
    private OrderLogisticsMapper orderLogisticsMapper;

    /**
     * 列表
     *
     * @param orderInfo
     * @return
     */
    @Override
    public List<OrderInfoVO> list(OrderInfoDTO orderInfo) {
        return baseMapper.selectOrderList(orderInfo);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public AjaxResult getOrderById(Integer id) {
        Map<String, Object> res = new HashMap<>();
        OrderInfoVO orderInfo = this.baseMapper.selectInfoById(id);
        List<OrderOperationInfo> operationInfo = operationInfoMapper.selectByOrderId(id);
        List<OrderDrugVO> orderDrug = orderDrugMapper.selectListByOrderId(id);
        List<OrderLogistics> orderLogistics = orderLogisticsMapper.selectListByOrderId(id);
        //订单信息
        res.put("orderInfo", orderInfo);
        //订单过程信息
        res.put("operationInfo", operationInfo);
        //订单药品信息
        res.put("orderDrug", orderDrug);
        //订单物流信息
        res.put("orderLogistics", orderLogistics);
        return AjaxResult.success(res);
    }


    /**
     * 新增修改
     *
     * @param orderInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addOrUpdate(OrderInfo orderInfo) {
        ThirdSession thirdSession = ThirdSessionHolder.getThirdSession();
        int orderCount = this.baseMapper.selectOrderCount(thirdSession.getWxUserId());
        if (orderCount > 0) {
            return AjaxResult.error("已存在进行中的订单!");
        }
        orderInfo.setCreateBy(thirdSession.getWxUserName());
        orderInfo.setCreateTime(new Date());
        orderInfo.setStatus(OrderStatusEnum.ONE.getType());
        orderInfo.setOrderNumber("PS"+System.currentTimeMillis());
        orderInfo.setUserId(thirdSession.getWxUserId());
        //orderInfo.setUserName(thirdSession.getWxUserName());
        this.baseMapper.insertSelective(orderInfo);
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        operationInfo.setOrderId(orderInfo.getId());
        operationInfo.setCreateId(thirdSession.getWxUserId());
        operationInfo.setCreateBy(thirdSession.getWxUserName());
        operationInfo.setCreateTime(new Date());
        String remarks = StringUtils.isEmpty(orderInfo.getRemarks()) ? "" : orderInfo.getRemarks();
        operationInfo.setRemarks("患者提交了订单，订单备注信息：" + remarks);
        operationInfo.setType(OrderStatusEnum.ONE.getText());
        operationInfoMapper.insertSelective(operationInfo);
        return AjaxResult.success();
    }


    /**
     * 患者小程序端 订单关闭
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderClose(Integer id, String remarks) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("status", OrderStatusEnum.BURDEN_ONE.getType());
        this.baseMapper.updateStatusById(map);
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        operationInfo.setOrderId(Long.valueOf(id));
        ThirdSession thirdSession = ThirdSessionHolder.getThirdSession();
        operationInfo.setCreateId(thirdSession.getWxUserId());
        operationInfo.setCreateBy(thirdSession.getWxUserName());
        operationInfo.setCreateTime(new Date());
        remarks = StringUtils.isEmpty(remarks) ? "" : remarks;
        operationInfo.setRemarks("患者关闭订单，备注信息：" + remarks);
        operationInfo.setType(OrderStatusEnum.BURDEN_ONE.getText());
        operationInfoMapper.insertSelective(operationInfo);
    }


    /**
     * PC端 订单关闭
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderClosePC(Integer id, String remarks) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("status", OrderStatusEnum.BURDEN_ONE.getType());
        this.baseMapper.updateStatusById(map);
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        operationInfo.setOrderId(Long.valueOf(id));
        operationInfo.setCreateId(getUserId().toString());
        operationInfo.setCreateBy(getUsername());
        operationInfo.setCreateTime(new Date());
        remarks = StringUtils.isEmpty(remarks) ? "" : remarks;
        operationInfo.setRemarks("订单已作废，备注信息：" + remarks);
        operationInfo.setType(OrderStatusEnum.BURDEN_ONE.getText());
        operationInfoMapper.insertSelective(operationInfo);
    }


    /**
     * 订单确认（护士确认订单）
     *
     * @param orderDrugDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult orderConfirm(OrderDrugDTO orderDrugDTO) {
        List<OrderDrug> orderDrugList = orderDrugDTO.getOrderDrugList();
        if (orderDrugList == null || orderDrugList.size() == 0) {
            return AjaxResult.error("药品不能为空!");
        }
        Integer orderId = orderDrugList.get(0).getOrderId();
        OrderInfo orderInfo = baseMapper.selectByPrimaryKey(orderId);
        if(ObjectUtils.isEmpty(orderInfo)){
            return AjaxResult.error("订单不存在!");
        }
        if(!orderInfo.getStatus().equals(OrderStatusEnum.ONE.getType())){
            return AjaxResult.error("订单不在待确认状态!");
        }

        Map map = new HashMap();
        map.put("hsqrBy", getUsername());
        map.put("hsqrTime", new Date());
        map.put("id", orderId);
        map.put("status", OrderStatusEnum.TOW.getType());
        this.baseMapper.updateStatusById(map);
        List<OrderDrug> orderDrugs = orderDrugMapper.selectByOrderId(orderId);
        if (orderDrugs != null && orderDrugs.size() > 0) {
            orderDrugMapper.deleteByOrderId(orderId);
        }
        orderDrugMapper.insertOrderDrugList(orderDrugList);
        //过程信息
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        operationInfo.setOrderId(Long.valueOf(orderId));
        operationInfo.setCreateId(getUserId().toString());
        operationInfo.setCreateBy(getUsername());
        operationInfo.setCreateTime(new Date());
        String remarks = StringUtils.isEmpty(orderDrugDTO.getRemarks()) ? "" : orderDrugDTO.getRemarks();
        operationInfo.setRemarks("订单已确认，备注信息：" + remarks);
        operationInfo.setType(OrderStatusEnum.TOW.getText());
        operationInfoMapper.insertSelective(operationInfo);
        return AjaxResult.success();
    }


    /**
     * 订单审核（医师审核）
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult orderExamine(Integer id,String remarks) {
        OrderInfo orderInfo = baseMapper.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(orderInfo)){
            return AjaxResult.error("订单不存在!");
        }
        if(!orderInfo.getStatus().equals(OrderStatusEnum.TOW.getType())){
            return AjaxResult.error("订单不在待审核状态!");
        }
        Map map = new HashMap();
        map.put("ysshBy", getUsername());
        map.put("ysshTime", new Date());
        map.put("id", id);
        map.put("status", OrderStatusEnum.THREE.getType());
        this.baseMapper.updateStatusById(map);
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        operationInfo.setOrderId(Long.valueOf(id));
        operationInfo.setCreateId(getUserId().toString());
        operationInfo.setCreateBy(getUsername());
        operationInfo.setCreateTime(new Date());
        remarks = StringUtils.isEmpty(remarks) ? "" : remarks;
        operationInfo.setRemarks("订单已审核，备注信息：" + remarks);
        operationInfo.setType(OrderStatusEnum.THREE.getText());
        operationInfoMapper.insertSelective(operationInfo);
        return AjaxResult.success();
    }

    /**
     * 订单批量审核（医师审核）
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult orderExamineBatch(JSONArray ids, String remarks) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.getInteger(i);
            OrderInfo orderInfo = baseMapper.selectByPrimaryKey(id);
            if(ObjectUtils.isEmpty(orderInfo)){
                return AjaxResult.error("订单不存在!");
            }
            if(!orderInfo.getStatus().equals(OrderStatusEnum.TOW.getType())){
                return AjaxResult.error("订单不在待审核状态!");
            }
            Map map = new HashMap();
            map.put("ysshBy", getUsername());
            map.put("ysshTime", new Date());
            map.put("id", id);
            map.put("status", OrderStatusEnum.THREE.getType());
            this.baseMapper.updateStatusById(map);
            OrderOperationInfo operationInfo = new OrderOperationInfo();
            operationInfo.setOrderId(Long.valueOf(id));
            operationInfo.setCreateId(getUserId().toString());
            operationInfo.setCreateBy(getUsername());
            operationInfo.setCreateTime(new Date());
            remarks = StringUtils.isEmpty(remarks) ? "" : remarks;
            operationInfo.setRemarks("订单已审核，备注信息：" + remarks);
            operationInfo.setType(OrderStatusEnum.THREE.getText());
            operationInfoMapper.insertSelective(operationInfo);
        }

        return AjaxResult.success();
    }


    /**
     * 订单审核（医师驳回）
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult orderReject(Integer id,String remarks) {
        OrderInfo orderInfo = baseMapper.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(orderInfo)){
            return AjaxResult.error("订单不存在!");
        }
        if(!orderInfo.getStatus().equals(OrderStatusEnum.TOW.getType())){
            return AjaxResult.error("订单不在待审核状态!");
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("status", OrderStatusEnum.ONE.getType());
        this.baseMapper.updateStatusById(map);
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        operationInfo.setOrderId(Long.valueOf(id));
        operationInfo.setCreateId(getUserId().toString());
        operationInfo.setCreateBy(getUsername());
        operationInfo.setCreateTime(new Date());
        remarks = StringUtils.isEmpty(remarks) ? "" : remarks;
        operationInfo.setRemarks("订单驳回，备注信息：" + remarks);
        operationInfo.setType("订单驳回");
        operationInfoMapper.insertSelective(operationInfo);
        return AjaxResult.success();
    }

    /**
     * 订单配送（配送方配送药品）
     *
     * @param orderLogisticsDrugAddDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult orderDelivery(OrderLogisticsDrugAddDTO orderLogisticsDrugAddDTO) {
        List<OrderLogisticsDrugDTO> orderDrugList = orderLogisticsDrugAddDTO.getOrderDrugList();
        if (orderDrugList == null || orderDrugList.size() == 0) {
            return AjaxResult.error("药品不能为空!");
        }
        Boolean flag = true;
        Integer orderId = orderDrugList.get(0).getOrderId();
        if(orderId == null){
            return AjaxResult.error("订单id不能为空!");
        }
        OrderInfo orderInfo = baseMapper.selectByPrimaryKey(orderId);
        if(ObjectUtils.isEmpty(orderInfo)){
            return AjaxResult.error("订单不存在!");
        }
        if(!orderInfo.getStatus().equals(OrderStatusEnum.THREE.getType()) && !orderInfo.getStatus().equals(OrderStatusEnum.FIVE.getType())){
            return AjaxResult.error("订单不在未配送和部分配送中状态!");
        }
        List<OrderLogisticsDrug> byOrderIds = orderLogisticsDrugMapper.getByOrderId(orderId);
        if(byOrderIds != null && byOrderIds.size() > 0){
            for (OrderLogisticsDrug logisticsDrug : byOrderIds) {
                for (OrderLogisticsDrugDTO drug : orderDrugList) {
                    if(logisticsDrug.getHospitalDrugId().equals(drug.getHospitalDrugId())){
                        Integer num = drug.getDeliveryNumber()+logisticsDrug.getDeliveryNumber();
                        if(num > drug.getOrderNumber()){
                            return AjaxResult.error("药品本次数量不可大于订单数量!");
                        }else if(num < drug.getOrderNumber()){
                            flag = false;
                        }
                        //drug.setDeliveryNumber(drug.getDeliveryNumber()+logisticsDrug.getDeliveryNumber());
                    }
                }
            }
        }else {
            for (OrderLogisticsDrugDTO drug : orderDrugList) {
                if (!drug.getOrderNumber().equals(drug.getDeliveryNumber())) {
                    flag = false;
                    break;
                }
            }
        }
        OrderLogistics orderLogistics = new OrderLogistics();
        orderLogistics.setLogisticsNumber("WL"+System.currentTimeMillis());
        orderLogistics.setOrderId(orderId);
        orderLogistics.setStatus(OrderStatusEnum.ZERO.getType()); //已发货
        orderLogistics.setCreateBy(getUsername());
        orderLogistics.setCreateTime(new Date());
        orderLogisticsMapper.insertSelective(orderLogistics);
        for (OrderLogisticsDrugDTO drug : orderDrugList) {
            OrderLogisticsDrug logisticsDrug = new OrderLogisticsDrug();
            BeanUtil.copyProperties(drug,logisticsDrug);
            logisticsDrug.setLogisticsId(orderLogistics.getId());
            logisticsDrug.setCreateBy(getUsername());
            logisticsDrug.setCreateTime(new Date());
            orderLogisticsDrugMapper.insertSelective(logisticsDrug);
        }

        Map map = new HashMap();
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        map.put("id", orderId);
        String remarks = StringUtils.isEmpty(orderLogisticsDrugAddDTO.getRemarks()) ? "" : orderLogisticsDrugAddDTO.getRemarks();
        if(flag){
            map.put("status", OrderStatusEnum.FOUR.getType());
            operationInfo.setRemarks("订单配送中，备注信息：" + remarks);
            operationInfo.setType(OrderStatusEnum.FOUR.getText());
        }else {
            map.put("status", OrderStatusEnum.FIVE.getType());
            operationInfo.setRemarks("订单部分配送中，备注信息：" + remarks);
            operationInfo.setType(OrderStatusEnum.FIVE.getText());
        }
        this.baseMapper.updateStatusById(map);
        /*for (OrderDrug drug : orderDrugList) {
            orderDrugMapper.updateByPrimaryKeySelective(drug);
        }*/
        //过程信息
        operationInfo.setOrderId(Long.valueOf(orderId));
        operationInfo.setCreateId(getUserId().toString());
        operationInfo.setCreateBy(getUsername());
        operationInfo.setCreateTime(new Date());
        operationInfoMapper.insertSelective(operationInfo);
        return AjaxResult.success();
    }


    /**
     * pc端签收
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult confirmReceiptPC(Integer id,String signatureUrl,String remarks,String boxUrl) {
        OrderLogistics orderLogistics = orderLogisticsMapper.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(orderLogistics)){
            return AjaxResult.error("物流信息不存在!");
        }
        if(!orderLogistics.getStatus().equals(OrderStatusEnum.ZERO.getType())){
            return AjaxResult.error("已签收，请勿重复签收!");
        }
        OrderInfo orderInfo = baseMapper.selectByPrimaryKey(orderLogistics.getOrderId());
        if(ObjectUtils.isEmpty(orderInfo)){
            return AjaxResult.error("订单不存在!");
        }
        Map map2 = new HashMap();
        OrderOperationInfo operationInfo = new OrderOperationInfo();
        map2.put("id",id);
        map2.put("orderId",orderLogistics.getOrderId());
        int byOrderIdNotId = orderLogisticsMapper.getByOrderIdNotId(map2);
        if(orderInfo.getStatus().equals(OrderStatusEnum.FOUR.getType()) && byOrderIdNotId <= 0){
            Map map = new HashMap();
            map.put("id", orderLogistics.getOrderId());
            map.put("status", OrderStatusEnum.ZERO.getType());
            this.baseMapper.updateStatusById(map);
            operationInfo.setRemarks("订单已签收");
            operationInfo.setType(OrderStatusEnum.ZERO.getText());
        }else {
            operationInfo.setRemarks("订单已签收");
            operationInfo.setType("订单已签收");
        }
        orderLogistics.setStatus(OrderStatusEnum.ONE.getType());
        orderLogistics.setSignatureUrl(signatureUrl);
        orderLogistics.setBoxUrl(boxUrl);
        orderLogistics.setRemarks(remarks);
        orderLogistics.setUpdateBy(getUsername());
        orderLogistics.setUpdateTime(new Date());
        orderLogistics.setQsBy(getUsername());
        orderLogistics.setQsTime(new Date());
        orderLogisticsMapper.updateByPrimaryKeySelective(orderLogistics);

        operationInfo.setOrderId(Long.valueOf(orderLogistics.getOrderId()));
        operationInfo.setCreateId(getUserId().toString());
        operationInfo.setCreateBy(getUsername());
        operationInfo.setCreateTime(new Date());

        operationInfoMapper.insertSelective(operationInfo);
        return AjaxResult.success();
    }
}
