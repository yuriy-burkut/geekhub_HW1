package com.yuriy.geekhub_hw1

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.content_scrolling.*

class MainActivity : AppCompatActivity() {

    val TEXT_REQUEST_CODE = 1
    val IMAGE_REQUEST_CODE = 101

    var imageUri: Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextIntent = Intent(this, EditTextActivity::class.java)

        add_text_button.setOnClickListener {
            editTextIntent.putExtra("initial_text", text_preview.text)
            startActivityForResult(editTextIntent, TEXT_REQUEST_CODE)
        }

        add_photo_button.setOnClickListener {
            getFromGallery()
        }

        send_to_email_button.setOnClickListener {
            sendToMail()
        }

        crash_button.setOnClickListener {
            Crashlytics.getInstance().crash()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == TEXT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val textToSend = data?.extras?.getString("edited_text") ?: "null"
            text_preview.text = textToSend
        } else if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data ?: Uri.EMPTY
            image_preview.visibility = View.VISIBLE
            image_preview.setImageURI(imageUri)
        }

    }

    private fun getFromGallery() {
        val mimeTypes = arrayOf<String>("image/jpeg", "image/png")
        Intent(Intent.ACTION_PICK).also { takePictureIntent ->
            takePictureIntent.type = "image/*"
            takePictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(takePictureIntent, IMAGE_REQUEST_CODE)
        }
    }

    private fun sendToMail() {
        val sendIntent = Intent(Intent.ACTION_SENDTO)
        sendIntent.data = Uri.parse("mailto:")
        sendIntent.putExtra(Intent.EXTRA_TEXT, text_preview.text)
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

}
