package com.example.pibbou.afca.repository

import android.content.Context
import android.util.Log
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.SeedInformation
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


/**
 * Created by arnaudpinot on 02/01/2018.
 * Class to manage app datas
 */
class DataRepository constructor(contextArg: Context) {

    var context: Context

    /**
     * Initialization
     */
    init {
        context = contextArg
    }

    /**
     *  Load all datas
     */
    fun loadDatas(){
        deserializeDatas()
    }

    /**
     *  GSON deserialize
     */
    private fun deserializeDatas() {
        val jsonDatas = getStringFromJson(R.raw.seed, context)

        val gson = Gson()

        val SeedInformation = gson.fromJson(jsonDatas, SeedInformation::class.java)

        Log.d("init", jsonDatas)
    }


    /**
     * Json to string
     */
    fun getStringFromJson(path: Int?, context: Context): String {
        val sb = StringBuilder()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(InputStreamReader(context.resources.openRawResource(path!!)))
            while (true) {
                val temp = br.readLine() ?: break
                sb.append(temp)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (br != null) {
                    br.close() // stop reading
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return sb.toString()
    }
}