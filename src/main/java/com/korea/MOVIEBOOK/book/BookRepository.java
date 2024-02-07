package com.korea.MOVIEBOOK.book;

import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    Book findByTitle(String title);
    List<Book> findByAddDate(LocalDate addDate);
    List<Book> findByBestRankAndAddDate(Integer bestRank,LocalDate addDate);
    List<Book> findByIsNewBookAndAddDate(Boolean isNew, LocalDate addDate);
    List<Book> findByRecommended(Boolean recommended);

    Page<Book> findAll(Pageable pageable);


    @Query("SELECT b FROM Book b " +
            "  where REGEXP_REPLACE(REPLACE(REPLACE( b.author, '(', '<'), ')' ,'>'), '<([^<>]+)>', '') LIKE %:kw% " +
            "   OR b.title LIKE %:kw% ")
    Page<Book> findAllByBookKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("SELECT b FROM Book b " +
            "  where REGEXP_REPLACE(REPLACE(REPLACE( b.author, '(', '<'), ')' ,'>'), '<([^<>]+)>', '') LIKE %:kw% " +
            "   OR b.title LIKE %:kw% ")
    List<Book> findAllByBookKeywordnotPaging(@Param("kw") String kw);



}
