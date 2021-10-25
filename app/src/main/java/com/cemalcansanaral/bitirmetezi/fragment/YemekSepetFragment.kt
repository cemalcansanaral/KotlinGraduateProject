package com.cemalcansanaral.bitirmetezi.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cemalcansanaral.bitirmetezi.R
import com.cemalcansanaral.bitirmetezi.adapter.YemekSepetAdapter
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekSepetBinding
import com.cemalcansanaral.bitirmetezi.databinding.YemekSepetCardTasarimBinding
import com.cemalcansanaral.bitirmetezi.entity.YemekSepet
import com.cemalcansanaral.bitirmetezi.repo.YemeklerDaoRepository
import com.cemalcansanaral.bitirmetezi.viewmodel.YemekSepetFragmentViewModel

class YemekSepetFragment : Fragment() {

    private lateinit var tasarim : FragmentYemekSepetBinding
    private lateinit var viewModel : YemekSepetFragmentViewModel
    private lateinit var adapter : YemekSepetAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_yemek_sepet, container, false)
        tasarim.fragmentYemekSepet = this

        viewModel.sepetYemekRvAnim(tasarim)

        tasarim.textViewSepetToplamFiyat.visibility = View.INVISIBLE

        viewModel.sepetYemeklerListesi.observe(viewLifecycleOwner, { sepetYemeklerListesi ->
            var sonuc = 0
            sepetYemeklerListesi.map {
                sonuc += it.yemek_fiyat * it.yemek_siparis_adet
            }
            if (sepetYemeklerListesi.size > 0){
                tasarim.textViewSepetBos.visibility = View.INVISIBLE
                tasarim.textViewSepetToplamFiyat.visibility = View.VISIBLE
            }
            tasarim.textViewSepetToplamFiyat.text = "Sepet Tutarı: ${sonuc.toString()} ₺"
            adapter = YemekSepetAdapter(requireContext(),sepetYemeklerListesi, viewModel,tasarim)
            tasarim.yemekSepetAdapter = adapter
        })

        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : YemekSepetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

}