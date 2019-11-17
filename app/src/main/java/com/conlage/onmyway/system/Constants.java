package com.conlage.onmyway.system;

public class Constants {

    public static final int GROUP_ID_1 = 38411582; //Нам по пути Оса-Пермь-Оса
    public static final int GROUP_ID_2 = 36875025; //ПОПУТЧИКИ из Осы в Пермь
    public static final int GROUP_ID_3 = 40343014; //ПОПУТЧИКИ Оса - Пермь - Оса
    public static final int GROUP_ID_4 = 155096355;//Попутка Оса-Пермь-Оса

    public static final String GROUP_TITLE_1 = "Нам по пути Оса-Пермь-Оса";
    public static final String GROUP_TITLE_2 = "ПОПУТЧИКИ из Осы в Пермь";
    public static final String GROUP_TITLE_3 = "ПОПУТЧИКИ Оса - Пермь - Оса";
    public static final String GROUP_TITLE_4 = "Попутка Оса-Пермь-Оса";

    public static final String GROUP_IMAGE_1 = "https://sun9-62.userapi.com/c638525/v638525945/3555a/3Ry8buHurpM.jpg";
    public static final String GROUP_IMAGE_2 = "https://sun9-23.userapi.com/c633220/v633220170/240eb/nfkzuZTtzT8.jpg";
    public static final String GROUP_IMAGE_3 = "https://sun9-3.userapi.com/Es0QgDkg2U5jLlmveIGMPnFTeLndWmCokn-3vQ/w88V4lOEV9c.jpg";
    public static final String GROUP_IMAGE_4 = "https://sun9-24.userapi.com/c850532/v850532211/1b868a/OPmZDkE6BcE.jpg";

    static final int NOTIFICATION_ID_FOREGROUND_SERVICE = 8466503;

    public static class ACTION {
        static final String MAIN_ACTION = "test.action.main";
        public static final String START_ACTION = "test.action.start";
        public static final String STOP_ACTION = "test.action.stop";
    }

    static class STATE_SERVICE {
        static final int CONNECTED = 10;
        static final int NOT_CONNECTED = 0;
    }

}
