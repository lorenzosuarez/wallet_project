package com.stori.challenge.util.mappers

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.stori.challenge.data.models.UserProfile
import com.stori.challenge.data.models.UserTransaction
import com.stori.challenge.data.models.UserTransactionData
import com.stori.challenge.domain.entities.Transaction
import com.stori.challenge.domain.entities.TransactionData
import com.stori.challenge.domain.entities.User
import com.stori.challenge.util.constants.FirestoreUserConstants.FIELD_EMAIL
import com.stori.challenge.util.constants.FirestoreUserConstants.FIELD_ID_IMAGE_URL
import com.stori.challenge.util.constants.FirestoreUserConstants.FIELD_LAST_NAME
import com.stori.challenge.util.constants.FirestoreUserConstants.FIELD_NAME
import com.stori.challenge.util.constants.FirestoreUserConstants.FIELD_UID

/**
 * Maps an [AuthResult] to a [Result] containing a [User].
 * If the [AuthResult] does not contain a user, it returns a [Result.failure] with an [FirebaseAuthException].
 *
 * @return A [Result] wrapping a [User] on success or an [FirebaseAuthException] on failure.
 */
fun AuthResult.toUserResult(): Result<User> {
    return this.user?.toDomainUser()?.let { Result.success(it) }
        ?: Result.failure(FirebaseAuthException("Auth error", "Failed to get Firebase user"))
}

/**
 * Converts a [FirebaseUser] to a domain [User] object.
 *
 * @return A domain [User] object.
 */
fun FirebaseUser.toDomainUser() = User(
    uid = this.uid,
    email = this.email ?: "",
    name = "",
    lastName = "",
    documentImageUrl = "",
)

/**
 * Converts a [DocumentSnapshot] to a domain [User] object. Throws [UserNotFoundException] if the snapshot does not exist.
 *
 * @return A domain [User] object.
 * @throws UserNotFoundException If the [DocumentSnapshot] does not exist.
 */
fun DocumentSnapshot.toUser(): User {
    return try {
        if (exists()) {
            User(
                uid = getString(FIELD_UID).orEmpty(),
                name = getString(FIELD_NAME).orEmpty(),
                lastName = getString(FIELD_LAST_NAME).orEmpty(),
                email = getString(FIELD_EMAIL).orEmpty(),
                documentImageUrl = getString(FIELD_ID_IMAGE_URL).orEmpty(),
            )
        } else {
            throw UserNotFoundException("User with the provided UID does not exist.")
        }
    } catch (e: Exception) {
        throw e
    }
}

/**
 * Converts a [DocumentSnapshot] to a [TransactionData] object if the snapshot exists, null otherwise.
 *
 * @return A [TransactionData] object or null if the snapshot does not exist.
 */
fun DocumentSnapshot.toUserTransactionData(): TransactionData? {
    return if (exists()) {
        val transactionsData = (get("transactions") as? List<Map<String, Any>>) ?: emptyList()

        val transactionsList = transactionsData.map {
            Transaction(
                it["transactionAmount"] as? String ?: "0.0",
                it["transactionDetail"] as? String ?: "",
                (it["transactionNumber"] as? Long)?.toInt() ?: 0,
                (it["transactionType"] as? Long)?.toInt() ?: 0,
            )
        }

        TransactionData(getDouble("amount") ?: 0.0, transactionsList)
    } else {
        null
    }
}

/**
 * Converts a [TransactionData] to a [UserTransactionData] object.
 *
 * @return A [UserTransactionData] object.
 */
fun TransactionData.toUserTransactionData(): UserTransactionData {
    return UserTransactionData(
        amount = this.amount,
        transactions = this.transactions.map {
            it.toUserTransaction()
        },
    )
}

/**
 * Converts a [Transaction] to a [UserTransaction] object.
 *
 * @return A [UserTransaction] object.
 */
private fun Transaction.toUserTransaction(): UserTransaction {
    return UserTransaction(
        transactionAmount = this.transactionAmount,
        transactionDetail = this.transactionDetail,
        transactionNumber = this.transactionNumber,
        transactionType = this.transactionType,
    )
}

/**
 * Converts a domain [User] to a [UserProfile] data model.
 *
 * @return A [UserProfile] data model object.
 */
fun User.toUserProfile(): UserProfile {
    return UserProfile(
        uid = this.uid,
        email = this.email,
        name = this.name,
        lastName = this.lastName,
        documentImageUrl = this.documentImageUrl,
    )
}

/**
 * Exception to be thrown when a user is not found within a Firebase [DocumentSnapshot].
 *
 * @param message The detail message string of this throwable.
 */
class UserNotFoundException(message: String) : Exception(message)
