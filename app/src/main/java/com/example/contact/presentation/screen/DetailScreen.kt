package com.example.contact.presentation.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import kotlin.math.abs

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenUI(
    contactId: Int?,
    viewModel: ContactViewModel,
    navController: NavController = rememberNavController()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val contact = remember { mutableStateOf<Contact?>(null) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val scrollState = rememberScrollState()

    val gradientColors = listOf(
        listOf(Color(0xFF55036B), Color(0xFF8A69C4)), // Purple
        listOf(Color(0xFF045196), Color(0xFF42A5F5)), // Blue
        listOf(Color(0xFFE1125E), Color(0xFFAD0ED7)), // Green
        listOf(Color(0xFF5D4036), Color(0xFF936A6E)), //Brown
        listOf(Color(0xFFFD5C16), Color(0xFFFAB147)), // Orange

    )


    val permissionState = rememberPermissionState(
        permission = Manifest.permission.CALL_PHONE
    )

    LaunchedEffect(contactId, state) {
        contact.value = (state as? AppState.Data)?.data?.find { it.id == contactId }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.horizontalGradient(listOf(Color(0xFF3488E7), Color(0xFF3E9AEA))))
            .verticalScroll(scrollState)
    )
    {
        Spacer(Modifier.height(screenHeight * 0.035f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (screenWidth.value * 0.05).dp)
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size((screenHeight * 0.025f)),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Back",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = (screenWidth.value * 0.046).sp
            )

        }

        Spacer(Modifier.height(screenHeight * 0.025f))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (screenWidth.value * 0.02).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            contact.value?.let {
                if (it.profile == null) {
                    val nameEsc = it.name.split(" ").filter {
                        it.isNotEmpty()
                    }.map {
                        it.first()
                    }

                    val nameHash = it.name.hashCode()
                    val gradient = gradientColors[abs(nameHash) % gradientColors.size]

                    Box(
                        modifier = Modifier
                            .size(145.dp)
                            .shadow(
                                elevation = 12.dp,
                                shape = CircleShape,
                                ambientColor = Color.Gray,
                                spotColor = Color.Black
                            )
                            .clip(CircleShape)
                            .background( Brush.horizontalGradient(
                                gradient
                            )),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nameEsc.toString().replace("[", "").replace("]", "")
                                .replace(",", "")
                                .replace(" ", "").uppercase(),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White,
                            fontSize = (screenWidth.value * 0.09).sp,
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
                            .border(2.dp, Color.LightGray, CircleShape)
                    )
                }
            }

            Spacer(Modifier.height(screenHeight * 0.024f))
            contact.value?.let {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = (screenWidth.value * 0.06).sp,
                    color = Color.White,
                    letterSpacing = (1).sp
                )
                Spacer(Modifier.height(screenHeight * 0.02f))
                Text(
                    text = "+91 ${it.phoneNo}",
                    fontWeight = FontWeight.Normal,
                    fontSize = (screenWidth.value * 0.044).sp,
                    color = Color.White,
                    letterSpacing = (0.5).sp
                )
            }


        }

        Spacer(Modifier.height(screenHeight * 0.03f))

        contact.value?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Spacer(Modifier.height(screenHeight * 0.02f))

                var showEmailDialog by remember { mutableStateOf(false) }
                var showWhatsAppDialog by remember { mutableStateOf(false) }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.06).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = {
                            if (permissionState.status.isGranted) {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_CALL
                                    data = Uri.parse("tel:${it.phoneNo}")
                                }
                                navController.context.startActivity(intent)
                            }
                            else {
                                permissionState.launchPermissionRequest()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27B62D)),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.buttonElevation( 5.dp ),
                        modifier = Modifier.weight(1f)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Icon(
                                imageVector = Icons.Rounded.Call,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }

                    if (showEmailDialog) {
                        AlertDialog(
                            onDismissRequest = { showEmailDialog = false },
                            title = { Text("Permission Required") },
                            text = { Text("Do you want to send an email using your default email app?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    showEmailDialog = false
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("mailto:${it.email}")
                                    }
                                    navController.context.startActivity(intent)
                                }) {
                                    Text("Allow")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showEmailDialog = false }) {
                                    Text("Deny")
                                }
                            }
                        )
                    }

                    Button(
                        onClick = { showEmailDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        elevation = ButtonDefaults.buttonElevation( 5.dp ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }

                    if (showWhatsAppDialog) {
                        AlertDialog(
                            onDismissRequest = { showWhatsAppDialog = false },
                            title = { Text("Permission Required") },
                            text = { Text("Do you want to open WhatsApp to message this contact?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    showWhatsAppDialog = false
                                    val phoneNumber = it.phoneNo.replace(" ", "").replace("+", "")
                                    val url = "https://wa.me/$phoneNumber"

                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            data = Uri.parse(url)
                                            setPackage("com.whatsapp")
                                        }
                                        navController.context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(navController.context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
                                    }
                                }) {
                                    Text("Allow")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showWhatsAppDialog = false }) {
                                    Text("Deny")
                                }
                            }
                        )
                    }


                    Button(
                        onClick = {
                            showWhatsAppDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA5EFA7)),
                        elevation = ButtonDefaults.buttonElevation( 5.dp ),
                        border = BorderStroke(1.dp, Color(0xFF066057)),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.weight(1f)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Icon(
                                imageVector = Icons.Rounded.Whatsapp,
                                contentDescription = null,
                                tint = Color(0xFF066057)
                            )
                        }
                    }

                }

                Spacer(Modifier.height(screenHeight * 0.02f))


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.02).dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Spacer(Modifier.height(screenHeight * 0.015f))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = (screenWidth.value * 0.03).dp)
                    ) {
                        Spacer(Modifier.height(screenHeight * 0.01f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.01).dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = "Contact Information",
                                fontSize = (screenWidth.value * 0.042).sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                letterSpacing = (0.5).sp
                            )
                        }
                        Spacer(Modifier.height(screenHeight * 0.015f))

                        contact.value?.let {
                            ContactDetailRow(Icons.Outlined.Phone, "Phone", "+91 ${it.phoneNo}")
                            Spacer(Modifier.height(screenHeight * 0.015f))
                            ContactDetailRow(Icons.Outlined.Email, "Email", it.email)
                            Spacer(Modifier.height(screenHeight * 0.015f))
                            ContactDetailRow(Icons.Outlined.Whatsapp, "WhatsApp", "+91 ${it.phoneNo}")
                            Spacer(Modifier.height(screenHeight * 0.015f))
                            ContactDetailRow(
                                Icons.Outlined.DateRange,
                                "Created At",
                                getFormattedDate(it.dateOfEdit)
                            )
                            Spacer(Modifier.height(screenHeight * 0.015f))
                        }
                    }

                }

                Spacer(Modifier.height(screenHeight * 0.02f))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.02).dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Spacer(Modifier.height(screenHeight * 0.015f))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = (screenWidth.value * 0.03).dp)
                    ) {
                        Spacer(Modifier.height(screenHeight * 0.01f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.01).dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = "Actions",
                                fontSize = (screenWidth.value * 0.042).sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                letterSpacing = (0.5).sp
                            )
                        }
                        Spacer(Modifier.height(screenHeight * 0.015f))

                        contact.value?.let {
                            Button(
                                onClick = {
                                    val updatedContact = it.copy(isFavorite = if (it.isFavorite == 1) 0 else 1)
                                    viewModel.upsertContact(updatedContact)
                                },
                                modifier = Modifier.fillMaxWidth().padding(horizontal = (screenWidth.value * 0.03).dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color.LightGray)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if(it.isFavorite == 1) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                        contentDescription = null,
                                        tint = if (it.isFavorite == 1 )Color(0xFFF1B11A) else Color.DarkGray
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = if (it.isFavorite == 1) "Remove from Favorites" else "Add to Favorites",
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = (screenWidth.value * 0.04).sp,
                                        letterSpacing = (0.3).sp
                                    )
                                }
                            }
                            Spacer(Modifier.height(screenHeight * 0.01f))
                            Button(
                                onClick = {
                                    navController.navigate(Routes.AddEditScreen(it.id))
                                },
                                modifier = Modifier.fillMaxWidth().padding(horizontal = (screenWidth.value * 0.03).dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color.LightGray)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text =  "Edit Contact" ,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = (screenWidth.value * 0.04).sp,
                                        letterSpacing = (0.3).sp
                                    )
                                }
                            }
                            Spacer(Modifier.height(screenHeight * 0.01f))
                            Button(
                                onClick = {
                                    viewModel.deleteContact(it)
                                    navController.navigateUp()
                                    Toast.makeText(navController.context, "Contact Deleted", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth().padding(horizontal = (screenWidth.value * 0.03).dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color.LightGray)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text ="Delete Contact" ,
                                        color = Color.Red,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = (screenWidth.value * 0.04).sp,
                                        letterSpacing = (0.3).sp
                                    )
                                }
                            }
                            Spacer(Modifier.height(screenHeight * 0.015f))
                        }

                        Spacer(Modifier.height(screenHeight * 0.015f))
                    }

                }


                Spacer(Modifier.height(screenHeight * 0.03f))


            }

        }


    }
}

@Composable
fun ContactDetailRow(icon: ImageVector, label: String, value: String?) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = (screenWidth.value * 0.03).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.DarkGray)
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                "$label: ",
                fontSize = (screenWidth.value * 0.035).sp,
                fontWeight = FontWeight.W400,
                color = Color.DarkGray
            )
            Spacer(Modifier.height(screenHeight * 0.005f))
            Text(
                text = value ?: "N/A",
                fontSize = (screenWidth.value * 0.04).sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

    }
}

fun getFormattedDate(timestamp: Long): String {
    val formatter = SimpleDateFormat(" d MMMM , yyyy", Locale.ENGLISH)
    return formatter.format(Date(timestamp))
}
