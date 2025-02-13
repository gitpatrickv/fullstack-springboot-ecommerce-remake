package com.ecommerce.ecommerce_remake.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableUtils {

    public static Pageable createPaginationAndSorting(Integer pageNo,
                                                      Integer pageSize,
                                                      String fieldName,
                                                      String sortDirection){
        Sort sort;
        if(sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())){
            sort = Sort.by(fieldName).ascending();
        }else {
            sort = Sort.by(fieldName).descending();
        }
        return PageRequest.of(pageNo, pageSize, sort);
    }
}
