package com.ecommerce.ecommerce_remake.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountResponse {
    private Integer followerCount;
    private Integer productCount;
}
