package edu.puj.procesobbva.sor.utils;

import com.github.javafaker.Faker;
import edu.puj.procesobbva.sor.domain.Question;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuestionUtils {
    public static String createAnswer(Question question) {
        String answer = "";
        Faker faker = new Faker(new Locale("es"));
        Date tmpDate2;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        switch (question.getType()) {
            case DIRECCION:
                answer = faker.address().fullAddress();
                break;
            case ANHO:
                answer =
                        String.valueOf(
                                faker.number().numberBetween(1910, 2019)
                        );
                break;
            case CUENTA:
                answer = faker.number().digits(10);
                break;
            case EDAD:
                answer =
                        String.valueOf(
                                faker.number().numberBetween(18, 80)
                        );
                break;
            case FECHA:
                if(question.getStatement().contains("cédula")) {
                    Date from = Date.from(
                            LocalDate.of(2000, 01, 01)
                                    .atStartOfDay(ZoneId.systemDefault()).toInstant()
                    );
                    tmpDate2 = faker.date().past(365, TimeUnit.DAYS, from);
                } else {
                    tmpDate2 = faker.date().past(365, TimeUnit.DAYS);
                }

                answer = format.format(tmpDate2);
                break;
            case TRABAJO:
                answer = faker.company().name();
                break;
            case PROFESION:
                answer = faker.company().profession();
                break;
            case TELEFONO:
                answer = faker.phoneNumber().cellPhone();
                break;
        }

        return answer;
    }
}
