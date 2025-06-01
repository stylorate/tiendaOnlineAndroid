package com.example.tiendaonline.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaonline.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class LoginActivity : AppCompatActivity() {

    private lateinit var btnGoogle: Button
    private lateinit var btnIngresar: Button
    private lateinit var btnRegistrar: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)
        val mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnIngresar = findViewById<Button>(R.id.btn_ingresar)

        btnIngresar.setOnClickListener {
            val email = findViewById<TextView>(R.id.edt_email_login)
            val password = findViewById<TextView>(R.id.edt_pass_login)

            if (email == null || email.text.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese su correo", Toast.LENGTH_SHORT).show()
            }
            if (password == null || password.text.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show()
            }
//            val intent = Intent(this, ProductsActivity::class.java)
//            startActivity(intent)

            mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        Log.d("Firebase", "Sesión iniciada: " + user!!.email)
                        val intent = Intent(this, ProductsActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e("Firebase", "Error al iniciar sesión: ", task.exception)
                        Toast.makeText(
                            this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        btnRegistrar = findViewById<Button>(R.id.btn_registrarse)
        btnRegistrar.setOnClickListener {
            val email = findViewById<TextView>(R.id.edt_email_login)
            val password = findViewById<TextView>(R.id.edt_pass_login)

            if (email == null || email.text.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese su correo", Toast.LENGTH_SHORT).show()
            }
            if (password == null || password.text.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show()
            }

            mAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth.currentUser
//                        updateUI(user)
                        val intent = Intent(this, ProductsActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                        updateUI(null)
                        val e = task.exception
                        when (e) {
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    this,
                                    "Este correo ya está registrado.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(
                                    this,
                                    "La contraseña es muy débil.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(
                                    this,
                                    "Correo electrónico inválido.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    this,
                                    "Error: ${e?.localizedMessage}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        }

        btnGoogle = findViewById<Button>(R.id.btn_google)

        btnGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            Log.w(TAG, "onActivityResult: ${requestCode}")
            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSignInResult(completeTask: Task<GoogleSignInAccount>) {
        try {
            val account = completeTask.getResult(ApiException::class.java)
            Log.d(TAG, "signSuccess: ${account.email}")
            Toast.makeText(
                this, "Bienvenido ${account.displayName}",
                Toast.LENGTH_SHORT
            ).show()

            intent = Intent(this, ProductsActivity::class.java)
            intent.putExtra("email", account.email)
            intent.putExtra("name", account.displayName)

            startActivity(intent)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult: failed code = " + e.statusCode)
            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }
}