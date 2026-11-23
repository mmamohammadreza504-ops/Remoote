package com.drsite.drremote

import android.app.Activity
import android.os.Bundle
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrRemoteApp(this)
        }
    }
}

@Composable
fun DrRemoteApp(activity: Activity) {
    var consent by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("آماده اتصال") }
    var loading by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "DR REMOTE",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("اتصال دستگاه با اجازه صاحب دستگاه")

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = consent,
                    onCheckedChange = { consent = it }
                )
                Text("با اتصال دستگاه موافقم")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = consent && !loading,
                onClick = {
                    loading = true
                    status = "در حال اتصال..."

                    Thread {
                        var result = "خطا در اتصال به سرور"

                        try {
                            val url = URL(
                                "http://127.0.0.1:5000/connect"
                            )

                            val connection =
                                url.openConnection() as HttpURLConnection

                            connection.requestMethod = "POST"
                            connection.connectTimeout = 10000
                            connection.readTimeout = 15000
                            connection.doOutput = true
                            connection.setRequestProperty(
                                "Content-Type",
                                "application/json"
                            )

                            val body =
                                """{"device_name":"${Build.MODEL}","consent":true}"""

                            connection.outputStream.use {
                                it.write(body.toByteArray(Charsets.UTF_8))
                            }

                            val code = connection.responseCode

                            result = if (code in 200..299) {
                                "درخواست اتصال ارسال شد"
                            } else {
                                "خطای سرور: $code"
                            }

                            connection.disconnect()

                        } catch (e: Exception) {
                            result = "اتصال ناموفق: ${e.message}"
                        }

                        activity.runOnUiThread {
                            status = result
                            loading = false
                        }
                    }.start()
                }
            ) {
                Text(if (loading) "لطفاً صبر کنید..." else "شروع اتصال")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(status)
        }
    }
}
