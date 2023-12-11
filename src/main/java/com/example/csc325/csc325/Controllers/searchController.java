package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class searchController {
    @FXML
    public TextField searchField;
    @FXML
    public VBox jobListingsContainer;

    // Replace the local list with a Firestore collection reference
    private final CollectionReference jobCollection;


    public searchController() {
        // Assuming 'jobs' is the name of your Firestore collection
        this.jobCollection = FirestoreClient.getFirestore().collection("jobs");
    }

    public void onLoad() throws ExecutionException, InterruptedException {
        List<JobPosting> recent = fetchJobs("", 10); // Fetch recent jobs with a limit of 10
        displayJobs(recent);
    }

    public void searchJobs(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        String searchTerm = searchField.getText().toLowerCase();
        jobListingsContainer.getChildren().clear();

        // Split the input string into multiple keywords using space as a separator
        String[] keywords = searchTerm.split("\\s+");

        // Fetch jobs for each keyword
        for (String keyword : keywords) {
            List<JobPosting> jobs = fetchJobs(keyword, 5); // Adjust the limit as needed
            displayJobs(jobs);
        }
    }

    public List<JobPosting> fetchJobs(String keyword, int limit) throws ExecutionException, InterruptedException {
        if (keyword.isEmpty()) {
            ApiFuture<QuerySnapshot> query = jobCollection
                    .orderBy("unixTime", Query.Direction.DESCENDING)
                    .limit(10)
                    .get();

            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            List<JobPosting> recentJobs = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                recentJobs.add(document.toObject(JobPosting.class));
            }
            return recentJobs;
        }
        List<JobPosting> jobs = new ArrayList<>();
        try {
            // Query Firestore for jobs using the keyword
            ApiFuture<QuerySnapshot> future = jobCollection
                    .whereArrayContains("keywords", keyword)
                    .get();
            QuerySnapshot querySnapshot = future.get();

            // Display the matching jobs
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                JobPosting job = document.toObject(JobPosting.class);
                if (job != null) {

                    // Check if the job ID has already been pulled

                    jobs.add(job);

                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return jobs;
    }

//    public void onLoad() throws ExecutionException, InterruptedException {
//        List<JobPosting> recent = fetchRecentJobs();
//        displayJobs(recent);
//    }
//
//    public void searchJobs(ActionEvent actionEvent) {
//        String searchTerm = searchField.getText().toLowerCase();
//        jobListingsContainer.getChildren().clear();
//
//        // Split the input string into multiple keywords using space as a separator
//        String[] keywords = searchTerm.split("\\s+");
//
//        for (String keyword : keywords) {
//            List<JobPosting> jobs = fetchJobs(keyword);
//            for (JobPosting job : jobs) {
//                HBox jobUI = createJobListingUI(job);
//                jobListingsContainer.getChildren().add(jobUI);
//            }
//        }
//    }


//    public List<JobPosting> fetchJobs(String keyword) {
//        List<JobPosting> jobs = new ArrayList<>();
//        List<String> pulledJobIds = new ArrayList<>(); // Use a List to store pulled job IDs
//
//        try {
//            // Query Firestore for jobs using the keyword
//            ApiFuture<QuerySnapshot> future = jobCollection.whereArrayContains("keywords", keyword).get();
//            QuerySnapshot querySnapshot = future.get();
//
//            // Display the matching jobs
//            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
//                JobPosting job = document.toObject(JobPosting.class);
//                if (job != null) {
//                    String jobId = job.getId();
//
//                    // Check if the job ID has already been pulled
//                    if (!pulledJobIds.contains(jobId)) {
//                        jobs.add(job);
//                        pulledJobIds.add(jobId);
//                    }
//                }
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return jobs;
//    }


    public HBox createJobListingUI(JobPosting job) {
        HBox jobBox = new HBox(10);
        jobBox.getStyleClass().add("job-listing");

        VBox detailsBox = new VBox(5);

        Label titleLabel = new Label(job.getTitle()); // Assuming getTitle() is defined in Post
        titleLabel.getStyleClass().add("job-title");

        Label companyNameLabel = new Label(job.getCompany());
        companyNameLabel.getStyleClass().add("company-name");

        Label locationLabel = new Label("Location: Unknown"); // Replace with actual location if available
        locationLabel.getStyleClass().add("job-location");

        Label salaryLabel = new Label("Salary: " + job.getSalary());
        salaryLabel.getStyleClass().add("job-description");

        Label descriptionLabel = new Label(job.getDescription()); // Assuming getDescription() is defined in Post
        descriptionLabel.getStyleClass().add("job-description");

        Button applyButton = new Button("Apply");
        applyButton.getStyleClass().add("apply-button");
        applyButton.setOnAction(event -> {
            try {
                handleApplyAction(job);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        detailsBox.getChildren().addAll(titleLabel, companyNameLabel, locationLabel, salaryLabel, descriptionLabel, applyButton);
        jobBox.getChildren().add(detailsBox);

        return jobBox;
    }


    private void handleApplyAction(JobPosting job) throws IOException {
        System.out.println("Applied for: " + job.getId());
    }

//    public List<JobPosting> fetchRecentJobs() throws ExecutionException, InterruptedException {
//
    // Assuming 'jobs' is your collection and 'postedDate' is the timestamp field
//        ApiFuture<QuerySnapshot> query = jobCollection
//                .orderBy("unixTime", Query.Direction.DESCENDING)
//                .limit(10)
//                .get();
//
//        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
//        List<JobPosting> recentJobs = new ArrayList<>();
//        for (QueryDocumentSnapshot document : documents) {
//            recentJobs.add(document.toObject(JobPosting.class));
//        }
//        return recentJobs;
//    }

    public void employeeProfileLoader(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showEmployeeProfileScene();
    }

    private void displayJobs(List<JobPosting> jobs) {
        for (JobPosting job : jobs) {
            HBox jobUI = createJobListingUI(job);
            jobListingsContainer.getChildren().add(jobUI);
        }
    }

}
