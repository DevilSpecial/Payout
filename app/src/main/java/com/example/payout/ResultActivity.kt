package com.example.payout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.payout.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val model = bundle?.getString("Model")
        model?.let {
            binding.tvModel.text = "Model - "+ it
        }
        if(model?.isEmpty()!!){
            binding.tvModel.text = "Model - Not Specified"
        }
        val channel = bundle?.getString("Channel")
        val series= bundle?.getString("Series")
        binding.btnHome.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        if(channel=="channel"){
            binding.tvChannel.visibility=View.GONE
        }else binding.tvChannel.text = "Channel : $channel"
        if(series=="Velocity" || series=="UpFront"){
            binding.textView7.visibility = View.GONE
            binding.tvAccelerator.visibility = View.GONE
            binding.textView8.visibility = View.GONE
            binding.tvStrategy.visibility = View.GONE
            binding.textView9.visibility = View.GONE
            binding.tvInstore.visibility = View.GONE
            binding.textView10.visibility = View.GONE
            binding.tvPrime.visibility = View.GONE
        }
        val gstPaid = bundle?.getString("GST_PAID")?.toIntOrNull() ?: 0
        val sellThru = bundle?.getString("SELL_THRU")?.toIntOrNull() ?: 0
        val bagPrice = bundle?.getString("BAG_PRICE")?.toIntOrNull() ?: 0
        val sellOut = bundle?.getString("SELL_OUT")?.toIntOrNull() ?: 0
        val upFront = bundle?.getDouble("UpFront") ?: 0.0
        val inCountry = bundle?.getDouble("InCountry") ?: 0.0
        val accl = bundle?.getDouble("Accl") ?: 0.00
        val strategic = bundle?.getDouble("Strategic") ?: 0.00
        val inStore = bundle?.getDouble("InStore") ?: 0.00
        val parivartan = bundle?.getDouble("Parivartan") ?: 0.00

        var pretaxPrice = (gstPaid - sellThru) / 1.18
        var upFrontLessPrice = pretaxPrice - (pretaxPrice * upFront) - bagPrice
        var ndpPrice = upFrontLessPrice - (upFrontLessPrice * 0.0099)

        var pretaxPriceRounded = Math.round(pretaxPrice).toInt()
        var upFrontLessPriceRounded = Math.round(upFrontLessPrice).toInt()
        var ndpPriceRounded = Math.round(ndpPrice).toInt()

        var inStorePrice = Math.round(ndpPrice * inStore).toInt()
        var inCountryPrice = Math.round(ndpPrice * inCountry).toInt()
        var acclPrice = Math.round(ndpPrice * accl).toInt()
        var strategicPrice = Math.round(ndpPrice * strategic).toInt()
        var parivartanPrice = Math.round(ndpPrice * parivartan).toInt()

        if(series=="UpFront"){
            upFrontLessPrice=pretaxPrice-bagPrice
            upFrontLessPriceRounded = Math.round(upFrontLessPrice).toInt()
        }
        val nlc = (upFrontLessPrice + bagPrice - (inStorePrice + inCountryPrice + acclPrice + parivartanPrice + strategicPrice)) * 1.18 - sellOut
        val nlcPrice = Math.round(nlc).toInt()

        binding.tvGst.text = gstPaid.toString()
        binding.tvPretax.text = pretaxPriceRounded.toString()
        binding.tvSellthru.text = sellThru.toString()
        binding.tvNdp.text = ndpPriceRounded.toString()
        binding.tvBag.text = bagPrice.toString()
        binding.tvUpfrontLess.text = upFrontLessPriceRounded.toString()
        binding.tvAccelerator.text = acclPrice.toString()
        binding.tvStrategy.text = strategicPrice.toString()
        binding.tvInstore.text = inStorePrice.toString()
        binding.tvPrime.text = parivartanPrice.toString()
        binding.tvSellout.text = sellOut.toString()
        binding.tvNlc.text = nlcPrice.toString()

        binding.btnCalc.setOnClickListener {
            if(series == "Velocity") {
                val intent = Intent(this, VelocityActivity::class.java)
                startActivity(intent)
            } else if(series == "Core") {
                val intent = Intent(this, Core_series_activity::class.java)
                startActivity(intent)
            } else if(series == "Premium") {
                val intent = Intent(this, PremiumSeriesActivity::class.java)
                startActivity(intent)
            } else if(series == "SuperPremium") {
                val intent = Intent(this, SuperPremiumActivity::class.java)
                startActivity(intent)
            } else if(series == "UpFront") {
                val intent = Intent(this, UpfrontT2Activity::class.java)
                startActivity(intent)
            }
        }

    }
}