package com.example.contact.presentation.screen

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.contact.R
import com.example.contact.data.entities.Contact
import com.example.contact.presentation.AppState
import com.example.contact.presentation.ContactViewModel
import com.example.contact.presentation.navigation.routes.Routes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailScreenUI(
    contactId: Int?,
    viewModel: ContactViewModel,
    navController: NavController = rememberNavController()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val contact = remember { mutableStateOf<Contact?>(null) }


    val permissionState = rememberPermissionState(
        permission = Manifest.permission.CALL_PHONE
    )

    LaunchedEffect(contactId, state) {
        contact.value = (state as? AppState.Data)?.data?.find { it.id == contactId }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topBar) = createRefs()
            Image(
                painter = painterResource(R.drawable.rectangle),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(245.dp)
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navController.navigateUp() }
                        .padding(18.dp)
                        .size(30.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(270.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    contact.value?.let {
                        if (it.profile == null) {
                            val nameEsc = it.name.split(" ").filter {
                                it.isNotEmpty()
                            }.map {
                                it.first()
                            }
                            Box(
                                modifier = Modifier
                                    .size(145.dp)
                                    .border(2.dp, Color.Black, CircleShape)
                                    .clip(CircleShape)
                                    .background(color = Color(0xFF3258C7)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = nameEsc.toString().replace("[", "").replace("]", "").replace(",", "")
                                        .replace(" ", "").uppercase(),
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White,
                                    fontSize = 67.sp,
                                    letterSpacing = 5.sp,
                                    modifier = Modifier,
                                )
                            }
                        } else {
                            val image = BitmapFactory.decodeByteArray(it.profile, 0, it.profile!!.size)
                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(165.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Black, CircleShape)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                contact.value?.let {
                    ContactDetailRow(Icons.Rounded.Person, "Name", it.name)
                    ContactDetailRow(Icons.Rounded.Phone, "Phone Number", it.phoneNo)
                    ContactDetailRow(Icons.Rounded.Whatsapp, "WhatsApp", it.phoneNo)
                    ContactDetailRow(Icons.Rounded.Email, "E-mail", it.email)
                    ContactDetailRow(Icons.Rounded.CalendarMonth, "Created On", formatTimestamp(it.dateOfEdit))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {

                    Spacer(Modifier.width(10.dp))
                    ElevatedButton(
                        onClick = {  contact.value?.let { viewModel.deleteContact(it)
                            navController.navigate(Routes.HomeScreen) }  },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD74010)),
                        modifier = Modifier.size(80.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 5.dp
                        ),
                        shape = CircleShape
                    ) {
                        Icon(Icons.Rounded.Delete, contentDescription = null,modifier = Modifier.size(35.dp))
//                        Spacer(Modifier.width(2.dp))
//                        Text("Delete", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(40.dp))
                    ElevatedButton(
                        onClick = { contact.value?.id?.let { navController.navigate(Routes.AddEditScreen(it)) } },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD74010)),
//                        modifier = Modifier.size(height = 45.dp, width = 120.dp),
                        shape = CircleShape,
                        modifier = Modifier.size(80.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 5.dp
                        )
                    ) {
                        Icon(Icons.Rounded.Edit, contentDescription = null, modifier = Modifier.size(35.dp))
//                        Spacer(Modifier.width(8.dp))
//                        Text("Edit", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.width(40.dp))
                    ElevatedButton(
                        onClick = {
                            if (permissionState.status.isGranted) {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_CALL
                                    data = Uri.parse("tel:${contact.value?.phoneNo}")
                                }
                                navController.context.startActivity(intent)
                            } else {
                                permissionState.launchPermissionRequest()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10D77A)),
                        shape = CircleShape,
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 5.dp
                        ),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(Icons.Rounded.Call, contentDescription = null, modifier = Modifier.size(50.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ContactDetailRow(icon: ImageVector, label: String, value: String?) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp)) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(5.dp))
        Text("$label: ", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(value ?: "N/A", fontSize = 18.sp, fontWeight = FontWeight.Normal, overflow = TextOverflow.Ellipsis, maxLines = 1)
    }
}

fun formatTimestamp(timestamp: Long?): String {
    return timestamp?.let {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        sdf.format(Date(it))
    } ?: "Unknown"
}
