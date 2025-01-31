package order.tracking.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
	private String itemName;
	private BigDecimal itemPrice;
	
	@Column(unique = true)
	private String itemUpc;
	
	//Items are contained in many tickets for many customers
	//set up many to many relationship with order table.  This is the owned side of the many to many
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "items", 
			cascade = CascadeType.PERSIST)
	private Set<Ticket> tickets = new HashSet<>();
	
}
