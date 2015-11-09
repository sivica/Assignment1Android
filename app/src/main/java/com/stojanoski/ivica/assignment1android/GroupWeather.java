package com.stojanoski.ivica.assignment1android;

import java.util.List;

/**
 * Created by sivic on 11/9/2015.
 */
public class GroupWeather {
    private int cnt;
    private List<CityWeather> list;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<CityWeather> getList() {
        return list;
    }

    public void setList(List<CityWeather> list) {
        this.list = list;
    }

    public class CityWeather {
        private String name;
        private Main main;
        private List<Weather> weather;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public String getTemp() {
            return getMain().getTemp();
        }

        public String getHumidity() {
            return getMain().getHumidity();
        }

        public String getDescription() {
            return getWeather().get(0).getDescription();
        }
    }

    public class Main {
        private String temp;
        private String humidity;

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }
    }

    public class Weather {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
