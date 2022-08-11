package ru.ds.weatherfirst.notimplemented

//
//class SaveDataStore(private val context: Context) {
//
//    // to make sure there's only one instance
//    companion object {
//        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("temp_C")
//        val TEMP_C = stringPreferencesKey("temp_C")
//    }
//
//    //save email into datastore
//    suspend fun save(name: String) {
//            context.dataStore.edit { preferences ->
//                preferences[TEMP_C] = name
//            }
//    }
//
//    //get the saved email
//    val read: Flow<String?> = context.dataStore.data
//        .map { preferences ->
//            preferences[TEMP_C] ?: "!!!"
//        }
//
//    fun internetCheck(c: Context): Boolean {
//        val cmg = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            // Android 10+
//            cmg.getNetworkCapabilities(cmg.activeNetwork)?.let { networkCapabilities ->
//                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
//                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//            }
//        } else {
//            return cmg.activeNetworkInfo?.isConnectedOrConnecting == true
//        }
//
//        return false
//    }
//
//}