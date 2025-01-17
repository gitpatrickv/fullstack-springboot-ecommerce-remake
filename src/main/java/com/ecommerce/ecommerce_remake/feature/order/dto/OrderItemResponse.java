package com.ecommerce.ecommerce_remake.feature.order.dto;

import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private List<OrderModel> orderModels;
    private PageResponse pageResponse;

}
