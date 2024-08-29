package order.tracking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import order.tracking.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
	

}
