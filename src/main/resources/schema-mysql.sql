-- MySQL Workbench Synchronization
-- Generated: 2017-06-27 01:29
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: ethan kim

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `nine_tique`.`authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`authority` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(20) NOT NULL COMMENT '권한 이름',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '권한';

-- -----------------------------------------------------
-- Table `nine_tique`.`brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`brand` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '브랜드 이름',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '상품의 브랜드';

-- -----------------------------------------------------
-- Table `nine_tique`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`category` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `main` VARCHAR(255) NOT NULL COMMENT '상위 카테고리 이름',
  `sub` VARCHAR(255) NULL DEFAULT NULL COMMENT '하위 카테고리 이름',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`product_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`product_image` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 이름(서버에서 중복되지 않게 재생성)',
  `original_file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 원본 이름',
  `size` INT(11) NULL DEFAULT NULL COMMENT '파일 사이즈',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `product_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `product_id`),
  UNIQUE INDEX `UK_q3dl7t9fh6cx7bjk5pcxfg1oe` (`file_name` ASC),
  INDEX `fk_product_image_product1_idx` (`product_id` ASC),
  CONSTRAINT `fk_product_image_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `nine_tique`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '상품 이름',
  `size` VARCHAR(20) NOT NULL COMMENT '상품 사이즈',
  `price` INT(11) NOT NULL DEFAULT '0' COMMENT '상품 가격',
  `description` TEXT NOT NULL COMMENT '상품 설명',
  `status` VARCHAR(20) NOT NULL COMMENT '상품 상태(SELL, SOLD_OUT)',
  `zzim_count` INT(11) NOT NULL DEFAULT '0' COMMENT '상품을 얼마나 많은 유저가 찜했는지 카운트 ',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `brand_id` INT(11) NOT NULL,
  `shop_id` INT(11) NOT NULL,
  `category_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `brand_id`, `shop_id`, `category_id`),
  INDEX `fk_product_brand1_idx` (`brand_id` ASC),
  INDEX `fk_product_shop1_idx` (`shop_id` ASC),
  INDEX `fk_product_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_product_brand1`
    FOREIGN KEY (`brand_id`)
    REFERENCES `nine_tique`.`brand` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `nine_tique`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `nine_tique`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`seller_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`seller_product` (
  `seller_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`product_id`, `seller_id`),
  INDEX `fk_seller_product_seller1_idx` (`seller_id` ASC),
  CONSTRAINT `fk_seller_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `nine_tique`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_seller_product_seller1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `nine_tique`.`seller` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`seller`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`seller` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `authenti_code` VARCHAR(20) NULL DEFAULT NULL COMMENT '판매자 인증 코드',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `shop_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL COMMENT '인증번호를 입력하면 users 테이블과 연관관계가 생성',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `shop_id`),
  UNIQUE INDEX `UK_emn5cngcpfy3otvpc3oyslnxi` (`authenti_code` ASC),
  INDEX `fk_seller_shop1_idx` (`shop_id` ASC),
  INDEX `fk_seller_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_seller_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `nine_tique`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_seller_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `nine_tique`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '매장의 판매자. 관리자가 생성한다';

-- -----------------------------------------------------
-- Table `nine_tique`.`shop_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`shop_comment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL COMMENT '댓글 내용',
  `shop_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `shop_id`, `user_id`),
  INDEX `fk_shop_comment_shop1_idx` (`shop_id` ASC),
  INDEX `fk_shop_comment_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_shop_comment_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `nine_tique`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `nine_tique`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '매장의 댓글';

-- -----------------------------------------------------
-- Table `nine_tique`.`shop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`shop` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '매장 이름',
  `description` VARCHAR(255) NOT NULL COMMENT '매장 설명',
  `phone_no` VARCHAR(20) NOT NULL COMMENT '매장 전화번호',
  `kakao_open_chat_url` VARCHAR(255) NULL DEFAULT NULL,
  `comment_count` INT(11) NOT NULL DEFAULT 0 COMMENT '매장의 댓글 갯수',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_9kx772t4ejr6mw0fd25pvlw0k` (`phone_no` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '매장';

-- -----------------------------------------------------
-- Table `nine_tique`.`user_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`user_authority` (
  `user_id` INT(11) NOT NULL,
  `authority_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`user_id`, `authority_id`),
  INDEX `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id` ASC),
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89`
    FOREIGN KEY (`authority_id`)
    REFERENCES `nine_tique`.`authority` (`id`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy`
    FOREIGN KEY (`user_id`)
    REFERENCES `nine_tique`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL DEFAULT NULL COMMENT '유저의 이름',
  `email` VARCHAR(30) NULL DEFAULT NULL COMMENT '이메일',
  `oauth_token` VARCHAR(256) NULL DEFAULT NULL COMMENT '유저의 oauth token',
  `oauth_type` VARCHAR(20) NULL DEFAULT NULL COMMENT 'oauth의 type. FB, KAKAO',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '유저';

