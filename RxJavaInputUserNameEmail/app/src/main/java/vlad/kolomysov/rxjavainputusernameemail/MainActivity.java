package vlad.kolomysov.rxjavainputusernameemail;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by admin on 08.10.15.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //***************111***************************
        // just print in ADM "Hello, Vlad" and "Hello, Moscow!"  - CREATE
        // The helloObservable will emits "Hello World".
        Observable<String> helloWorldVlad = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello, Vlad");
                subscriber.onNext("Hello, Moscow!");
                subscriber.onCompleted();
            }
        });

        // helloObservable is being subscribed. The subscriber is an instance of Action1. It takes 1 parameter in form of a String and print them.
        helloWorldVlad.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("helloWorldVlad", "1) call() called with: " + "s1 = [" + s + "]");
            }
        });


        // second sunscriber helloWorldVlad
        helloWorldVlad.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("helloWorldVlad", "2) call() called with: " + "s2 = [" + s + "]");
            }
        });
        //***************111***************************

        //***************222***************************
        // emit list of names
        List<String> names = Arrays.asList("Didiet", "Doni", "Asep", "Reza",
                "Sari", "Rendi", "Akbar");

        Observable<String> emitNames = Observable.from(names);

        emitNames.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("emitNames", "call() called with: " + "s = [" + s + "]");
            }
        });
        //***************222***************************

        //***************333***************************
        Observable<String> emitStringJust = Observable.just("OFK");

        emitStringJust.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("emitStringJust", "call() called with: " + "s = [" + s + "]");
            }
        });
        //***************333***************************

        // emit range of Integer form 1 to 10
        //***************444***************************
        Observable<Integer> emitRangeInteger = Observable.range(1,10);

        emitRangeInteger.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("emitRangeInteger", "call() called with: " + "integer = [" + integer + "]");
            }
        });

        //***************444***************************


        //***************555**************************
        emitNames.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s.toUpperCase();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("emitNames", "call() called with: " + "s = [" + s + "]");
            }
        });



       //***************555**************************
        List<String> names2 = Arrays.asList("Didiet Doni1111", "Doni Asep33 Asep4444","Sari", "Rendi", "Akbar");

        Observable<String> names2Rx = Observable.from(names2);
        names2Rx.flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return Observable.timer(10,TimeUnit.SECONDS);
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.d("names2", "call() called with: " + "o = [" + o + "]");
            }
        });


        if (savedInstanceState == null){

            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        }


}
