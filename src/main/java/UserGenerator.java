public class UserGenerator {
    public static User generic() {
        return new User("mda", "qwerty", "menshikoffda@yandex.ru");
    }
    public static User genericNoName() {
        return new User("", "qwerty", "menshikoffda@yandex.ru");
    }
    public static User genericNoPassword() {
        return new User("mda", "", "menshikoffda@yandex.ru");
    }
    public static User genericNoEmail() {
        return new User("mda", "qwerty", "");
    }

}
