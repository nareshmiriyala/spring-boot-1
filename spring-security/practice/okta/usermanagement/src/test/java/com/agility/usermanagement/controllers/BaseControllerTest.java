package com.agility.usermanagement.controllers;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class BaseControllerTest {

    protected static final String USER_TOKEN = "eyJraWQiOiJJVHUyZVZTaWVrR2N2d19FRkYwYmgwMXlZSmV1Uy1ZMzFrd0NDV09Yc1k4IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiIwMHVuZmlwdjZLVHYwV0F0VTM1NiIsIm5hbWUiOiJ1c2VyIHVzZXIiLCJ2ZXIiOjEsImlzcyI6Imh0dHBzOi8vZGV2LTM0MzM2Mi5va3RhLmNvbS9vYXV0aDIvZGVmYXVsdCIsImF1ZCI6IjBvYW40bXQ4eURzY0J3QU9GMzU2IiwiaWF0IjoxNTYwODU2MjUzLCJleHAiOjE1NjA4NTk4NTMsImp0aSI6IklELkVsQ3JNb3JmbGt6X0pqVWNVRTc4MjhtazJmRTZrU2hoVl9GRGM2MnFUMzQiLCJhbXIiOlsicHdkIl0sImlkcCI6IjAwb240aGcwc0VMWU1KS0VXMzU2Iiwibm9uY2UiOiJxamhvaDR3b3g3YyIsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXJAZ21haWwuY29tIiwiYXV0aF90aW1lIjoxNTYwODU2MjUzLCJhdF9oYXNoIjoiMXRXMWJIUGdWSmhtck9JaHprektDdyJ9.cUBQqyLFBPNuZjJgQMfpgLTJhDH5qCr61y7lMX30AOMNLPYiY3FRX38OxYGj6tSOqKGpB1_i-MyrzAdJd3yxuYS3Yjh369Shxj-siIsZiexnzhRWhWSnvAJEzUwtTIAVk_UlEBfHbZcsMpfL9cRCwV-1pG51OVQfOS6bGqyxGAQhtbzUwEwFl4_GCb997plqKXWGg1afrPmjCwX8_gHz8qtJfcycbWhAPsAAaleNg7C9F63LAbBXM1GvFh3Wc-eWxOKGO0Rr2o6H9xmggMHWzLyQ1M7jolBrsgVQbfKUiN0X88qmhuZrGoR4pYQmA4il-6OkqNyjBCy4eU2tTiALYA";
    protected static final String MANAGER_TOKEN = "eyJraWQiOiJJVHUyZVZTaWVrR2N2d19FRkYwYmgwMXlZSmV1Uy1ZMzFrd0NDV09Yc1k4IiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkdkQmxidlZEemxORDVmR3paNmNNMG54SkJacEMxUTBoTG1qTi1FSWJ0V1EiLCJpc3MiOiJodHRwczovL2Rldi0zNDMzNjIub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTYwODU2MTUyLCJleHAiOjE1NjA5NDI1NTIsImNpZCI6IjBvYW40bXQ4eURzY0J3QU9GMzU2IiwidWlkIjoiMDB1bmZoM2ZnTmc4a3k5WUgzNTYiLCJzY3AiOlsicHJvZmlsZSIsIm9wZW5pZCJdLCJzdWIiOiJtYW5hZ2VyQGdtYWlsLmNvbSIsImdyb3VwcyI6WyJFdmVyeW9uZSIsIlVzZXIiLCJNYW5hZ2VyIl19.aqQ6OBLJDGgsfSpjeFjF5e6epQGVvKWLH1FVsSS_eVTqOpu-BKmC85UNIRLkYVci7Rwld_LYKOhWVmrEWeL6tV2-AzPwMI3acgA9_LKeIoom1ukeJeXK7sLk7ruzSCg3cVaDbqiwFa49VDGysmHcYxCNQlwI_HvjYV7JQtR_ZpFdg6DVpPEXvJRzjeqT-ZxPQ1YIneKALb76CYQQ1imQa8EraCIqX1FB56bEaBjD-p9jwR0SSopEkiEvkNfXV5KBLtCIwh1e_pMdBn043CjSdZOIKsmmLutN1BnqvoiS-h8KEIy6YqKyqRqS_SbZvK91eP3FRpwn9ZlfN0AOIhOcLA";
    protected static final String ADMIN_TOKEN = "eyJraWQiOiJJVHUyZVZTaWVrR2N2d19FRkYwYmgwMXlZSmV1Uy1ZMzFrd0NDV09Yc1k4IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiIwMHVuZmg1MTRZQk1TRlNkQTM1NiIsIm5hbWUiOiJhZG1pbiBhZG1pbiIsInZlciI6MSwiaXNzIjoiaHR0cHM6Ly9kZXYtMzQzMzYyLm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiMG9hbjRtdDh5RHNjQndBT0YzNTYiLCJpYXQiOjE1NjA4NTYzMDksImV4cCI6MTU2MDg1OTkwOSwianRpIjoiSUQuYW1NN3prUGVDczhOR0FuS0ZWMU9wZERkalJvNHdWQnVrM2NTUWk1blU0MCIsImFtciI6WyJwd2QiXSwiaWRwIjoiMDBvbjRoZzBzRUxZTUpLRVczNTYiLCJub25jZSI6InFqaG9oNHdveDdjIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW5AZ21haWwuY29tIiwiYXV0aF90aW1lIjoxNTYwODU2MzA4LCJhdF9oYXNoIjoiMEY0TmtxbWc2QW1pVmNRZGp4SGZFdyJ9.OWVjuXV90EYXn7xDLBl5X1veG60PPfLChTvcMYyDc7OT0ed5RUSZS20BnKGqWAitAqwRbuHZhQFzeJjMDErxBVe-ykMVFvS3YYwvYFBSS1b_7mbYCMWjUN_G2uqDnELjAXEpLhvPJgQJ0XgN7Dd2hVdket8HtJ3I6YsNkaiLH_8XlxtbkryJ9Nw_u-CUPWMJiBheO2lGdr_VH_ibXV1iWwxbp2Ox0RkRVgtSe060X-SZJP-alP3Re3Q4m4e6Vgt9McRxIjxiwABwV1yCt2jN3l4-qH8HrhJNhEZ2eFa4CmlH79vGpyMpWj9WcG5GnmVbVSbnO2kMHgi5aa89Bxrv0g";

    protected Faker faker = new Faker();

    @Autowired
    protected MockMvc mockMvc;
}
