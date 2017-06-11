package kr.co.mash_up_9tique.vo;

import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;

/**
 * 페이지와 사이즈를 지정하여 요청할 경우 사용
 */
@Setter
public class DataListRequestVO extends RequestVO {

    public static final int DEFAULT_PAGE_ROW = 20;

    protected int pageNo;

    protected int pageSize;

    public int getPageNo() {
        return pageNo < 0 ? 0 : pageNo;
    }

    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_ROW : pageSize;
    }

    public Pageable getPageable(){
        return new QPageRequest(getPageNo(), getPageSize());
    }
}
