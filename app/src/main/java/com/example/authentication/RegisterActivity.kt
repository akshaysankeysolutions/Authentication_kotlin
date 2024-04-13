package com.example.authentication

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.authentication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() , View.OnClickListener,View.OnFocusChangeListener,View.OnKeyListener{

    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding=ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.fullNameEt.onFocusChangeListener=this
        mBinding.emailEt.onFocusChangeListener=this
        mBinding.passwordEt.onFocusChangeListener=this
        mBinding.confirmPwdEt.onFocusChangeListener=this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateFullName() : Boolean
    {
        var errorMessage:String?= null
        val value :String=mBinding.fullNameEt.text.toString();

        if(value.isEmpty())
        {
            errorMessage="Full Name is required"
        }
        else if(value.length<3)
        {
            errorMessage="Full Name must be at least 3 characters";
        }
        else if(!value.matches(Regex("[a-zA-Z ]+"))) {
            errorMessage = "Full Name must contain only English letters"
        }

        if(errorMessage!=null)
        {
            mBinding.fullNameTil.apply {
                isErrorEnabled=true
                error=errorMessage
            }
        }
        return errorMessage==null
    }

    private fun validateEmail() :Boolean
    {
        var errorMessage:String?=null
        val value=mBinding.emailEt.text.toString();

        if(value.isEmpty())
        {
            errorMessage="Email is required"
        }
        else if(!value.matches(Regex("^[A-Za-z](.*)(@{1})(.{1,})(\\.)(.{1,})")))
        {
            errorMessage="Email address is invalid"
        }

        if(errorMessage!=null)
        {
            mBinding.emailTil.apply {
                isErrorEnabled=true
                error=errorMessage
            }
        }
        return errorMessage==null
    }


    private fun validatePassword() : Boolean
    {
        var errorMessage: String?=null
        val value=mBinding.passwordEt.text.toString()

        if(value.isEmpty())
        {
            errorMessage="Password is required"
        }
        else if(!value.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")))
        {
            errorMessage="Password must be of 8 charachters,with atleast one alphabetical charachter and atleast one digit "
        }

        if(errorMessage!=null)
        {
            mBinding.passwordTil.apply {
                isErrorEnabled=true
                error=errorMessage
            }
        }

        return errorMessage==null
    }

    private fun validateConfirmPassword() : Boolean
    {
        var errorMessage: String?=null
        val value=mBinding.confirmPwdEt.text.toString()

        if(value.isEmpty())
        {
            errorMessage="Confirm Password is required"
        }
        else if(!value.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")))
        {
            errorMessage="Confirm Password must be of 8 charachters,with atleast one alphabetical charachter and atleast one digit "
        }

        if(errorMessage!=null)
        {
            mBinding.confirmPwdTil.apply {
                isErrorEnabled=true
                error=errorMessage
            }
        }

        return errorMessage==null
    }

    private fun comparePassword() :Boolean
    {
        var errorMessage: String?=null
        val password=mBinding.passwordEt.text.toString();
        val confirmPassword=mBinding.confirmPwdEt.text.toString();

        if(password !=confirmPassword)
        {
            errorMessage="Confirm password doesnt match with password"
        }

        if(errorMessage!=null)
        {
            mBinding.confirmPwdTil.apply {
                isErrorEnabled=true
                error=errorMessage
            }
        }

        return errorMessage==null
    }

    override fun onClick(view: View?) {
//        TODO("Not yet implemented")
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(view!=null) {
            when (view.id) {
                R.id.fullNameEt -> {
                    if (hasFocus) {
                        if (mBinding.fullNameTil.isErrorEnabled) {
                            mBinding.fullNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateFullName()
                    }
                }

                R.id.emailEt -> {
                    if (hasFocus) {
                        if (mBinding.emailTil.isErrorEnabled) {
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }

                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (mBinding.passwordTil.isErrorEnabled) {
                            mBinding.passwordTil.isErrorEnabled = false
                        }
                    } else {
                        if(validatePassword() && mBinding.confirmPwdEt.text!!.isNotEmpty() && validateConfirmPassword() && comparePassword())
                        {
                            if(mBinding.confirmPwdTil.isErrorEnabled)
                            {
                                mBinding.confirmPwdTil.isErrorEnabled=false;
                            }
                            mBinding.confirmPwdTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24);
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN));
                            }
                        }
                    }
                }

                R.id.confirmPwdEt -> {
                    if (hasFocus) {
                        if (mBinding.confirmPwdTil.isErrorEnabled) {
                            mBinding.confirmPwdTil.isErrorEnabled = false
                        }
                    } else {
                        validateConfirmPassword()
                        if(validateConfirmPassword() && validatePassword() && comparePassword())
                        {
                            if(mBinding.passwordTil.isErrorEnabled)
                            {
                                mBinding.passwordTil.isErrorEnabled=false;
                            }
                            mBinding.confirmPwdTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24);
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN));
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false;
    }
}