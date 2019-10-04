package com.wlkg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 17:27
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnums {
    CATEGOPY_NOT_FOUND(400,"没有对应的商品"),
    PRICE_CANNOT_BE_NULL(400,"价格不对"),
    BRAND_IS_EMPTY(401,"品牌为空"),
    GOODS_SKU_NOT_FOUND(402,"商品sku为空"),

    REGISTER_FAIL(403,"用户注册失败"),
    INVALID_USERNAME_PASSWORD(406,"没有此用户名和密码"),

    FILE_UPLOAD_TYPR_ERROR(500,"文件上传只支持图片"),
    FILE_UPLOAD_INFO_NOTNULL(501,"文件上传内容不符合要求"),

    SPEC_GROP_IS_NULL(600,"该类别暂无分组"),
    SPEC_PARAM_IS_NULL(601,"该规格暂无参数"),

    SPU_IS_NULL(700,"没有商品数据"),
    SPU_UPDATE_FAIL(701,"修改商品失败");


    private int code; //状态码
    private String msg; //异常信息
}
