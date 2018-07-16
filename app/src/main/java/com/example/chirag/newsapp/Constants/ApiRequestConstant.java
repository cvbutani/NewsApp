package com.example.chirag.newsapp.Constants;

public class ApiRequestConstant {

    public static final String SCHEME_PART = "https://content.guardianapis.com/search?";

    //  Resource Section names.
    public static final String RESOURCE_SECTION_BUSINESS = "business";
    public static final String RESOURCE_SECTION_ENTERTAINMENT = "tv-and-radio";
    public static final String RESOURCE_SECTION_SCIENCE = "science";
    public static final String RESOURCE_SECTION_HEALTH = "healthcare-network";
    public static final String RESOURCE_SECTION_SPORT = "sport";
    public static final String RESOURCE_SECTION_TECHNOLOGY = "technology";
    public static final String RESOURCE_SECTION_EDUCATION = "education";
    public static final String RESOURCE_SECTION_CULTURE = "culture";

    //  Select Resource field.
    public static final String RESOURCE_FIELDS = "all";

    //  API Key.
    public static final String API_KEY = "yourApiKey";

    //  Scheme parts required in order.
    public static final String SCHEME_PART_QUERY = "q";
    public static final String SCHEME_PART_SECTION = "section";
    public static final String SCHEME_PART_SHOW_FIELDS = "show-fields";
    public static final String SCHEME_PART_API = "api-key";
    public static final String SCHEME_PART_ORDER_BY = "order-by";
    public static final String SCHEME_PART_PAGE_SIZE = "page-size";

    //  User Input Replacement from empty space to %20%.
    public static final String INPUT_REPLACEMENT = "%20%";

    public static final String BUNDLE_STRING_EXTRA = "news_url";

    //  Setup Fragmnet Heading constants.
    public static final String LISTVIEW_EXTRA_INFO = "info";
    public static final String LISTVIEW_EXTRA_HEADER = "header";

    //  JSON Parse Key values constants.
    public static final String JSON_PARSE_KEY_RESPONSE = "response";
    public static final String JSON_PARSE_KEY_RESULTS = "results";
    public static final String JSON_PARSE_KEY_SECTION_NAME = "sectionName";
    public static final String JSON_PARSE_KEY_TITLE = "webTitle";
    public static final String JSON_PARSE_KEY_URL = "webUrl";
    public static final String JSON_PARSE_KEY_PUBLICATION_DATE = "webPublicationDate";
    public static final String JSON_PARSE_KEY_PRODUCTION_OFFICE = "productionOffice";
    public static final String JSON_PARSE_KEY_THUMBNAIL = "thumbnail";
    public static final String JSON_PARSE_KEY_BODY_TEXT = "bodyText";
    public static final String JSON_PARSE_KEY_FIELDS = "fields";

    public ApiRequestConstant() {
        //  Empty constructor is require.
    }
}
