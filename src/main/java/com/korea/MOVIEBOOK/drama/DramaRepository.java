package com.korea.MOVIEBOOK.drama;

import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DramaRepository extends JpaRepository<Drama, Long> {

    @Query("SELECT d FROM Drama d WHERE d.id BETWEEN ?1 AND ?2")
    List<Drama> findDramasByIdRange(int startId, int endId);

    Page<Drama> findAll(Pageable pageable);


    @Query("SELECT d FROM Drama d " +
            "  where REGEXP_REPLACE(REPLACE(REPLACE( d.actor, '(', '<'), ')' ,'>'), '<([^<>]+)>', '') LIKE %:kw% " +
            "   OR d.title LIKE %:kw% ")
    Page<Drama> findAllByDramaKeyword(@Param("kw") String kw, Pageable pageable);
    @Query("SELECT d FROM Drama d " +
            "  where REGEXP_REPLACE(REPLACE(REPLACE( d.actor, '(', '<'), ')' ,'>'), '<([^<>]+)>', '') LIKE %:kw% " +
            "   OR d.title LIKE %:kw% ")
    List<Drama> findAllByMovieKeywordnotPaging(@Param("kw") String kw);



}

