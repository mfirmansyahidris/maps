package com.dev.fi.maps

data class GeoJson(
	val features: List<FeaturesItem?>? = null,
	val crs: Crs? = null,
	val name: String? = null,
	val type: String? = null
){
	data class FeaturesItem(
			val geometry: Geometry? = null,
			val type: String? = null,
			val properties: Properties? = null
	){
		data class Geometry(
				val coordinates: List<List<List<Double?>?>?>? = null,
				val type: String? = null
		)
	}

	data class Crs(
			val type: String? = null,
			val properties: Properties? = null
	)

	data class Properties(
			val NAME: String? = null,
			val BRANCH: String? = null,
			val WARNA: String? = null,
			val KABUPATEN: String? = null,
			val SUBS: Double? = null,
			val LONGITUDE: Double? = null,
			val TLLAT: Double? = null,
			val IDREG: String? = null,
			val BRLONG: Double? = null,
			val KAB: String? = null,
			val REGION: String? = null,
			val IDKAB: String? = null,
			val IDPRO: String? = null,
			val TLLONG: Double? = null,
			val LATITUDE: Double? = null,
			val BRLAT: Double? = null
	)
}
