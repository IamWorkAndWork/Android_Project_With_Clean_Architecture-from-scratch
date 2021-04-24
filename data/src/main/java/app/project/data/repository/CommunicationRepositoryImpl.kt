package app.project.data.repository

import android.content.Context
import android.util.Log
import app.project.domain.repository.CommunicationRepository
import com.parse.Parse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class CommunicationRepositoryImpl(private val context: Context) : CommunicationRepository {

    override fun initialzeCommunication() {

        Log.d("dataLayer", "initialzeCommunication")
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.networkInterceptors().add(httpLoggingInterceptor)

        Parse.initialize(
            Parse.Configuration
                .Builder(context).applicationId("")
                .clientKey(null)
                .clientBuilder(builder)
                .server("")
                .enableLocalDataStore()
                .build()
        )

    }

}