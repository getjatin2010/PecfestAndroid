package in.pecfest.www.pecfest.Model.Common;

import android.os.Environment;

/**
 * Created by jatin on 20/1/16.
 */
public class Constants {

    public static String appVersion = "3.1";
    public static int SPLASH_SCREEN_WAIT = 2000;
    public static final String STORAGE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pecfest/.data/";
    public static final String STORAGE_PATH_DP= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pecfest/";

    public static final int  PICK_IMAGE_REQUEST = 123;
    public static String newNotifs = "newnotifs";
    public static class METHOD
    {

        public static final String RESGISTRATION = "register";
        public static final String SPONSOR_REQUEST = "getSponsorlist";
        public static final String LOAD_SPONSER = "loadSponsor";
        public static final String EVENT_DETAILS = "getEventDetails";
        public static final String LECTURE_DETAILS = "getLectureDetails";
        public static final String VERIFY = "verifyIdWithoutPhone";
        public static final String LOGIN = "checkLogin";
        public static final String GET_MEGA_SHOWS = "getMegaShowImgs";
        public static final String GET_POSTERS = "getPosterURLs";
        public static final String REGISTERED_EVENT = "EventsForId";
        public static final String APP_PERMISSIONS = "appPermissions";
        public static final String GET_FILTERS = "getFilters";
        // We can add method name here

    }

    public static class FILENAME
    {
        public static final String HOMEWORK_FILE_NAME = "Homework_File";
       // We can add other Constants by making static classes
    }

    public static class PERMISSIONS
    {
        public static final String WARNING = "warning";
        public static final String STOP = "stop";
    }

}
