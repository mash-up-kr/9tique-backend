package kr.co.mash_up.nine_tique.vo;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 페이지와 사이즈를 지정하여 요청할 경우 사용
 */
@Setter
public class DataListRequestVO extends RequestVO {

    public static final int DEFAULT_PAGE_ROW = 20;

    protected int page;

    protected int pageSize;

    public int getPage() {
        return page < 0 ? 0 : page;
    }

    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_ROW : pageSize;
    }

    public Pageable getPageable(){
        return new QPageRequest(getPage(), getPageSize());
    }


}
