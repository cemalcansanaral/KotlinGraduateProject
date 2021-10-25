package com.cemalcansanaral.bitirmetezi.retrofit

import com.cemalcansanaral.bitirmetezi.entity.YemekSepetCevap
import com.cemalcansanaral.bitirmetezi.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDaoInterface {
    @GET("yemekler/tumYemekleriGetir.php")
    //Bu fonksiyon çalıştığı zaman YemeklerCevap dönecek.
    fun tumYemekler() : Call<YemeklerCevap>

    @POST("yemekler/sepeteYemekEkle.php")
    //Türkçe harf için
    @FormUrlEncoded
    //Field'lardaki değer web servisindeki isimlerle aynı olmak zorunda
    fun sepeteYemekEkle(@Field("sepet_yemek_id") yemek_id : Int,
                        @Field ("yemek_adi") yemek_adi : String,
                        @Field ("yemek_resim_adi") yemek_resim_adi : String,
                        @Field ("yemek_fiyat") yemek_fiyat : Int,
                        @Field ("yemek_siparis_adet") yemek_siparis_adet : Int,
                        @Field ("kullanici_adi") kullanici_adi : String) : Call<YemekSepetCevap>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun sepetYemekler(@Field ("kullanici_adi") kullanici_adi: String) : Call<YemekSepetCevap>

    @POST ("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun sepettenYemekSil(@Field ("sepet_yemek_id") sepet_yemek_id : Int,
                         @Field ("kullanici_adi") kullanici_adi: String) : Call<YemekSepetCevap>
}