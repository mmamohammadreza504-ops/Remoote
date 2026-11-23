package com.drsite.drremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkBackground = Color(0xFF090D0B)
private val Emerald = Color(0xFF00C987)
private val White = Color(0xFFF4F7F5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrRemoteApp()
        }
    }
}

@Composable
fun DrRemoteApp() {
    var consent by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "DR",
                    color = Emerald,
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "REMOTE",
                    color = White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "مدیریت امن دستگاه‌های شما",
                    color = Color.LightGray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(42.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF151D19)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "اتصال دستگاه",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "برای اتصال به پنل Dr Remote، ابتدا باید درخواست اتصال را با اطلاع و رضایت خودتان ثبت کنید.",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = consent,
                                onCheckedChange = { consent = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Emerald
                                )
                            )

                            Text(
                                text = "درخواست اتصال را تأیید می‌کنم",
                                color = White,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {},
                            enabled = consent,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Emerald,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("شروع اتصال")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "نسخه 1.0.0 | Dr Site",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
