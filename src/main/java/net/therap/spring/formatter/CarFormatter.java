package net.therap.spring.formatter;

import net.therap.spring.entity.Car;
import net.therap.spring.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Component
public class CarFormatter implements Formatter<Car> {

    @Autowired
    private CarService carService;

    @Override
    public Car parse(String text, Locale locale) throws ParseException {
        if (text.isEmpty()) {
            return null;
        }

        return carService.findByIdAndStatus(Integer.parseInt(text));
    }

    @Override
    public String print(Car car, Locale locale) {
        return car.getRegistrationNo();
    }

}
