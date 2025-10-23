package net.therap.spring.util;

import net.therap.spring.entity.Attachment;
import net.therap.spring.entity.EmployeeType;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Objects.nonNull;
import static net.therap.spring.constant.Constants.REDIRECT;

/**
 * @author shoebakib
 * @since 4/23/24
 */
public class Util {

    public static boolean isSeatAvailable(int availableSeat) {
        return availableSeat > 0;
    }

    public static String generateImageUrl(Attachment image) {
        if (nonNull(image)) {
            return "data:" + image.getType() + ";base64," + Base64.getEncoder().encodeToString(image.getData());
        } else {
            return null;
        }
    }

    public static String redirectTo(String link) {
        return REDIRECT + link;
    }

    public static boolean isZero(int value) {
        return value == 0;
    }

}
