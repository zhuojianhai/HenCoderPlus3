package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.CacheUtilsKtKt
import com.example.core.utils.Utils
import com.example.lesson.LessonActivity

class MainConvertKTActivity : AppCompatActivity(), View.OnClickListener {
    val  usernameKey:String = "username"
    val passwordKey:String = "password"

    lateinit var et_username:EditText
    lateinit var et_password:EditText
    lateinit var et_code:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_convert_ktactivity)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        et_code = findViewById(R.id.et_code)

        et_username.setText(CacheUtilsKtKt.get(usernameKey))
    }

    override fun onClick(v: View) {
        if (v is CodeView){
            v.updateCode()
        }else if (v is Button){
            login()
        }
    }
    private fun login(){
        val username = et_username?.text.toString()
        val password = et_password!!.text.toString()
        val code = et_code!!.text.toString()
        val user = User(username, password, code)
        if (verify(user)) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }

    }

    private fun verify(user:User):Boolean{
        if (user.username != null && user.username.length < 4) {
            Utils.toast("用户名不合法")
            return false
        }
        if (user.password != null && user.password.length < 4) {
            Utils.toast("密码不合法")
            return false
        }
        return true
    }
}