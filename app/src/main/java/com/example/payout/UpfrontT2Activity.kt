package com.example.payout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.payout.databinding.ActivityUpfrontT2Binding

class UpfrontT2Activity : AppCompatActivity() {
    lateinit var binding: ActivityUpfrontT2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpfrontT2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val bag = arrayOf("No Bag","Wings 110","Entry 210","Trendsetter 310","Hyperx Knight","Creator")
        val aa = ArrayAdapter(this, R.layout.bag_spinner_bg, bag)
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        binding.etBag.adapter=aa
        binding.btnCalc.setOnClickListener {
            validateFields()
        }}
        private fun validateFields() {
            val itemPrices = hashMapOf(
                "No Bag" to 0,
                "Wings 110" to 531,
                "Entry 210" to 889,
                "Trendsetter 310" to 1530,
                "Hyperx Knight" to 3533,
                "Creator" to 2399
            )

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
                    putString("Channel","channel")
                    putString("Series","UpFront")
                    putString("Model",model)
                    putString("GST_PAID", gstPaid)
                    putString("SELL_THRU", sellThru)
                    putString("BAG_PRICE", bagPrice)
                    putString("SELL_OUT", sellOut)
                    putDouble("UpFront",0.0)
                    putDouble("InCountry",0.015)
                    putDouble("Accl",0.00)
                    putDouble("Strategic",0.00)
                    putDouble("InStore",0.00)
                    putDouble("Parivartan",0.00)
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
