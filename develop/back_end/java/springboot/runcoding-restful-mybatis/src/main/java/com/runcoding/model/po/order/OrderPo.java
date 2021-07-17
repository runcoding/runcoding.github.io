package com.runcoding.model.po.order;

import com.runcoding.handler.type.annotation.ColumnType;
import com.runcoding.model.po.account.AccountPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author runcoding
 * @Date 2018-01-02 17:23:57
 */
@Getter
@Setter
@ToString
public class OrderPo {

    @ApiModelProperty( "")
    private Long id;

    @ApiModelProperty( "订单编号")
    private String orderNumber;

    @ApiModelProperty( "用户编号")
    private Long userId;

    @ApiModelProperty( "用户信息")
    @ColumnType()
    private AccountPo userInfo;

    @ApiModelProperty( "")
    private Date createTime;

    @ApiModelProperty( "")
    private Date updateTime;


}