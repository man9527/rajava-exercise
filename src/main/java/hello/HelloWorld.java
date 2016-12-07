package hello;

import rx.Observable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wchu on 12/7/2016.
 */
public class HelloWorld {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("one", "two", "three");

        Observable<String> observable = Observable.from(list);

        observable.subscribe(e-> System.out.println("1" + e));

        observable.subscribe(e-> System.out.println("2" + e));


    }
}
