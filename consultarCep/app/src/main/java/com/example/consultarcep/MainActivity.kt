package com.example.consultarcep

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val EtCep = findViewById<EditText>(R.id.EtCep)
        val BtnPesquisarCep = findViewById<Button>(R.id.BtnPesquisarCep)

        BtnPesquisarCep.setOnClickListener {
            val cep = EtCep.text.toString()
            if (cep.isNotBlank()) {
                buscarEndereco(cep)
            } else {
                Toast.makeText(this, "Não deixe a caixa vazia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarEndereco(cep: String) {
        val retrofit = RetrofitUtils.getRetrofitInstance("https://viacep.com.br/")
        val endpoint = retrofit.create(Endpoint::class.java)

        endpoint.get(cep).enqueue(object : Callback<CepModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<CepModel>, response: Response<CepModel>) {
                val tvResultado = findViewById<TextView>(R.id.TvResultado)
                if (response.isSuccessful) {
                    val cepModel = response.body()
                    if (cepModel != null && cepModel.logradouro != null) {
                        tvResultado.text = """
                            Logradouro: ${cepModel.logradouro}
                            Bairro: ${cepModel.bairro}
                            Cidade: ${cepModel.localidade}
                            UF: ${cepModel.uf}
                        """.trimIndent()
                    } else {
                        tvResultado.text = ""
                        Toast.makeText(this@MainActivity, "Digite um Cep valido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    tvResultado.text = ""
                    Toast.makeText(this@MainActivity, "Erro ao encontrar seu Cep", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CepModel>, t: Throwable) {
                val tvResultado = findViewById<TextView>(R.id.TvResultado)
                tvResultado.text = ""
                Toast.makeText(this@MainActivity, "Sem resultados: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
