package net.transpo.spring.validator;

import net.transpo.spring.command.CarCmd;
import net.transpo.spring.entity.Car;
import net.transpo.spring.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.NoResultException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shoebakib
 * @since 4/4/24
 */
@Component
public class CarValidator implements Validator {

    @Autowired
    private CarService carService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CarCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CarCmd carCmd = (CarCmd) target;

        if (carCmd.getCar().isNew()) {
            carIsNew(carCmd.getCar(), errors);
        } else {
            carIsNotNew(carCmd.getCar(), errors);
        }
    }

    private void carIsNew(Car car, Errors errors) {
        this.carSeatMissMatch(car, errors);

        this.carRegistrationNoExist(car, errors);

        this.carNameExist(car, errors);
    }

    private void carIsNotNew(Car car, Errors errors) {

        Car existingCar = this.findCarByIdAndStatus(car.getId());

        this.carSeatMissMatch(car, existingCar, errors);

        this.carRegistrationNoExist(car, existingCar, errors);

        this.carNameExist(car, existingCar, errors);
    }

    private void carSeatMissMatch(Car car, Errors errors) {
        if (nonNull(car.getAvailableSeat()) && nonNull(car.getTotalSeat())) {
            if (!car.getAvailableSeat().equals(car.getTotalSeat())) {

                errors.rejectValue("car.availableSeat", "seat.MissMatch");
            }
        }
    }

    private void carRegistrationNoExist(Car car, Errors errors) {
        if (nonNull(car.getRegistrationNo())) {
            if (carService.isExistByRegistrationNo(car.getRegistrationNo())) {

                errors.rejectValue("car.registrationNo", "CarRegistrationNoExist");
            }
        }
    }

    private void carNameExist(Car car, Errors errors) {
        if (nonNull(car.getName())) {
            if (carService.isExistByName(car.getName())) {

                errors.rejectValue("car.name", "CarNameExist");
            }
        }
    }

    private void carSeatMissMatch(Car car, Car existingCar, Errors errors) {
        if (nonNull(car.getAvailableSeat()) && nonNull(car.getTotalSeat())) {

            if (!car.getAvailableSeat().equals(existingCar.getAvailableSeat())) {
                errors.rejectValue("car.availableSeat", "seat.update.Impossible");
            }

            if (!car.getTotalSeat().equals(existingCar.getTotalSeat())) {
                errors.rejectValue("car.totalSeat", "seat.update.Impossible");
            }
        }
    }

    private void carRegistrationNoExist(Car car, Car existingCar, Errors errors) {
        if (nonNull(car.getRegistrationNo())) {
            if (carService.isExistByRegistrationNo(car.getRegistrationNo())) {
                if (!car.getRegistrationNo().equals(existingCar.getRegistrationNo())) {

                    errors.rejectValue("car.registrationNo", "CarRegistrationNoExist");
                }
            }
        }
    }

    private void carNameExist(Car car, Car existingCar, Errors errors) {
        if (nonNull(car.getName())) {
            if (carService.isExistByName(car.getName())) {
                if (!car.getName().equals(existingCar.getName())) {

                    errors.rejectValue("car.name", "CarNameExist");
                }
            }
        }
    }

    private Car findCarByIdAndStatus(int carId) {
        Car car = carService.findByIdAndStatus(carId);

        if (isNull(car)) {
            throw new NoResultException(String.format("Car not fount with id %d", carId));
        }

        return car;
    }

}
