package com.example.to_do_list.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentTimesTamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMonthForNumber(String monthNumber) {
        switch (monthNumber) {
            case "01":
                return "Jan";

            case "02":
                return "Fev";

            case "03":
                return "Mar";

            case "04":
                return "Abr";

            case "05":
                return "Mai";

            case "06":
                return "Jun";

            case "07":
                return "Jul";

            case "08":
                return "Ago";

            case "09":
                return "Set";

            case "10":
                return "Out";

            case "11":
                return "Nov";

            case "12":
                return "Dez";
            default:
                return "Error";
        }
    }
}
