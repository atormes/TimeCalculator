package com.hotelbeds.timecalculator.services.impl;

import com.hotelbeds.timecalculator.services.TimeCalculatorService;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author tony trabajo
 */
@Service
public class TimeCalculatorServiceImpl implements TimeCalculatorService {

    private String mensajeError = "";

    @Override
    public Long minutosEntreFechas(String dateRFC1, String dateRFC2) {
        if ((StringUtils.isBlank(dateRFC1)) || (StringUtils.isBlank(dateRFC2))) {
            mensajeError = "Las fechas son obligatorias de rellenar";
            return null;
        }

        Long numMinutos = new Long("0");

        // Pasar las cadenas de texto de fechas a LocalDateTime
        LocalDateTime date1 = getParserRF828ToLocalDateTime(dateRFC1);
        LocalDateTime date2 = getParserRF828ToLocalDateTime(dateRFC2);

        // Calclar el tiempo en minutos entre las dos fechas 
        numMinutos = calcularDiferenciaMinutos(date1, date2);

        return numMinutos;
    }

    private LocalDateTime getParserRF828ToLocalDateTime(String fecha) {
        LocalDateTime fechaReturn = null;
        // Convertir dateRFC1 de String a LocalDateTime
        DateTimeFormatter format = DateTimeFormatter.RFC_1123_DATE_TIME;
        try {
            ZonedDateTime zoned = ZonedDateTime.parse(fecha, format);
            fechaReturn = zoned.toLocalDateTime();
        } catch (DateTimeParseException e) {
            mensajeError = "Formato de la fecha: " + fecha + "INCORRECTO. No tiene frmato RFC-282";
            return null;
        }
        return fechaReturn;
    }

    private Long calcularDiferenciaMinutos(LocalDateTime fecha1, LocalDateTime fecha2) {
        if ((fecha1 == null) || (fecha2 == null)) {
            mensajeError = "Las fechas no tiene formato correcto";
            return null;
        }
        return new Long(ChronoUnit.MINUTES.between(fecha1, fecha2));
    }

    public String getMensajeError() {
        return mensajeError;
    }

}
