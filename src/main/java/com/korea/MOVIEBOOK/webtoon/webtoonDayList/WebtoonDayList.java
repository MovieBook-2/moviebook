package com.korea.MOVIEBOOK.webtoon.webtoonDayList;


import com.korea.MOVIEBOOK.heart.Heart;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.webtoon.days.Day;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class WebtoonDayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Day webtoonDay;

    @ManyToOne
    private Webtoon webtoonList;
}
