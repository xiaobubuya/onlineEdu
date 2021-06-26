package com.xiaobubuya.servicebase.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-25 10:33
 * @Modified By:
 */
@Data
@ApiModel(value="课程评论所需昵称和头像", description="课程评论所需昵称和头像")
public class UcenterMemberCommon {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String mobile;
}
