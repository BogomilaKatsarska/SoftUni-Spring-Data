package db02;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        MyConnector.createConnection("root", "123456", "soft_uni");

        Connection connection = MyConnector.getConnection();

        EntityManager em = new EntityManager<User>(connection);

        User user1 = new User("user", "pass", 20, LocalDate.now());

        User firstUser = em.findFirst(User.class);
    }

//    class Singleton{
//        private static Object instance;
//
//        private Singleton(){}
//
//        public static Object getInstance(){
//            if (instance == null){
//                init();
//            }
//            return instance;
//        }
//
//        private static void init(){
//            ...
//            instance...
//        }
//    }
}
