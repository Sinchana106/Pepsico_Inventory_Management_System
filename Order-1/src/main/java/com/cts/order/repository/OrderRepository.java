package com.cts.order.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.cts.order.model.OrderModel;
@Repository
public interface OrderRepository extends CouchbaseRepository<OrderModel, String> {
	@View
	public List<OrderModel> findAllByOrderStatus(String orderStatus);
	
	@View
	public List<OrderModel> findAllByOrderStatusAndOrderDateTimeGreaterThan(String orderStatus,LocalDateTime orderDateTime);
}
