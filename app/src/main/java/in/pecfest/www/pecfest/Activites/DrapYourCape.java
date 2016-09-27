package in.pecfest.www.pecfest.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
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

import org.lucasr.twowayview.TwoWayView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
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

    Button downloadImage;
    ImageView cropped;
    Bitmap croppedBitmap;
    TwoWayView filterHoriList ;
    Button uploadButton;
    Bitmap finalBitmap;
    FilterApadter filterApadter;
    float width,height;
    Bitmap filterBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drap_your_cape);
        cropped = (ImageView)findViewById(R.id.CroppedImage);
        downloadImage = (Button)findViewById(R.id.downloadImage);
        uploadButton = (Button)findViewById(R.id.imageUpload);
        filterHoriList = (TwoWayView) findViewById(R.id.filterHoriList);

        positionEverything();
        getFilterUrls();

        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (croppedBitmap == null) {
                    Toast.makeText(DrapYourCape.this, "Upload Your Image First", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (filterBitmap == null) {
                    Toast.makeText(DrapYourCape.this, "Select Your Tag fromm bottom", Toast.LENGTH_SHORT).show();
                    return;
                }
                SaveImage(finalBitmap);

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveImage(Bitmap finalBitmap) {

       File myDir = new File(Constants.STORAGE_PATH_DP);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Pecfest-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
         showDialog();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DrapYourCape.this, "Image couldn't be saved in Pecfest Folder. Please use share to get Image. ", Toast.LENGTH_SHORT).show();
        }
    }
        public void showDialog()
        {
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Image is saved in Pecfest Folder. Yayy!!");

            alertDialogBuilder.setPositiveButton("Show", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    File file = new File(Constants.STORAGE_PATH_DP);
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setDataAndType(Uri.fromFile(file), "*/*");
                    startActivity(intent);
                }
            });

            alertDialogBuilder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });


            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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
        params.topMargin = (int) ((height*0.19));
        cropped.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) (.8*width/2), (int) (0.1f * height));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) (height*0.08);
        uploadButton.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) (.8*width/2), (int) (0.1f * height));
        params.leftMargin = (int) ((1*width/2));
        params.topMargin = (int) (height*0.08);
        downloadImage.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) (width), (int) (0.21f * height));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) (height*2.1/3);
        filterHoriList.setLayoutParams(params);

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
               filterApadter = new FilterApadter(this, fr.filterUrls,width,height);
               filterHoriList.setAdapter(filterApadter);
               filterHoriList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       if(croppedBitmap==null)
                       {
                           Toast.makeText(DrapYourCape.this, "Upload A Image first", Toast.LENGTH_SHORT).show();
                           return;
                       }
                       getBitmap b = Utility.GetBitmap(fr.filterUrls[position], null, false, 0, true);
                       try {
                           filterBitmap = b.get();
                            finalBitmap = overlay(croppedBitmap, filterBitmap);
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
