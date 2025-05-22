package cloud.tientn.zinc.service;

import cloud.tientn.zinc.model.Coupon;
import cloud.tientn.zinc.response.CouponDto;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(CouponDto couponDto);
    List<Coupon> findAllCoupon();
    Coupon findCouponByCode(String code);
    Coupon updateCoupon(Long id, CouponDto couponDto);
    Coupon updateCouponStatus(Long id);
}
