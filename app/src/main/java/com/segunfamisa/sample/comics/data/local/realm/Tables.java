package com.segunfamisa.sample.comics.data.local.realm;


/**
 * Table names and column names: useful for migrations and query.
 */
public final class Tables {

    public static class ComicDate {
        public static final String date = "date";
        public static final String type = "type";
    }

    public static class CreatorSummary {
        public static final String name = "name";
        public static final String role = "role";
        public static final String resourceUri = "resourceUri";
    }

    public static class ComicCreators {
        public static final String available = "available";
        public static final String collectionUri = "collectionUri";
        public static final String items = "items";
    }

    public static class ComicPrice {
        public static final String type = "type";
        public static final String price = "price";
    }

    public static class Image {
        public static final String path = "path";
        public static final String extension = "extension";
    }

    public class Comic {
        public static final String id = "id";
    }

    /**
     * Build a realm query - concatenating the param values with ".".
     * @param values - values to use to build the query
     * @return query string with field names separated by "."
     */
    public static String buildQuery(String... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

}
