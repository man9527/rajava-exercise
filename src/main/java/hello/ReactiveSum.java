package hello;

import rx.Observable;
import rx.Observer;

/**
 * Created by wchu on 12/7/2016.
 */
public final class ReactiveSum implements Observer<Double> {
    private double sum=0;

    public ReactiveSum(Observable<Double> a, Observable<Double> b) {
        Observable.combineLatest(a, b, (a1,b1) -> a1+b1).subscribe(this);
    }

    @Override
    public void onCompleted() {
        System.out.println("Final sum is=" + sum);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Double aDouble) {
        sum += aDouble;
        System.out.println("New sum is=" + sum);
    }
}
