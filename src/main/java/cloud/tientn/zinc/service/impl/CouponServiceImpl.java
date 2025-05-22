package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.exception.ResourceAlreadyExistException;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Coupon;
import cloud.tientn.zinc.repository.CouponRepository;
import cloud.tientn.zinc.response.CouponDto;
import cloud.tientn.zinc.response.converter.CouponMapper;
import cloud.tientn.zinc.service.CouponService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(CouponDto couponDto) {
        boolean check = couponRepository.existsByCode(couponDto.getCode());
        if(check){
            throw new ResourceAlreadyExistException(couponDto.getCode());
        }
        couponDto.setIsActive(true);
        Coupon coupon= couponRepository.save(CouponMapper.convertToModel(couponDto));
        return coupon;
    }

    @Override
    public List<Coupon> findAllCoupon() {
        List<Coupon> coupons=couponRepository.findAll();
        return coupons;
    }

    @Override
    public Coupon findCouponByCode(String code) {
        Coupon coupon=couponRepository.findByCode(code).orElseThrow(()-> new ResourceNotFoundException("Code",code));
        return coupon;
    }

    @Override
    public Coupon updateCoupon(Long id, CouponDto couponDto) {
        Coupon coupon=couponRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Code",id));
        coupon.setCode(couponDto.getCode());
        Coupon updated=couponRepository.save(coupon);
        return updated;
    }

    @Override
    public Coupon updateCouponStatus(Long id) {
        Coupon coupon=couponRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Code",id));
        coupon.setIsActive(false);
        Coupon updated=couponRepository.save(coupon);
        return updated;
    }


}
