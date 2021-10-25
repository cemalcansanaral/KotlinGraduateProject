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
            "Ayran" -> tasarim.textViewYemekAciklama.text = "Doğal ve sağlıklı Sütaş Ayran benzersiz kıvamı ve lezzeti ile 7’den 70’e herkesin tercihi! Sütaş Ayran içtikçe ferahlayacak ve kendinizi yenilenmiş hissedeceksiniz. İster evde, ister dışarıda; yemeklerin yanında veya atıştırmalıklarla beraber günün herhangi bir saatinde keyifle tüketeceğiniz Sütaş Ayran hem sağlıklı hem de çok lezzetli!"
            "Baklava" -> tasarim.textViewYemekAciklama.text = "Türk mutfağının, asırlardan bu yana gelen en sevilen lezzetlerinden baklavada, en çok tercih edilen çeşitlerden biri kare baklavadır. Özel kare baklavaların sunduğu eşsiz lezzeti, yöresel baklava hazırlama tekniği ile sunan MyDesire ayrıcalığı ile tatmak, baklavanın tüm malzemelerinde doğallığı ve kaliteyi hissetmektir."
            "Fanta" -> tasarim.textViewYemekAciklama.text = "C vitaminli Fanta C ile hem lezzetli hem de ferahlatan bir mola ver.\n" +
                    "\n" +
                    "Hemen ürünü sepetine ekle!\n" +
                    "\n" +
                    "• Portakal aromalı gazoz\n" +
                    "\n" +
                    "• 250 ML\n" +
                    "\n" +
                    "Kap Bi' Fanta Eğlence Eve Gelsin"
            "Izgara Somon" -> tasarim.textViewYemekAciklama.text = "Omega-3 yağları ve A vitamini gibi vitamin ve mineraller açısından zengindir. Somon balığı kılçıksız, etli ve yağlı yapısıyla hem doyurucu hem de besleyici bir deniz mahsulüdür."
            "Izgara Tavuk" -> tasarim.textViewYemekAciklama.text = "Sporcu beslenme düzeninde ve diyet listelerinde önemli bir yeri olan ızgara tavuk MyDesire kalitesiyle ayağınıza kadar gelmektedir."
            "Kadayıf" -> tasarim.textViewYemekAciklama.text = "Vazgeçilmezlerimiz arasında olan kadayıf tatlısını birde böyle deneyin. Özenle döşenmiş kadayıfların arasına bolca koyduğumuz antep fıstığı ile lezzetini arttırdığımız kadayıf tatlısını hemen denemelisiniz."
            "Kahve" -> tasarim.textViewYemekAciklama.text = "Kendine has tadı, pürüzsüz köpüğü, eşsiz kokusu, pişiriliş yöntemi ve sunumuyla özgün bir kimliği ve geleneği olan Türk kahvesi"
            "Köfte" -> tasarim.textViewYemekAciklama.text = "MyDesire ile köfte siparişi vermek ise ayrı bir güzellik. İster öğle yemeklerinizde, ister kısa bir atıştırmalık için isterseniz de zengin bir akşam yemeği sofrası kurmak için vereceğiniz köfte siparişi size yetip de artacaktır bile. Hele ki yanında ayran veya şöyle kutu içeceklerden eklerseniz keyfinizden geçilmez."
            "Lazanya" -> tasarim.textViewYemekAciklama.text = "Lazanya, makarna hamuruyla yapılan, plaka halinde paketlenen, genellikle bolonez sosu ile birlikte fırında pişirilen İtalyan mutfağına ait nefis bir lezzettir."
            "Makarna" -> tasarim.textViewYemekAciklama.text = "Makarna (İtalyanca: Pasta), geleneksel İtalyan mutfağının temel besini ve ulusal yemeğidir. İlk defa 1154 yılında Sicilya'da ortaya çıkmıştır. Genellikle irmik veya durum buğdayından elde edilmiş una yumurta karıştırılarak hazırlanmış türlü biçimlerdeki kuru hamur ve bu hamurdan yapılır[3] Türkçeye İtalyanca maccherone sözcüğünden geçmiştir."
            "Pizza" -> tasarim.textViewYemekAciklama.text = "Her zevke hitap eden leziz pizzalar, tavuklar, tatlılar ve ekstra lezzetler MyDesire'da"
            "Su" -> tasarim.textViewYemekAciklama.text = "Türkiye’nin en çok kaynağa sahip markası olmanın getirdiği büyük sorumluluğun farkındayız. Kaynaklarımızdan çıkan tertemiz suyu,yüksek hijyen standartlarıyla Türkiye’nin dört bir yanına ulaştırıyoruz."
            "Sütlaç" -> tasarim.textViewYemekAciklama.text = "Evde yaptığınız Sütlacı nasıl yaparsınız? Pirinç, Süt, Pirinç Unu ve Şeker. İşte MyDesire Sütlacının içinde de tam olarak ve sadece bunlar var! Gluten , Glikoz-Fruktoz şurubu ve Koruyucu katkı içermez."
            "Tiramisu" -> tasarim.textViewYemekAciklama.text = "Kahve ile ıslatılan keki, üzerine serpilen kakaosu ve içerisinde labne peyniri karışımı kreması ile damaklarınızda vazgeçilmez bir lezzet olacak. Diğer tatlılara göre daha hafif ve serinletici olan tiramisu henüz tatmayan herkesin daha önce tatmadığına pişmanlık yaşayacağı enfes bir tatlı. "
            else -> tasarim.textViewYemekAciklama.text = "Açıklama en kısa sürede eklenecektir..."
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