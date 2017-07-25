package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostComment;
import kr.co.mash_up.nine_tique.domain.PostImage;
import kr.co.mash_up.nine_tique.domain.PostProduct;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.dto.CommentDto;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.dto.PostDto;
import kr.co.mash_up.nine_tique.dto.UserDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.ImageRepository;
import kr.co.mash_up.nine_tique.repository.PostCommentRepository;
import kr.co.mash_up.nine_tique.repository.PostRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override
    public void removePost(Long userId, Long postId) {
        // Todo: 작성자에 대한 검증이 필요한가..?
        Optional<Post> postOptional = postRepository.findOneByPostId(postId);
        postOptional.orElseThrow(() -> new IdNotFoundException("removePost -> post not found"));

        // dir move tmp dir to post id dir
        FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.POST, postId), FileUtil.getImageUploadTempPath());
        postRepository.delete(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostDto> readPosts(DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<Post> postPage = postRepository.findPosts(pageable);

        List<PostDto> posts = postPage.getContent().stream()
                .map(post -> {
                    List<ImageDto> images = post.getPostImages().stream()
                            .map(PostImage::getImage)
                            .map(image ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.POST, post.getId(), image.getFileName()))
                                            .build())
                            .collect(Collectors.toList());

                    return new PostDto.Builder()
                            .id(post.getId())
                            .name(post.getName())
                            .contents(post.getContents())
                            .images(images)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(postPage.getNumber(), postPage.getSize());

        return new PageImpl<PostDto>(posts, resultPageable, postPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto readPost(Long postId) {
        Optional<Post> postOptional = postRepository.findOneByPostId(postId);
        Post post = postOptional.orElseThrow(() -> new IdNotFoundException("readPost -> post not found"));

        Image image = post.getPostImages().get(0).getImage();
        ImageDto imageDto = new ImageDto.Builder()
                .url(FileUtil.getImageUrl(ImageType.POST, postId, image.getFileName()))
                .build();

        List<ImageDto> images = new ArrayList<>();
        images.add(imageDto);

        return new PostDto.Builder()
                .id(postId)
                .name(post.getName())
                .contents(post.getContents())
                .images(images)
                .build();
    }

    @Transactional
    @Override
    public void addPostComment(Long postId, Long userId, CommentRequestVO requestVO) {
        Optional<Post> postOptional = postRepository.findOneByPostId(postId);
        Post post = postOptional.orElseThrow(() -> new IdNotFoundException("addPostComment -> post not found"));

        User writer = userRepository.findOne(userId);

        PostComment comment = new PostComment(post, writer, requestVO.getContents());
        post.addComment(comment);

        postRepository.save(post);
    }

    @Transactional
    @Override
    public void modifyPostComment(Long postId, Long commentId, Long userId, CommentRequestVO requestVO) {
        Optional<PostComment> commentOptional = postCommentRepository.findOneByPostIdAndCommentId(postId, commentId);
        PostComment comment = commentOptional.orElseThrow(() -> new IdNotFoundException("modifyPostComment -> comment not found"));

        User user = userRepository.findOne(userId);
        if (!comment.matchWriter(user)) {
            throw new UserIdNotMatchedException("modifyPostComment -> forbbiden access");
        }

        comment.update(requestVO.getContents());
        postCommentRepository.save(comment);
    }

    @Transactional
    @Override
    public void removePostComment(Long postId, Long commentId, Long userId) {
        Optional<PostComment> commentOptional = postCommentRepository.findOneByPostIdAndCommentId(postId, commentId);
        PostComment comment = commentOptional.orElseThrow(() -> new IdNotFoundException("removePostComment -> comment not found"));

        User user = userRepository.findOne(userId);
        if (!comment.matchWriter(user)) {
            throw new UserIdNotMatchedException("removePostComment -> forbbiden access");
        }

        Post post = comment.getPost();
        post.removeComment();

        postCommentRepository.delete(comment);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentDto> readPostComments(Long postId, DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<PostComment> postCommentPage = postCommentRepository.findPostComments(postId, pageable);

        List<CommentDto> postComments = postCommentPage.getContent().stream()
                .map(postComment -> {
                    User writer = postComment.getWriter();

                    UserDto userDto = new UserDto.Builder()
                            .name(writer.getName())
                            .profileImageUrl(writer.getProfileImageUrl())
                            .build();

                    return new CommentDto.Builder()
                            .id(postComment.getId())
                            .contents(postComment.getContents())
                            .writer(userDto)
                            .updatedAt(postComment.getUpdatedTimestamp())
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(postCommentPage.getNumber(), postCommentPage.getSize());
        return new PageImpl<>(postComments, resultPageable, postCommentPage.getTotalElements());
    }
}
