package com.example.consultarcep
import com.google.gson.annotations.SerializedName
class CepModel {
    @SerializedName("cep")
    val cep: String = ""
    @SerializedName("logradouro")
    val logradouro: String = ""
    @SerializedName(value = "complemento")
    val complemento: String = ""
    @SerializedName("bairro")
    val bairro: String = ""
    @SerializedName("localidade")
    val localidade: String = ""
    @SerializedName("uf")
    val uf: String = ""
    @SerializedName("unidade")
    val unidade: String = ""
    @SerializedName("ibge")
    val ibge: String = ""
    @SerializedName("ddd")
    val ddd: String = ""


}