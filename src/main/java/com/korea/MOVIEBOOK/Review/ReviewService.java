package com.korea.MOVIEBOOK.Review;

import com.korea.MOVIEBOOK.Movie.Movie.Movie;
import com.korea.MOVIEBOOK.Movie.Movie.MovieService;
import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookRepository;
import com.korea.MOVIEBOOK.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final BookRepository bookRepository;

    public void saveMovieReview(String movieCD, String comment, Double rating){
        Movie movie =  this.movieService.findMovieByCD(movieCD);
        Review review = new Review();
        review.setMovie(movie);
        review.setComment(comment);
        review.setCategory("movie");
        review.setRating(rating);
        this.reviewRepository.save(review);
    }

    public void saveBookReview(String isbn, String comment, Double rating){
        Book book = bookRepository.findByIsbn(isbn);
        Review review = new Review();
        review.setBook(book);
        review.setComment(comment);
        review.setCategory("book");
        review.setRating(rating);
        this.reviewRepository.save(review);
    }

    public List<Review> findReviews(Long id){
       return this.reviewRepository.findReviewsByMovieId(id);
    }
//    public List<com.korea.MOVIEBOOK.dramaReview.Review> getReviewsByDramaId(Long dramaId) {
//        return reviewRepository.findByDramaId(dramaId);
//    }
//
//    public com.korea.MOVIEBOOK.dramaReview.Review findReviewById(Long id) {
//        return reviewRepository.findById(id).orElse(null);
//    }
//
//    public void addReview(com.korea.MOVIEBOOK.dramaReview.Review review) {
//        reviewRepository.save(review);
//    }
//
//    public void saveReview(Review review) {
//        // Review 저장 로직
//        reviewRepository.save(review);
}
