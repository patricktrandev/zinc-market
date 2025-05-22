package cloud.tientn.zinc.utils;

import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.model.Coupon;
import cloud.tientn.zinc.model.Product;
import cloud.tientn.zinc.repository.CategoryRepository;
import cloud.tientn.zinc.repository.CouponRepository;
import cloud.tientn.zinc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class DBInitialization implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        Category c= new Category();
        c.setName("Electric");

        Category c1= new Category();
        c1.setName("Cosmetic");
        categoryRepository.save(c);
        categoryRepository.save(c1);

        Product p= new Product();
        p.setName("Laptop");
        p.setPrice(999.0F);
        p.setIsActive(true);
        p.setDescription("test description");
        p.setThumbnail("test thumbnail");
        p.setQuantity(5);
        p.setCategory(c);
        Product p1= new Product();
        p1.setName("Headphone");
        p1.setPrice(99.0F);
        p1.setIsActive(true);
        p1.setDescription("test description");
        p1.setThumbnail("test thumbnail");
        p1.setQuantity(3);
        p1.setCategory(c);
        Product p2= new Product();
        p2.setName("Face wash");
        p2.setPrice(9.0F);
        p2.setIsActive(true);
        p2.setDescription("test description");
        p2.setThumbnail("test thumbnail");
        p2.setQuantity(3);
        p2.setCategory(c1);
        productRepository.save(p);
        productRepository.save(p1);
        productRepository.save(p2);

        Coupon coupon= new Coupon();
        coupon.setCode("OFF10");
        coupon.setDiscount(10);
        coupon.setDescription("Off 10% for all products");
        coupon.setIsActive(true);
        couponRepository.save(coupon);
    }
}
