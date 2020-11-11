package com.fayer.resepnew.network;

import com.fayer.resepnew.model.ResponseData;
import com.fayer.resepnew.model.ResponseGetAllDataResep;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestAPI {

    @GET("getdataresep.php")
    Call <ResponseGetAllDataResep> getDataResep();

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseData> insertData(
        @Field("nama_resep") String nama,
        @Field("detail") String detail,
        @Field("gambar") String gambar
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseData> updateData(
        @Field("id_resep") String id,
        @Field("nama_resep") String nama,
        @Field("detail") String detail,
        @Field("gambar") String gambar
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseData> deleteData(
        @Field("id_resep") String id
    );

    //for User Data
    @FormUrlEncoded
    @POST("user_insert.php")
    Call<ResponseData> userRegister(
            @Field("name") String nama,
            @Field("jk") String jk,
            @Field("image") String image,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<ResponseData> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}


