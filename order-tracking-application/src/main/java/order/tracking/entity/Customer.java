package order.tracking.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Customer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	
	@Column(unique = true)
	private String customerEmail;
	
	//Customer can have many tickets 
	//define one customer to many tickets relationship customer to ticket, with customer being the owning side
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "customer", 
			cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ticket> tickets = new HashSet<>();
}
