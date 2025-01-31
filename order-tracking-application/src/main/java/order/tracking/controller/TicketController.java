package order.tracking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import order.tracking.controller.model.CustomerData;
import order.tracking.controller.model.ItemData;
import order.tracking.controller.model.TicketData;
import order.tracking.service.TicketService;

@RestController
@RequestMapping("/order_tracking")
@Slf4j
public class TicketController {
	
	
	@Autowired
	private TicketService ticketService;
	
	/**********************************************************************************************************
	 **********************************************Customer Table Operations **********************************
	 *********************************************************************************************************/
	
	//create and save new customer 
		@PostMapping("/customer")
		@ResponseStatus(code =HttpStatus.CREATED )
		public CustomerData insertCustomerData(@RequestBody CustomerData customerData) {
			log.info("Creating Customer {}", customerData);
			return ticketService.saveCustomerData(customerData);
		}
	
	//retrieve a customer by Id
		@GetMapping("/customer/{customerId}")
		@ResponseStatus(code = HttpStatus.OK)
		public CustomerData retrieveCustomerById(@PathVariable Long customerId) {
			log.info("Retrieving Customer with ID = {}", customerId);
			return ticketService.retrieveCustomerById(customerId);
		}

	// modify an existing customer by customer ID
		@PutMapping("/customer/{customerId}")
		@ResponseStatus(code = HttpStatus.ACCEPTED)
		public CustomerData updateCustomerData(@PathVariable Long customerId, @RequestBody CustomerData customerData) {
			customerData.setCustomerId(customerId);

			log.info("Update Customer {}", customerId);

			return ticketService.saveCustomerData(customerData);
		}
		

	/**********************************************************************************************************
	**********************************************Ticket Table Operations *************************************
	**********************************************************************************************************/
	
	// create and save new order ticket
		@PostMapping("/customer/{customerId}/ticket")
		@ResponseStatus(code = HttpStatus.CREATED)
		public TicketData addTicket(@PathVariable Long customerId, @RequestBody TicketData ticketData) {

		log.info("Adding order ticket {} to Customer {}", ticketData, customerId);
		return ticketService.saveTicket(customerId, ticketData);
		}
		
	// delete a customer order ticket
		@DeleteMapping("/ticket/{ticketId}")		
		public Map<String,String> deleteTicketById(@PathVariable Long ticketId){
			
			log.info("Deleting Order Ticket with ID = {}", ticketId);
			ticketService.deleteTicketById(ticketId);
			
			return Map.of("message", "Deletion of order Ticket with ID =" + ticketId + " was successful.");
		}

	// modify a customer order ticket
		@PutMapping("/ticket/{ticketId}")
		@ResponseStatus(code = HttpStatus.ACCEPTED)
		public TicketData updateTicketData(@PathVariable Long ticketId, @RequestBody TicketData ticketData) {
			ticketData.setTicketId(ticketId);
			
			log.info("Update customer ticket {}, ticketId");
			
			return ticketService.saveTicketData(ticketData);
		}
		
		
	//retrieve all order ticket items by ticket Id
		@GetMapping("/ticket/{ticketId}")
		@ResponseStatus(code = HttpStatus.OK)
		public TicketData retrieveTicketById(@PathVariable Long ticketId) {
			log.info("Retrieving order ticket with ID= {}", ticketId);
			return ticketService.retrieveTicketById(ticketId);
		}

	// retrieve all customer order tickets by customer Id
		@GetMapping("/ticket/customer/{customerId}")
		@ResponseStatus(code = HttpStatus.OK)
		public List<TicketData> retrieveAllCustomerTicketsByCustomerId(@PathVariable Long customerId) {
			log.info("Retrieving all order tickets from Customer with ID={}", customerId);
			return ticketService.retrieveAllCustomerTicketsByCustomerId(customerId);
		}
		
		
	/***********************************************************************************************************
	**************************************Item_Ticket Join Table Operations ************************************
	***********************************************************************************************************/
	
	// add item to customer order ticket on the item_ticket join table
		@PostMapping("/item_ticket/{ticketId}/{itemId}")
		@ResponseStatus(code = HttpStatus.OK)
		public ItemData addItemToCustomerTicket(@PathVariable Long ticketId, @PathVariable Long itemId) {

			log.info("Adding item {} to order ticket {}", itemId, ticketId);

			return ticketService.saveItemToTicket(ticketId, itemId);
		}
	
	// add item to item table
		@PostMapping("/item")
		@ResponseStatus(code = HttpStatus.CREATED)
		public ItemData insertNewItem(@RequestBody ItemData itemData) {
			
			log.info("Item {} created", itemData);
			
			return ticketService.saveItemData(itemData);
		}
}
