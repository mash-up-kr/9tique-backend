package kr.co.mash_up.nine_tique.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 도메인 클래스 추상화
 */
@Getter
@MappedSuperclass  //Parent Entity Class 지원
@JsonInclude(value = JsonInclude.Include.ALWAYS)   //필드값 존재 여부에 따라 Json에 포함할지 여부
//어떤 필드를 Json으로 변환할지 옵션 설정
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility =
        JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractEntity<K extends Serializable> implements Serializable {

    /**
     * 생성일자
     */
    @Column(name = "created_at", insertable = true, updatable = false)  // insert 구문 포함, update 못하게 설정
    protected LocalDateTime createdAt;

    /**
     * 수정일자
     */
    @Column(name = "updated_at", insertable = true, updatable = true)  // insert, update 구문 포함
    protected LocalDateTime updatedAt;

    @PrePersist  // 객체 영속화 전에 호출
    protected void onPersist() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate  // 업데이트 전에 호출
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public abstract String toString();

    public abstract K getId();

    public long getCreatedTimestamp(){
        if(this.createdAt == null){
            return 0;
        }
        return Timestamp.valueOf(this.createdAt).getTime();
    }

    public long getUpdatedTimestamp(){
        if(this.updatedAt == null){
            return 0;
        }
        return Timestamp.valueOf(this.updatedAt).getTime();
    }
}
