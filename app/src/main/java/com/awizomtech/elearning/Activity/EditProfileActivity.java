package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.LoginModel;
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class EditProfileActivity extends AppCompatActivity {
    String result;
    EditText Fname, LName, Phone, Email;
    CardView SelectImage;
    LinearLayout Save;
    de.hdodenhof.circleimageview.CircleImageView imageView1, imageView2;
    String mediaPath=" ";
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    TextView str1, str2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Inintview();
    }

    private void Inintview() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        ImageView backpress = findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Fname = findViewById(R.id.username);
        LName = findViewById(R.id.lastname);
        SelectImage = findViewById(R.id.selectImage);
        Save = findViewById(R.id.ll_submit);
        imageView1 = findViewById(R.id.Iconimage1);
        imageView2 = findViewById(R.id.Iconimage2);
        Phone = findViewById(R.id.mobile);
        Email = findViewById(R.id.email);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Fname.getText().toString().isEmpty() || LName.getText().toString().isEmpty()) {
                    Fname.setError("Required !");
                    LName.setError("Required !");
                } else {
                    progressDialog.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            uploadFile();
                            ProfileData();
                        }
                    }, 100);
                }
            }
        });
        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        String Name = SharedPrefManager.getInstance(this).getUser().getName();
        String Image = SharedPrefManager.getInstance(this).getUser().getProfilePhoto();
        String LastName = "";
        LastName = SharedPrefManager.getInstance(this).getUser().getLastName();
        ProfileNew();
        if (LastName.equals("null")) {
            LName.setText(" ");
        } else {
            LName.setText(LastName.toString());
        }
        Fname.setText(Name);
        try {
            Glide.with(this).load(AppConfig.BASE_URL + Image).into(imageView1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ProfileData() {
        try {
            String userid = SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new AccountHelper.Profile().execute(userid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                ProfileModel profileModel = new Gson().fromJson(result, listType);
                String name = profileModel.getFirstName().toString();
                String Lname = String.valueOf(profileModel.getLastName());
                String Image = profileModel.getProfilePhoto();
                String mobile = profileModel.getMobileNumber();
                String uid = profileModel.getUserID();
             int id = profileModel.getID();
                if (!userid.equals("null")) {
                    SharedPrefManager sp = new SharedPrefManager(EditProfileActivity.this);
                    sp.logout();
                    LoginModel loginmodel1 = new LoginModel();
                    loginmodel1.UserName = mobile;
                    loginmodel1.Name = name;
                    loginmodel1.LastName = Lname;
                    loginmodel1.ProfilePhoto = Image;
                    loginmodel1.MobileNo=mobile;
                    loginmodel1.UserID=uid;
                    loginmodel1.ID=id;
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(loginmodel1);
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ;
    }

    private void ProfileNew() {
        try {
            String userid = SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new AccountHelper.Profile().execute(userid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                ProfileModel profileModel = new Gson().fromJson(result, listType);
                String mobile = profileModel.getMobileNumber();
                String uid = profileModel.getEmailAddrss();
                Phone.setText(mobile);
                Email.setText(uid);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ;
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
                imageView2.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);
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
        String lastname = LName.getText().toString();
        String name = Fname.getText().toString();
        /*  String userid = SharedPrefManager.getInstance(EditProfileActivity.this).getUser().getUserID();*/
        String userid = SharedPrefManager.getInstance(EditProfileActivity.this).getUser().getMobileNo();
        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), name.toString());
        RequestBody Posrwrite = RequestBody.create(MediaType.parse("text/plain"), lastname.toString());
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