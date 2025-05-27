package cloud.tientn.zinc.model;

import cloud.tientn.zinc.config.BaseAudit;
import cloud.tientn.zinc.utils.PriorityHelper;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "processOrder",
                procedureName = "create_order_and_calculate_total",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_customer_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_shipping_address", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_discount", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_priority", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_status", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_order_items", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_order_id", type = Long.class)
                }
        )
})


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "orders")
public class Order extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "processing_date")
    private LocalDateTime processingDate;
    private Double total;
    private Double discount;
    private String status;
    private String priority;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "coupon_id")
    private Long coupon;



    public boolean checkId( Long id){
        return this.customer.getId().equals(id);
    }
    public int calculatePriorityLevel(String priority) {
        int result= PriorityHelper.comparePriority(this.priority, priority);
        return result;
    }

}
