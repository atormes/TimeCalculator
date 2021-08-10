package com.hotelbeds.timecalculator.test;

import com.hotelbeds.timecalculator.Configurator.SpringConfiguration;
import com.hotelbeds.timecalculator.services.impl.TimeCalculatorServiceImpl;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * @author tony trabajo
 */
public class TestTimeCalculatorService {

    private static final Logger log = Logger.getLogger(TestTimeCalculatorService.class.getName());
    private static TimeCalculatorServiceImpl timeCalculatorServiceImp;

    @BeforeClass
    public static void init_TestTimeCalculatorService() {
        // Recoger el bean de HackerDetectorImpl desde el contexto de configuracion de la aplicacion SPRING
        AnnotationConfigApplicationContext contexto = new AnnotationConfigApplicationContext();
        contexto.register(SpringConfiguration.class);
        contexto.refresh();
        timeCalculatorServiceImp = contexto.getBean(TimeCalculatorServiceImpl.class);

        // Derivar log a un fichero en carpeta ..\logs
        try {
            FileHandler fileHandler = new FileHandler("logs\\TimeCalculator.log");
            fileHandler.setLevel(Level.INFO);
            log.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException ex) {
            log.warning("...WARNING: Los logs no se estan generando");
        }
    }
    
    @Test
    public void testValidacionFecha() {
        String dateRFC1, dateRFC2;
        Long minutos = null;
        
        // Fecha 1 en vacio
        dateRFC1 = ""; 
        dateRFC2 = "Fri, 6 Aug 2021 10:56:08 +0200";
        minutos = timeCalculatorServiceImp.minutosEntreFechas(dateRFC1, dateRFC2);
        assertNull("Ocurrio un error en formato de fechas: " + timeCalculatorServiceImp.getMensajeError(), minutos);
       
        // Fecha 1 en vacio
        dateRFC1 = "Fri, 6 Aug 2021 10:56:08 +0200"; 
        dateRFC2 = "";
        minutos = timeCalculatorServiceImp.minutosEntreFechas(dateRFC1, dateRFC2);
        assertNull("Ocurrio un error en formato de fechas: " + timeCalculatorServiceImp.getMensajeError(), minutos);        
    }

    @Test
    public void testMinutosEntreMismoDia() {
        String dateRFC1, dateRFC2;
        dateRFC1 = "Fri, 6 Aug 2021 10:23:00 +0200"; 
        dateRFC2 = "Fri, 6 Aug 2021 10:56:08 +0200";

        Long minutos = timeCalculatorServiceImp.minutosEntreFechas(dateRFC1, dateRFC2);
        assertNotNull("Ocurrio un error en el calculo de diferencia en minutos entre fechas: " + timeCalculatorServiceImp.getMensajeError(), minutos);
        System.out.println("La diferencia en minutos entre las fechas: " + dateRFC1 + " y " + dateRFC2 + " es de: " + minutos);
    }

    @Test
    public void testMinutosEntreDistintoDia() {
        String dateRFC1, dateRFC2;
        dateRFC1 = "Fri, 6 Aug 2021 10:23:00 +0200"; 
        dateRFC2 = "Sat, 7 Aug 2021 10:56:08 +0200";

        Long minutos = timeCalculatorServiceImp.minutosEntreFechas(dateRFC1, dateRFC2);
        assertNotNull("Ocurrio un error en el calculo de diferencia en minutos entre fechas: " + timeCalculatorServiceImp.getMensajeError(), minutos);
        System.out.println("La diferencia en minutos entre las fechas: " + dateRFC1 + " y " + dateRFC2 + " es de: " + minutos);
    }
    
}
