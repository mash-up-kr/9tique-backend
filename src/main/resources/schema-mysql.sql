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
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `authority` VARCHAR(30) NOT NULL COMMENT '권한 이름',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '권한';

-- -----------------------------------------------------
-- Table `nine_tique`.`brands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`brands` (
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
-- Table `nine_tique`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`categories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `main` VARCHAR(255) NOT NULL,
  `sub` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`product_images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`product_images` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 이름(서버에서 겹치지 않게 재생성)',
  `original_file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 원본 이름',
  `size` VARCHAR(45) NULL DEFAULT NULL COMMENT '이미지 파일 사이즈',
  `products_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `products_id`),
  UNIQUE INDEX `UK_q3dl7t9fh6cx7bjk5pcxfg1oe` (`file_name` ASC),
  INDEX `fk_product_images_products1_idx` (`products_id` ASC),
  CONSTRAINT `fk_product_images_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `nine_tique`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`products` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `description` TEXT NOT NULL COMMENT '상품 설명',
  `name` VARCHAR(50) NOT NULL COMMENT '상품 이름',
  `price` INT(11) NOT NULL DEFAULT '0' COMMENT '상품 가격',
  `size` VARCHAR(20) NOT NULL COMMENT '상품 사이즈',
  `status` VARCHAR(20) NOT NULL COMMENT '상품 상태(SELL, SOLD_OUT)',
  `brands_id` INT(11) NOT NULL,
  `shops_id` INT(11) NOT NULL,
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `zzim_count` INT(11) NOT NULL DEFAULT '0' COMMENT '상품을 얼마나 많은 유저가 찜했는지 카운트 ',
  `categories_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `brands_id`, `shops_id`, `categories_id`),
  INDEX `fk_products_brands1_idx` (`brands_id` ASC),
  INDEX `fk_products_shops1_idx` (`shops_id` ASC),
  INDEX `fk_products_categories1_idx` (`categories_id` ASC),
  CONSTRAINT `fk_products_brands1`
    FOREIGN KEY (`brands_id`)
    REFERENCES `nine_tique`.`brands` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_shops1`
    FOREIGN KEY (`shops_id`)
    REFERENCES `nine_tique`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_categories1`
    FOREIGN KEY (`categories_id`)
    REFERENCES `nine_tique`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`seller_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`seller_products` (
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `products_id` INT(11) NOT NULL,
  `sellers_id` INT(11) NOT NULL,
  PRIMARY KEY (`products_id`, `sellers_id`),
  INDEX `fk_seller_products_sellers1_idx` (`sellers_id` ASC),
  CONSTRAINT `fk_seller_products_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `nine_tique`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_seller_products_sellers1`
    FOREIGN KEY (`sellers_id`)
    REFERENCES `nine_tique`.`sellers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`sellers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`sellers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `authenti_code` VARCHAR(255) NULL DEFAULT NULL COMMENT '판매자 인증 코드',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `shops_id` INT(11) NOT NULL,
  `users_id` INT(11) NOT NULL COMMENT '인증번호를 입력하면 users 테이블과 연관관계가 생성',
  PRIMARY KEY (`id`, `shops_id`),
  UNIQUE INDEX `UK_emn5cngcpfy3otvpc3oyslnxi` (`authenti_code` ASC),
  INDEX `fk_sellers_shops1_idx` (`shops_id` ASC),
  INDEX `fk_sellers_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_sellers_shops1`
    FOREIGN KEY (`shops_id`)
    REFERENCES `nine_tique`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sellers_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `nine_tique`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '매장의 판매자. 관리자가 생성한다';

-- -----------------------------------------------------
-- Table `nine_tique`.`shop_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`shop_comments` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL COMMENT '댓글 내용',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `shops_id` INT(11) NOT NULL,
  `users_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `shops_id`, `users_id`),
  INDEX `fk_shop_comments_shops1_idx` (`shops_id` ASC),
  INDEX `fk_shop_comments_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_shop_comments_shops1`
    FOREIGN KEY (`shops_id`)
    REFERENCES `nine_tique`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_comments_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `nine_tique`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '매장의 댓글';

-- -----------------------------------------------------
-- Table `nine_tique`.`shops`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`shops` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `info` VARCHAR(255) NOT NULL COMMENT '매장 정보',
  `kakao_open_chat_url` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(100) NOT NULL COMMENT '매장 이름',
  `phone` VARCHAR(20) NOT NULL COMMENT '매장 전화번호',
  `comment_count` INT(11) NOT NULL DEFAULT 0 COMMENT '매장의 댓글 갯수',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_9kx772t4ejr6mw0fd25pvlw0k` (`phone` ASC))
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
    REFERENCES `nine_tique`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `email` VARCHAR(30) NULL DEFAULT NULL COMMENT '이메일',
  `name` VARCHAR(20) NULL DEFAULT NULL COMMENT '유저의 이름',
  `oauth_token` VARCHAR(255) NULL DEFAULT NULL COMMENT '유저의 oauth token',
  `oauth_type` VARCHAR(255) NULL DEFAULT NULL COMMENT 'oauth의 type. FB, KAKAO',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '유저';

