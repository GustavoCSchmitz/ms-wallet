package br.com.wallet.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DateUtils {

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
