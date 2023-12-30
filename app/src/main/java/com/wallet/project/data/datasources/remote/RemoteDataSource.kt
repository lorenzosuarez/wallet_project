package com.wallet.project.data.datasources.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.wallet.project.domain.entities.AuthenticationState
import com.wallet.project.domain.entities.AuthenticationState.Authenticated
import com.wallet.project.domain.entities.AuthenticationState.Unauthenticated
import com.wallet.project.domain.entities.TransactionData
import com.wallet.project.domain.entities.User
import com.wallet.project.util.constants.FirestoreUserConstants.COLLECTION_TRANSACTIONS
import com.wallet.project.util.constants.FirestoreUserConstants.COLLECTION_USERS
import com.wallet.project.util.constants.FirestoreUserConstants.PREFIX_ID_PICTURE
import com.wallet.project.util.extensions.await
import com.wallet.project.util.mappers.toUser
import com.wallet.project.util.mappers.toUserResult
import com.wallet.project.util.mappers.toUserTransactionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class RemoteDataSource {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val firebaseAuth: FirebaseAuth by lazy {
        Firebase.auth
    }

    suspend fun login(email: String, password: String): Result<User> =
        performAuthOperation {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }

    /**
     * Performs an authentication operation asynchronously and returns the result.
     *
     * This function is a suspend function that takes another suspend function as a parameter,
     * which should be the actual authentication operation returning a Firebase Task of AuthResult.
     * It converts the AuthResult into a Result containing a User, handling any exceptions by
     * returning a Result.failure.
     *
     * @param authOperation The suspend function representing the Firebase authentication operation.
     * @return A Result object containing the User on success or an Exception on failure.
     */
    private suspend fun performAuthOperation(
        authOperation: suspend () -> Task<AuthResult>,
    ): Result<User> = withContext(Dispatchers.IO) {
        runCatching { authOperation().await()?.toUserResult() }
            .getOrElse { e ->
                if (e is FirebaseAuthException) {
                    Result.failure(e)
                } else {
                    Result.failure(
                        FirebaseAuthException(
                            "Auth error",
                            e.message
                                ?: "An unexpected error occurred during Firebase authentication.",
                        ),
                    )
                }
            } ?: Result.failure(
            FirebaseAuthException(
                "Auth error",
                "An unexpected error occurred during Firebase authentication.",
            ),
        )
    }

    suspend fun getUserProfile(uid: String): Result<User> {
        return withContext(Dispatchers.IO) {
            runCatching {
                firestore
                    .collection(COLLECTION_USERS)
                    .document(uid)
                    .get()
                    .await()
                    ?.toUser()
                    ?: throw Exception("User not found")
            }.fold(
                onSuccess = { userProfile ->
                    Result.success(userProfile)
                },
                onFailure = { e ->
                    Result.failure(e)
                },
            )
        }
    }

    suspend fun getTransactions(uid: String): Result<TransactionData> {
        return withContext(Dispatchers.IO) {
            runCatching {
                firestore
                    .collection(COLLECTION_TRANSACTIONS)
                    .document(uid)
                    .get()
                    .await()
                    ?.toUserTransactionData()
                    ?: throw Exception("User transactions not found")
            }.fold(
                onSuccess = { transactionData ->
                    Result.success(transactionData)
                },
                onFailure = { e ->
                    Result.failure(e)
                },
            )
        }
    }

    suspend fun register(
        email: String,
        password: String,
        name: String,
        lastName: String,
    ): Result<User> = withContext(Dispatchers.IO) {
        try {
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            authResult?.user?.uid?.let { uid ->
                val newUser = User(
                    uid = uid,
                    email = email,
                    name = name,
                    lastName = lastName,
                    documentImageUrl = "$PREFIX_ID_PICTURE$uid",
                )

                firestore.collection(COLLECTION_USERS)
                    .document(uid)
                    .set(newUser)
                    .await()

                Result.success(newUser)
            } ?: throw FirebaseAuthException("Registration error", "User UID was null")
        } catch (exception: FirebaseAuthException) {
            Result.failure(exception)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getAuthState(): Flow<AuthenticationState> {
        return callbackFlow {
            delay(2000)
            val listener = FirebaseAuth.AuthStateListener { auth ->
                val state = auth.uid?.let { uid -> Authenticated(uid = uid) } ?: Unauthenticated

                trySend(state).isSuccess
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }
    }
}
