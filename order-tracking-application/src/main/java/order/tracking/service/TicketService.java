package order.tracking.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import order.tracking.controller.model.CustomerData;
import order.tracking.controller.model.ItemData;
import order.tracking.controller.model.TicketData;
import order.tracking.dao.CustomerDao;
import order.tracking.dao.ItemDao;
import order.tracking.dao.TicketDao;
import order.tracking.entity.Customer;
import order.tracking.entity.Item;
import order.tracking.entity.Ticket;

@Service
public class TicketService {

	/*****************************************************************************************************************************
	 **************************************************** Ticket Object Service Methods********************************************
	 *****************************************************************************************************************************/
	//create the data layer object for customer autowired for JPA
	@Autowired
	private TicketDao ticketDao;
	
	

	// create and save new order ticket or update existing order ticket
	public TicketData saveTicket(Long customerId, TicketData ticketData) {
		Long ticketId = ticketData.getTicketId();
		Customer customer = findCustomerById(customerId);
		Ticket ticket = findOrCreateTicket(customerId, ticketId);

		copyTicketFields(ticket, ticketData);
		ticket.setCustomer(customer);
		customer.getTickets().add(ticket);
		
		Ticket tempTicketData = ticketDao.save(ticket);
		
		return new TicketData(tempTicketData);
	}

	// Copy all the fields in the ticketData object to the ticket Object
	private void copyTicketFields(Ticket ticket, TicketData ticketData) {
		//List<ItemData> items = new LinkedList<>();

		ticket.setTicketId(ticketData.getTicketId());
		ticket.setTicketDate(ticketData.getTicketDate());
		ticket.setTicketDueDate(ticketData.getTicketDueDate());
		ticket.setTicketShipmentDate(ticketData.getTicketShipmentDate());

		// copy all the items in the ticket DTO to the Ticket Object Item list
		//for (ItemData item : items) {
		//items.addAll(ticketData.getItems());
		//
		//	}

	}

	// checks for existing order ticket and creates a new one if does not exist,
	// else finds ticket by Id
	private Ticket findOrCreateTicket(Long customerId, Long ticketId) {
		Ticket ticket;

		if (Objects.isNull(ticketId)) {
			ticket = new Ticket();
		} else {
			ticket = findTicketById(customerId, ticketId);
		}
		return ticket;
	}

