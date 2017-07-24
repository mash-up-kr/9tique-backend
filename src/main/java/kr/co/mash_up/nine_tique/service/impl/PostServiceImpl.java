package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostImage;
import kr.co.mash_up.nine_tique.domain.PostProduct;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.dto.CommentDto;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.dto.PostDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.ImageRepository;
import kr.co.mash_up.nine_tique.repository.PostRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.service.PostService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.vo.CommentRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.PostRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by ethankim on 2017. 7. 24..
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void addPost(Long userId, PostRequestVO requestVO) {
        // Todo: 작성자에 대한 검증이 필요한가..?

        Post post = new Post();
        post.setName(requestVO.getName());
        post.setContents(requestVO.getContents());
        post.setCommentCount(0L);
        post.setPostImages(new ArrayList<>());
        post.setPostProducts(new ArrayList<>());
        postRepository.save(post);

        requestVO.getProducts()
                .forEach(productVO -> {
                    Product product = productRepository.findOneByProductId(productVO.getId());
                    post.addProduct(new PostProduct(post, product));
                });

        requestVO.getImages()
                .forEach(imageDto -> {
                    Optional<Image> imageOptional = imageRepository.findByFileName(FileUtil.getFileName(imageDto.getUrl()));
                    Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("addPost -> image not found"));

                    post.addImage(new PostImage(post, image));

                    // file move tmp dir to promotion id dir
                    FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                            FileUtil.getImageUploadPath(ImageType.POST, post.getId()));
                });
    }

    @Override
    public void modifyPost(Long userId, Long postId, PostRequestVO requestVO) {
        // Todo: 작성자에 대한 검증이 필요한가..?

        Optional<Post> postOptional = postRepository.findOneByPostId(postId);
        Post post = postOptional.orElseThrow(() -> new IdNotFoundException("modifyPost -> post not found"));

        post.setName(requestVO.getName());
        post.setContents(requestVO.getContents());

        List<PostProduct> oldProducts = post.getPostProducts();
        List<ProductRequestVO> updateProducts = requestVO.getProducts();

        // 게시물 상품 삭제
        Map<Long, ProductRequestVO> productMapUpdate = updateProducts.stream()
                .collect(Collectors.toMap(ProductRequestVO::getId, productRequestVO -> productRequestVO));
        oldProducts.removeIf(postProduct -> productMapUpdate.get(postProduct.getProductId()) == null);

        // 게시물 상품 추가
        Map<Long, PostProduct> productMapToOld = oldProducts.stream()
                .collect(Collectors.toMap(PostProduct::getProductId, postProduct -> postProduct));
        updateProducts.forEach(productVO -> {
            if (productMapToOld.get(productVO.getId()) == null) {
                Product product = productRepository.findOneByProductId(productVO.getId());
                post.addProduct(new PostProduct(post, product));
            }
        });

        List<PostImage> oldImages = post.getPostImages();
        List<ImageDto> updateImages = requestVO.getImages();

        // 게시물 이미지 삭제
        Map<String, ImageDto> imageMapToUpdate = updateImages.stream()
                .collect(Collectors.toMap(image -> FileUtil.getFileName(image.getUrl()), image -> image));

        Iterator<PostImage> imageIterator = oldImages.iterator();
        while (imageIterator.hasNext()) {
            PostImage oldImage = imageIterator.next();

            if (imageMapToUpdate.get(oldImage.getImage().getFileName()) == null) {
                // Todo: 2017.07.19 바로 파일 삭제할지 선택
                FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.POST, postId) + "/" + oldImage.getImage().getFileName()
                        , FileUtil.getImageUploadTempPath());
                imageIterator.remove();
            }
        }

        // 게시물 이미지 추가
        updateImages.forEach(imageDto -> {
            Optional<Image> imageOptional = imageRepository.findByFileName(FileUtil.getFileName(imageDto.getUrl()));
            Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("modifyPost -> image not found"));

            post.addImage(new PostImage(post, image));

            // file move tmp dir to promotion id dir
            FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                    FileUtil.getImageUploadPath(ImageType.POST, postId));
        });

        postRepository.save(post);
    }

    @Override
    public void removePost(Long userId, Long postId) {
        // Todo: 작성자에 대한 검증이 필요한가..?
        Optional<Post> postOptional = postRepository.findOneByPostId(postId);
        postOptional.orElseThrow(() -> new IdNotFoundException("removePost -> post not found"));

        // dir move tmp dir to post id dir
        FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.POST, postId), FileUtil.getImageUploadTempPath());
        postRepository.delete(postId);
    }

    @Override
    public Page<PostDto> readPosts(DataListRequestVO requestVO) {
        // 이미지 1개, 제목
        return null;
    }

    @Override
    public PostDto readPost(Long postId) {
        // 이미지 전부, 내용, 제목
        return null;
    }

    @Override
    public void addPostComment(Long postId, Long userId, CommentRequestVO requestVO) {

    }

    @Override
    public void modifyPostComment(Long postId, Long commentId, Long userId, CommentRequestVO requestVO) {

    }

    @Override
    public void removePostComment(Long postId, Long commentId, Long userId) {

    }

    @Override
    public Page<CommentDto> readPostComments(Long postId, DataListRequestVO requestVO) {
        return null;
    }
}
