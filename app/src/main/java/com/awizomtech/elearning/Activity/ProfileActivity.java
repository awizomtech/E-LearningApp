package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    String result;
    TextView Username, Name,Mob,Address,Email;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    String mediaPath, mediaPath1;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    TextView str1, str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_profile);
        Inintview();
    }
    private void Inintview() {
ImageView backpress=findViewById(R.id.back);
      backpress.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBackPressed();
          }
      });
      
        Username =findViewById(R.id.username);
        Name=findViewById(R.id.name);
        Address=findViewById(R.id.address);
        Mob=findViewById(R.id.mob);
        Email=findViewById(R.id.email);
        imageView=findViewById(R.id.Iconimage2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new AccountHelper.Profile().execute(userid.toString()).get();
            if (result.isEmpty()) {
            } else {

                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                ProfileModel profileModel = new Gson().fromJson(result, listType);
                Username.setText(profileModel.getFirstName().toString());
                Name.setText(profileModel.getFirstName().toString()+" "+profileModel.getLastName().toString());
                Mob.setText(profileModel.getMobileNumber().toString());
                Email.setText(profileModel.getEmailAddrss().toString());
                String date=profileModel.getRegistrationDate().toString();
                String ne=date.split("T")[0];
                Address.setText(ne);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
             /*   str1.setText(mediaPath);*/
                // Set the Image in ImageView for Previewing the Media
                 imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    private void uploadFile() {
        /*  progressDialog.show();*/

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);
        String postwrite = "demo";
        String userid = SharedPrefManager.getInstance(ProfileActivity.this).getUser().getUserID();

        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody Posrwrite = RequestBody.create(MediaType.parse("text/plain"), postwrite.toString());
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userid.toString());
       ApiConfigurations getResponse = AppConfigurations.getRetrofit().create(ApiConfigurations.class);
        Call call = getResponse.uploadFile(fileToUpload, filename, Posrwrite, userId);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                ServerResponse serverResponse = (ServerResponse) response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {

                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;

                    Log.v("Response", serverResponse.toString());
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

}