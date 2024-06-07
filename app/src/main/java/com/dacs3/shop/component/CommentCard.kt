package com.dacs3.shop.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dacs3.shop.model.User
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Primary50
import com.dacs3.shop.ui.theme.PrimaryTransparent10

@Composable
fun CommentCard(user: User, content: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (user.avatar != null) {
                    Image(painter = rememberAsyncImagePainter(model = user.avatar), contentDescription = "avatar", modifier = Modifier.size(40.dp).clip(
                        CircleShape))
                } else {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = null, modifier = Modifier.size(40.dp))
                }
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )
            }
            SpacerHeight(int = 8)
            Text(
                text = content,
                fontWeight = FontWeight(450),
                fontSize = 12.sp,
                color = Black50
            )
        }
    }
}

@Composable
fun CommentInput(value: String, onValueChange: (String) -> Unit, onComment: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(PrimaryTransparent10)
                .padding(5.dp)
        ) {
            CustomTextField(value = value, onValueChange = onValueChange, label = "Comment")
            ButtonPrimary(
                modifier = Modifier.align(Alignment.End),
                onClick = onComment,
                text = "Comment"
            )
        }
    }
}