-- -----------------------------------------------------
-- Table `nine_tique`.`zzim_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`zzim_product` (
  `product_id` INT(11) NOT NULL,
  `zzim_id` INT(11) NOT NULL,
  `active` VARCHAR(1) NULL DEFAULT 'Y',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`product_id`, `zzim_id`),
  INDEX `fk_zzim_product_zzim1_idx` (`zzim_id` ASC),
  CONSTRAINT `fk_zzim_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `nine_tique`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zzim_product_zzim1`
    FOREIGN KEY (`zzim_id`)
    REFERENCES `nine_tique`.`zzim` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`zzim`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`zzim` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_zzim_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_zzim_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `nine_tique`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '유저의 찜(장바구니라고 생각하면 이해하기 쉬움)';

-- -----------------------------------------------------
-- Table `nine_tique`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(255) NOT NULL COMMENT '게시물 이름(제목)',
  `content` TEXT NOT NULL COMMENT '게시물 내용(형식이 복잡할 수 있다)',
  `comment_count` INT(11) NOT NULL DEFAULT '0' COMMENT '게시물의 댓글 갯수',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '게시물';

-- -----------------------------------------------------
-- Table `nine_tique`.`post_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post_comment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL COMMENT '댓글 내용',
  `post_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `post_id`, `user_id`),
  INDEX `fk_post_comment_post1_idx` (`post_id` ASC),
  INDEX `fk_post_comment_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_post_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `nine_tique`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `nine_tique`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '게시물의 댓글';

-- -----------------------------------------------------
-- Table `nine_tique`.`post_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post_product` (
  `product_id` INT(11) NOT NULL,
  `post_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`product_id`, `post_id`),
  INDEX `fk_product_has_post_post1_idx` (`post_id` ASC),
  INDEX `fk_product_has_post_product1_idx` (`product_id` ASC),
  CONSTRAINT `fk_product_has_post_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `nine_tique`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_post_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `nine_tique`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`post_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post_image` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 이름(서버에서 중복되지 않게 재생성)',
  `original_file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 원본 이름',
  `size` INT(11) NULL DEFAULT NULL COMMENT '파일 사이즈',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `post_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `post_id`),
  UNIQUE INDEX `UK_q3dl7t9fh6cx7bjk5pcxfg1oe` (`file_name` ASC),
  INDEX `fk_post_image_post1_idx` (`post_id` ASC),
  CONSTRAINT `fk_post_image_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `nine_tique`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`promotion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`promotion` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '프로모션 이름',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`promotion_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`promotion_image` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 이름(서버에서 중복되지 않게 재생성)',
  `original_file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 원본 이름',
  `size` INT(11) NULL DEFAULT NULL COMMENT '파일 사이즈',
  `active` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `promotion_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`, `promotion_id`),
  UNIQUE INDEX `UK_q3dl7t9fh6cx7bjk5pcxfg1oe` (`file_name` ASC),
  INDEX `fk_promotion_image_promotion1_idx` (`promotion_id` ASC),
  CONSTRAINT `fk_promotion_image_promotion1`
    FOREIGN KEY (`promotion_id`)
    REFERENCES `nine_tique`.`promotion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`promotion_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`promotion_product` (
  `product_id` INT(11) NOT NULL,
  `promotion_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`product_id`, `promotion_id`),
  INDEX `fk_product_has_promotion_promotion1_idx` (`promotion_id` ASC),
  INDEX `fk_product_has_promotion_product1_idx` (`product_id` ASC),
  CONSTRAINT `fk_product_has_promotion_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `nine_tique`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_promotion_promotion1`
    FOREIGN KEY (`promotion_id`)
    REFERENCES `nine_tique`.`promotion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
