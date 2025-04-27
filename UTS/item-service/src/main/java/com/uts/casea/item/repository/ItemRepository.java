package com.uts.casea.item.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uts.casea.item.model.Item;

// import com.uts.casea.item_service.service.Item;
// import com.uts.casea.item_service.service.Optional;

@Repository 
public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
