package com.ecommerce.ecommerce_remake.feature.store_rating.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "store_ratings")
public class StoreRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeRatingId;
    private Integer storeId;
    private Integer userId;
    private Integer rating;
}
