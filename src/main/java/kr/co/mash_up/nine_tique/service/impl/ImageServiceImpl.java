package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.repository.ImageRepository;
import kr.co.mash_up.nine_tique.service.ImageService;
import kr.co.mash_up.nine_tique.util.CodeGeneratorUtil;
import kr.co.mash_up.nine_tique.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    @Override
    public List<ImageDto> addImages(List<MultipartFile> files) {
        return files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> {
                    String fileName = CodeGeneratorUtil.generateFileName(file.getOriginalFilename());

                    Image image = new Image();
                    image.setFileName(fileName);
                    image.setOriginalFileName(file.getOriginalFilename());
                    image.setSize(file.getSize());

                    // 파일 저장
                    FileUtil.upload(file, FileUtil.getImageUploadTempPath(), fileName);

                    Image savedImage = imageRepository.save(image);
                    log.debug(savedImage.getFileName());

                    return new ImageDto.Builder()
                            .url(FileUtil.getTempImageUrl(fileName))
                            .build();
                }).collect(Collectors.toList());
    }
}
