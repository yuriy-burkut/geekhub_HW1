package com.yuriy.geekhub_hw1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.activity_edit_text.*

class EditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)

        text_edit_field.setText(intent.extras?.getString("initial_text"))
        text_clear_button.isEnabled = text_edit_field.text.isNotEmpty()

        text_edit_field.doAfterTextChanged {
            text_clear_button.isEnabled = text_edit_field.text.isNotEmpty()
        }

        text_save_button.setOnClickListener {
            val text = text_edit_field.text.toString()

            if (text.isNotBlank()) {
                setResult(Activity.RESULT_OK, Intent().putExtra("edited_text", text))
                finish()
            }
        }

        text_clear_button.setOnClickListener {
            text_edit_field.text.clear()
        }
    }
}
