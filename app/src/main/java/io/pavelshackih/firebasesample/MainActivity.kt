package io.pavelshackih.firebasesample

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId

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
    }
}
