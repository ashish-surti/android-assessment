package com.example.universities.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UniversityModel(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("web_pages")
	val webPages: List<String>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("domains")
	val domains: List<String>? = null,

	@field:SerializedName("state-province")
	val stateProvince: String? = null,

	@field:SerializedName("alpha_two_code")
	val alphaTwoCode: String? = null
): Serializable
