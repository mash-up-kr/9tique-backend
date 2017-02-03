package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.repository.ProductImageRepository;
import kr.co.mash_up.nine_tique.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Product Image와 관련된 비즈니스 로직
 */
@Service(value = "productImageService")
@Slf4j
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Transactional
    public List<ProductImageDto> create(List<MultipartFile> files) {
        Long createdAtUnixTimestamp = System.currentTimeMillis() / 1000;

        List<ProductImageDto> productImageDtos = files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> {
                    ProductImage productImage = new ProductImage();

                    // 고유이름 생성(uuid + unix timestamp)
                    String saveName = UUID.randomUUID().toString() + "_" + createdAtUnixTimestamp
                            + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                    productImage.setFileName(saveName);
                    productImage.setOriginalFileName(file.getOriginalFilename());
                    productImage.setSize(file.getSize());
                    productImage.setEnabled(true);

                    // 파일 저장
                    FileUtil.upload(file, productImage.getImageUploadTempPath(), saveName);

//                FileUtil.upload(file, FileUtil.getImageUploadPath(savedProduct.getId()), saveName);
//                productImage.setImageUrl(FileUtil.getImageUrl(savedProduct.getId(), saveName));

                    ProductImage savedProductImage = productImageRepository.save(productImage);
                    log.debug(savedProductImage.getFileName());

                    return new ProductImageDto.Builder()
                            .withUrl(savedProductImage.getTempImageUrl())
                            .build();
                }).collect(Collectors.toList());

        return productImageDtos;
    }
}
