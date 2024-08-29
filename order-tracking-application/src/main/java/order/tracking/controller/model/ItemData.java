package order.tracking.controller.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import order.tracking.entity.Item;

@Data
@NoArgsConstructor
public class ItemData {

	private Long itemId;
	private String itemName;
	private BigDecimal itemPrice;  
	private String itemUpc;
	
	//Define OrderTrackingItem DTO Constructor 
	public ItemData(Item item) {
		itemId = item.getItemId();
		itemName = item.getItemName();
		itemPrice = item.getItemPrice();
		itemUpc = item.getItemUpc();
	}
}
