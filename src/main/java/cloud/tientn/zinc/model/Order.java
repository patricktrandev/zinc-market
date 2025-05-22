package cloud.tientn.zinc.model;

import cloud.tientn.zinc.config.BaseAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "processOrder",
                procedureName = "create_order_and_calculate_total",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_customer_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_shipping_address", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_discount", type = Double.class),
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
@Table(name = "orders")
public class Order extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "processing_date")
    private Date processingDate;
    private Double total;
    private Double discount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "coupon_id")
    private Long coupon;

}
