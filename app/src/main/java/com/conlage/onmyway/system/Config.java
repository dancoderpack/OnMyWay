package com.conlage.onmyway.system;

public class Config {

    public static class Group{
        private boolean isRun;
        private String message1;
        private String message2;

        private int postID1 = -1;
        private int postID2 = -1;

        public Group(boolean isRun, String message1, String message2) {
            this.isRun = isRun;
            this.message1 = message1;
            this.message2 = message2;
        }

        public boolean isRun() {
            return isRun;
        }

        public void setRun(boolean run) {
            isRun = run;
        }

        public String getMessage1() {
            return message1;
        }

        public void setMessage1(String message1) {
            this.message1 = message1;
        }

        public String getMessage2() {
            return message2;
        }

        public void setMessage2(String message2) {
            this.message2 = message2;
        }

        public int getPostID1() {
            return postID1;
        }

        public void setPostID1(int postID1) {
            this.postID1 = postID1;
        }

        public int getPostID2() {
            return postID2;
        }

        public void setPostID2(int postID2) {
            this.postID2 = postID2;
        }
    }

}
