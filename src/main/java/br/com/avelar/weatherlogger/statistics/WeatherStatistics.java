package br.com.avelar.weatherlogger.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.avelar.weatherlogger.data.WeatherData;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WeatherStatistics {

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private Date firstDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private Date lastDate;

  private double minimumTemperature;

  private double maximumTemperature;

  private double averageTemperature;

  private double minimumHumidity;

  private double maximumHumidity;

  private double averageHumidity;

  private double minimumDewPoint;

  private double maximumDewPoint;

  private double averageDewPoint;

  private List<WeatherData> data;

  public Date getFirstDate() {
    return firstDate;
  }

  public Date getLastDate() {
    return lastDate;
  }

  public double getAverageTemperature() {
    return averageTemperature;
  }

  public double getAverageHumidity() {
    return averageHumidity;
  }

  public double getAverageDewPoint() {
    return averageDewPoint;
  }

  public double getMinimumTemperature() {
    return minimumTemperature;
  }

  public double getMaximumTemperature() {
    return maximumTemperature;
  }

  public double getMinimumHumidity() {
    return minimumHumidity;
  }

  public double getMaximumHumidity() {
    return maximumHumidity;
  }

  public List<WeatherData> getData() {
    return data;
  }

  public double getMinimumDewPoint() {
    return minimumDewPoint;
  }


  public double getMaximumDewPoint() {
    return maximumDewPoint;
  }

  public void calculateStatistics(List<WeatherData> weatherDataList) {
    if (weatherDataList == null) {
      throw new IllegalArgumentException("Cannot calculate statistics. Null list");
    }

    if (weatherDataList.size() > 0) {
      data = weatherDataList;
      List<Date> dates = new ArrayList<Date>();
      List<Double> temperatures = new ArrayList<Double>();
      List<Double> humidities = new ArrayList<Double>();
      List<Double> dewPoints = new ArrayList<Double>();

      double sumTemperature = 0.0;
      double sumHumidity = 0.0;
      double sumDewPoint = 0.0;

      for (WeatherData weatherData : weatherDataList) {
        dates.add(weatherData.getDate());
        temperatures.add(weatherData.getTemperature());
        humidities.add(weatherData.getRelativeHumidity());
        dewPoints.add(weatherData.getDewPoint());
        sumTemperature += weatherData.getTemperature();
        sumHumidity += weatherData.getRelativeHumidity();
        sumDewPoint += weatherData.getDewPoint();
      }

      this.firstDate = Collections.min(dates);
      this.lastDate = Collections.max(dates);

      this.minimumTemperature = Collections.min(temperatures);
      this.maximumTemperature = Collections.max(temperatures);
      this.averageTemperature = sumTemperature / temperatures.size();


      this.minimumHumidity = Collections.min(humidities);
      this.maximumHumidity = Collections.max(humidities);
      this.averageHumidity = sumHumidity / humidities.size();

      this.minimumDewPoint = Collections.min(dewPoints);
      this.maximumDewPoint = Collections.max(dewPoints);
      this.averageDewPoint = sumDewPoint / dewPoints.size();
    }

  }

}
