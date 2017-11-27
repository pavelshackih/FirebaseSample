package io.pavelshackih.firebasesample

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_view)
        val token = FirebaseInstanceId.getInstance().token
        textView.text = token

        findViewById<Button>(R.id.button).setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("token", token)
            textView.text = token
            clipboard.primaryClip = clip
            Toast.makeText(applicationContext, "Token copied", Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.send_button).setOnClickListener {
            token?.let {
                SendTokenTask(it).execute()
            }
        }
    }

    class SendTokenTask(private val token: String) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            val client = OkHttpClient().newBuilder().build()
            val requestBody = MultipartBody.Builder()
                    .addFormDataPart("name", Build.MODEL)
                    .addFormDataPart("token", token)
                    .addFormDataPart("system", Build.VERSION.RELEASE)
                    .build()

            val request = Request.Builder()
                    .url("http://push-ping.us-west-2.elasticbeanstalk.com/save")
                    .post(requestBody)
                    .build()

            val response = client.newCall(request).execute()
            Log.d(TAG, "Response : $response")
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}