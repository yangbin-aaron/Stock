package com.aaron.myviews.model.newmodel;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/23.
 */
public class ReplyRecord {

    private ArrayList<ReplyRecordEntity> onlist;

    public ArrayList<ReplyRecordEntity> getOnlist() {
        return onlist;
    }

    public static class ReplyRecordEntity {
        /**
         * id : null
         * userId : null
         * title : 0
         * content : 打听打听更丰富
         * userMobile : 15093326577
         * type : 0
         * serverId : null
         * subTime : 2015-09-23 12:22:58
         * status : 0
         * des : null
         * answerTime : null
         * message : null
         * updateTime : null
         */

        private String id;
        private String userId;
        private int title;
        private String content;
        private String userMobile;
        private int type;
        private String serverId;
        private String subTime;
        private int status;
        private String des;
        private String answerTime;
        private String message;
        private String updateTime;


        public String getContent() {
            return content;
        }

        public String getSubTime() {
            return subTime;
        }


        public String getAnswerTime() {
            return answerTime;
        }

        public String getMessage() {
            return message;
        }

        public boolean isHaveReply(){
            if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(answerTime)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static class ReplyRecordIsRead {

        private Integer isbool;

        public Integer getIsbool() {
            return isbool;
        }

        public boolean hasUnreadReplies() {
            return isbool > 0;
        }
    }
}
