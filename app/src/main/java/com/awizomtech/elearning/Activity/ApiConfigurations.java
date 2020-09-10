package com.awizomtech.elearning.Activity;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface ApiConfigurations {
    /*@Multipart
    @POST("upload")
    Call<ServerResponse> uploadFile(@Part("photo") MultipartBody.Part photo);*/
    @Multipart
    @POST("upload")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("description") RequestBody name,
                                    @Part("writepost") RequestBody Posrwrite,
                                    @Part("UserID") RequestBody UserId);

    @Multipart
    @POST("uploadpay")
    Call<ServerResponse> paymentUpload(@Part MultipartBody.Part file,
                                       @Part("courseid") RequestBody courseid,
                                       @Part("transactionid") RequestBody transactionid,
                                       @Part("levelid") RequestBody levelid,
                                       @Part("paymenttype") RequestBody paymenttype,
                                       @Part("price") RequestBody price,
                                       @Part("UserID") RequestBody UserId);


    @Multipart
    @POST("retrofit_example/upload_multiple_files.php")
    Call<ServerResponse> uploadMulFile(@Part MultipartBody.Part file1,
                                       @Part MultipartBody.Part file2);
}