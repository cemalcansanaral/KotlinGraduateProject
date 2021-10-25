package com.cemalcansanaral.bitirmetezi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekDetayBinding
import com.cemalcansanaral.bitirmetezi.databinding.YemekCardTasarimBinding
import com.cemalcansanaral.bitirmetezi.entity.Yemekler
import com.cemalcansanaral.bitirmetezi.fragment.AnasayfaFragmentDirections
import com.cemalcansanaral.bitirmetezi.fragment.YemekDetayFragment
import com.squareup.picasso.Picasso

class YemeklerAdapter(var mContext: Context, var yemeklerListe: List<Yemekler>) :
    RecyclerView.Adapter<YemeklerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(yemeklerCardTasarimBinding: YemekCardTasarimBinding) :
        RecyclerView.ViewHolder(yemeklerCardTasarimBinding.root) {
        var yemeklerCardTasarimBinding: YemekCardTasarimBinding

        init {
            this.yemeklerCardTasarimBinding = yemeklerCardTasarimBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = YemekCardTasarimBinding.inflate(layoutInflater,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return yemeklerListe.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemeklerListe.get(position)
        val t = holder.yemeklerCardTasarimBinding
        t.yemekNesnesi = yemek

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Picasso.get().load(url).into(t.imageViewYemekResim)

        t.yemekCard.setOnClickListener {
            val gecis = AnasayfaFragmentDirections.yemekDetayGecis(yemek)
            Navigation.findNavController(it).navigate(gecis)
        }
    }
}