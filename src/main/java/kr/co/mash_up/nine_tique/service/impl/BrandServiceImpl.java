package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Brand;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.BrandRepository;
import kr.co.mash_up.nine_tique.service.BrandService;
import kr.co.mash_up.nine_tique.vo.BrandVO;

/**
 * Created by ethankim on 2017. 7. 3..
 */
@Service(value = "brandService")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Transactional
    @Override
    public void addBrand(BrandVO brandVO) {
        Optional<Brand> brand = brandRepository.findByNameKo(brandVO.getNameKo());
        brand.orElseThrow(() -> new AlreadyExistException("brand add -> brand already exist"));
        brandRepository.save(brandVO.toBrandEntity());
    }

    @Transactional
    @Override
    public void modifyBrand(Long brandId, BrandVO brandVO) {
        Brand oldBrand = brandRepository.findOne(brandId);
        Optional.ofNullable(oldBrand).orElseThrow(() -> new IdNotFoundException("brand modify -> brand not found"));
        oldBrand.update(brandVO.toBrandEntity());
        brandRepository.save(oldBrand);
    }

    @Transactional
    @Override
    public void removeBrand(Long brandId) {
        Brand brand = brandRepository.findOne(brandId);
        Optional.ofNullable(brand).orElseThrow(() -> new IdNotFoundException("brand remove -> brand not found"));
        brandRepository.delete(brandId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BrandVO> readBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> new BrandVO.Builder()
                        .withNameKo(brand.getNameKo())
                        .withNameEng(brand.getNameEng())
                        .build()
                ).collect(Collectors.toList());
    }
}
