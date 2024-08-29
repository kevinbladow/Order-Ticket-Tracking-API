package order.tracking.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import order.tracking.entity.Item;

public interface ItemDao extends JpaRepository<Item, Long> {

	Optional<Item> findByItemUpc(String itemUpc); //add sql script to find item by upc

}
