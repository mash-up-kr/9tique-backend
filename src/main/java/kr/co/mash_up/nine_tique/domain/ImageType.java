package kr.co.mash_up.nine_tique.domain;

/**
 * Created by ethankim on 2017. 7. 17..
 */
public enum ImageType {
    // 상품
    PRODUCT,
    // 프로모션
    PROMOTION,
    // 게시물
    POST,
    // 매장
    SHOP;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
