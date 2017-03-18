package br.com.avelar.weatherlogger.data;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WeatherDataValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return WeatherData.class.equals(clazz);
  }

  @Override
  public void validate(Object object, Errors errors) {

    WeatherData weatherData = (WeatherData) object;

    if (weatherData.getTemperatureUnit().equals(TemperatureUnit.FAHRENHEIT)) {

      if (weatherData.getTemperature() > 149 || weatherData.getTemperature() < -4) {
        errors.rejectValue("temperature", "temp_out_of_range");
      }

      if (weatherData.getDewPoint() > 149 || weatherData.getDewPoint() < -85) {
        errors.rejectValue("dewPoint", "dew_point_out_of_range");
      }

    }

    if (weatherData.getTemperatureUnit().equals(TemperatureUnit.CELSIUS)) {

      if (weatherData.getTemperature() > 65 || weatherData.getTemperature() < -20) {
        errors.rejectValue("temperature", "temp_out_of_range");
      }

      if (weatherData.getDewPoint() > 65 || weatherData.getDewPoint() < -65) {
        errors.rejectValue("dewPoint", "dew_point_out_of_range");
      }

    }

  }

}
