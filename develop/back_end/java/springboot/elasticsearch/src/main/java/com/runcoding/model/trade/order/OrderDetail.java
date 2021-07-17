package com.runcoding.model.trade.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

/**
 * @author runcoding
 * @date 2019-01-23
 * @desc: 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @ApiModelProperty("订单编号")
    @Field(type = FieldType.Keyword)
    private String orderNumber;

    @ApiModelProperty("商品skuId")
    @Field(type = FieldType.Long)
    private Long productSkuId;

    @ApiModelProperty("商品sku名称")
    @Field(type = FieldType.Text)
    private String productSkuName;

    @ApiModelProperty("商品sku编号")
    @Field(type = FieldType.Text)
    private String productSkuCode;

    @ApiModelProperty( "商品数量")
    @Field(type = FieldType.Integer)
    private int qty;

    @ApiModelProperty("销售单价")
    @Field(type = FieldType.Double,index = false)
    private BigDecimal productSalePrice;

    @ApiModelProperty("(均摊)实付总金额")
    @Field(type = FieldType.Double,index = false)
    private BigDecimal realAmount;

    @ApiModelProperty("(均摊)优惠金额")
    @Field(type = FieldType.Double,index = false)
    private BigDecimal promotionAmount;

    @ApiModelProperty("(均摊)运费金额")
    @Field(type = FieldType.Double,index = false)
    private BigDecimal freightAmount;


}
