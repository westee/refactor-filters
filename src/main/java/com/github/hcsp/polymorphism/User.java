package com.github.hcsp.polymorphism;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class User {
    /** 用户ID，数据库主键，全局唯一 */
    private final Integer id;

    /** 用户名 */
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

    // 过滤ID为偶数的用户
    public static List<User> filterUsersWithEvenId(List<User> users) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.id % 2 == 0) {
                results.add(user);
            }
        }
        return results;
    }

    // 过滤姓张的用户
    public static List<User> filterZhangUsers(List<User> users) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.name.startsWith("张")) {
                results.add(user);
            }
        }
        return results;
    }

    // 过滤姓王的用户
    public static List<User> filterWangUsers(List<User> users) {
        return new filter(users, new 姓王的条件());
    }

    // 永远使用static的内部类，除非报错
    static class 姓王的条件 implements 条件{
        @Override
        public boolean 判断满不满足一个条件(User user){
            return user.getName().startsWith("王");
        }
    }

    interface 条件{
        boolean 判断满不满足一个条件(User user);
    }
    // 你可以发现，在上面三个函数中包含大量的重复代码。
    // 请尝试通过Predicate接口将上述代码抽取成一个公用的过滤器函数
    // 并简化上面三个函数
    public static List<User> filter(List<User> users, 条件 一个条件) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (一个条件.判断满不满足一个条件(user)) {
                results.add(user);
            }
        }
        return results;
    }
}
