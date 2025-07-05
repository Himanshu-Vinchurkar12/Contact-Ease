package com.example.contact.presentation.screen

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.DismissValue
import androidx.compose.material.rememberDismissState
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlin.math.abs


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreenUI(
    viewModel: ContactViewModel,
    navController: NavController,
    contactId: Int? = null
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp


    var state = viewModel.state.collectAsStateWithLifecycle()
    var text = remember { mutableStateOf("") }
    var active = remember { mutableStateOf(false) }
    var isSortedByName = remember { mutableStateOf(true) }
    val dropDown = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()




    when (state.value) {
        is AppState.Data -> {
            val contacts = (state.value as AppState.Data).data

            var filteredContacts = contacts.filter {
                it.name.contains(text.value, ignoreCase = true)
            }.let { list ->
                if (isSortedByName.value) list.sortedBy { it.name.toLowerCase() } else list.sortedBy { it.dateOfEdit }
            }
            val favoriteContacts = contacts.filter { it.isFavorite == 1 }.let { list ->
                if (isSortedByName.value) list.sortedBy { it.name.toLowerCase() } else list.sortedBy { it.dateOfEdit }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )
            {
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
                    Spacer(Modifier.height(screenHeight * 0.04f))

                    Text(
                        text = "Contacts",
                        fontSize = (screenWidth.value * 0.06).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(screenHeight * 0.008f))
                    Text(
                        text = "${contacts.size} contacts",
                        fontSize = (screenWidth.value * 0.04).sp,
                        fontWeight = FontWeight.Medium,
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
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier.clickable {
                                    dropDown.value = true
                                }
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        contentAlignment = Alignment.TopStart
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .wrapContentWidth()
                        ) {

                            val Shapes = Shapes(
                                small = RoundedCornerShape(4.dp),
                                medium = RoundedCornerShape(4.dp),  //<- used by `DropdownMenu`
                                large = RoundedCornerShape(0.dp)
                            )
                            // DropdownMenu Positioned Properly
                            AnimatedVisibility(
                                visible = dropDown.value,
                                enter = scaleIn(tween(300)) + slideInVertically(
                                    initialOffsetY = { -40 },
                                    animationSpec = tween(300)
                                ),
                                exit = scaleOut(tween(300)) + slideOutVertically(
                                    targetOffsetY = { -40 },
                                    animationSpec = tween(300)
                                )
                            )
                            {
                                DropdownMenu(
                                    expanded = dropDown.value,
                                    onDismissRequest = { dropDown.value = false },
                                    modifier = Modifier
                                        .width(150.dp)
                                        .clip(RoundedCornerShape(20.dp)) // First clip the shape
                                        .background(Color.White) // Then apply background
                                ) {

                                    Column(modifier = Modifier.padding(vertical = 4.dp))
                                    {
                                        // Sort by Name
                                        DropdownMenuItem(
                                            text = { Text("Sort by Name") },
                                            onClick = {
                                                dropDown.value = false
                                                isSortedByName.value = true
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )

                                        // Divider for separation
                                        HorizontalDivider(
                                            color = Color.LightGray,
                                            thickness = 1.dp
                                        )

                                        // Sort by Date
                                        DropdownMenuItem(
                                            text = { Text("Sort by Date") },
                                            onClick = {
                                                dropDown.value = false
                                                isSortedByName.value = false
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    }
                                }
                            }

                        }


                    }

                    Spacer(Modifier.height(screenHeight * 0.02f))

                }

                Spacer(Modifier.height(screenHeight * 0.02f))

                if (filteredContacts.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = (screenWidth.value * 0.04).dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFFFFFFFF),
                                        Color(0xF3BADBF6)
                                    )
                                )
                            )
                            .shadow(20.dp, RoundedCornerShape(20.dp)),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Spacer(Modifier.width((screenWidth.value * 0.02).dp))
                        Icon(
                            imageVector = if (isSortedByName.value) Icons.Outlined.SortByAlpha else Icons.Outlined.DateRange,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size((screenWidth.value * 0.04).dp)
                        )
                        Spacer(Modifier.width((screenWidth.value * 0.01).dp))

                        Text(
                            text = if (isSortedByName.value) "Sorted by Name" else "Sorted by Date",
                            fontWeight = FontWeight.Bold,
                            fontSize = (screenWidth.value * 0.03).sp
                        )
                        Spacer(Modifier.width((screenWidth.value * 0.02).dp))

                    }
                }

                Spacer(Modifier.height(screenHeight * 0.03f))


                // Favourites Contacts
                if (favoriteContacts.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = (screenWidth.value * 0.025).dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(width = 1.dp, color = Color.LightGray), // yellow accent
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp
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
                                imageVector = Icons.Rounded.StarBorder,
                                contentDescription = null,
                                tint = Color(0xFFDEBB21),
                                modifier = Modifier.size((screenWidth.value * 0.06).dp)
                            )
                            Spacer(Modifier.width((screenWidth.value * 0.02).dp))
                            Text(
                                text = "Favorites",
                                fontWeight = FontWeight.Bold,
                                fontSize = (screenWidth.value * 0.05).sp
                            )
                        }

                        Spacer(Modifier.height(screenHeight * 0.02f))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp)
                        ) {
                            favoriteContacts.forEachIndexed { index, contact ->
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(tween(300)) + slideInVertically(tween(300)) { it / 2 },
                                    exit = fadeOut(tween(300)) + slideOutVertically(tween(300)) { it / 2 }
                                ) {
                                    ContactItemsUI(
                                        contact = contact,
                                        viewModel = viewModel,
                                        navController = navController,
                                        modifier = Modifier
                                    )
                                }

                                // Add spacing between contacts (but not after the last one)
                                if (index < favoriteContacts.lastIndex) {
                                    Spacer(Modifier.height(screenHeight * 0.015f))
                                }
                            }
                        }


                        Spacer(Modifier.height(screenHeight * 0.02f))
                    }

                    Spacer(Modifier.height(screenHeight * 0.03f))
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = (screenWidth.value * 0.025).dp),
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

                    if (filteredContacts.isEmpty()) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.02).dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.People,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size((screenWidth.value * 0.3).dp)
                            )
                            Spacer(Modifier.height(screenHeight * 0.015f))
                            Text(
                                text = "No Contacts Found",
                                fontWeight = FontWeight.Bold,
                                fontSize = (screenWidth.value * 0.05).sp,
                                color = Color.DarkGray
                            )
                            Spacer(Modifier.height(screenHeight * 0.01f))
                            Text(
                                text = "Try adjusting your search",
                                fontWeight = FontWeight.Normal,
                                fontSize = (screenWidth.value * 0.035).sp,
                                color = Color.Gray
                            )

                            Spacer(Modifier.height(screenHeight * 0.02f))
                        }
                    }
                    else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (screenWidth.value * 0.01).dp)
                        ) {

                            filteredContacts.forEach { contact ->
                                val permissionState = rememberPermissionState(Manifest.permission.CALL_PHONE)
                                val context = navController.context

                                val dismissState = rememberDismissState(
                                    confirmStateChange = { dismissValue ->
                                        if (dismissValue == DismissValue.DismissedToEnd) {
                                            if (permissionState.status.isGranted) {
                                                val intent = Intent(Intent.ACTION_CALL).apply {
                                                    data = Uri.parse("tel:${contact.phoneNo}")
                                                }
                                                context.startActivity(intent)
                                            } else {
                                                permissionState.launchPermissionRequest()
                                            }
                                        }
                                        false // Don't dismiss
                                    }
                                )

                                // Wrap the whole SwipeToDismiss with padding
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = (screenWidth.value * 0.04).dp, vertical = 6.dp)
                                ) {
                                    SwipeToDismiss(
                                        state = dismissState,
                                        directions = setOf(DismissDirection.StartToEnd),
                                        background = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(20.dp))
                                                    .background(Color(0xFF4CAF50)),
                                                contentAlignment = Alignment.CenterStart
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(start = 24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Call,
                                                        contentDescription = "Call",
                                                        tint = Color.White,
                                                        modifier = Modifier.size(24.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(12.dp))
                                                    Text(
                                                        text = "Calling...",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        },
                                        dismissContent = {
                                            ContactItemsUI(
                                                contact = contact,
                                                viewModel = viewModel,
                                                navController = navController,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(20.dp))
                                                    .background(Color.White)
                                                    .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
                                            )
                                        }
                                    )
                                }
                            }


                        }
                        Spacer(Modifier.height(screenHeight * 0.02f))
                    }
                }
                Spacer(Modifier.height(screenHeight * 0.03f))

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
        val gradientColors = listOf(
            listOf(Color(0xFF55036B), Color(0xFF8A69C4)), // Purple
            listOf(Color(0xFF045196), Color(0xFF42A5F5)), // Blue
            listOf(Color(0xFFE1125E), Color(0xFFAD0ED7)), // Green
            listOf(Color(0xFF5D4036), Color(0xFF936A6E)), //Brown
            listOf(Color(0xFFFD5C16), Color(0xFFFAB147)), // Orange

        )

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

                val nameHash = contact.name.hashCode()
                val gradient = gradientColors[abs(nameHash) % gradientColors.size]

                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .border(1.dp, Color.LightGray, CircleShape)
                        .clip(CircleShape)
                        .background(
                            Brush.horizontalGradient(
                                gradient
                            )
                        ),
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
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    maxLines = 1,
                    color = Color(0xFF032A4D),
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )

            }
            Row(
                modifier = Modifier
                    .padding( end = 1.dp)
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ){
                Icon(
                    imageVector = if (contact.isFavorite == 1) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Toggle Favorite",
                    tint = if (contact.isFavorite == 1) Color(0xFFFFC107) else Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            val updatedContact = contact.copy(isFavorite = if (contact.isFavorite == 1) 0 else 1)
                            viewModel.upsertContact(updatedContact)
                        }
                )
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

