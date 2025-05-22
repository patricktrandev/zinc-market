package cloud.tientn.zinc.controller;

import cloud.tientn.zinc.response.CouponDto;
import cloud.tientn.zinc.response.Response;
import cloud.tientn.zinc.response.converter.CouponMapper;
import cloud.tientn.zinc.service.CouponService;
import cloud.tientn.zinc.utils.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Response> createCoupon(@RequestBody CouponDto couponDto){
        CouponDto coupon=CouponMapper.convertToDto(couponService.createCoupon(couponDto));
        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create Coupon successfully", coupon), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Response> findAllCoupon(){
        Map<String, Object> data= new HashMap<>();
        List<CouponDto> couponDtos= couponService.findAllCoupon().stream().map(CouponMapper::convertToDto).toList();
        data.put("coupons",couponDtos);
        data.put("couponSize",couponDtos.size());

        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find all Coupon successfully",data), HttpStatus.OK);
    }
    @GetMapping("/{code}")
    public ResponseEntity<Response> findCouponById(@PathVariable String code){
        CouponDto couponDto= CouponMapper.convertToDto(couponService.findCouponByCode(code));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find one Coupon successfully",couponDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> updateCouponStatusById(@PathVariable Long id){
        CouponDto updated= CouponMapper.convertToDto(couponService.updateCouponStatus(id));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Update Coupon status successfully", updated), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCouponById(@PathVariable Long id, @Valid @RequestBody CouponDto couponDto){
        CouponDto updated= CouponMapper.convertToDto(couponService.updateCoupon(id, couponDto));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Update Coupon successfully", updated), HttpStatus.OK);
    }
}
