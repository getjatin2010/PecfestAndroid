package in.pecfest.www.pecfest.Communication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.pecfest.www.pecfest.Activites.HomeScreen;
import in.pecfest.www.pecfest.R;


/**
 * Created by Pradeep on 20-01-2016.
 */
public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;
    private ProgressBar mImageIndeterminateProgressBar;
    private boolean roundImage;
    private float ratio;
    private boolean isSopnsor = false;
    //for loading sponsors to localVariable--------------------------------------------------------------
    public ImageLoader(String url,  float ratio,boolean roundImage,boolean isSopnsor) {
        this.url = url;
        this.imageView = imageView;
        this.ratio = ratio;
        this.roundImage = roundImage;
        this.isSopnsor=isSopnsor;
    }
    //---------------------------------------------------------------------------------------------------
    public ImageLoader(String url, ImageView imageView,  float ratio,boolean roundImage) {
        this.url = url;
        this.imageView = imageView;
        this.ratio = ratio;
        this.roundImage = roundImage;
    }

    public ImageLoader(String url, ImageView imageView,  float ratio, ProgressBar progressBar,boolean roundImage) {
        this.url = url;
        this.imageView = imageView;
        this.ratio = ratio;
        mImageIndeterminateProgressBar = progressBar;
        this.roundImage = roundImage;
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
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if(mImageIndeterminateProgressBar!=null)
        mImageIndeterminateProgressBar.setVisibility(View.INVISIBLE);
        //imageView.setBackground(null);

        if(result!=null)
        {
            if(roundImage==true)
            result = getRoundedShape(result);
            if(isSopnsor){//if is a sponsor image add to sponsor array
                HomeScreen.sponsorImage[HomeScreen.spon++]=result;
            }else{
                imageView.setImageBitmap(result);
            }
        }
        else
        {
            if(!isSopnsor)
                imageView.setImageResource(R.drawable.no_image);
        }
    }


}