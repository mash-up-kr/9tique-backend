package kr.co.mash_up.nine_tique.domain;

/*
Spring Security는 Application의 보안을 강화하는데 사용하는 프레임워크
Authentication, Authorization, Access Controll 처리를 간단한 설정만으로 쉽게 적용 가능
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Spring Security에서 권한 별로 Access Controll하기 때문에 Authority 엔티티 필요
 */
@Entity
@Table(name = "authority")
@Data
@EqualsAndHashCode(callSuper = false)
public class Authority extends AbstractEntity<Long> implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String authority;
}
