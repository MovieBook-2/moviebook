package com.korea.MOVIEBOOK.Movie.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieDateService {
    private final MovieDateRepository movieDateRepository;
    int i = 0;
    public boolean add(String date){
        MovieDate movieDate = this.movieDateRepository.findBydailyDate(date);

        if(movieDate == null){
            MovieDate movieDate2 = new MovieDate();
            movieDate2.setDailyDate(date);
            this.movieDateRepository.save(movieDate2);
            return true;
        } else if ( movieDate != null || i < 11){
            i++;
            return true;
        }
        return false;
    }

    public void delete(String date){
        MovieDate movieDate = this.movieDateRepository.findBydailyDate(date);
        this.movieDateRepository.delete(movieDate);
    }
}
