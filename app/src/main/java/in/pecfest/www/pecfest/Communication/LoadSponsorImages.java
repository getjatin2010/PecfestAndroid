package in.pecfest.www.pecfest.Communication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.DataHolder;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Sponsor.Sponsor;
import in.pecfest.www.pecfest.Model.Sponsor.SponsorResponse;
import in.pecfest.www.pecfest.R;


/**
 * Created by Pradeep on 20-01-2016.
 */
public class LoadSponsorImages extends AsyncTask<Void, Void, Response> {

    private SponsorResponse sponsorResponse;
    private ImageView imageView;
    private ProgressBar mImageIndeterminateProgressBar;
    private boolean roundImage;
    private float ratio;
    private Context context;
    private boolean isSopnsor = false;
    //for loading sponsors to localVariable--------------------------------------------------------------

    public LoadSponsorImages(SponsorResponse sponsorResponse,Context context, float ratio, boolean roundImage) {
        this.sponsorResponse = sponsorResponse;
        this.ratio = ratio;
        this.roundImage = roundImage;
        this.context = context;
        DataHolder.getInstance().sponsorImage=new Bitmap[sponsorResponse.sponsorlist.length+1];
        DataHolder.getInstance().spon = 0;
    }

    public LoadSponsorImages(SponsorResponse sponsorResponse,Context context, float ratio, ProgressBar progressBar, boolean roundImage) {
        this.sponsorResponse = sponsorResponse;
        this.context = context;
        this.ratio = ratio;
        mImageIndeterminateProgressBar = progressBar;
        this.roundImage = roundImage;
        DataHolder.getInstance().sponsorImage=new Bitmap[sponsorResponse.sponsorlist.length+1];
        DataHolder.getInstance().spon = 0;

    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = (int)(scaleBitmapImage.getWidth() * ratio);
        int targetHeight = (int)(scaleBitmapImage.getHeight() * ratio);
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    @Override
    protected Response doInBackground(Void... params) {

        Response rr = new Response();
        rr.isSuccess = true;
        for (int i = 0; i < sponsorResponse.sponsorlist.length; i++) {

            try {
                URL urlConnection = new URL(sponsorResponse.sponsorlist[i].sponsorUrl);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap result = BitmapFactory.decodeStream(input);
                if(result!=null)
                {
                    if(roundImage==true)
                        result = getRoundedShape(result);

                    DataHolder.getInstance().sponsorImage[DataHolder.getInstance().spon++]=result;

                }

            } catch (Exception e) {
                rr.isSuccess = false;
                rr.errorMessage = e.toString();
                e.printStackTrace();
            }
        }
        return rr;
    }

    @Override
    protected void onPostExecute(Response rr) {
        super.onPostExecute(rr);
        if(mImageIndeterminateProgressBar!=null)
        mImageIndeterminateProgressBar.setVisibility(View.INVISIBLE);

        if(rr == null)
        {
            Toast.makeText(context, "Server Problem", Toast.LENGTH_SHORT).show();
            return;
        }

        CommunicationInterface tt = (CommunicationInterface)context;
        tt.onRequestCompleted(Constants.METHOD.LOAD_SPONSER, rr);

    }


}