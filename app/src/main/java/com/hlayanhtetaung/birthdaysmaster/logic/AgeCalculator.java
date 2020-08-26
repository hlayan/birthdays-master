package com.hlayanhtetaung.birthdaysmaster.logic;

import com.hlayanhtetaung.birthdaysmaster.data.DataClass;
import com.hlayanhtetaung.birthdaysmaster.data.DataForExtra;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;

public class AgeCalculator {

    private static LocalDate now = new LocalDate();

    public final static String[] MONTH12 = new String[]{"January", "February", "Match", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public final static String[] WEEK7 = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public final static String[] DAY31 = new String[]{"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31st"};

    public final static char ARIES = '\u2648';
    public final static char TAURUS = '\u2649';
    public final static char GEMINI = '\u264A';
    public final static char CANCER = '\u264B';
    public final static char LEO = '\u264C';
    public final static char VIRGO = '\u264D';
    public final static char LIBRA = '\u264E';
    public final static char SCORPIUS = '\u264F';
    public final static char SAGITTARIUS = '\u2650';
    public final static char CAPRICORN = '\u2651';
    public final static char AQUARIUS = '\u2652';
    public final static char PISCES = '\u2653';
    public final static char OPHIUCHUS = '\u26CE';

    private int a, b, c, d, e, f, g, h, ii, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, aa, bb, cc, dd, ee = 0;

    public static DataClass calculateAge(int years, int months, int days) {
        LocalDate birthday = new LocalDate(years, months, days);
        Period period = new Period(birthday, now, PeriodType.yearMonthDay());
        return new DataClass(period.getYears(), period.getMonths(), period.getDays());
    }

    public static DataClass calculateNextBirthday(int months, int days) {
        int checkYears = now.getYear();
        LocalDate birthday = new LocalDate(checkYears + 1, months, days);
        Period period = new Period(now, birthday, PeriodType.yearMonthDay());
        if (now.getMonthOfYear() > months) {
            return new DataClass(period.getMonths(), period.getDays());
        } else if (now.getDayOfMonth() > days && now.getMonthOfYear() == months) {
            return new DataClass(period.getMonths(), period.getDays());
        } else if (now.getDayOfMonth() == days && now.getMonthOfYear() == months) {
            return new DataClass(0, 0);
        } else {
            birthday = new LocalDate(checkYears, months, days);
            period = new Period(now, birthday, PeriodType.yearMonthDay());
            return new DataClass(period.getMonths(), period.getDays());
        }
    }

    public static DataForExtra calculateExtras(int years, int months, int days) {
        double totalMonths = (years * 12) + months;
        double totalDays = (years * 365.25) + (months * 30.45) + days;
        double totalWeeks = totalDays / 7.0;
        double totalHours = (totalDays * 24);
        double totalMinutes = (totalHours * 60);
        double totalSeconds = totalMinutes * 60;
        return new DataForExtra(years, (int) totalMonths, (int) totalWeeks, (int) totalDays, (int) totalHours, (int) totalMinutes, (int) totalSeconds);
    }

    public int[] calculateTwelveMonths(ArrayList<Integer> arrayList) {
        for (int i : arrayList) {
            switch (i) {
                case 1:
                    a++;
                    break;
                case 2:
                    b++;
                    break;
                case 3:
                    c++;
                    break;
                case 4:
                    d++;
                    break;
                case 5:
                    e++;
                    break;
                case 6:
                    f++;
                    break;
                case 7:
                    g++;
                    break;
                case 8:
                    h++;
                    break;
                case 9:
                    ii++;
                    break;
                case 10:
                    j++;
                    break;
                case 11:
                    k++;
                    break;
                case 12:
                    l++;
                    break;
            }
        }
        return new int[]{a, b, c, d, e, f, g, h, ii, j, k, l};
    }

    public int[] calculateDaysOfWeek(ArrayList<Integer> arrayList) {
        for (int i : arrayList) {
            switch (i) {
                case 1:
                    a++;
                    break;
                case 2:
                    b++;
                    break;
                case 3:
                    c++;
                    break;
                case 4:
                    d++;
                    break;
                case 5:
                    e++;
                    break;
                case 6:
                    f++;
                    break;
                case 7:
                    g++;
                    break;
            }
        }
        return new int[]{a, b, c, d, e, f, g};
    }

    public int[] calculate31Days(ArrayList<Integer> arrayList) {
        for (int i : arrayList) {
            switch (i) {
                case 1:
                    a++;
                    break;
                case 2:
                    b++;
                    break;
                case 3:
                    c++;
                    break;
                case 4:
                    d++;
                    break;
                case 5:
                    e++;
                    break;
                case 6:
                    f++;
                    break;
                case 7:
                    g++;
                    break;
                case 8:
                    h++;
                    break;
                case 9:
                    ii++;
                    break;
                case 10:
                    j++;
                    break;
                case 11:
                    k++;
                    break;
                case 12:
                    l++;
                    break;
                case 13:
                    m++;
                    break;
                case 14:
                    n++;
                    break;
                case 15:
                    o++;
                    break;
                case 16:
                    p++;
                    break;
                case 17:
                    q++;
                    break;
                case 18:
                    r++;
                    break;
                case 19:
                    s++;
                    break;
                case 20:
                    t++;
                    break;
                case 21:
                    u++;
                    break;
                case 22:
                    v++;
                    break;
                case 23:
                    w++;
                    break;
                case 24:
                    x++;
                    break;
                case 25:
                    y++;
                    break;
                case 26:
                    z++;
                    break;
                case 27:
                    aa++;
                    break;
                case 28:
                    bb++;
                    break;
                case 29:
                    cc++;
                    break;
                case 30:
                    dd++;
                    break;
                case 31:
                    ee++;
                    break;
            }
        }
        return new int[]{a, b, c, d, e, f, g, h, ii, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, aa, bb, cc, dd, ee};
    }

    public static int calculateTotalDays(int years, int months, int days) {
        double totalDays = (years * 365.25) + (months * 30.45) + days;
        return (int) totalDays;
    }

    public static String checkDaysOfWeeks(int dayOfWeeks) {
        switch (dayOfWeeks) {
            case 1:
                return "MON";
            case 2:
                return "TUE";
            case 3:
                return "WED";
            case 4:
                return "THU";
            case 5:
                return "FRI";
            case 6:
                return "SAT";
            case 7:
                return "SUN";
            default:
                return "0";
        }
    }

    public static String checkMonths(int months) {
        switch (months) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "0";
        }
    }

