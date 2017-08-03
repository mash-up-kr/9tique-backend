package kr.co.mash_up.nine_tique.web.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.mash_up.nine_tique.domain.Product;
import lombok.Setter;
import lombok.ToString;

/**
 * 페이지와 사이즈를 지정하여 요청할 경우 사용
 */
/*
    리스트(offset, limit, pageNo, total)
    Pageable 인터페이스를 위한 request param
    pageNo - 원하는 페이지 번호(0부터)
    size - 한페이지 개수
    sort - 정렬방식
    ex. 1번째 페이지, 한페이지에 10개, 생성일자 내림차순, 이름 오름차순 정렬
    /product?pageNo=0&size=10&sort=createdAt,desc&sort=name,ascs
    /product?pageNo=0 & size=10 & sort=-createdAt & sort=name
 */
@Setter
@ToString
public class DataListRequestVO extends RequestVO {

    public static final int DEFAULT_PAGE_ROW = 20;

    protected int pageNo;

    protected int pageSize;

    protected Product.SortType sort;

    public int getPageNo() {
        return pageNo < 0 ? 0 : pageNo;
    }

    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_ROW : pageSize;
    }

    public Sort getSort() {
        return sort == null ? Product.SortType.CREATED.getSort() : sort.getSort();
    }

    public Pageable getPageable() {
        return new PageRequest(getPageNo(), getPageSize(), getSort());
    }
}
