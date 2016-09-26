package in.pecfest.www.pecfest.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import in.pecfest.www.pecfest.Adapters.FilterApadter;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Filters.FilterResponse;
import in.pecfest.www.pecfest.Model.Permissions.PermissionResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;
import in.pecfest.www.pecfest.Utilites.getBitmap;

public class DrapYourCape extends AppCompatActivity implements CommunicationInterface{

    Button getMerged;
    ImageView cropped;
    Bitmap croppedBitmap;
    Button uploadButton;
    FilterApadter filterApadter;
    GridView gv;
    float width,height;
    HorizontalScrollView scrollViewHorizontal ;
    Bitmap filterBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drap_your_cape);
        cropped = (ImageView)findViewById(R.id.CroppedImage);
        getMerged = (Button)findViewById(R.id.getMerged);
        uploadButton = (Button)findViewById(R.id.imageUpload);
        gv = (GridView)findViewById(R.id.filterGridView);
        scrollViewHorizontal = (HorizontalScrollView)findViewById(R.id.scrollViewHorizontal);

        positionEverything();
        getFilterUrls();
    }
    public void getFilterUrls()
    {
        Request rr= new Request();
        rr.method = Constants.METHOD.GET_FILTERS;
        rr.showPleaseWaitAtStart = true;
        rr.hidePleaseWaitAtEnd = true;
        rr.heading = "Getting Filters";
        Utility.SendRequestToServer(this,rr);
    }
    void positionEverything()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        float w = dm.widthPixels/dm.xdpi;
        float h = dm.heightPixels/dm.ydpi;
        float textSize = h*5;
         width = dm.widthPixels;
         height = dm.heightPixels;
        float croppedWidth = .9f*width;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)croppedWidth,(int)(croppedWidth));
        params.leftMargin = (int) ((width*.015f));
        params.topMargin = (int) ((height*0.1));
        cropped.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) (.8*width/2), (int) (0.1f * height));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) (height*0.00125);
        uploadButton.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) (.8*width/2), (int) (0.1f * height));
        params.leftMargin = (int) ((1*width/2));
        params.topMargin = (int) (height*0.00125);
        getMerged.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) (width), (int) (0.2f * height));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) (height*2/3);
        scrollViewHorizontal.setLayoutParams(params);

//
//        LinearLayout.LayoutParams paramsLineaer = new LinearLayout.LayoutParams((int) (width), (int) (0.3f * height));
//        paramsLineaer.leftMargin = (int) ((0));
//        paramsLineaer.topMargin = (int) (height*2/3);
//        gv.setLayoutParams(paramsLineaer);


    }
    public  Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        try {
            bmp2 = scaleDown(bmp2,bmp1.getHeight(),bmp1.getWidth(),false);
            Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, new Matrix(), null);
            canvas.drawBitmap(bmp2, 0, 0, null);
            return bmOverlay;
        }
        catch(Exception e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    public static Bitmap scaleDown(Bitmap realImage, float maxImageSizeHeight,float maxImageSizeWidth,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSizeWidth / realImage.getWidth(),
                (float) maxImageSizeHeight / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,height, filter);
        return newBitmap;
    }

    public void uploadImage(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            CropImage.activity(uri)
                    .setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    croppedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);
                    cropped.setImageBitmap(croppedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

//


        }


    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false)
        {
            Toast.makeText(this,rr.errorMessage,Toast.LENGTH_LONG).show();
        }
        if(method.equalsIgnoreCase(Constants.METHOD.GET_FILTERS))
        {

           final  FilterResponse fr =(FilterResponse)Utility.getObjectFromJson(rr.JsonResponse,FilterResponse.class);              ;
           if(fr==null) {
                Toast.makeText(this,"No filters Available for now",Toast.LENGTH_LONG).show();
               finish();
           }
            else {
               gv.setNumColumns(fr.filterUrls.length);
               filterApadter = new FilterApadter(this, fr.filterUrls,width,height);
               gv.setAdapter(filterApadter);
               gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       getBitmap b = Utility.GetBitmap(fr.filterUrls[position], null, false, 0, true);
                       try {
                           filterBitmap = b.get();
                           Bitmap finalBitmap = overlay(croppedBitmap, filterBitmap);
                           cropped.setImageBitmap(finalBitmap);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       } catch (ExecutionException e) {
                           e.printStackTrace();
                       }

                   }
               });

           }

        }
    }
}