    public static ArrayList<String> leapYearsTime(int currentYears, int userYears) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int year = userYears; year <= currentYears; year++) {
            if (year % 400 == 0) {
                arrayList.add(String.valueOf(year));
            } else if (year % 100 == 0) {
                break;
            } else if (year % 4 == 0) {
                arrayList.add(String.valueOf(year));
            }
        }
        return arrayList;
    }

    public static ArrayList<String> sameBirthdayTime(int currentYears, int userYears, int userMonths, int userDays) {
        ArrayList<String> arrayList = new ArrayList<>();
        int userWeeks = new LocalDate(userYears, userMonths, userDays).getDayOfWeek();
        for (int leap = userYears + 1; leap < currentYears; leap++) {
            LocalDate date = new LocalDate(leap, userMonths, userDays);
            if (date.getDayOfWeek() == userWeeks) {
                arrayList.add(String.valueOf(leap));
            }
        }
        return arrayList;
    }

    public static String[] isSign(int day, int month) {

        if (month == 12) {

            if (day < 22)
                return new String[]{"SAGITTARIUS", String.valueOf(SAGITTARIUS)};
            else
                return new String[]{"CAPRICORN", String.valueOf(CAPRICORN)};
        } else if (month == 1) {
            if (day < 20)
                return new String[]{"CAPRICORN", String.valueOf(CAPRICORN)};
            else
                return new String[]{"AQUARIUS", String.valueOf(AQUARIUS)};
        } else if (month == 2) {
            if (day < 19)
                return new String[]{"AQUARIUS", String.valueOf(AQUARIUS)};
            else
                return new String[]{"PISCES", String.valueOf(PISCES)};
        } else if (month == 3) {
            if (day < 21)
                return new String[]{"PISCES", String.valueOf(PISCES)};
            else
                return new String[]{"ARIES", String.valueOf(ARIES)};
        } else if (month == 4) {
            if (day < 20)
                return new String[]{"ARIES", String.valueOf(ARIES)};
            else
                return new String[]{"TAURUS", String.valueOf(TAURUS)};
        } else if (month == 5) {
            if (day < 21)
                return new String[]{"TAURUS", String.valueOf(TAURUS)};
            else
                return new String[]{"GEMINI", String.valueOf(GEMINI)};
        } else if (month == 6) {
            if (day < 21)
                return new String[]{"GEMINI", String.valueOf(GEMINI)};
            else
                return new String[]{"CANCER", String.valueOf(CANCER)};
        } else if (month == 7) {
            if (day < 23)
                return new String[]{"CANCER", String.valueOf(CANCER)};
            else
                return new String[]{"LEO", String.valueOf(LEO)};
        } else if (month == 8) {
            if (day < 23)
                return new String[]{"LEO", String.valueOf(LEO)};
            else
                return new String[]{"VIRGO", String.valueOf(VIRGO)};
        } else if (month == 9) {
            if (day < 23)
                return new String[]{"VIRGO", String.valueOf(VIRGO)};
            else
                return new String[]{"LIBRA", String.valueOf(LIBRA)};
        } else if (month == 10) {
            if (day < 23)
                return new String[]{"LIBRA", String.valueOf(LIBRA)};
            else
                return new String[]{"SCORPIUS", String.valueOf(SCORPIUS)};
        } else if (month == 11) {
            if (day < 22)
                return new String[]{"SCORPIUS", String.valueOf(SCORPIUS)};
            else
                return new String[]{"SAGITTARIUS", String.valueOf(SAGITTARIUS)};
        } else {
            return new String[]{"OPHIUCHUS", String.valueOf(OPHIUCHUS)};
        }
    }

}
