package udsregep.com.member.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import udsregep.com.member.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by agustinaindah on 12/07/2017.
 */

public class Helper {

    /**
     * method for create toast
     *
     * @param context
     * @param msg
     */
    public static void createToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * method for createAlert
     *
     * @param context
     * @param title
     * @param msg
     */
    public static void createAlert(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    /**
     * overloading method for createAlert with CallbackInterface
     *
     * @param context
     * @param title
     * @param msg
     * @param cancel
     * @param callbackInterface
     */
    public static void createAlert(Context context, String title, String msg, boolean cancel,
                                   final CallbackInterface callbackInterface) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(cancel)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbackInterface.callback();
                    }
                })
                .create()
                .show();
    }

    /**
     * method for createPrompt
     *
     * @param context
     * @param title
     * @param msg
     * @param callbackInterface
     */
    public static void createPrompt(Context context, String title, String msg,
                                    final CallbackInterface callbackInterface) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbackInterface.callback();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

    }

    /**
     * method for createSnackbar
     *
     * @param view
     * @param msg
     * @param action
     * @param callbackInterface
     */
    public static void createSnackbar(View view, String msg, String action,
                                      final CallbackInterface callbackInterface) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callbackInterface.callback();
                    }
                }).show();
    }

    /**
     * parse String to JsonObject
     *
     * @param string
     * @return JsonObject
     */
    public static JsonObject parseToJsonObject(String string) {

        JsonObject result = null;

        try {
            result = (new JsonParser()).parse(string).getAsJsonObject();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * parse string to JsonArray
     *
     * @param string
     * @return JsonArray
     */
    public static JsonArray parseToJsonArray(String string) {

        JsonArray result = null;

        try {
            result = (new JsonParser()).parse(string).getAsJsonArray();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Display Image using Picasso into ImageView
     *
     * @param context
     * @param url
     * @param imgView
     * @param isFitCenter
     */
    public static void displayImage(Context context, String url, ImageView imgView, boolean isFitCenter) {
        RequestCreator creator = Picasso.with(context).load(url);
        if (isFitCenter) {
            creator.fit().centerInside();
        }
        creator.placeholder(R.drawable.no_image).into(imgView);
    }
    /**
     * overloading diplayImage with isFitCenter : false
     *
     * @param context
     * @param url
     * @param imgView
     */
    public static void displayImage(Context context, String url, ImageView imgView) {
        displayImage(context, url, imgView, false);
    }

    /**
     * @param context
     * @param string
     * @param type
     * @return relative time
     */
    public static String parseRelativaTime(Context context, String string, String type) {
        Date date = parseStringToDate(string, type);
        return DateUtils
                .getRelativeDateTimeString(context,
                        date.getTime(),
                        DateUtils.SECOND_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL
                ) + "";
    }


    /**
     * @param string | ex. 2016-09-21 15:22:00
     * @param type   | see Consts TYPE_DATETIME, TYPE_DATE
     * @return string   | ex. 21 September 2016
     */
    public static String parseToDateString(String string, String type) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(type);
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newFormat = (new SimpleDateFormat("dd MMMM yyyy").format(date));

        return newFormat;
    }

    public static Date parseStringToDate(String strDate, String type) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(type);
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * method Log to debug
     *
     * @param string
     */
    public static void log(String string) {
        Log.d(Consts.DEBUG, string);
    }

    /**
     * method for check email format
     *
     * @param email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * method for encode basic auth
     *
     * @param username
     * @param password
     * @return String basic
     */
    public static String basicAuth(String username, String password) {
        String credentials = username + ":" + password;
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        return basic;
    }

    /**
     * @param number int
     * @return String number with separator
     */
    public static String numberFormat(int number) {
        return NumberFormat.getInstance(Locale.US).format(number);
    }

    /**
     * @param number long
     * @return String number with separator
     */
    public static String numberFormat(Long number) {
        return NumberFormat.getInstance(Locale.US).format(number);
    }

    /**
     * @param number int
     * @return number with currency
     */
    public static String numberCurrency(int number) {
        return "Rp " + numberFormat(number);
    }

    /**
     * @param number long
     * @return number with currency
     */
    public static String numberCurrency(Long number) {
        return "Rp " + numberFormat(number);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * @param integer
     * @return String calculate with gram or kilogram
     */
    public static String calculateWeight(int integer) {
        String result = integer + "gr";
        if (integer >= 1000) {
            result = (integer / 1000) + "kg";
        }
        return result;
    }

    /**
     * @return Gson
     */
    public static Gson getGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                });
        Gson gson = gsonBuilder.create();
        return gson;
    }

    /**
     * generate random int with specified range
     *
     * @param min
     * @param max
     * @return int random
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /*public static int calculateTotalBill(List<ItemInvoice> invoices) {
        int grandGrandTotal = 0;
        for (ItemInvoice item : invoices) {
            grandGrandTotal += item.getGrandTotal();
        }
        return grandGrandTotal;
    }*/
}
