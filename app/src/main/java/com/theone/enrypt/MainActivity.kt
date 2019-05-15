package com.theone.enrypt

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.theone.encrypt.Encrypt
import com.theone.encrypt.strategy.AesStrategy
import com.theone.encrypt.strategy.Des3Strategy
import com.theone.encrypt.strategy.IEncrypt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var iEncrypt:IEncrypt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testEncrypt()
    }

    @SuppressLint("SetTextI18n")
    private fun testEncrypt() {
        val origin = origin.text.toString()
        iEncrypt = Encrypt.with(this)
        val encode = iEncrypt.encrypt(origin)
        val decode = iEncrypt.decode(encode)
        encrypt.text = getString(R.string.encode_tip)+encode
        decrypt.text = getString(R.string.decode_tip)+decode

        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position){
                    0-> iEncrypt = Encrypt.with(this@MainActivity)
                    1-> iEncrypt = Encrypt.with(AesStrategy(this@MainActivity))
                    2-> iEncrypt = Encrypt.with(Des3Strategy(this@MainActivity))
                }

                val encode = iEncrypt.encrypt(origin)
                val decode = iEncrypt.decode(encode)
                encrypt.text = resources.getStringArray(R.array.encrypt)[position]+"加密:"+encode
                decrypt.text = resources.getStringArray(R.array.encrypt)[position]+"解密:"+decode
            }

        }
    }

}
