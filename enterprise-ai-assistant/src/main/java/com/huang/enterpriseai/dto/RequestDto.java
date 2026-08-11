package com.huang.enterpriseai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @Author: huang
 * @Description: 不可变数据载体 record，适用于防止被篡改
 * @DateTime: 2026/8/7 17:03
 **/
public record RequestDto(
        @NotBlank(message = "知识库名称不能为空")
        @Size(max = 100, message = "知识库名称不能超过100个字符")
        String name,

        @Size(max = 500, message = "知识库描述不能超过500个字符")
        String description
) {
}
