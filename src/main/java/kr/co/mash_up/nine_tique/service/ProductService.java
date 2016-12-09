package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ProductImageRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerInfoRepository;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    //Todo: seller info 정보 출력. 현재 null임
    /*
    ManyToOne은 알아서 join을 안한다.
    OneToMany는 알아서 join을 한다.
    트랜잭션안에서 1번이라도 쿼리를 날리면 join된다.
     */
    @Transactional(readOnly = true)
    public Page<Product> findProductsByCategory(ProductListRequestVO requestVO) {
        Category category = null;

        Pageable pageable = requestVO.getPageable();
        String mainCategory = requestVO.getMainCategory();
        String subCategory = requestVO.getSubCategory();

        if (mainCategory != null && subCategory != null) {
            category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
            log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());
        }

        return productRepository.findByCategory(pageable, category);
    }

    @Transactional(readOnly = true)
    public Page<Product> findProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    @Transactional
    public Product update(Long id, ProductRequestVO requestVO) {
        Product oldProduct = productRepository.findOne(id);

        if (oldProduct != null) {

            // 바뀐 정보만 update
            String name = requestVO.getName();
            if (!ParameterUtil.isEmpty(name)) {
                oldProduct.setName(name);
            }

            String brandName = requestVO.getBrandName();
            if (!ParameterUtil.isEmpty(brandName)) {
                oldProduct.setBrandName(brandName);
            }

            String size = requestVO.getSize();
            if (!ParameterUtil.isEmpty(size)) {
                oldProduct.setSize(size);
            }

            int price = requestVO.getPrice();
            if (!ParameterUtil.isEmpty(price)) {
                oldProduct.setPrice(price);
            }

            String description = requestVO.getDescription();
            if (!ParameterUtil.isEmpty(description)) {
                oldProduct.setDescription(description);
            }

            long sellerId = requestVO.getSellerId();
            if (!ParameterUtil.isEmpty(sellerId)) {
                SellerInfo sellerInfo = sellerInfoRepository.findOne(sellerId);
                oldProduct.setSellerInfo(sellerInfo);
            }

            String mainCategory = requestVO.getMainCategory();
            String subCategory = requestVO.getSubCategory();  // ""가 있어서 체크하지 않는다.
            if (!ParameterUtil.isEmpty(mainCategory)) {
                Category category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
                oldProduct.setCategory(category);
            }

            String productStatus = requestVO.getProductStatus();
            if (productStatus.equals("SELL")) {
                oldProduct.setProductStatus(ProductStatus.SELL);
            } else if (productStatus.equals("SOLD_OUT")) {
                oldProduct.setProductStatus(ProductStatus.SOLD_OUT);
            }

            List<MultipartFile> files = requestVO.getFiles();
            if (!ParameterUtil.isEmpty(files)) {
                saveMultipartFile(files, oldProduct);
            }
        }

        return productRepository.save(oldProduct);
    }

    @Transactional
    public Product save(ProductRequestVO requestVO) {
        Product product = requestVO.toProductEntity();

        SellerInfo sellerInfo = sellerInfoRepository.findOne(requestVO.getSellerId());
        product.setSellerInfo(sellerInfo);

        Category category = categoryRepository.findByMainAndSubAllIgnoreCase(requestVO.getMainCategory(), requestVO.getSubCategory());
        product.setCategory(category);

        if (requestVO.getProductStatus().equals("SELL")) {
            product.setProductStatus(ProductStatus.SELL);
        } else if (requestVO.getProductStatus().equals("SOLD_OUT")) {
            product.setProductStatus(ProductStatus.SOLD_OUT);
        }

        Product savedProduct = productRepository.save(product);

        List<MultipartFile> files = requestVO.getFiles();
        saveMultipartFile(files, savedProduct);

        return savedProduct;
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(id);
    }

    private void saveMultipartFile(List<MultipartFile> files, Product product){

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                ProductImage productImage = new ProductImage();

                // 고유이름 생성
                String saveName = UUID.randomUUID().toString() +
                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                log.debug(saveName);

                productImage.setFileName(saveName);
                productImage.setOriginalFileName(file.getOriginalFilename());
                productImage.setSize(file.getSize());
                productImage.setProduct(product);

                FileUtil.upload(file, productImage.getImageUploadPath(), saveName);  // 저장

//                FileUtil.upload(file, FileUtil.getImageUploadPath(savedProduct.getId()), saveName);
//                productImage.setImageUrl(FileUtil.getImageUrl(savedProduct.getId(), saveName));

                productImageRepository.save(productImage);
                log.debug(file.getOriginalFilename());
            }
        }

    }
}
