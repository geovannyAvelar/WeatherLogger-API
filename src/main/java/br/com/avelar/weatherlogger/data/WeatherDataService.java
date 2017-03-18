package br.com.avelar.weatherlogger.data;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import org.springframework.stereotype.Service;
import br.com.avelar.weatherlogger.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class WeatherDataService {

  @Autowired
  private WeatherDataDao dao;

  public WeatherData save(WeatherData data) {
    return dao.save(data);
  }

  public WeatherData findOne(Long id) {
    return dao.findOne(id);
  }

  public List<WeatherData> findByDay(Date date) {
    Date finalDate = DateUtils.sumDate(date, Calendar.DAY_OF_MONTH, 1);
    return dao.findByDateBetweenOrderByDateAsc(date, finalDate);
  }

  public List<WeatherData> findByRange(Date from, Date to) {
    Date finalDate = DateUtils.sumDate(to, Calendar.DAY_OF_MONTH, 1);
    return dao.findByDateBetweenOrderByDateAsc(from, finalDate);
  }

  public WeatherData findLast() {
    List<WeatherData> all = dao.findAll();

    if (all.isEmpty())
      return null;

    return all.get(all.size() - 1);
  }

  public List<WeatherData> findAll() {
    return dao.findAll();
  }

  public boolean exists(Long id) {
    return dao.findOne(id) != null;
  }

  public void delete(WeatherData data) {
    dao.delete(data);
  }

}
