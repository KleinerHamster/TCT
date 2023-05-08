package com.example.tct.mymail

import android.content.Context
import android.os.AsyncTask
import java.util.*
import javax.activation.DataHandler
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource


class MyMailAPI(private var context: Context, private var email: String, private var subject: String,
                private var headerMessageOrder: String, private var userName: String,
                private var userEmail: String, private var userPhone: String,
                private var orderComment: String, private var goods: String) : AsyncTask<Void, Void, Void>() {
    private var session: Session? = null



    override fun doInBackground(vararg p0: Void?): Void? {
        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.mail.ru"
        properties["mail.smtp.socketFactory.port"] = "465"
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.port"] = "465"

        session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD)
            }
        })

        val mimeMessage = MimeMessage(session)
        try {
            mimeMessage.setFrom(InternetAddress(Utils.EMAIL))
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress(email).toString())
            mimeMessage.subject = subject

            val _multipart: Multipart = MimeMultipart()
            val messageBodyPart: BodyPart = MimeBodyPart()
            val `is` = context.assets.open("user_profile.html")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            var str = String(buffer)
            str = str.replace("$\$headermessage$$", headerMessageOrder)
            str = str.replace("$\$username$$", userName)
            str = str.replace("$\$email$$", userEmail)
            str = str.replace("$\$mobile$$", userPhone)
            str = str.replace("$\$comment$$", orderComment)
            str = str.replace("$\$goods$$", goods)
            messageBodyPart.setContent(str, "text/html; charset=utf-8")
            _multipart.addBodyPart(messageBodyPart)
            mimeMessage.setContent(_multipart)

            Transport.send(mimeMessage)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
        return null
    }
}