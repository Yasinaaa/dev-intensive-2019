package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val STATUS_TAG = "STATUS"
        private val QUESTION_TAG = "QUESTION"
    }

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        messageEt.imeOptions = EditorInfo.IME_ACTION_DONE

        messageEt.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                send()
                hideKeyboard()
                true
            } else false
        }


        val status = savedInstanceState?.getString(STATUS_TAG) ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(QUESTION_TAG) ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.askQuestion()

        sendBtn.setOnClickListener(this)
        enableActionDoneForSendBtn(messageEt)
    }

    private fun enableActionDoneForSendBtn(editText: EditText){
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) sendBtn.performClick()
            false
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.iv_send) {
            send()
        }
    }

    fun send() {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())

        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(STATUS_TAG, benderObj.status.name)
        outState.putString(QUESTION_TAG, benderObj.question.name)
        hideKeyboard()
    }


}