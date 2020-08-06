package com.hlayanhtetaung.birthdaysmaster.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hlayanhtetaung.birthdaysmaster.R;

public class ThemeUtils {

    private static int cTheme;

    public final static int ORIGINAL = 1;
    public final static int A = 2;
    public final static int B = 3;
    public final static int C = 4;
    public final static int D = 5;
    public final static int E = 6;
    public final static int F = 7;
    public final static int G = 8;
    public final static int H = 9;
    public final static int I = 10;
    public final static int J = 11;
    public final static int K = 12;
    public final static int L = 13;
    public final static int M = 14;
    public final static int N = 15;
    public final static int O = 16;
    public final static int P = 17;
    public final static int Q = 18;
    public final static int r = 19;
    public final static int S = 20;

    public static void changeToTheme(Activity activity, int theme) {

        cTheme = theme;

        SharedPreferences sharedPref = activity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("cTheme", theme);
        editor.apply();

        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));

    }

    public static void onActivityCreateSetTheme(Activity activity) {

        SharedPreferences sharedPref = activity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        cTheme = sharedPref.getInt("cTheme", ORIGINAL);

        switch (cTheme) {

            case ORIGINAL:

                activity.setTheme(R.style.AppTheme);

                break;

            case A:

                activity.setTheme(R.style.a);

                break;

            case B:

                activity.setTheme(R.style.b);

                break;

            case C:

                activity.setTheme(R.style.c);

                break;

            case D:

                activity.setTheme(R.style.d);

                break;

            case E:

                activity.setTheme(R.style.e);

                break;

            case F:

                activity.setTheme(R.style.f);

                break;

            case G:

                activity.setTheme(R.style.g);

                break;

            case H:

                activity.setTheme(R.style.h);

                break;

            case I:

                activity.setTheme(R.style.i);

                break;

            case J:

                activity.setTheme(R.style.j);

                break;

            case K:

                activity.setTheme(R.style.k);

                break;

            case L:

                activity.setTheme(R.style.l);

                break;

            case M:

                activity.setTheme(R.style.m);

                break;

            case N:

                activity.setTheme(R.style.n);

                break;

            case O:

                activity.setTheme(R.style.o);

                break;

            case P:

                activity.setTheme(R.style.p);

                break;

            case Q:

                activity.setTheme(R.style.q);

                break;

            case r:

                activity.setTheme(R.style.r);

                break;

            case S:

                activity.setTheme(R.style.s);

                break;

        }

    }

}
