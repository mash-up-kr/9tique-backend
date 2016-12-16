package kr.co.mash_up.nine_tique.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 응답코드/메시지 + 여러개의 데이터(엔티티, VO)가 있는 경우
 */
@Getter
@Setter
@ToString
public class DataListResponseVO<T> extends ResponseVO {

    private List<T> list;

    @JsonProperty(value = "page_size")
    private int pageSize;  // page에 들어있는 item 수

    @JsonProperty(value = "page_no")
    private int pageNo;  // 페이지 번호

    private long total;  // item total count

    @JsonProperty(value = "page_total")
    private int pageTotal;  // pageNo total count

    public DataListResponseVO(String resultCode, String message){
        super(resultCode, message);
    }

    public DataListResponseVO(List<T> list){
        this(ResultCodes.OK, "success");
        this.list = list;
    }

    public DataListResponseVO(Page<T> page){
        this(ResultCodes.OK, "success");
        this.list = page.getContent();  // 검색된 데이터
        this.pageSize = page.getSize();
        this.pageNo = page.getNumber();
        this.total = page.getTotalElements();  // 검색된 전체 data 수
        this.pageTotal = page.getTotalPages();  // 전체 페이지 수
    }
}
