INSERT INTO category(main, sub, created_at) VALUES ('아우터', '자켓', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('아우터', '코트', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('아우터', '점퍼', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('아우터', '후드집업', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('아우터', '베스트', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '티셔츠', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '후드 티셔츠', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '슬리브리스', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '셔츠', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '니트', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '블라우스', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('상의', '원피스', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('하의', '데님팬츠', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('하의', '팬츠', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('하의', '쇼츠', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('하의', '스커트', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('신발', '', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('모자', '', CURRENT_TIMESTAMP());


INSERT INTO user(name, email, oauth_token, access_token, created_at) VALUES ('유저1', 'aa@naver.com', 'aafsdfsd434', 'aafsdfsd434', CURRENT_TIMESTAMP());
INSERT INTO user(name, email, oauth_token, access_token, created_at) VALUES ('유저2', 'bb@naver.com', 'bbfsdfsd434', 'bbfsdfsd434', CURRENT_TIMESTAMP());
INSERT INTO user(name, email, oauth_token, access_token, created_at) VALUES ('유저3', 'cc@naver.com', 'ccfsdfsd434', 'ccfsdfsd434', CURRENT_TIMESTAMP());
INSERT INTO user(name, email, oauth_token, access_token, created_at) VALUES ('유저4', 'dd@naver.com', 'ddfsdfsd434', 'ddfsdfsd434', CURRENT_TIMESTAMP());


INSERT INTO seller_info(shop_name, shop_info, phone, created_at, user_id) VALUES ('매장1', '매장1 정보', '010-0000-0000', CURRENT_TIMESTAMP(),1);
INSERT INTO seller_info(shop_name, shop_info, phone, created_at, user_id) VALUES ('매장2', '매장2 정보', '010-0000-0001', CURRENT_TIMESTAMP(), 2);


INSERT INTO product(name, description, product_status, created_at, category_id, seller_info_id) VALUES ('상품1', '상품1 설명', 'SELL', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO product(name, description, product_status, created_at, category_id, seller_info_id) VALUES ('상품2', '상품2 설명', 'SELL', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO product(name, description, product_status, created_at, category_id, seller_info_id) VALUES ('상품3', '상품3 설명', 'SOLD_OUT', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO product(name, description, product_status, created_at, category_id, seller_info_id) VALUES ('상품4', '상품4 설명', 'SOLD_OUT', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO product(name, description, product_status, created_at, category_id, seller_info_id) VALUES ('상품5', '상품5 설명', 'SELL', CURRENT_TIMESTAMP(), 1, 1);



