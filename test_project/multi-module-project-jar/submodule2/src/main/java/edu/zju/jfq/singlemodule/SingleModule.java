package edu.zju.jfq.singlemodule;

public class SingleModule {
    private class InnerClass {
        public static final long SERIALIZEABLE_ID = 1;
        private static final int ID = 2;

        public void output() {
            System.out.println(ID);
        }
    }
    public boolean isExist(UserInfo userInfo) {
        //check the user email
        String email = "jfq@zju.edu.cn";
        return userInfo.getEmail() == email;
    }

    public void testInnerClass() {
        InnerClass ac = this.new InnerClass();
        ac.output();
    }

    public void testAnoClass() {
        Login lg = new Login() {
            @Override
            public void login(UserInfo info) {
                info.setName("Alice");
            }

            @Override
            public boolean register(UserInfo info) {
                return info.getAge() == 18;
            }

            @Override
            public void exit(UserInfo info) {
                System.out.println(info.getName() + " exit.");
            }

            @Override
            public void logInfo(String logInfo) {
                System.out.println(logInfo);
            }
        };
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(18);
        lg.login(userInfo);
        lg.exit(userInfo);
    }
}