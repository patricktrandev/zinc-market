package cloud.tientn.zinc.repository;

import cloud.tientn.zinc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);

    List<Product> findAllByCategory_Id(Long id);
    @Query(nativeQuery = true, value = "SELECT * FROM product WHERE id IN (:ids)")
    List<Product> findAllByIds(@Param("ids") Set<Long> ids);

}
