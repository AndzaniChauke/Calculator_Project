package com.example.Calculator_Project.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class RequestRouting {
    public static final String HOME="/";


    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class Cache{
        public static final String ROUTE="/cache";

        public static final String SAVE=ROUTE+"/save";

        public static final String GET=ROUTE+"/get";

    }

    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class Login{
        public static final String ROUTE="/login";


    }



    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class Registration{
        public static final String ROUTE="/registration";
        public static final String DONE_REGISTRATION="/done_registration";

    }

    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class User{
        public static final String ROUTE="/user";
        public static final String HOME="/home";
        public static final String VIEW_ALL_CALCULATIONS="/calculations";

    }

    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class Admin{
        public static final String ROUTE="/home";
        public static final String SEARCH_CALCULATIONS="/search";
        public static final String ADD_ADMIN="/add_admin";

    }
}
