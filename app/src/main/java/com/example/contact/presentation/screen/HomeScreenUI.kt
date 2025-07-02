package com.example.contact.presentation.screen

import android.Manifest
import android.content.Intent
import com.example.contact.R
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.contact.data.entities.Contact
import com.example.contact.presentation.AppState
import com.example.contact.presentation.ContactViewModel
import com.example.contact.presentation.navigation.routes.Routes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun HomeScreenUI(
    viewModel: ContactViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp


    var state = viewModel.state.collectAsStateWithLifecycle()
    var text = remember { mutableStateOf("") }
    var active = remember { mutableStateOf(false) }
    var isSortedByName = remember { mutableStateOf(true) }
    val dropDown = remember { mutableStateOf(false) }



    when (state.value) {
        is AppState.Data -> {
            val contacts = (state.value as AppState.Data).data

            var filteredContacts = contacts.filter {
                it.name.contains(text.value, ignoreCase = true)
            }.let { list ->
                if (isSortedByName.value) list.sortedBy { it.name } else list.sortedBy { it.dateOfEdit }
            }


            Scaffold(

                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Routes.AddEditScreen(null))
                        },
                        containerColor = Color(0xFF476CD9),
                        contentColor = Color.White

                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                },
            ) {
                it
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF3C8CE7),
                                        Color(0xFF4FA3EC)
                                    )
                                )
                            )
                            .padding(horizontal = (screenWidth.value * 0.05).dp)
                    ) {
                        Spacer(Modifier.height(screenHeight * 0.02f))

                        Text(
                            text = "Contacts",
                            fontSize = (screenWidth.value * 0.05).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(Modifier.height(screenHeight * 0.008f))
                        Text(
                            text = "${contacts.size} contacts",
                            fontSize = (screenWidth.value * 0.03).sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White

                        )
                        Spacer(Modifier.height(screenHeight * 0.02f))

                        GradientButton(
                            text = "Add Contact",
                            onClick = {
                                navController.navigate(Routes.AddEditScreen(null))
                            },
                            icon = Icons.Default.Add,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp)
                        )
                        Spacer(Modifier.height(screenHeight * 0.02f))

                        OutlinedTextField(
                            value = text.value,
                            onValueChange = {
                                text.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp),
                            placeholder = {
                                Text(text = "Search contacts...")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.List,
                                    contentDescription = null
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color(0xFFB6DBFA),
                                unfocusedContainerColor = Color(0xFFAED5F6),
                                focusedContainerColor = Color(0xFFB6DBFA)
                            )
                        )


                        Spacer(Modifier.height(screenHeight * 0.02f))


                    }

                    Spacer(Modifier.height(screenHeight * 0.02f))


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = (screenWidth.value * 0.02).dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(width = 1.dp, color = Color.LightGray),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {
                        Spacer(Modifier.height(screenHeight * 0.03f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width((screenWidth.value * 0.01).dp))

                            Icon(
                                imageVector = Icons.Outlined.People,
                                contentDescription = null,
                                tint = Color.Blue,
                                modifier = Modifier.size((screenWidth.value * 0.06).dp)
                            )
                            Spacer(Modifier.width((screenWidth.value * 0.02).dp))

                            Text(
                                text = "All Contacts",
                                fontWeight = FontWeight.Bold,
                                fontSize = (screenWidth.value * 0.05).sp
                            )
                        }
                        Spacer(Modifier.height(screenHeight * 0.02f))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp)
                        ) {
                            items(filteredContacts) {
                                ContactItemsUI(
                                    contact = it,
                                    viewModel = viewModel,
                                    navController = navController,
                                    modifier = Modifier.animateItem(
                                        fadeInSpec = tween(durationMillis = 300),
                                        fadeOutSpec = tween(durationMillis = 300) // Correct Usage,
                                    )
                                )
                            }
                        }

                        Spacer(Modifier.height(screenHeight * 0.03f))

                    }

                }

            }
        }

        AppState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = Color.Blue
                )
            }
        }
    }
}


@Composable
fun GradientButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .border(
                width = 0.8.dp,
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF2997F8), Color(0xFF33B1EC))
                )
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

        }

    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ContactItemsUI(
    contact: Contact,
    viewModel: ContactViewModel,
    navController: NavController,
    modifier: Modifier
) {
    // Phone Call Permission

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.CALL_PHONE
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .combinedClickable(
                onClick = {
                    navController.navigate(Routes.DetailScreen(contact.id))
                },
                onLongClick = {
                    navController.navigate(Routes.AddEditScreen(contact.id))
                },
                onDoubleClick = {}
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (contact.profile == null) {
                val nameEsc = contact.name.split(" ").filter {
                    it.isNotEmpty()
                }.map {
                    it.first()
                }
                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .border(1.dp, Color.LightGray, CircleShape)
                        .clip(CircleShape)
                        .background(Brush.horizontalGradient(listOf(Color(0xFFE05D5B),Color(
                            0xFFE8100B
                        )
                        ))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = nameEsc.toString().replace("[", "").replace("]", "").replace(",", "")
                            .replace(" ", "").uppercase(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        fontSize = 21.sp,
                        letterSpacing = 1.sp,
                        modifier = Modifier,
                    )
                }

            } else {
                val image =
                    BitmapFactory.decodeByteArray(contact.profile, 0, contact.profile!!.size)
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp, end = 5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = contact.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "+91\u202F${contact.phoneNo}",
                    fontWeight = FontWeight.Thin,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    maxLines = 1,
                    color = Color.DarkGray,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )

            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp)
                        .clickable {
                            if (permissionState.status.isGranted) {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_CALL
                                    data = Uri.parse("tel:${contact.phoneNo}")
                                }
                                navController.context.startActivity(intent)
                            } else {
                                permissionState.launchPermissionRequest()
                            }

                        }
                )
            }

        }
        Spacer(Modifier.height(10.dp))
    }
}

