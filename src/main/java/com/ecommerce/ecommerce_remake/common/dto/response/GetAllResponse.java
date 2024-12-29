package com.ecommerce.ecommerce_remake.common.dto.response;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllResponse {

    private List<? extends Model> models;
    private PageResponse pageResponse;
}
