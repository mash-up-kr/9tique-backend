package kr.co.mash_up_9tique.util;


public final class Constant {

    /**
     * Result Code 모음
     */
    public final class ResultCodes {

        public static final int OK = 200;  // 정상처리
        public static final int CREATED = 201;  // 정상처리, 리소스 생성
        public static final int NO_CONTENT = 204;  // 정상처리, body에 아무값 없음
        public static final int BAD_REQUEST = 400;  // 잘못된 요청
        public static final int UN_AUTHORIZED = 401;  // 인증 필요
        public static final int FORBIDDEN = 403;  // 권한 없음
        public static final int NOT_FOUND = 404;  // 찾을 수 없음
    }

    /**
     * api endpoint 모음
     */
    public final class RestEndpoint {

        public static final String SUFFIX = "/**";
        public static final String API_SHOP = "/api/shops";
        public static final String API_CATEGORY = "/api/categories";
        public static final String API_PRODUCT = "/api/products";
        public static final String API_PRODUCT_IMAGE = "/api/product_images";
        public static final String API_ZZIM = "/api/zzims";
        public static final String API_USER = "/api/users";
        public static final String API_SELLER = "/api/sellers";


        public static final String STORAGE = "/storage";
        public static final String H2_CONSOLE = "/h2-console";
    }
}
