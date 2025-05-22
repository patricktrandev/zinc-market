package cloud.tientn.zinc.repository;

import cloud.tientn.zinc.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Boolean existsByCode(String code);
    Optional<Coupon> findByCode(String code);
}
