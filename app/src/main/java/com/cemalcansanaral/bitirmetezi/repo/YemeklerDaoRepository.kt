package com.cemalcansanaral.bitirmetezi.repo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cemalcansanaral.bitirmetezi.R
import com.cemalcansanaral.bitirmetezi.databinding.FragmentAnasayfaBinding
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekDetayBinding
import com.cemalcansanaral.bitirmetezi.databinding.FragmentYemekSepetBinding
import com.cemalcansanaral.bitirmetezi.entity.YemekSepet
import com.cemalcansanaral.bitirmetezi.entity.YemekSepetCevap
import com.cemalcansanaral.bitirmetezi.entity.Yemekler
import com.cemalcansanaral.bitirmetezi.entity.YemeklerCevap
import com.cemalcansanaral.bitirmetezi.fragment.AnasayfaFragment
import com.cemalcansanaral.bitirmetezi.fragment.YemekDetayFragment
import com.cemalcansanaral.bitirmetezi.retrofit.ApiUtils
import com.cemalcansanaral.bitirmetezi.retrofit.YemeklerDaoInterface
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemeklerDaoRepository {
    private val yemeklerListesi : MutableLiveData<List<Yemekler>>
    private val yemeklerDaoInterface : YemeklerDaoInterface
    private val sepetYemeklerListesi : MutableLiveData<List<YemekSepet>>

    init {
        yemeklerDaoInterface = ApiUtils.getYemeklerDaoInterface()
        yemeklerListesi = MutableLiveData()
        sepetYemeklerListesi = MutableLiveData()
    }

    fun yemekleriGetir() : MutableLiveData<List<Yemekler>> {
        return yemeklerListesi
    }

    fun tumYemekleriAl() {
        yemeklerDaoInterface.tumYemekler().enqueue(object : Callback<YemeklerCevap>{
            override fun onResponse(call: Call<YemeklerCevap>, response: Response<YemeklerCevap>) {
                val liste = response.body().yemekler
                yemeklerListesi.value = liste
            }

            override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {}
        })
    }

    fun sepetYemekleriGetir() : MutableLiveData<List<YemekSepet>>{
        return  sepetYemeklerListesi
    }

    fun tumSepetYemekleriAl() {
        yemeklerDaoInterface.sepetYemekler("cemal_can_sanaral").enqueue(object : Callback<YemekSepetCevap>{
            override fun onResponse(call: Call<YemekSepetCevap>, response: Response<YemekSepetCevap>
            ) {
                val liste = response.body().sepet_yemekler
                sepetYemeklerListesi.value = liste
            }

            override fun onFailure(call: Call<YemekSepetCevap>, t: Throwable) {}
        })
    }

    fun sepeteEkle(sepet_yemek_id : Int,
                   yemek_adi : String,
                   yemek_resim_adi : String,
                   yemek_fiyat : Int,
                   yemek_siparis_adet : Int,
                   kullanici_adi : String) {
        yemeklerDaoInterface.sepeteYemekEkle(sepet_yemek_id,yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi).enqueue(object : Callback<YemekSepetCevap>{
            override fun onResponse(call: Call<YemekSepetCevap>, response: Response<YemekSepetCevap>) {
                if (response.isSuccessful) {
                    if (response.body().success == 1){
                        Log.e("Mesaj","${response.body().success}")
                    }
                }
            }
            override fun onFailure(call: Call<YemekSepetCevap>, t: Throwable) {}
        })
    }

    fun sepettenYemekSilme(yemek_id : Int, kullanici_adi: String){
        yemeklerDaoInterface.sepettenYemekSil(yemek_id, kullanici_adi).enqueue(object : Callback<YemekSepetCevap>{
            override fun onResponse(call: Call<YemekSepetCevap>, response: Response<YemekSepetCevap>) {
                tumSepetYemekleriAl()
            }
            override fun onFailure(call: Call<YemekSepetCevap>, t: Throwable) {}
        })
    }

    fun yemekAciklama(yemek_adi: String, tasarim : FragmentYemekDetayBinding){
        when (yemek_adi) {
            "Ayran" -> tasarim.textViewYemekAciklama.text = "Do??al ve sa??l??kl?? S??ta?? Ayran benzersiz k??vam?? ve lezzeti ile 7???den 70???e herkesin tercihi! S??ta?? Ayran i??tik??e ferahlayacak ve kendinizi yenilenmi?? hissedeceksiniz. ??ster evde, ister d????ar??da; yemeklerin yan??nda veya at????t??rmal??klarla beraber g??n??n herhangi bir saatinde keyifle t??ketece??iniz S??ta?? Ayran hem sa??l??kl?? hem de ??ok lezzetli!"
            "Baklava" -> tasarim.textViewYemekAciklama.text = "T??rk mutfa????n??n, as??rlardan bu yana gelen en sevilen lezzetlerinden baklavada, en ??ok tercih edilen ??e??itlerden biri kare baklavad??r. ??zel kare baklavalar??n sundu??u e??siz lezzeti, y??resel baklava haz??rlama tekni??i ile sunan MyDesire ayr??cal?????? ile tatmak, baklavan??n t??m malzemelerinde do??all?????? ve kaliteyi hissetmektir."
            "Fanta" -> tasarim.textViewYemekAciklama.text = "C vitaminli Fanta C ile hem lezzetli hem de ferahlatan bir mola ver.\n" +
                    "\n" +
                    "Hemen ??r??n?? sepetine ekle!\n" +
                    "\n" +
                    "??? Portakal aromal?? gazoz\n" +
                    "\n" +
                    "??? 250 ML\n" +
                    "\n" +
                    "Kap Bi' Fanta E??lence Eve Gelsin"
            "Izgara Somon" -> tasarim.textViewYemekAciklama.text = "Omega-3 ya??lar?? ve A vitamini gibi vitamin ve mineraller a????s??ndan zengindir. Somon bal?????? k??l????ks??z, etli ve ya??l?? yap??s??yla hem doyurucu hem de besleyici bir deniz mahsul??d??r."
            "Izgara Tavuk" -> tasarim.textViewYemekAciklama.text = "Sporcu beslenme d??zeninde ve diyet listelerinde ??nemli bir yeri olan ??zgara tavuk MyDesire kalitesiyle aya????n??za kadar gelmektedir."
            "Kaday??f" -> tasarim.textViewYemekAciklama.text = "Vazge??ilmezlerimiz aras??nda olan kaday??f tatl??s??n?? birde b??yle deneyin. ??zenle d????enmi?? kaday??flar??n aras??na bolca koydu??umuz antep f??st?????? ile lezzetini artt??rd??????m??z kaday??f tatl??s??n?? hemen denemelisiniz."
            "Kahve" -> tasarim.textViewYemekAciklama.text = "Kendine has tad??, p??r??zs??z k??p??????, e??siz kokusu, pi??irili?? y??ntemi ve sunumuyla ??zg??n bir kimli??i ve gelene??i olan T??rk kahvesi"
            "K??fte" -> tasarim.textViewYemekAciklama.text = "MyDesire ile k??fte sipari??i vermek ise ayr?? bir g??zellik. ??ster ????le yemeklerinizde, ister k??sa bir at????t??rmal??k i??in isterseniz de zengin bir ak??am yeme??i sofras?? kurmak i??in verece??iniz k??fte sipari??i size yetip de artacakt??r bile. Hele ki yan??nda ayran veya ????yle kutu i??eceklerden eklerseniz keyfinizden ge??ilmez."
            "Lazanya" -> tasarim.textViewYemekAciklama.text = "Lazanya, makarna hamuruyla yap??lan, plaka halinde paketlenen, genellikle bolonez sosu ile birlikte f??r??nda pi??irilen ??talyan mutfa????na ait nefis bir lezzettir."
            "Makarna" -> tasarim.textViewYemekAciklama.text = "Makarna (??talyanca: Pasta), geleneksel ??talyan mutfa????n??n temel besini ve ulusal yeme??idir. ??lk defa 1154 y??l??nda Sicilya'da ortaya ????km????t??r. Genellikle irmik veya durum bu??day??ndan elde edilmi?? una yumurta kar????t??r??larak haz??rlanm???? t??rl?? bi??imlerdeki kuru hamur ve bu hamurdan yap??l??r[3] T??rk??eye ??talyanca maccherone s??zc??????nden ge??mi??tir."
            "Pizza" -> tasarim.textViewYemekAciklama.text = "Her zevke hitap eden leziz pizzalar, tavuklar, tatl??lar ve ekstra lezzetler MyDesire'da"
            "Su" -> tasarim.textViewYemekAciklama.text = "T??rkiye???nin en ??ok kayna??a sahip markas?? olman??n getirdi??i b??y??k sorumlulu??un fark??nday??z. Kaynaklar??m??zdan ????kan tertemiz suyu,y??ksek hijyen standartlar??yla T??rkiye???nin d??rt bir yan??na ula??t??r??yoruz."
            "S??tla??" -> tasarim.textViewYemekAciklama.text = "Evde yapt??????n??z S??tlac?? nas??l yapars??n??z? Pirin??, S??t, Pirin?? Unu ve ??eker. ????te MyDesire S??tlac??n??n i??inde de tam olarak ve sadece bunlar var! Gluten , Glikoz-Fruktoz ??urubu ve Koruyucu katk?? i??ermez."
            "Tiramisu" -> tasarim.textViewYemekAciklama.text = "Kahve ile ??slat??lan keki, ??zerine serpilen kakaosu ve i??erisinde labne peyniri kar??????m?? kremas?? ile damaklar??n??zda vazge??ilmez bir lezzet olacak. Di??er tatl??lara g??re daha hafif ve serinletici olan tiramisu hen??z tatmayan herkesin daha ??nce tatmad??????na pi??manl??k ya??ayaca???? enfes bir tatl??. "
            else -> tasarim.textViewYemekAciklama.text = "A????klama en k??sa s??rede eklenecektir..."
        }
    }

    private fun display(number : Int, tasarim : FragmentYemekDetayBinding) {
        tasarim.textViewNumber.text = "$number"
    }

    fun increaseInteger(tasarim : FragmentYemekDetayBinding) {
        display(tasarim.textViewNumber.text.toString().toInt() + 1,tasarim)
    }

    fun decreaseInteger(tasarim : FragmentYemekDetayBinding) {
        display(tasarim.textViewNumber.text.toString().toInt() - 1,tasarim)
        if (tasarim.textViewNumber.text == "0"){
            tasarim.textViewNumber.text = "1"
        }
    }

    fun addRemoveButton(tasarim: FragmentYemekDetayBinding){
        tasarim.imageButtonAdd.setOnClickListener { increaseInteger(tasarim) }
        tasarim.imageButtonRemove.setOnClickListener { decreaseInteger(tasarim) }
    }

    fun homePageRvAnim(tasarim : FragmentAnasayfaBinding){
        val rvanim = AnimationUtils.loadAnimation(tasarim.yemekRecyclerView.context, R.anim.recyclerview_animation)
        tasarim.yemekRecyclerView.startAnimation(rvanim)
    }

    fun yemekSepetRvAnim(tasarim : FragmentYemekSepetBinding){
        val rvanim = AnimationUtils.loadAnimation(tasarim.recyclerView.context, R.anim.recyclerview_animation)
        tasarim.recyclerView.startAnimation(rvanim)
    }

}