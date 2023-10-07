package com.example.csc_325;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.util.concurrent.ExecutionException;


public class FirestoreExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Initialize Firestore
        Firestore firestore = FirestoreOptions.getDefaultInstance().getService();

        // Define the user data
        String userId = "uniqueUserId";
        String userName = "John Doe";

        // Create a reference to the Firestore document for the user
        DocumentReference userRef = firestore.collection("users").document(userId);

        // Create a user object
        User user = new User(userId, userName);

        // Add the user to Firestore
        userRef.set(user);

        // Verify that the user has been added
        DocumentSnapshot userSnapshot = userRef.get().get();
        if (userSnapshot.exists()) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("User was not added.");
        }
    }
}