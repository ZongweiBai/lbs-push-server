package com.guotion.pushserver.client.constant;

public abstract interface CommonConstant {
    public static abstract interface VALUE {
        public static final int RESULT_SUCCESS = 0;
        public static final int RESULT_FAILURE = 1;
        public static final int VERSION_LOW = 2;
    }

    public static abstract interface KEY {
        public static final String ATTACH_TEXT_KEY = "attach_text";
        public static final String RESPONSE_DATA_KEY = "response_data_key";
        public static final String KEY_STATE = "state";
    }
}