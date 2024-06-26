package com.example.supportservice.auth.presentation.registratrionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.supportservice.R
import com.example.supportservice.core.presentation.components.CustomButton
import com.example.supportservice.core.presentation.components.CustomTextField
import com.example.supportservice.core.util.getEnable

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    LaunchedEffect(key1 = state.registrationRespState.response) {
        if (state.registrationRespState.response != null) {
            onEvent(RegistrationEvent.GoToAuthScreen)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "appIcon",
                modifier = Modifier
                    .padding(22.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(size = 15.dp))
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(state.rolesState) {
                    CustomButton(
                        text = it.role,
                        textSize = 16,
                        isLoading = false,
                        height = 55,
                        shape = 5,
                        selected = state.selectedRole.roleId == it.roleId,
                        modifier = Modifier.width(170.dp)
                    ) {
                        onEvent(RegistrationEvent.OnRoleChange(it))
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.username,
                hint = "никнейм",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnUsernameChange(
                            it
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.email,
                hint = "почта",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnEmailChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.phone,
                hint = "телефон",
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnPhoneChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.password,
                hint = "пароль",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnPasswordChange(
                            it
                        )
                    )
                },
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.passwordConfirm,
                hint = "подтвердите пароль",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnPasswordConfirmChange(
                            it
                        )
                    )
                },
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.height(5.dp))

            CustomButton(
                text = "Регистрироваться",
                textSize = 16,
                isLoading = state.registrationRespState.isLoading,
                enabled = getEnable(state),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 22.dp, top = 40.dp)
            ) {
                onEvent(RegistrationEvent.Registration)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp), contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Авторизация",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onEvent(RegistrationEvent.GoToSignUp) })
        }
    }
}