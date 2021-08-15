package com.xero.refactorthis.repository;

import com.xero.refactorthis.entity.ProductOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductOptionRepository extends CrudRepository<ProductOption, UUID> {
    List<ProductOption> findByProductId(UUID id);
}
