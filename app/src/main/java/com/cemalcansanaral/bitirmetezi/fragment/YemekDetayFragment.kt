package com.cemalcansanaral.bitirmetezi.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransitionImpl
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.cemalcansanaral.bitirmetezi.R
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekDetayBinding
import com.cemalcansanaral.bitirmetezi.entity.Yemekler
import com.cemalcansanaral.bitirmetezi.viewmodel.YemekDetayFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class YemekDetayFragment : Fragment() {
    private lateinit var tasarim : FragmentYemekDetayBinding
    private lateinit var viewModel : YemekDetayFragmentViewModel
    val kullanici_adi = "cemal_can_sanaral"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_yemek_detay, container, false)

        val bundle : YemekDetayFragmentArgs by navArgs()
        val gelenYemek = bundle.nesne

        tasarim.yemekDetayFragment = this

        tasarim.yemekNesnesi = gelenYemek
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
        Picasso.get().load(url).into(tasarim.imageViewDetayResim)

        viewModel.yemekAciklamalariAl(gelenYemek.yemek_adi,tasarim)
        viewModel.imageButtonAddRemove(tasarim)

        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : YemekDetayFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun buttonSepeteEkle(yemekNesne : Yemekler, adet : String, it : View) {
            var snackbar = Snackbar.make(it,"${adet} tane ${yemekNesne.yemek_adi} sepete eklensin mi?",Snackbar.LENGTH_LONG)
                snackbar.setAction("Evet"){
                    viewModel.sepetEkle(yemekNesne.yemek_id,yemekNesne.yemek_adi,yemekNesne.yemek_resim_adi,yemekNesne.yemek_fiyat,adet.toInt(),kullanici_adi)
                    var snackbar2 = Snackbar.make(it,"${adet} tane ${yemekNesne.yemek_adi} başarıyla eklendi...",Snackbar.LENGTH_SHORT)
                    snackbar2.view.background = resources.getDrawable(R.drawable.snackbar_background, null)
                    snackbar2.setAnchorView(tasarim.textViewNumber)
                    val textView2 = snackbar2.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView2.setTextColor(Color.WHITE)
                    textView2.textSize = 20f
                    snackbar2.show()
                }
        snackbar.view.background = resources.getDrawable(R.drawable.snackbar_background, null)
        snackbar.setAnchorView(tasarim.textViewNumber)
            snackbar.setActionTextColor(Color.WHITE)
            val textView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 20f
            snackbar.show()
    }
}
