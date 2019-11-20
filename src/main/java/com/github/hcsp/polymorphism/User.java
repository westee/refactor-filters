package com.github.hcsp.polymorphism;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class User {
    private final Integer id;
    private final String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        // Consumer 接口内部接受一个对象，输出void（没有输出）
        users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                // println方法内部接受一个对象，输出void，符合这个规则因此可以进行转换。
                System.out.println(user);
            }
        });
//        users.forEach(System.out::println);
//        users.forEach(User::myPrint);
    }

    public static void test() {
//        map(new User(1, "老王"), new Function<User, String>() {
//            @Override
//            public String apply(User user) {
//                return user.getName();
//            }
//        });
        map(new User(1, "老王"), user -> user.getName());
//          map(new User(1, "老王"), User::getName);
    }

    public static String map(User user, Function<User, String> function) {
        return function.apply(user);
    }

    // 实例方法 实际有一个this参数
    public boolean isLi() {
        return this.name.startsWith("王");
    }

    // 静态方法
    private static boolean userWithEvenId(User user) {
        return user.getId() % 2 == 0;
    }

    // 过滤ID为偶数的用户
    public static List<User> filterUsersWithEvenId(List<User> users) {
        // Java 8 中我们可以通过 `::` 关键字来访问类的构造方法，对象方法，静态方法。
        return filter(users, User::userWithEvenId);
    }

    // 过滤姓张的用户
    public static List<User> filterZhangUsers(List<User> users) {
        // lambda表达式
        return filter(users, (Predicate<User>) user -> user.getName().startsWith("张"));
    }

    public static List<User> filterLi(List<User> users) {
        return filter(users, User::isLi);
    }

    // 过滤姓王的用户
    public static List<User> filterWangUsers(List<User> users) {
        return filter(users, new startWithWang());
    }

    // 永远使用static的内部类，除非报错
    static class startWithWang implements Predicate<User> {
        @Override
        public boolean test(User user) {
            return user.getName().startsWith("王");
        }
    }

    // 并简化上面三个函数
    public static List<User> filter(List<User> users, Predicate<User> predicate) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (predicate.test(user)) {
                results.add(user);
            }
        }
        return results;
    }


}
