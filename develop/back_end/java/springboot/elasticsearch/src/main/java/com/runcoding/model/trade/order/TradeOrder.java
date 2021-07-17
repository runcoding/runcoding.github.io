package com.runcoding.model.trade.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author runcoding
 * @Date 2019-01-23 17:23:57
 * @desc 交易订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrder {

    @ApiModelProperty("交易编号")
    @Field(type = FieldType.Keyword,index = true)
    private String tradeNumber;

    @ApiModelProperty( "订单编号")
    @Field(type = FieldType.Keyword,index = true)
    private String orderNumber;

    @ApiModelProperty("订单总金额")
    @Field(type = FieldType.Double, index = false)
    private BigDecimal totalAmount;

    @ApiModelProperty("实付金额")
    @Field(type = FieldType.Double, index = false)
    private BigDecimal realAmount;

    @ApiModelProperty("优惠金额")
    @Field(type = FieldType.Double, index = false)
    private BigDecimal promotionAmount;

    @ApiModelProperty("运费金额")
    @Field(type = FieldType.Double, index = false)
    private BigDecimal freightAmount;

    @ApiModelProperty("订单状态")
    @Field(type = FieldType.Integer)
    private Integer orderStatus;

    @ApiModelProperty("订单明细")
    @Field(type = FieldType.Nested)
    private List<OrderDetail>  orderDetails;

    @ApiModelProperty("订单创建时间")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("订单修改时间")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}