	// finds the order ticket by ticketId using DTO
	private Ticket findTicketById(Long customerId, Long ticketId) {
		Ticket ticket = ticketDao.findById(ticketId).orElseThrow(() -> new NoSuchElementException());
		
		if(ticket.getCustomer().getCustomerId().equals(customerId)) {
			return ticket;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	// finds and deletes order ticket
	public void deleteTicketById(Long ticketId) {
		Ticket ticket = findTicketById(ticketId);
		ticketDao.delete(ticket);
	}
	
	//***************************************************************************************duplicate?
	private Ticket findTicketById(Long ticketId) {  
		return ticketDao.findById(ticketId).orElseThrow(() -> new NoSuchElementException());
	}

	
	// retrieves order ticket by ticket ID
	@Transactional(readOnly = true)
	public TicketData retrieveTicketById(Long ticketId) {
		Ticket ticket = findTicketById(ticketId);
		
		return new TicketData(ticket);
	}
	
	//retrieve all tickets for single customer by customer Id
	public List<TicketData> retrieveAllCustomerTicketsByCustomerId(Long customerId) {
		List<TicketData> result = new LinkedList<>();	//create a list for the results
		
		Customer customer = findCustomerById(customerId);	// assign the customer object
		for(Ticket ticket :customer.getTickets()) {			// move the data from the customer object to the ticket DTO
			TicketData tktData = new TicketData(ticket);
			
			result.add(tktData);
		}
		
		return result;
	}
	
	//modify ticket data by ticket id only
	public TicketData saveTicketData(TicketData ticketData) {
		Long ticketId = ticketData.getTicketId();
		Ticket ticket = findTicketById(ticketId);
		
		copyTicketFields(ticket, ticketData);
		
		Ticket tempTicketData = ticketDao.save(ticket);
		return new TicketData(tempTicketData);
	}
	
	/*****************************************************************************************************************************
	 **************************************************** Item Object Service Methods**********************************************
	 *****************************************************************************************************************************/
	//create the data layer object for customer autowired for JPA
	@Autowired
	private ItemDao itemDao;
	
	//inserts new item to item table
	@Transactional(readOnly = false)
	public ItemData saveItemData(ItemData itemData) {
		Long itemId = itemData.getItemId();
		Item item = findOrCreateItem(itemId, itemData.getItemUpc());

		copyItemFields(item, itemData);
		Item tempItem = itemDao.save(item);

		return new ItemData(tempItem);
	}
	// finds the item of creates it 
	private Item findOrCreateItem(Long itemId, String itemUpc) {
		Item item;
		
		// check for duplicate UPC by searching item table, if exists throw exception
		if(Objects.isNull(itemId)) {
			Optional<Item> opItem = itemDao.findByItemUpc(itemUpc);
			if (opItem.isPresent()) {
				throw new DuplicateKeyException("Item with UPC " + itemUpc + " already exists.");
			}
			item = new Item();
		} else {
			item = findItemById(itemId);
		}
		return item;
	}

	private Item findItemById(Long itemId) {
		return itemDao.findById(itemId).orElseThrow(() -> new NoSuchElementException());
	}

	//adds item to order ticket
	@Transactional(readOnly = false)
	public ItemData saveItemToTicket(Long ticketId, Long itemId) {
		//Long itemId = itemData.getItemId();
		Ticket ticket = findTicketById(ticketId);
		Item item = findItemById(itemId);

		//copyItemFields(item, itemData);

		ticket.getItems().add(item);
		item.getTickets().add(ticket);

		//Item tempItem = itemDao.save(item);

		return new ItemData(item);
	}

//	private Item findOrCreateItem(Long ticketId, Long itemId) {
//		Item item;
//		if(Objects.isNull(itemId)) {
//			item = new Item();
//		} else {
//			item = findItemById(ticketId, itemId);
//		}
//	//do I need to check if the item is already assigned to the order?
//		return item;
//	}
//
//	private Item findItemById(Long ticketId, Long itemId) {
//		Item item = itemDao.findById(itemId).orElseThrow(() -> new NoSuchElementException());
//		
//		for (Ticket tickets : item.getTickets()) {
//			if(tickets.getTicketId().equals(ticketId)) {
//				break;
//			} else {throw new IllegalArgumentException();
//				}
//		}
//		return item;
//	}

	private void copyItemFields(Item item, ItemData itemData) {
		item.setItemId(itemData.getItemId());
		item.setItemName(itemData.getItemName());;
		item.setItemPrice(itemData.getItemPrice());  
		item.setItemUpc(itemData.getItemUpc());
	}

	/*****************************************************************************************************************************
	 **************************************************** Customer Object Service Methods*****************************************
	 *****************************************************************************************************************************/
	//create the data layer object for customer autowired for JPA
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public CustomerData saveCustomerData(CustomerData customerData) {
		Long customerId = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId);
		
		copyCustomerFields(customer, customerData);
		
	//	move the database customer object info to the customer object
		Customer tempCustomerData = customerDao.save(customer);
		return new CustomerData(tempCustomerData);
		
	} //end method saveCustomerData

	private void copyCustomerFields(Customer customer, CustomerData customerData) {
		customer.setCustomerId(customerData.getCustomerId());
		customer.setCustomerName(customerData.getCustomerName());
		customer.setCustomerAddress(customerData.getCustomerAddress());
		customer.setCustomerPhone(customerData.getCustomerPhone());
		customer.setCustomerEmail(customerData.getCustomerEmail());
		
	} //end method copyCustomerFields

	private Customer findOrCreateCustomer(Long customerId) {
		Customer customer;
		
		if(Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(customerId);
		}
		return customer;
	} //end method findOrCreateCustomer

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException());
	}  //end method findCustomerBy Id

	// display list of all customers
	@Transactional
	public CustomerData retrieveCustomerById(Long customerId) {
		Customer customer = findCustomerById(customerId);

		return new CustomerData(customer);
	}
}
