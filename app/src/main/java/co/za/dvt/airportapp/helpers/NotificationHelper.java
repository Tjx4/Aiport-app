package co.za.dvt.airportapp.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.Toast;
import co.za.dvt.airportapp.R;

public class NotificationHelper {

    public static void showShortToast(Context context, String message) {
        Toast toast = getToast(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongToast(Context context, String message) {
        Toast toast = getToast(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private static Toast getToast(Context context, String message, int length) {
        Toast toast = Toast.makeText(context, message, length);
        return toast;
    }

    public static void showErrorDialog(Context context, String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.ic_error);
        showAlertMessage(context, ab);
    }

    public static void showSuccessDialog(Context context, String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.ic_success);
        showAlertMessage(context, ab);
    }

    private static AlertDialog.Builder setupBasicMessage(Context context, String title, String message, String posiTiveButtonText, boolean showNagativeButton, boolean showNutralButton) {

        AlertDialog.Builder ab = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        ab.setMessage(message)
                .setTitle(title)
                .setPositiveButton(posiTiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        if (showNagativeButton) {
            ab.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return ab;
    }

    private static void showAlertMessage(Context context, AlertDialog.Builder ab) {
        AlertDialog a = ab.create();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.show();

        a.getButton(a.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.lightText));
        a.getButton(a.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.lightText));
        a.getButton(a.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.lightText));
    }
}
