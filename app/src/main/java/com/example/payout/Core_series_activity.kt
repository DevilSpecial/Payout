package com.example.payout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.payout.databinding.ActivityCoreSeriesBinding

class Core_series_activity : AppCompatActivity() {
    lateinit var binding : ActivityCoreSeriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCoreSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val channel = arrayOf("HPW Power","HPW Synergy","MBO","Power CDR")
        val bb = ArrayAdapter(this,R.layout.custom_spinner_bg,channel)
        bb.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        binding.etChannel.adapter=bb
        val bag = arrayOf("No Bag","Wings 110","Entry 210","Trendsetter 310","Hyperx Knight","Creator")
        val aa = ArrayAdapter(this, R.layout.bag_spinner_bg, bag)
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        binding.etBag.adapter=aa
        binding.btnCalc.setOnClickListener {
            validateFields()
        }

    }
    private fun validateFields() {
        val itemPrices = hashMapOf(
            "No Bag" to 0,
            "Wings 110" to 531,
            "Entry 210" to 889,
            "Trendsetter 310" to 1530,
            "Hyperx Knight" to 3533,
            "Creator" to 2399
        )
        val channel = binding.etChannel.selectedItem.toString()
        val model = binding.etModel.text.toString()
        val gstPaid = binding.etGst.text.toString()
        val sellThru = binding.etSellThru.text.toString()
        val selectedBag = binding.etBag.selectedItem.toString()
        val sellOut = binding.etSellOut.text.toString()
        val bagPrice = itemPrices[selectedBag].toString()
        val allFieldsFilled = gstPaid.isNotEmpty() && sellThru.isNotEmpty() && sellOut.isNotEmpty()

        if(selectedBag=="Bag"){
            binding.tvError.text = "No Bag Selected"
            binding.tvError.visibility = View.VISIBLE
            fadeInView(binding.tvError)
            Handler(Looper.getMainLooper()).postDelayed({
                fadeOutView(binding.tvError)
            }, 1500)}

        if (allFieldsFilled) {
            val intent = Intent(this, ResultActivity::class.java) // Replace with your next activity

            // Create a Bundle and add data to it
            val bundle = Bundle().apply {
                putString("Series","Core")
                putString("Model",model)
                putString("GST_PAID", gstPaid)
                putString("SELL_THRU", sellThru)
                putString("BAG_PRICE", bagPrice)
                putString("SELL_OUT", sellOut)
            }
            bundle.putString("Channel",channel)
            if(channel=="HPW Power"){
               bundle.putDouble("UpFront",0.06)
                bundle.putDouble("InCountry",0.015)
                bundle.putDouble("Accl",0.005)
                bundle.putDouble("Strategic",0.005)
                bundle.putDouble("InStore",0.003)
                bundle.putDouble("Parivartan",0.005)
            }else if(channel=="HPW Synergy"){
                bundle.putDouble("UpFront",0.06)
                bundle.putDouble("InCountry",0.015)
                bundle.putDouble("Accl",0.005)
                bundle.putDouble("Strategic",0.0)
                bundle.putDouble("InStore",0.0)
                bundle.putDouble("Parivartan",0.005)
            }else if(channel=="MBO"){
                bundle.putDouble("UpFront",0.06)
                bundle.putDouble("InCountry",0.015)
                bundle.putDouble("Accl",0.005)
                bundle.putDouble("Strategic",0.0)
                bundle.putDouble("InStore",0.0)
                bundle.putDouble("Parivartan",0.0)
            }else{
                bundle.putDouble("UpFront",0.06)
                bundle.putDouble("InCountry",0.015)
                bundle.putDouble("Accl",0.005)
                bundle.putDouble("Strategic",0.005)
                bundle.putDouble("InStore",0.0025)
                bundle.putDouble("Parivartan",0.0)
            }

            intent.putExtras(bundle)

            // Start the next Activity
            startActivity(intent)
        } else {
            binding.tvError.text = "**All Fields are required"
            binding.tvError.visibility = View.VISIBLE
            fadeInView(binding.tvError)
            Handler(Looper.getMainLooper()).postDelayed({
                fadeOutView(binding.tvError)
            }, 1500)
        }
    }

    fun fadeInView(view: View, duration: Long = 500) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(null)
    }

    fun fadeOutView(view: View, duration: Long = 500) {
        view.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.INVISIBLE
                }
            })
    }
}