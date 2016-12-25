INSERT INTO category(main, sub, created_at) VALUES ('OUTER', 'JACKET', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('OUTER', 'COATS', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('OUTER', 'JUMPER', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('OUTER', 'HOODS_ZIPUP', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('OUTER', 'VEST', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'TSHIRT', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'HOODS_TSHIRT', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'SLEEVELESS', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'SHIRT', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'KNIT', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'BLOUSE', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('TOP', 'ONE_PIECE', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('BOTTOM', 'DENIM_PANTS', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('BOTTOM', 'PANTS', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('BOTTOM', 'SHORTS', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('BOTTOM', 'SKIRT', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('SHOSE', '', CURRENT_TIMESTAMP());
INSERT INTO category(main, sub, created_at) VALUES ('CAP', '', CURRENT_TIMESTAMP());

INSERT INTO user(name, email, oauth_token, created_at) VALUES ('admin', '9tique@naver.com', '9tique', CURRENT_TIMESTAMP());
INSERT INTO user(name, email, oauth_token, created_at) VALUES ('유저2', 'bb@naver.com', 'bbfsdfsd434', CURRENT_TIMESTAMP());
INSERT INTO user(name, email, oauth_token, created_at) VALUES ('유저3', 'cc@naver.com', 'ccfsdfsd434', CURRENT_TIMESTAMP());
INSERT INTO user(name, email, oauth_token, created_at) VALUES ('유저4', 'dd@naver.com', 'ddfsdfsd434', CURRENT_TIMESTAMP());

INSERT INTO authority(authority) VALUES ('USER');
INSERT INTO authority(authority) VALUES ('SELLER');
INSERT INTO authority(authority) VALUES ('ADMIN');

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 3);
INSERT INTO user_authority(user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority(user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority(user_id, authority_id) VALUES (4, 1);


INSERT INTO seller_info(shop_name, shop_info, phone, created_at, user_id) VALUES ('매장1', '매장1 정보', '010-0000-0000', CURRENT_TIMESTAMP(), 2);
INSERT INTO seller_info(shop_name, shop_info, phone, created_at, user_id) VALUES ('매장2', '매장2 정보', '010-0000-0001', CURRENT_TIMESTAMP(), 3);


INSERT INTO zzim(user_id, created_at) VALUES (1, CURRENT_TIMESTAMP());
INSERT INTO zzim(user_id, created_at) VALUES (2, CURRENT_TIMESTAMP());
INSERT INTO zzim(user_id, created_at) VALUES (3, CURRENT_TIMESTAMP());
INSERT INTO zzim(user_id, created_at) VALUES (4, CURRENT_TIMESTAMP());


INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품1', '상품1 브렌드', '상품1 설명', 1000, 'SELL',  '상품1 사이즈', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품2', '상품2 브렌드', '상품2 설명', 2000, 'SOLD_OUT',  '상품2 사이즈', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품3', '상품3 브렌드', '상품3 설명', 30000, 'SOLD_OUT',  '상품3 사이즈', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품4', '상품4 브렌드', '상품4 설명', 40000, 'SELL',  '상품4 사이즈', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품5', '상품5 브렌드', '상품5 설명', 50000, 'SELL',  '상품5 사이즈', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품6', '상품6 브렌드', '상품6 설명', 60000, 'SELL',  '상품6 사이즈', CURRENT_TIMESTAMP(), 2, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품7', '상품7 브렌드', '상품7 설명', 70000, 'SOLD_OUT',  '상품7 사이즈', CURRENT_TIMESTAMP(), 2, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품8', '상품8 브렌드', '상품8 설명', 80000, 'SOLD_OUT',  '상품8 사이즈', CURRENT_TIMESTAMP(), 2, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품9', '상품9 브렌드', '상품9 설명', 90000, 'SELL',  '상품9 사이즈', CURRENT_TIMESTAMP(), 2, 1);
INSERT INTO product(name, brand_name, description, price, status, size, created_at, category_id, seller_info_id) VALUES ('상품10', '상품10 브렌드', '상품10 설명', 100000, 'SELL',  '상품10 사이즈', CURRENT_TIMESTAMP(), 17, 1);