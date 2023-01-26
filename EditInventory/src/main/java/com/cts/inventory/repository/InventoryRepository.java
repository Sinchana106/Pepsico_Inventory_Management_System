package com.cts.inventory.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.cts.inventory.model.InventoryModel;

@Repository
public interface InventoryRepository extends CouchbaseRepository<InventoryModel, Integer> {
	@View
	List<InventoryModel> findByLocationNbr(int LocationNbr);

	@View
	InventoryModel findByLocationNbrAndMaterialId(int LocationNbr, String MaterialId);
}
