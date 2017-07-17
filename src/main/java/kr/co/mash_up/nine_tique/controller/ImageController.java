package kr.co.mash_up.nine_tique.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.service.ImageService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_IMAGE;

@RestController
@RequestMapping(value = API_IMAGE)
@Slf4j
@Api(description = "이미지", tags = {"image"})
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "이미지 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping(value = "")
    public DataListResponseVO<ImageDto> addImages(@RequestParam(name = "files") List<MultipartFile> files) {
        log.info("addImages - files : {}", files);

        ParameterUtil.checkParameterEmpty(files);
        List<ImageDto> images = imageService.addImages(files);
        return new DataListResponseVO<>(images);
    }
}
