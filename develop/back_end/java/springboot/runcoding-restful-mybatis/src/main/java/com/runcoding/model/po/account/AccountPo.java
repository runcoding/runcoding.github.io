package com.runcoding.model.po.account;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * @author runcoding
 * @Date 2018-01-02 17:22:57
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountPo {

    @ApiModelProperty( "")
    private Long id;

    @ApiModelProperty( "")
    private String username;

    @ApiModelProperty( "创建时间")
    private Date createdTime;

    @ApiModelProperty( "修改时间")
    private Date updatedTime;


}