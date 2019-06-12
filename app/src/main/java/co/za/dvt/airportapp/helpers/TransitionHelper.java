package co.za.dvt.airportapp.helpers;

import co.za.dvt.airportapp.R;

public class TransitionHelper {

    private static int[] getTransitions(int inTransition, int outTransition) {
        return new int[]{inTransition, outTransition};
    }

    public static int[] slideInActivity() {
        return getTransitions(R.anim.slide_right, R.anim.no_transition);
    }

    public static int[] slideOutActivity() {
        return getTransitions(R.anim.no_transition, R.anim.slide_left);
    }

    public static int[] fadeInActivity() {
        return getTransitions(R.anim.fade_in, R.anim.no_transition);
    }

    public static int[] fadeOutActivity() {
        return getTransitions(R.anim.no_transition, R.anim.fade_out);
    }
}
