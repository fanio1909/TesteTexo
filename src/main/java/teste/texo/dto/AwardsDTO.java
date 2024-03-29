package teste.texo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AwardsDTO {
    private Integer year;
    private String title;
    private String studios;
    private String producers;
    private String winner;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public List<String> getProducersAsList() {
        if (producers == null) return null;

        return Arrays.stream(producers.split(", | and ")).map(String::trim).collect(Collectors.toList());
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
