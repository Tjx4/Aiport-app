package co.za.dvt.airportapp.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.base.activity.BaseAsyncActivity;
import co.za.dvt.airportapp.fragments.BaseDialogFragment;

public class NotificationHelper {

    public static void showFragmentDialog(AppCompatActivity activity, String title, int Layout, BaseDialogFragment newFragment) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Bundle payload = new Bundle();
        payload.putString(Constants.TITLE, title);
        payload.putInt(Constants.LAYOUT, Layout);

        newFragment.setArguments(payload);
        newFragment.show(ft, "dialog");
    }

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

    public static void showErrorDialog(BaseAsyncActivity asyncActivity, String title, String message, String buttonText) {
        AlertDialog.Builder ab = setupBasicMessage(asyncActivity, title, message, buttonText, false, false);
        ab.setIcon(R.drawable.ic_error);
        showAlertMessage(asyncActivity, ab);
    }

    public static void showSuccessDialog(BaseAsyncActivity asyncActivity, String title, String message, String buttonText) {
        AlertDialog.Builder ab = setupBasicMessage(asyncActivity, title, message, buttonText, false, false);
        ab.setIcon(R.drawable.ic_success);
        showAlertMessage(asyncActivity, ab);
    }

    private static AlertDialog.Builder setupBasicMessage(final BaseAsyncActivity asyncActivity, String title, String message, String posiTiveButtonText, boolean showNagativeButton, boolean showNutralButton) {
        AlertDialog.Builder ab = new AlertDialog.Builder(asyncActivity, R.style.AlertDialogCustom);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ab.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    asyncActivity.onDialogDismissed(dialogInterface);
                }
            });
        }

        return ab;
    }

    private static void showAlertMessage(Context context, AlertDialog.Builder ab) {
        AlertDialog a = ab.create();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.show();

        a.getButton(a.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.darkerText));
        a.getButton(a.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.darkerText));
        a.getButton(a.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.darkerText));
    }
}