package order.tracking.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import order.tracking.entity.Item;
import order.tracking.entity.Ticket;


@Data
@NoArgsConstructor
public class TicketData {

	private Long ticketId;
	private String ticketDate;
	private String ticketDueDate;
	private String ticketShipmentDate;
	private Set<ItemData> items = new HashSet<>();
	//private CustomerData customer;
	
	//Define OrderTrackingData DTO constructor 
	public TicketData(Ticket ticket) {
		ticketId = ticket.getTicketId();
		ticketDate = ticket.getTicketDate();
		ticketDueDate = ticket.getTicketDueDate();
		ticketShipmentDate = ticket.getTicketShipmentDate();
		
		for(Item item : ticket.getItems()) {
			items.add(new ItemData(item));
		}
		
	}
}
