package kr.co.mash_up.nine_tique.vo;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/*
    리스트(offset, limit, page, total)
    Pageable 인터페이스를 위한 request param
    page - 원하는 페이지 번호(0부터)
    size - 한페이지 개수
    sort - 정렬방식
    ex. 1번째 페이지, 한페이지에 10개, 생성일자 내림차순, 이름 오름차순 정렬
    /product?page=0&size=10&sort=createdAt,desc&sort=name,asc
 */
@Setter
public class ProductListRequestVO extends DataListRequestVO {

    private String mainCategory = "";

    private String subCategory = "";

    public String getMainCategory() {
        return mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    @Override
    public Pageable getPageable() {
        return new PageRequest(getPage(), getPageSize(), new Sort(Sort.Direction.DESC, "createdAt"));
    }
}
