package com.cemalcansanaral.bitirmetezi.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cemalcansanaral.bitirmetezi.R
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekSepetBinding
import com.cemalcansanaral.bitirmetezi.databinding.YemekSepetCardTasarimBinding
import com.cemalcansanaral.bitirmetezi.entity.YemekSepet
import com.cemalcansanaral.bitirmetezi.viewmodel.YemekSepetFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class YemekSepetAdapter(var mContext : Context, var sepetYemekListesi : List<YemekSepet>, var viewModel: YemekSepetFragmentViewModel, var yemekSepetBinding: FragmentYemekSepetBinding)
    : RecyclerView.Adapter<YemekSepetAdapter.SepetCardTasarimTutucu>(){

    inner class SepetCardTasarimTutucu(sepetYemekCardTasarimBinding : YemekSepetCardTasarimBinding)
        : RecyclerView.ViewHolder(sepetYemekCardTasarimBinding.root){
        var cardTasarimBinding : YemekSepetCardTasarimBinding

        init {
            this.cardTasarimBinding = sepetYemekCardTasarimBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetCardTasarimTutucu {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = YemekSepetCardTasarimBinding.inflate(layoutInflater,parent,false)
        return SepetCardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: SepetCardTasarimTutucu, position: Int) {
        val sepetyemek = sepetYemekListesi.get(position)
        val t = holder.cardTasarimBinding
        t.yemekSepetNesnesi = sepetyemek

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetyemek.yemek_resim_adi}"
        Picasso.get().load(url).into(t.imageViewSepetYemekResim)

        t.imageViewSilResim.setOnClickListener {
            val snackbar = Snackbar.make(it,"${sepetyemek.yemek_adi} silinsin mi?",Snackbar.LENGTH_LONG)
                snackbar.setAction("Evet"){
                    viewModel.yemekSil(sepetyemek.sepet_yemek_id,sepetyemek.kullanici_adi)
                    val snackbar2 = Snackbar.make(it,"${sepetyemek.yemek_adi} başarıyla silindi...",Snackbar.LENGTH_SHORT)
                    snackbar2.view.setBackgroundResource(R.drawable.snackbar_background)
                    snackbar2.setAnchorView(yemekSepetBinding.textViewSepetToplamFiyat)
                    snackbar2.setBackgroundTint(Color.RED)
                    val textView2 = snackbar2.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView2.setTextColor(Color.WHITE)
                    textView2.textSize = 20f
                    snackbar2.show()
                }
            snackbar.view.setBackgroundResource(R.drawable.snackbar_background)
            snackbar.setAnchorView(yemekSepetBinding.textViewSepetToplamFiyat)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(Color.RED)
            val textView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 20f
            snackbar.show()
        }
    }


    override fun getItemCount(): Int {
        return sepetYemekListesi.size
    }
}
