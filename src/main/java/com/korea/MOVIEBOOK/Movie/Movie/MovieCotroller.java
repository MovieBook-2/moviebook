package com.korea.MOVIEBOOK.Movie.Movie;


import com.korea.MOVIEBOOK.Movie.Date.MovieDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MovieCotroller {

    private final MovieDailyService movieDailyService;
    private final MovieAPI movieAPI;
    private final MovieDateService movieDateService;

    LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    String date = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    @GetMapping("movie")
    public String movie(Model model) {
        List<MovieDaily> movieDailyList = this.movieDailyService.findDailyMovie(date);
        if(movieDailyList.isEmpty()){
            movieAPI.movieDb(date);
            movieDailySize();
        }  else{
            model.addAttribute("movieDaily",movieDailyList);
        }
        return "Movie/movie";
    }

    public List<MovieDaily> movieDailySize(){
        List<MovieDaily> movieDailyList = this.movieDailyService.findDailyMovie(date);
        if(movieDailyList.isEmpty()) {
            movieAPI.movieDb(date);
            List<MovieDaily> movieDailyList2 = this.movieDailyService.findDailyMovie(date);
            if(movieDailyList2.size()<10){
                movieDailySize2();
            }
        }
        return movieDailyList;
    }

    public List<MovieDaily> movieDailySize2(){
        System.out.println("======재시작=====");
        this.movieDailyService.deleteDailyMovie(date);
        movieAPI.movieDb(date);
        return movieDailySize();
    }
}

