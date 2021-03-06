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
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class SubscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String cname = " ", level, price, cid, duration, levelId, USDValue, Payable, Discount, PaymentType = " ";
    TextView Tv_coursename, Tv_price, Tv_duration, PayableAmt, Tv_LevelID, Tv_Level, Tv_offer, Tv_actualprice, Tv_afterdiscount;
    EditText Transactionid;
    CardView Next, Enrollsend;
    String result;
    ProgressDialog progressDialog;
    private Spinner PaymentOption;
    LinearLayout BankLayout, QRlayout, Document;
    Button SelectFile;
    String mediaPath = " ";
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ImageView Preview;
    private static final String[] option = {"Select Payment Option", "Cash", "Bank Account", "Scan QR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_subscription);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        InitView();
    }

    private void InitView() {

        ImageView backpress = findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Tv_coursename = findViewById(R.id.tv_course_name);
        Tv_price = findViewById(R.id.tv_price);
        Tv_duration = findViewById(R.id.tv_duration);
        Tv_LevelID = findViewById(R.id.tv_levelid);
        Tv_Level = findViewById(R.id.tv_level_name);
        Next = findViewById(R.id.enroll);
        Enrollsend = findViewById(R.id.enrollsend);
        Transactionid = findViewById(R.id.transactionid);
        PayableAmt = findViewById(R.id.tv_payable);
        QRlayout = findViewById(R.id.qrcode);
        BankLayout = findViewById(R.id.babktype);
        Document = findViewById(R.id.document);
        Tv_offer = findViewById(R.id.tv_offer);
        Tv_afterdiscount = findViewById(R.id.tv_afterdiscount);
        Tv_actualprice = findViewById(R.id.tv_actualprice);
        Tv_actualprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        SelectFile = findViewById(R.id.selectfile);
        Preview = findViewById(R.id.preview);

        levelId = getIntent().getExtras().getString("levelID");
        price = getIntent().getExtras().getString("Price");
        cid = getIntent().getExtras().getString("Cid");
        duration = getIntent().getExtras().getString("Duration");
        level = getIntent().getExtras().getString("level");
        USDValue = getIntent().getExtras().getString("USDValue");
        Payable = getIntent().getExtras().getString("payable");
        Discount = getIntent().getExtras().getString("discount");
        if (level.contains("Short")) {
            cname = getIntent().getExtras().getString("CourseName");
        } else {
            cname = CourseLevelActivity.getActivityInstance().getData();
        }
        float usd = Float.parseFloat(USDValue);
        float inr = Float.parseFloat(price);
        final float payable = Float.parseFloat(Payable);
        float sum = inr / usd;
        float sum1 = payable / usd;
        String usdprice = String.valueOf(sum);
        String usdprice1 = String.valueOf(sum1);
        Tv_LevelID.setText(levelId);
        Tv_coursename.setText("Course Name : " + cname.toString());
        Tv_Level.setText("Level Name : " + level.toString());
        Tv_actualprice.setText("₹" + price + "/" + usdprice + "$");
        Tv_price.setText("₹" + price + "/" + usdprice + "$");
        Tv_duration.setText(duration.toString());
        Tv_offer.setText(Discount + "% OFF");
        Tv_afterdiscount.setText(" ₹" + Payable + "/" + usdprice1 + "$");
        PayableAmt.setText("Payable " + " ₹" + Payable + "/" + usdprice1 + "$");

        PaymentOption = (Spinner) findViewById(R.id.paymentOption);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SubscriptionActivity.this,
                android.R.layout.simple_spinner_item, option);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PaymentOption.setAdapter(adapter);
        PaymentOption.setOnItemSelectedListener(this);
        SelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PaymentType.contains("Select Payment Option")) {
                    Toast.makeText(SubscriptionActivity.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
                } else if (PaymentType.contains("Cash")) {
                    progressDialog.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            try {
                                String lid = levelId.toString();
                                String transactionid = "demo";
                                String usetid = SharedPrefManager.getInstance(SubscriptionActivity.this).getUser().getUserID();
                                String type = "Paid";
                                String price = Payable;
                                result = new UserHelper.POSTPayment().execute(usetid.toString(), cid.toString(), price.toString(), transactionid.toString(), lid.toString(), type.toString()).get();
                                if (result.isEmpty()) {
                                    progressDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    Next.setVisibility(View.GONE);
                                    Enrollsend.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(SubscriptionActivity.this, MyCourseActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 100);
                } else if (Transactionid.getText().toString().isEmpty()) {
                    Transactionid.setError("Required !");
                } else {
                    progressDialog.show();
                    uploadFile();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(SubscriptionActivity.this, MyCourseActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                BankLayout.setVisibility(View.GONE);
                QRlayout.setVisibility(View.GONE);
                Document.setVisibility(View.GONE);
                Preview.setVisibility(View.GONE);
                PaymentType = "Select Payment Option";
                break;
            case 1:
                BankLayout.setVisibility(View.GONE);
                QRlayout.setVisibility(View.GONE);
                Document.setVisibility(View.GONE);
                Preview.setVisibility(View.GONE);
                PaymentType = "Cash";
                break;
            case 2:
                BankLayout.setVisibility(View.VISIBLE);
                QRlayout.setVisibility(View.GONE);
                Document.setVisibility(View.VISIBLE);
                Preview.setVisibility(View.GONE);
                PaymentType = "Bank Account";
                break;
            case 3:
                BankLayout.setVisibility(View.GONE);
                QRlayout.setVisibility(View.VISIBLE);
                Document.setVisibility(View.VISIBLE);
                Preview.setVisibility(View.GONE);
                PaymentType = "Scan QR";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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
                Preview.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                Preview.setVisibility(View.VISIBLE);
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
        String tid = Transactionid.getText().toString();

        /*  String userid = SharedPrefManager.getInstance(EditProfileActivity.this).getUser().getUserID();*/
        String userid = SharedPrefManager.getInstance(SubscriptionActivity.this).getUser().getMobileNo();
        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);
        RequestBody courseid = RequestBody.create(MediaType.parse("text/plain"), cid.toString());
        RequestBody transactionid = RequestBody.create(MediaType.parse("text/plain"), tid.toString());
        RequestBody levelid = RequestBody.create(MediaType.parse("text/plain"), levelId.toString());
        RequestBody paymenttype = RequestBody.create(MediaType.parse("text/plain"), PaymentType.toString());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), Payable.toString());
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userid.toString());
        ApiConfigurations getResponse = AppConfigurations.getRetrofit().create(ApiConfigurations.class);
        Call call = getResponse.paymentUpload(fileToUpload, courseid, transactionid, levelid, paymenttype, price, userId);
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