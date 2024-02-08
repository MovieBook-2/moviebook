package com.korea.MOVIEBOOK.payment;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentCompany;    // 결제사

    private String paidAmount;        // 결제 금액

    private String paymentNo;         // 결제 구분자

    private String payType;           // 결제 타입

    private String phone;             // 회원 전화번호 (payment때 필수값으로 들어가야하기때문에 data에도 넣음)

    private LocalDateTime dateTime;   // 결제 일자

//    private String email;           // 회원 email

    //    private String name;            // 회원 이름(= nickname)
    @Column(columnDefinition = "text")
    private String content;          // 결제한 내용 (13800Coin 사용)

    private String contents;         // 결제한 컨텐츠 카테고리 ( movie,book,webtoon,drama)

    private String contentsID;       // 결제한 컨텐츠 구분 값

    @ManyToOne
    private Member member;

    @OneToOne
    private Movie movie;

    @OneToOne
    private Book book;

    @OneToOne
    private Drama drama;

    @OneToOne
    private Webtoon webtoon;

}
