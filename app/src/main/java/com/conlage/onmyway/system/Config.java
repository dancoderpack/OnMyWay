package com.conlage.onmyway.system;

public class Config {

    public static class Group{
        private boolean isRun;
        private String message1;
        private String message2;
        private int delay;

        public Group(boolean isRun, String message1, String message2, int delay) {
            this.isRun = isRun;
            this.message1 = message1;
            this.message2 = message2;
            this.delay = delay;
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

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }
    }

}
