package kr.co.mash_up.nine_tique.util;

import java.util.UUID;

/**
 * 고유값 생성 유틸
 */
public class CodeGeneratorUtil {

    private static final char sCodeGeneratePool[] = new char[]{
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '!', '@', '#', '$', '%', '&'
    };

    /**
     * 인증코드 생성
     * unix timestamp(Hex, 8) + pool에서 꺼낸 랜덤문자(5) -> 13자리
     * ex. 589d4c2f-qa&Hu
     *
     * @return 생성된 인증코드
     */
    public static String generateAuthentiCode() {
        StringBuilder sbAuthentiCode = new StringBuilder();

        Long createdAtUnixTimestamp = System.currentTimeMillis() / 1000;
        sbAuthentiCode.append(Long.toHexString(createdAtUnixTimestamp));
        sbAuthentiCode.append("-");

        for (int i = 0; i < 5; i++) {
            int selectIndex = (int) (Math.random() * sCodeGeneratePool.length);
            sbAuthentiCode.append(sCodeGeneratePool[selectIndex]);
        }
        return sbAuthentiCode.toString();
    }

    /**
     * 파일 고유이름 생성
     * uuid + unix timestamp
     * uuid - 랜덤한 32개의 '숫자' & '영어소문자' 와 구분자 '-' 로 된 36자리의 문자열 출력
     *
     * @param fileName
     * @return
     */
    public static String generateFileName(String fileName) {
        Long createdAtUnixTimestamp = System.currentTimeMillis() / 1000;
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + "_" + createdAtUnixTimestamp + extension;
    }
}
