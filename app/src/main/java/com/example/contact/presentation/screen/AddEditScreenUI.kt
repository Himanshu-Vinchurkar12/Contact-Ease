package com.example.contact.presentation.screen


import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.contact.R
import com.example.contact.data.entities.Contact
import com.example.contact.presentation.AppState
import com.example.contact.presentation.ContactViewModel
import com.example.contact.presentation.navigation.routes.Routes.HomeScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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

    val context = LocalContext.current

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
        profile = profile.value
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
        }
    }
    Column {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = modifier
                .clickable {
                    navController.navigate(HomeScreen)
                }
                .padding(top = 18.dp, start = 18.dp)
                .size(30.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            val image = BitmapFactory.decodeByteArray(profile.value, 0, profile.value!!.size)
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
                    containerColor = Color.Blue,
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

        Spacer(modifier.height(15.dp))
        OutlinedTextField(value = name.value,
            onValueChange = {
                name.value = it
            },
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text("Name")
            },
            label = { Text("Name") },
            trailingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier.height(15.dp))
        OutlinedTextField(
            value = phoneNo.value,
            onValueChange = {
                phoneNo.value = it
            },
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text("Phone Number")
            },
            label = { Text("Phone Number") },
            trailingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )

        Spacer(modifier.height(15.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text("E-mail")
            },
            label = { Text("E-mail") },
            trailingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )

        )
        Spacer(modifier.height(15.dp))
        Button(onClick = {
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
                    profile = profile.value
                )
                contact?.let { viewModel.upsertContact(it) }
                Toast.makeText(context,"Save Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(HomeScreen)
            }

        }) {
            Text("Save Contact")
        }

    }
}