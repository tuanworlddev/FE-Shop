package com.dacs3.shop.repository

import com.dacs3.shop.model.Comment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()
    private val commentCollection = db.collection("comments")

    suspend fun addComment(comment: Comment) {
        commentCollection.add(comment).await()
    }

    suspend fun getComments(productId: Int): List<Comment> {
        return try {
            val querySnapshot = commentCollection
                .whereEqualTo("productId", productId)
                .get()
                .await()
            querySnapshot.documents.map { it.toObject(Comment::class.java)!! }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateItem(comment: Comment) {
        commentCollection.document(comment.id!!).set(comment).await()
    }

    suspend fun deleteItem(commentId: String) {
        commentCollection.document(commentId).delete().await()
    }
}