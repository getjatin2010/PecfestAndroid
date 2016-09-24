package in.pecfest.www.pecfest.Utilites;

/**
 * Created by Abhi on 11-09-2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import in.pecfest.www.pecfest.Model.Common.Constants;

public class getBitmap extends AsyncTask<String, Void, Bitmap>{

    String url;
    WeakReference<ImageView> ivw=null;
    WeakReference<ProgressBar> pbw=null;
    boolean photo=false;
    boolean loadFromLocal= true;
    String id;
    boolean rounded = false;
    int width=0;
    boolean resize;
    private static int max_cache=0;
    static LruCache<String, Bitmap> imageCache;


    public getBitmap(String u, String id, ImageView i){
        this(u, id, i, null, false, 0);
    }

    getBitmap(String u, String id, ImageView i, ProgressBar p){
        this(u, id, i, p, false, 0);
    }

    getBitmap(String u, String id, ImageView i, ProgressBar p, boolean r, int width){
        this(u, id, i, p, r, width, true, false);
    }

    getBitmap(String url, String id, ImageView iv, ProgressBar p, boolean resize, int width, boolean loadFromLocal,boolean rounded){
        this.url= url;
        this.id=id;
        this.resize= resize;
        this.width= width;
        this.loadFromLocal= loadFromLocal;
        this.rounded = rounded;

        if(iv!=null)
            ivw=new WeakReference<ImageView>(iv);
        if(p!=null)
            pbw=new WeakReference<ProgressBar>(p);

        Log.v("url", url);
        Log.v("id", id);
    }

    @Override
    protected void onPreExecute(){
        if(ivw!=null)
            ivw.get().setImageBitmap(null);
    }
    @Override
    protected Bitmap doInBackground(String...qv){

        Bitmap b=null;
        b= getBitmapFromCache(id);
        if(b!=null)
            return b;

        if(loadFromLocal)
            b=fetchFromLocal(id);

        if(b==null){
            b=fetchFromWeb(url,rounded);
            if(b!=null){
                photo=true;
                try{
                    if(loadFromLocal)
                    saveToLocal(id, b);
                }
                catch(Exception e){
                    Log.v("error report", e.getMessage());
                }
            }
        }
        if(width>0 && b!=null && b.getWidth()>width){
                b=processBitmap(b,width);
        }
        if(b!=null)
            addBitmapToCache(id, b);
        return b;
    }

    protected void onPostExecute(Bitmap b){
        if(ivw!=null && b!=null){
            ImageView iv=ivw.get();
            if(resize)
                iv.setLayoutParams(new LayoutParams(b.getWidth(), b.getHeight()));
            iv.setImageBitmap(b);
            if(pbw!=null){
                ((ProgressBar)pbw.get()).setVisibility(View.GONE);
            }
        }
    }

    public static Bitmap fetchFromLocal(String i){
        try{
            Log.v("from local", "photo from local"+i);
            File f=new File(Constants.STORAGE_PATH+"images/"+i);
            return BitmapFactory.decodeStream((InputStream)new FileInputStream(f));
        }
        catch(Exception e){
            return null;
        }
    }

    public static Bitmap fetchFromWeb(String u,boolean rounded){
        Bitmap b=null;
        try{
            b=BitmapFactory.decodeStream((InputStream) new URL(u).getContent());
            if(rounded)
            {
                b =  getRoundedShape(b);
            }
        }
        catch(Exception e){
            Log.v("err-report", "from fetchFromWeb "+e.getMessage());
        }
        return b;
    }

    public static Bitmap fetchFromWeb(String u){
        Bitmap b=null;
        try{
            b=BitmapFactory.decodeStream((InputStream) new URL(u).getContent());
                b =  getRoundedShape(b);
        }
        catch(Exception e){
            Log.v("err-report", "from fetchFromWeb "+e.getMessage());
        }
        return b;
    }

    public static void saveToLocal(String id, Bitmap b){

        File f,o;
        FileOutputStream fos;

        try{
            f=new File(Constants.STORAGE_PATH+"images/");
            if(!f.exists()){
                f.mkdirs();
            }
            Log.v("filename", ""+id);
            o=new File(f,id);
            fos=new FileOutputStream(o);

            if(id.contains("png"))
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            else
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.close();
        }
        catch(Exception e){
            Log.v("err-report", "from saveToLocal "+e.getMessage());
        }
    }

    public static Bitmap processBitmap(Bitmap b, int w){

        Matrix m=new Matrix();

        float wf=(float)w/b.getWidth();
        m.postScale(wf, wf);
        b=Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false);
        return b;
    }

    public static void addBitmapToCache(String k, Bitmap b){

        if(max_cache==0){
            initCache();
        }
        if(getBitmapFromCache(k)==null){
            try{
                imageCache.put(k, b);
            }
            catch(Exception e){

            }
        }
    }

    public static void emptyCache(){
        try{
            imageCache.evictAll();
        }
        catch(Exception e){
        }
    }

    public static Bitmap getBitmapFromCache(String k){
        try{

            if(max_cache==0)
                initCache();
            return imageCache.get(k);
        }
        catch(Exception e){
            return null;
        }
    }

    public static void removeBitmap(String k){
        if(max_cache==0)
            initCache();
        try{
            imageCache.remove(k);
        }
        catch(Exception e){

        }
    }


    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = (int)(scaleBitmapImage.getWidth() * 1);
        int targetHeight = (int)(scaleBitmapImage.getHeight() * 1);
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


    private static void initCache(){
        max_cache=(int)(Runtime.getRuntime().maxMemory()/1024);
        max_cache=max_cache/6;

        imageCache=new LruCache<String, Bitmap>(max_cache){
            protected int sizeOf(String k, Bitmap b){
                return b.getByteCount()/1024;
            }
        };
    }
}