-- -----------------------------------------------------
-- Table `nine_tique`.`zzim_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`zzim_products` (
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NULL DEFAULT 'N',
  `products_id` INT(11) NOT NULL,
  `zzims_id` INT(11) NOT NULL,
  PRIMARY KEY (`products_id`, `zzims_id`),
  INDEX `fk_zzim_products_zzims1_idx` (`zzims_id` ASC),
  CONSTRAINT `fk_zzim_products_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `nine_tique`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zzim_products_zzims1`
    FOREIGN KEY (`zzims_id`)
    REFERENCES `nine_tique`.`zzims` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`zzims`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`zzims` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `users_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `users_id`),
  INDEX `fk_zzims_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_zzims_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `nine_tique`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '유저의 찜(장바구니라고 생각하면 이해하기 쉬움)';

-- -----------------------------------------------------
-- Table `nine_tique`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`posts` (
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
-- Table `nine_tique`.`post_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post_comments` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `posts_id` INT(11) NOT NULL,
  `users_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `posts_id`, `users_id`),
  INDEX `fk_post_comments_posts1_idx` (`posts_id` ASC),
  INDEX `fk_post_comments_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_post_comments_posts1`
    FOREIGN KEY (`posts_id`)
    REFERENCES `nine_tique`.`posts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_comments_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `nine_tique`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '게시물의 댓글';

-- -----------------------------------------------------
-- Table `nine_tique`.`post_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post_products` (
  `products_id` INT(11) NOT NULL,
  `posts_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`products_id`, `posts_id`),
  INDEX `fk_products_has_posts_posts1_idx` (`posts_id` ASC),
  INDEX `fk_products_has_posts_products1_idx` (`products_id` ASC),
  CONSTRAINT `fk_products_has_posts_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `nine_tique`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_has_posts_posts1`
    FOREIGN KEY (`posts_id`)
    REFERENCES `nine_tique`.`posts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`post_images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`post_images` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 이름(서버에서 겹치지 않게 재생성)',
  `original_file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 원본 이름',
  `size` VARCHAR(45) NULL DEFAULT NULL COMMENT '이미지 파일 사이즈',
  `posts_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `posts_id`),
  UNIQUE INDEX `UK_q3dl7t9fh6cx7bjk5pcxfg1oe` (`file_name` ASC),
  INDEX `fk_post_images_posts1_idx` (`posts_id` ASC),
  CONSTRAINT `fk_post_images_posts1`
    FOREIGN KEY (`posts_id`)
    REFERENCES `nine_tique`.`posts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`promotions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`promotions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '프로모션 이름',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`promotion_images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`promotion_images` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  `enabled` VARCHAR(1) NOT NULL DEFAULT 'N',
  `file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 이름(서버에서 겹치지 않게 재생성)',
  `original_file_name` VARCHAR(255) NOT NULL COMMENT '업로드한 이미지 파일 원본 이름',
  `size` VARCHAR(45) NULL DEFAULT NULL COMMENT '이미지 파일 사이즈',
  `promotions_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `promotions_id`),
  UNIQUE INDEX `UK_q3dl7t9fh6cx7bjk5pcxfg1oe` (`file_name` ASC),
  INDEX `fk_promotion_images_promotions1_idx` (`promotions_id` ASC),
  CONSTRAINT `fk_promotion_images_promotions1`
    FOREIGN KEY (`promotions_id`)
    REFERENCES `nine_tique`.`promotions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `nine_tique`.`promotion_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nine_tique`.`promotion_products` (
  `products_id` INT(11) NOT NULL,
  `promotions_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 날짜',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 날짜',
  PRIMARY KEY (`products_id`, `promotions_id`),
  INDEX `fk_products_has_promotions_promotions1_idx` (`promotions_id` ASC),
  INDEX `fk_products_has_promotions_products1_idx` (`products_id` ASC),
  CONSTRAINT `fk_products_has_promotions_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `nine_tique`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_has_promotions_promotions1`
    FOREIGN KEY (`promotions_id`)
    REFERENCES `nine_tique`.`promotions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
