package com.example.crud_firebase

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener(this)

        val save = findViewById<Button>(R.id.save)
        save.setOnClickListener(this)

        val showdata = findViewById<Button>(R.id.showdata)
        showdata.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

    }
    private fun isEmpty(s: String): Boolean{
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View?) {
        when (v?.getId()){
            R.id.save -> {
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()


//                tambahan
                var nim = findViewById<EditText>(R.id.nim)
                var nama = findViewById<EditText>(R.id.nama)
                var jurusan = findViewById<EditText>(R.id.jurusan)

                val getNIM: String = nim.getText().toString()
                val getNama: String = nama.getText().toString()
                val getJurusan: String = jurusan.getText().toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNIM) || isEmpty(getNama) || isEmpty(getJurusan)) {
                    Toast.makeText(this@MainActivity, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
                }else{
                    getReference.child("Admin").child(getUserID).child("Mahasiswa").push()
                            .setValue(data_mahasiswa(getNIM, getNama, getJurusan))
                            .addOnCompleteListener(this){
                                nim.setText("")
                                nama.setText("")
                                jurusan.setText("")
                                Toast.makeText(this@MainActivity, "Data Tersimpan",
                                        Toast.LENGTH_SHORT).show()
                            }
                }

            }
            R.id.logout ->
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(p0: Task<Void>) {
                                Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
            R.id.showdata -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}