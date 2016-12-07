package hello;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by wchu on 12/7/2016.
 */
public class HelloWorld {
    public static void main(String[] args) {
        ConnectableObservable<String> input = from(System.in);
        Observable<Double> a = varStream("a", input);
        Observable<Double> b = varStream("b", input);
        ReactiveSum sum = new ReactiveSum(a,b);

        input.connect();
    }

    private static ConnectableObservable<String> from(InputStream in) {
        return from (new BufferedReader(new InputStreamReader(in)));
    }

    private static ConnectableObservable<String> from(BufferedReader in) {
        return Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            if (subscriber.isUnsubscribed()) {
                return;
            }

            try {
                String line;

                while(!subscriber.isUnsubscribed()) {
                    line = in.readLine();
                    if (line==null || line.equals("exit")) {
                        break;
                    }

                    subscriber.onNext(line);
                }

            } catch(IOException io) {
                subscriber.onError(io);
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }

        }).publish();
    }

    public static Observable<Double> varStream(String varName, Observable<String> input) {
        final Pattern pattern = Pattern.compile("\\s*" + varName + "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$");
        return input.map(str-> pattern.matcher(str))
                .filter(matcher -> matcher.matches() && matcher.group(1)!=null)
                .map(matcher -> Double.parseDouble(matcher.group(1)));
    }

}
