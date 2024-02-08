package com.korea.MOVIEBOOK;

import com.korea.MOVIEBOOK.book.Book;
import com.korea.MOVIEBOOK.book.BookDTO;
import com.korea.MOVIEBOOK.book.BookService;
import com.korea.MOVIEBOOK.drama.Drama;
import com.korea.MOVIEBOOK.drama.DramaService;
import com.korea.MOVIEBOOK.movie.MovieDTO;
import com.korea.MOVIEBOOK.movie.daily.MovieDailyAPI;
import com.korea.MOVIEBOOK.movie.movie.Movie;
import com.korea.MOVIEBOOK.movie.movie.MovieService;
import com.korea.MOVIEBOOK.webtoon.days.Day;
import com.korea.MOVIEBOOK.webtoon.days.DayService;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayList;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayListService;
import com.korea.MOVIEBOOK.webtoon.webtoonList.Webtoon;
import com.korea.MOVIEBOOK.webtoon.webtoonList.WebtoonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MovieDailyAPI movieDailyAPI;
    private final BookService bookService;
    private final DramaService dramaService;
    private final MovieService movieService;
    private final WebtoonService webtoonService;
    private final ContentsService contentsService;
    private final WebtoonDayListService webtoonDayListService;
    private final DayService dayService;
    LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    String date = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    @GetMapping(value = "/test", produces = "application/json")
    @ResponseBody
    public Movie aaaa(Model model, @RequestParam("input") Long movieid) {

        Movie movie = this.movieService.getMovieUseId(movieid);

        return movie;
    }

    @GetMapping("/search")
    public String searchList(Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "kw", defaultValue = "") String kw) {


        Page<Movie> pagingMovie = movieService.getMovieList(page, kw);
        Page<Drama> pagingDrama = dramaService.getDramaList(page, kw);
        Page<Book> pagingBook = bookService.getBookList(page, kw);
        Page<Webtoon> pagingWebtoon = webtoonService.getWebtoonList(page, kw);

        model.addAttribute("pagingWebtoon", pagingWebtoon);
        model.addAttribute("pagingmovie", pagingMovie);
        model.addAttribute("pagingDrama", pagingDrama);
        model.addAttribute("pagingBook", pagingBook);
        model.addAttribute("kw", kw);

        return "search/search_list";
    }

    @GetMapping("/search/webtoon")
    public String searchWebtoonList(Model model,
                                  @RequestParam(value = "kw", defaultValue = "") String kw){

        List<Webtoon> webtoonList = this.webtoonService.getWebtoonListNotPaging(kw);

        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("kw", kw);
        return "search/webtoon_search_list";
    }

    @GetMapping("/search/movie")
    public String searchMovieList(Model model,
                                    @RequestParam(value = "kw", defaultValue = "") String kw){

        List<Movie> movieList = this.movieService.getMovieListNotPaging(kw);


        model.addAttribute("movieList", movieList);
        model.addAttribute("kw", kw);
        return "search/movie_search_list";
    }

    @GetMapping("/search/book")
    public String searchBookList(Model model,
                                 @RequestParam(value = "kw", defaultValue = "") String kw) {

        List<Book> bookList = this.bookService.getBookListNotPaging(kw);

        model.addAttribute("bookList", bookList);
        model.addAttribute("kw", kw);
        return "search/book_search_list";
    }

    @GetMapping("/search/drama")
    public String searchDramaList(Model model,
                                  @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Drama> dramaList = this.dramaService.getDramaListNotPaging(kw);

        model.addAttribute("dramaList", dramaList);
        model.addAttribute("kw", kw);
        return "search/drama_search_list";
    }

    @GetMapping("/")
    public String mainPage(Model model, Day day) {

        List<ContentsDTO> contentsDTOList = new ArrayList<>();
        List<Book> bestSellerList = bookService.getBestSellerList();
        List<Day> days = this.dayService.findAll();
        //  오늘 요일가져오는 변수
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        String dayOfWeekString = dayOfWeek.name();

        List<MovieDTO> boxofficeList = movieService.listOfMovieDailyDTO();

        List<Webtoon> webtoonList = new ArrayList<>();

        if (dayOfWeekString.equals("MONDAY")) {
            Day day1 = this.dayService.findByDay("mon");
            webtoonList = getWebtoonList(day1);
        } else if (dayOfWeekString.equals("TUESDAY")) {
            Day day1 = this.dayService.findByDay("tue");
            webtoonList = getWebtoonList(day1);
        } else if (dayOfWeekString.equals("WEDNESDAY")) {
            Day day1 = this.dayService.findByDay("wed");
            webtoonList = getWebtoonList(day1);
        } else if (dayOfWeekString.equals("THURSDAY")) {
            Day day1 = this.dayService.findByDay("thu");
            webtoonList = getWebtoonList(day1);
        } else if (dayOfWeekString.equals("FRIDAY")) {
            Day day1 = this.dayService.findByDay("fri");
            webtoonList = getWebtoonList(day1);
        } else if (dayOfWeekString.equals("SATURDAY")) {
            Day day1 = this.dayService.findByDay("sat");
            webtoonList = getWebtoonList(day1);
        } else if (dayOfWeekString.equals("SUNDAY")) {
            Day day1 = this.dayService.findByDay("sun");
            webtoonList = getWebtoonList(day1);
        }

        List<Book> bookList = bestSellerList.subList(0, Math.min(5, bestSellerList.size()));
        List<Webtoon> webtoonList1 = webtoonList.subList(0, Math.min(5, bestSellerList.size()));
        List<MovieDTO> movieList = boxofficeList.subList(0, Math.min(5, boxofficeList.size()));
        model.addAttribute("bookList", bookList);
        model.addAttribute("webtoonList", webtoonList1);
        model.addAttribute("movieList", movieList);
        return "mainPage";
    }

    public void movieDailySize(List<Map> failedMovieList) {
        if (failedMovieList != null && !failedMovieList.isEmpty()) {
            List<Map> failedMoiveList = movieDailyAPI.saveDailyMovieDataByAPI(failedMovieList);
            movieDailySize(failedMoiveList);
        }
    }

    public List<Webtoon> getWebtoonList(Day day) {
        List<Webtoon> findWebtonnByDay = new ArrayList<>();
        List<WebtoonDayList> webtoonDayList = this.webtoonDayListService.findBywebtoonDay(day);
        for (WebtoonDayList webtoonDayList1 : webtoonDayList) {
            Webtoon webtoon = webtoonDayList1.getWebtoonList();
            findWebtonnByDay.add(webtoon);
        }
        return findWebtonnByDay;
    }
}

