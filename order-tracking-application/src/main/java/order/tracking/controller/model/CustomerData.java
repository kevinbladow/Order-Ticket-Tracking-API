package order.tracking.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import order.tracking.entity.Customer;
import order.tracking.entity.Ticket;


@Data
@NoArgsConstructor
public class CustomerData {

	private Long customerId;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private String customerEmail;
	private Set<TicketData> tickets = new HashSet<>();
	
	//Define OrderTrackingCustomer DTO constructor
	public CustomerData(Customer customer) {
		customerId = customer.getCustomerId();
		customerName = customer.getCustomerName();
		customerAddress = customer.getCustomerAddress();
		customerPhone = customer.getCustomerPhone();
		customerEmail = customer.getCustomerEmail();
		
		for(Ticket ticket :customer.getTickets()) {
			tickets.add(new TicketData(ticket));
		}
	}
}
