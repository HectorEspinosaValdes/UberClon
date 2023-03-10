package com.ubertron.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.ubertron.databinding.ActivityRegisterBinding
import com.ubertron.models.Client
import com.ubertron.provider.AuthProvider
import com.ubertron.provider.ClientProvider

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authProvider = AuthProvider()
    private val clientProvider = ClientProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.btnGoToLogin.setOnClickListener{ goToLogin() }
        binding.btnRegister.setOnClickListener { register() }

    }

    private fun register (){
        val name = binding.textFieldName.text.toString()
        val lastName = binding.textFieldApellido.text.toString()
        val phone = binding.textFieldPhone.text.toString()
        val email = binding.textFieldEmail.text.toString()
        val password = binding.textFieldPassword.text.toString()
        val confirmPassword = binding.textFieldConfirmPassword.text.toString()

        if (isValid(name, lastName, phone, email, password, confirmPassword)){

            authProvider.register(email, password).addOnCompleteListener{
                if (it.isSuccessful){
                    val client = Client(
                        id = authProvider.getId(),
                        name = name,
                        lastname = lastName,
                        phone = phone,
                        email = email)

                    clientProvider.create(client).addOnCompleteListener(){
                        if (it.isSuccessful){
                            Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@RegisterActivity, "Hubo un error almacenando los datos ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Registro fallido ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
                    Log.d("FIREBASE", "ERROR ${it.exception.toString()}")
                }
            }

        }
    }

    private fun isValid(name: String,
                        lastName: String,
                        phone: String,
                        email: String,
                        password: String,
                        confirmPassword: String):Boolean
    {

        if (name.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (lastName.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu apellido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu t??lefono", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu contrase??a", Toast.LENGTH_SHORT).show()
            return false
        }
        if (confirmPassword.isEmpty()){
            Toast.makeText(this, "Debes confirmar tu contrase??a", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password != confirmPassword){
            Toast.makeText(this, "Las contrase??as deben coincidir", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length < 6){
            Toast.makeText(this, "La contrase??a debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}