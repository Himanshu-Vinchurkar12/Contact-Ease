package com.example.contact.presentation.screen


import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.contact.R
import com.example.contact.data.entities.Contact
import com.example.contact.presentation.AppState
import com.example.contact.presentation.ContactViewModel
import com.example.contact.presentation.navigation.routes.Routes.HomeScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.nio.file.WatchEvent
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEditScreenUI(
    modifier: Modifier = Modifier,
    viewModel: ContactViewModel,
    navController: NavController,
    contactId: Int? = null
) {


    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    var name = remember { mutableStateOf("") }
    var phoneNo = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    val profile = remember { mutableStateOf<ByteArray?>(null) }
    var isfavourite = remember { mutableStateOf(0) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {

            val inputStrem = context.contentResolver.openInputStream(it)
            val byteArray = inputStrem?.readBytes()
            profile.value = byteArray
            inputStrem?.close()
        }
    }

    var contact: Contact? = Contact(
        name = name.value,
        phoneNo = phoneNo.value,
        email = email.value,
        dateOfEdit = Calendar.getInstance().timeInMillis,
        profile = profile.value,
        isFavorite = isfavourite.value
    )
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        if (contactId != null) {

            contact = (state.value as AppState.Data).data.find {
                it.id == contactId
            }
            name.value = contact?.name ?: ""
            phoneNo.value = contact?.phoneNo ?: ""
            email.value = contact?.email ?: ""
            profile.value = contact?.profile ?: null
            isfavourite.value = contact?.isFavorite ?: 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        //Header Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF3488E7),
                            Color(0xFF3E9AEA)
                        )
                    )
                )
        ) {
            Spacer(Modifier.height(screenHeight * 0.06f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (screenWidth.value * 0.02).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier.width((screenWidth.value * 0.06).dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = modifier
                        .clickable {
                            navController.navigate(HomeScreen(null))
                        }
                        .size((screenHeight * 0.025f))
                )
                Spacer(modifier.width(10.dp))
                Text(
                    text = "Back",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = (screenWidth.value * 0.046).sp
                )

            }
            Spacer(Modifier.height(screenHeight * 0.03f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (screenWidth.value * 0.02).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier.width((screenWidth.value * 0.03).dp))
                Text(
                    text = "Add New Contact",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = (screenWidth.value * 0.06).sp
                )
            }

            Spacer(Modifier.height(screenHeight * 0.02f))


        }

        Spacer(Modifier.height(screenHeight * 0.03f))

        //Card for Contact Detail
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (screenWidth.value * 0.02).dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Spacer(Modifier.height(screenHeight * 0.02f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (screenWidth.value * 0.02).dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.02).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        if (profile.value == null) {
                            val borderWidth = 6.dp
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(200.dp)
                                    .border(
                                        BorderStroke(borderWidth, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth)
                                    .clip(CircleShape)
                                    .clickable {
                                        pickImage.launch("image/*")
                                    }
                            )
                        } else {
                            val image =
                                BitmapFactory.decodeByteArray(
                                    profile.value,
                                    0,
                                    profile.value!!.size
                                )
                            val borderWidth = 6.dp

                            Box(contentAlignment = Alignment.BottomEnd) {

                                Image(
                                    bitmap = image.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .border(
                                            BorderStroke(borderWidth, rainbowColorsBrush),
                                            CircleShape
                                        )
                                        .padding(borderWidth)
                                        .clip(CircleShape)
                                )
                                FloatingActionButton(
                                    onClick = {
                                        profile.value = null
                                    },
                                    containerColor = Color(0xFF0F6CD7),
                                    contentColor = Color.White,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(55.dp)
                                        .align(Alignment.BottomEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                        }

                        Spacer(Modifier.height(screenHeight * 0.024f))

                        Text(
                            text = "Contact Information",
                            fontWeight = FontWeight.Bold,
                            fontSize = (screenWidth.value * 0.05).sp,
                            color = Color.Black,
                            letterSpacing = (0.5).sp

                        )
                    }


                }

                Spacer(Modifier.height(screenHeight * 0.03f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.03).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = (screenWidth.value * 0.04).sp,
                            color = Color.Black,
                            letterSpacing = (0.6).sp
                        )
                        Spacer(Modifier.height(screenHeight * 0.01f))
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = {
                                name.value = it
                            },
                            shape = RoundedCornerShape(16.dp),
                            placeholder = {
                                Text(
                                    "Enter full name",
                                    fontSize = (screenWidth.value * 0.045).sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            maxLines = 1,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = (screenWidth.value * 0.045).sp,
                                lineHeight = 30.sp // 🔼 Increases the text height
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedBorderColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp)
                        )
                    }

                }

                Spacer(Modifier.height(screenHeight * 0.02f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.03).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Phone Number",
                            fontWeight = FontWeight.Bold,
                            fontSize = (screenWidth.value * 0.04).sp,
                            color = Color.Black,
                            letterSpacing = (0.6).sp
                        )
                        Spacer(Modifier.height(screenHeight * 0.01f))
                        OutlinedTextField(
                            value = phoneNo.value,
                            onValueChange = {
                                phoneNo.value = it
                            },
                            shape = RoundedCornerShape(16.dp),
                            placeholder = {
                                Text(
                                    "Enter Phone number",
                                    fontSize = (screenWidth.value * 0.045).sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Phone,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            maxLines = 1,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = (screenWidth.value * 0.045).sp,
                                lineHeight = 30.sp // 🔼 Increases the text height
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedBorderColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp)
                        )
                    }

                }

                Spacer(Modifier.height(screenHeight * 0.02f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.03).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Email",
                            fontWeight = FontWeight.Bold,
                            fontSize = (screenWidth.value * 0.04).sp,
                            color = Color.Black,
                            letterSpacing = (0.6).sp
                        )
                        Spacer(Modifier.height(screenHeight * 0.01f))
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = {
                                email.value = it
                            },
                            shape = RoundedCornerShape(16.dp),
                            placeholder = {
                                Text(
                                    "Enter email address",
                                    fontSize = (screenWidth.value * 0.045).sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Email,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            maxLines = 1,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = (screenWidth.value * 0.045).sp,
                                lineHeight = 30.sp // 🔼 Increases the text height
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedBorderColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp)
                        )
                    }

                }

                Spacer(Modifier.height(screenHeight * 0.03f))

                Button(
                    onClick = {
                    if (name.value.isEmpty() || phoneNo.value.isEmpty() || email.value.isEmpty()) {

                        Toast.makeText(
                            context,
                            "Please ensure all fields are completed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        contact = Contact(
                            id = contactId ?: 0,
                            name = name.value,
                            phoneNo = phoneNo.value,
                            email = email.value,
                            dateOfEdit = Calendar.getInstance().timeInMillis,
                            profile = profile.value,
                            isFavorite = isfavourite.value
                        )
                        contact?.let { viewModel.upsertContact(it) }
                        Toast.makeText(context, "Save Successfully", Toast.LENGTH_SHORT).show()
                        navController.navigate(HomeScreen(contact!!.id))
                    }

                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.03).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =   Color(0xFF3488E7),
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = (screenWidth.value * 0.02).dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Save Contact", color = Color.White, fontSize = (screenWidth.value * 0.04).sp)
                    }
                }

                Spacer(Modifier.height(screenHeight * 0.03f))


            }

        }

        Spacer(Modifier.height(screenHeight * 0.03f))


    }

}