package kr.co.mash_up.nine_tique.builder;

import kr.co.mash_up.nine_tique.domain.Shop;

/**
 * Created by Dong on 2017-02-09.
 */
public class ShopBuilder {

    private Long id;

    private String name;

    private String description;

    private String phoneNumber;

    private String kakaoOpenChatUrl;

    private Long commentCount;

    public ShopBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public ShopBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ShopBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public ShopBuilder withPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ShopBuilder withKakaoOpenChatUrl(String kakaoOpenChatUrl){
        this.kakaoOpenChatUrl = kakaoOpenChatUrl;
        return this;
    }

    public ShopBuilder withCommentCount(Long commentCount){
        this.commentCount = commentCount;
        return this;
    }

    public Shop build(){
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setDescription(description);
        shop.setPhoneNumber(phoneNumber);
        shop.setKakaoOpenChatUrl(kakaoOpenChatUrl);
        shop.setCommentCount(commentCount);
        return shop;
    }
}
