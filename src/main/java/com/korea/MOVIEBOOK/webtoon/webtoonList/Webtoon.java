package com.korea.MOVIEBOOK.webtoon.webtoonList;


import com.korea.MOVIEBOOK.payment.Payment;
import com.korea.MOVIEBOOK.heart.Heart;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
@Entity
public class Webtoon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private Integer fanCount;

    private Long webtoonId;

    private String title;

    private String author;

    private String imageUrl;

    private String searchKeyword;

    private String detailUrl;


    @Column(columnDefinition = "text")
    private String plot;     //  요약

    @OneToMany(mappedBy = "webtoonList")
    private List<WebtoonDayList> webtoonDayLists;

    @OneToMany(mappedBy = "webtoon",  cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToOne
    private Payment payment;

    @OneToMany(mappedBy = "webtoon", cascade = CascadeType.ALL)
    private List<Heart> heartList;
}
