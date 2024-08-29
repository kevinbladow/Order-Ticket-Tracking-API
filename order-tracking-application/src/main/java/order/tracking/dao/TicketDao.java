package order.tracking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import order.tracking.entity.Ticket;

public interface TicketDao extends JpaRepository<Ticket, Long> {

}
