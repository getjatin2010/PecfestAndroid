package pecapps.pecfest2015.Model.Common;

import android.graphics.Bitmap;

/**
 * Created by jatin on 1/9/16.
 */
public class DataHolder {
    public Bitmap sponsorImage[];
    public int spon = 1;
    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance()
    {
        return holder;
    }

}