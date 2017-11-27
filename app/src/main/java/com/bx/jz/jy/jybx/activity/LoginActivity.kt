package com.bx.jz.jy.jybx.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import com.bx.jz.jy.jybx.ConstantPool
import com.bx.jz.jy.jybx.R
import com.bx.jz.jy.jybx.base.BaseActivity
import com.bx.jz.jy.jybx.utils.DecorViewUtils
import com.bx.jz.jy.jybx.utils.OkHttpUtils
import com.bx.jz.jy.jybx.utils.T
import com.jaeger.library.StatusBarUtil

/**
 * Created by Administrator on 2017/11/23 0023.
 */
class LoginActivity : BaseActivity() {

    private lateinit var tv_title : TextView
    private lateinit var et_phone : EditText
    private lateinit var tv_get_code : TextView
    private lateinit var et_code : EditText
    private lateinit var btn_login : Button
    private lateinit var base_ll : LinearLayout
    private lateinit var img_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DecorViewUtils.getDarkDecorView(this)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        tv_title = findViewById(R.id.tv_title)
        base_ll = findViewById(R.id.base_ll)
        img_back = findViewById(R.id.img_back)

        et_phone = findViewById(R.id.et_phone)
        et_code = findViewById(R.id.et_code)
        tv_get_code = findViewById(R.id.tv_get_code)
        btn_login = findViewById(R.id.btn_login)

        tv_title.text = "登录"
        tv_title.visibility = VISIBLE
        base_ll.visibility = GONE
        img_back.visibility = GONE

        tv_get_code.setOnClickListener {
            getNewCode()
        }
        btn_login.setOnClickListener {
            startActivity(Intent(LoginActivity@this,MainActivity::class.java))
            LoginActivity@this.finish()
        }
    }

    private fun getNewCode() {
//        OkHttpUtils?.getInstance()?.postForMapAsynchronization(ConstantPool.GETNEWCODE,newCode(),OkHttpUtils.RequestCallBack<>)

    }

    private fun newCode(): MutableMap<String, Any>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null)
    }

    override fun filterViewByIds(): Array<View>? {
        return arrayOf(et_phone,et_code)
    }

    override fun hideSoftByEditViewIds(): IntArray {
        return intArrayOf(R.id.et_phone,R.id.et_code)
    }
}