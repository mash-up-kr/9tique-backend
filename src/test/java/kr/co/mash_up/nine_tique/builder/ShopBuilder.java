package kr.co.mash_up.nine_tique.builder;

import kr.co.mash_up.nine_tique.domain.Shop;

/**
 * Created by Dong on 2017-02-09.
 */
public class ShopBuilder {

    private long id;

    private String name;

    private String description;

    private String phoneNumber;

    private String kakaoOpenChatUrl;

    private long commentCount;

    public ShopBuilder id(long id){
        this.id = id;
        return this;
    }

    public ShopBuilder name(String name){
        this.name = name;
        return this;
    }

    public ShopBuilder description(String description){
        this.description = description;
        return this;
    }

    public ShopBuilder phoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ShopBuilder kakaoOpenChatUrl(String kakaoOpenChatUrl){
        this.kakaoOpenChatUrl = kakaoOpenChatUrl;
        return this;
    }

    public ShopBuilder commentCount(long commentCount){
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
