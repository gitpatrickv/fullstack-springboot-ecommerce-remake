package com.ecommerce.ecommerce_remake.feature.store_following.model;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "store_following")
public class StoreFollowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeFollowingId;
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


    public StoreFollowing(Integer userId, Store store) {
        this.userId = userId;
        this.store = store;
    }
}
