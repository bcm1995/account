package com.bcm.account.tools;

/**
 * Created by Bean on 2017/4/27.
 */

public class InterfaceCenter {
    public static refreshListener irefresh;
    public static interface  refreshListener{
        public void refreshDetail();
        public void refreshWallet();
        public void refreshReport();
    }
    public static void setInterface(refreshListener refreshListener)
    {
        irefresh = refreshListener;
    }
}
