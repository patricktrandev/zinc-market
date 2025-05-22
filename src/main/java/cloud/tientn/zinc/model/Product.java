package cloud.tientn.zinc.model;

import cloud.tientn.zinc.config.BaseAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "is_active")
    private Boolean isActive;
    private Integer quantity;
    private Float price;
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public void addCategoryToProduct(Category category){
        this.setCategory(category);
        category.getProducts().add(this);
    }
    public void removeCategoryToProduct(Category category){
        this.setCategory(null);
        category.getProducts().remove(category);
    }


}
