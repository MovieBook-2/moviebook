package com.korea.MOVIEBOOK.movie.movie;

import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findByTitleAndNationsAndReleaseDate(String movieNm, String nations, String releaseDate);
    Movie findByTitle(String title);

    Movie findBymovieCode(String movieCode);

    Page<Movie> findAll(Pageable pageable);


    @Query("SELECT m FROM Movie m " +
            "  where REGEXP_REPLACE(REPLACE(REPLACE( m.actor, '(', '<'), ')' ,'>'), '<([^<>]+)>', '') LIKE %:kw% " +
            "   OR m.title LIKE %:kw% ")
    Page<Movie> findAllByMovieKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("SELECT m FROM Movie m " +
            "  where REGEXP_REPLACE(REPLACE(REPLACE( m.actor, '(', '<'), ')' ,'>'), '<([^<>]+)>', '') LIKE %:kw% " +
            "   OR m.title LIKE %:kw% ")
    List<Movie> findAllByMovieKeywordnotPaging(@Param("kw") String kw);

//    Page<Movie> findByMemberId(Long paymentId, Pageable pageable);
}
