package kr.co.mash_up_9tique.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/*
복합키 설정
1. @Embeddable 선언
2. Serializable 인터페이스 구현
3. equals, hashCode 구현
4. default constructor 존재
5. public class여야 한다.
*/
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude
public abstract class AbstractEntityId implements Serializable {

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();
}
