package ru.javawebinar.topjava;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
        System.out.println();
        for (String s: "".split("[,\\s]+")) {
            System.out.println("[" + s + "]");
        }
        System.out.println("STOP");
    }
}
