package com.vistony.salesforce;

public class ListenerBackPress {
    private static String currentFragment;
    private static String temporaIdentityFragment;

    public static String getCurrentFragment() {
        return currentFragment;
    }

    public static void setCurrentFragment(String currentFragment) {
        ListenerBackPress.currentFragment = currentFragment;
    }

    public static String getTemporaIdentityFragment() {
        return temporaIdentityFragment;
    }

    public static void setTemporaIdentityFragment(String temporaIdentityFragment) {
        ListenerBackPress.temporaIdentityFragment = temporaIdentityFragment;
    }
}
