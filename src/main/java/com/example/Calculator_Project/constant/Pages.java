package com.example.Calculator_Project.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class Pages {

    public static final String HOME="index";

    public static final String LOGIN="login";

    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class Registration{
        public static final String ROUTE="registration";


    }


    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class User{
        public static final String ROUTE="user/home";
        public static final String VIEW_ALL_CALCULATIONS="user/calculations";

        public static final String RESULT="user/result";

    }

    @NoArgsConstructor(access= AccessLevel.PRIVATE)
    public static final class Admin{
        public static final String ROUTE="admin/home";
        public static final String SEARCH_CALCULATIONS="admin/search";

        public static final String RESULT="user/result";

        public static final String ADD_ADMIN="admin/add_admin";
        public static final String SUCCESSFUL_ADMIN_ADDITION="admin/success";

    }
}
