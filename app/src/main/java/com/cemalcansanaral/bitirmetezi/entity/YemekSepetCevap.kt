package com.cemalcansanaral.bitirmetezi.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YemekSepetCevap (@SerializedName("sepet_yemekler")
                            @Expose
                            var sepet_yemekler : List<YemekSepet>,
                            @SerializedName("success")
                            @Expose
                            var success : Int) {
}