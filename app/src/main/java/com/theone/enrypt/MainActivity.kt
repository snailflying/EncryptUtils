package com.theone.enrypt

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.theone.encrypt.Encrypt
import com.theone.encrypt.strategy.AesRsaStrategy
import com.theone.encrypt.strategy.AesStrategy
import com.theone.encrypt.strategy.Des3Strategy
import com.theone.encrypt.strategy.IEncryptStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var iEncryptStrategy: IEncryptStrategy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testEncrypt()
    }

    @SuppressLint("SetTextI18n")
    private fun testEncrypt() {
        val origin = origin.text.toString()
        iEncryptStrategy = Encrypt.with(this)
        val encode = iEncryptStrategy.encrypt(origin)
        val decode = iEncryptStrategy.decrypt(encode)
        encrypt.text = getString(R.string.encode_tip) + encode
        decrypt.text = getString(R.string.decode_tip) + decode

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> iEncryptStrategy = Encrypt.with(this@MainActivity)
                    1 -> iEncryptStrategy = Encrypt.with(AesRsaStrategy(this@MainActivity))
                    2 -> iEncryptStrategy = Encrypt.with(AesStrategy(this@MainActivity))
                    3 -> iEncryptStrategy = Encrypt.with(Des3Strategy(this@MainActivity))
                }

                val encode = iEncryptStrategy.encrypt(origin)
                val decode = iEncryptStrategy.decrypt(encode)
                encrypt.text = resources.getStringArray(R.array.encrypt)[position] + "加密:" + encode
                decrypt.text = resources.getStringArray(R.array.encrypt)[position] + "解密:" + decode
            }

        }
    }

}
