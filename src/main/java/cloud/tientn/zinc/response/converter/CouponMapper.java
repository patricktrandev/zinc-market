package cloud.tientn.zinc.response.converter;

import cloud.tientn.zinc.model.Coupon;
import cloud.tientn.zinc.response.CouponDto;

public class CouponMapper  {
    public static CouponDto convertToDto(Coupon coupon){
        return CouponDto.builder()
                .id(coupon.getId())

                .code(coupon.getCode())
                .description(coupon.getDescription())
                .isActive(coupon.getIsActive())
                .discount(coupon.getDiscount())
                .build();
    }

    public static Coupon convertToModel(CouponDto coupon){
        Coupon c= new Coupon();
        c.setId(coupon.getId());
        c.setCode(coupon.getCode());
        c.setIsActive(coupon.getIsActive());
        c.setDescription(coupon.getDescription());
        c.setDiscount(coupon.getDiscount());
        return c;
    }
}
