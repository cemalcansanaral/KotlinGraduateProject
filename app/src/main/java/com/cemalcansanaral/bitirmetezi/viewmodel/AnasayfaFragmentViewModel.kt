package com.cemalcansanaral.bitirmetezi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cemalcansanaral.bitirmetezi.databinding.FragmentAnasayfaBinding
import com.cemalcansanaral.bitirmetezi.entity.Yemekler
import com.cemalcansanaral.bitirmetezi.repo.YemeklerDaoRepository

class AnasayfaFragmentViewModel : ViewModel() {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    private val ydaor = YemeklerDaoRepository()

    init {
        yemekleriYukle()
        yemeklerListesi = ydaor.yemekleriGetir()
    }

    fun yemekleriYukle(){
        ydaor.tumYemekleriAl()
    }

    fun homePageRvAnim(tasarim : FragmentAnasayfaBinding){
        ydaor.homePageRvAnim(tasarim)
    }
}