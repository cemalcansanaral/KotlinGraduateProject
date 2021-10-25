package com.cemalcansanaral.bitirmetezi.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekSepetBinding
import com.cemalcansanaral.bitirmetezi.entity.YemekSepet
import com.cemalcansanaral.bitirmetezi.fragment.YemekDetayFragment
import com.cemalcansanaral.bitirmetezi.repo.YemeklerDaoRepository

class YemekSepetFragmentViewModel : ViewModel() {
    var sepetYemeklerListesi : MutableLiveData<List<YemekSepet>>
    private val ydaor = YemeklerDaoRepository()

    init {
        sepetYemekleriYukle()
        sepetYemeklerListesi = ydaor.sepetYemekleriGetir()
    }

    fun sepetYemekleriYukle() {
        ydaor.tumSepetYemekleriAl()
    }

    fun yemekSil(sepet_yemek_id : Int, kullanici_adi : String){
        ydaor.sepettenYemekSilme(sepet_yemek_id, kullanici_adi)
    }

    fun sepetYemekRvAnim(tasarim : FragmentYemekSepetBinding){
        ydaor.yemekSepetRvAnim(tasarim)
    }
}