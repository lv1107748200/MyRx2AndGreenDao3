package com.hr.myrx2andgreendao3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hr.myrx2andgreendao3.dao.operate.DBCallBack;
import com.hr.myrx2andgreendao3.dao.operate.UserDataManager;
import com.hr.myrx2andgreendao3.entiy.Dog;
import com.hr.myrx2andgreendao3.entiy.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
    }

    public void onlick(View view) {
        switch (view.getId()) {
            case R.id.check:

                UserDataManager.getInstance().LoadAll(new DBCallBack<List<User>>() {
                    @Override
                    public void onDBError(Throwable e) {
                        System.out.println("DB--->onDBError" + e.toString());
                    }

                    @Override
                    public void onDBSuccess(List<User> users) {
                        System.out.println("DB--->" + users.size());

                        StringBuffer stringBuffer = new StringBuffer();


                        for(int i=0; i<users.size();i++){
                            User user = users.get(i);
                            stringBuffer.append(user.getName()+"\n");
                            user.refresh();
                            List<Dog> dogs = user.getDogs();

                            if(null != dogs){
                                for(int j = 0; j<dogs.size(); j++){
                                    stringBuffer.append("\t"+ dogs.get(j).getName()+"\n");
                                }
                            }
                        }

                        textView.setText(stringBuffer);

                    }
                });

                break;

            case R.id.insert:

                set();
                get();
                break;
        }
    }



    private void set(){
      final   long potion = getNumber();

      final  List<Dog> dogss = new ArrayList<>();

        for(int i = 0 ; i<4; i++){
            Dog dog = new Dog();
            dog.setName("哈喇子"+potion);
            dog.setTagId(potion);
            dogss.add(dog);
        }




        UserDataManager.getInstance().insertOrReplaceInTx(dogss, new DBCallBack<List<Dog>>() {
            @Override
            public void onDBError(Throwable e) {
                System.out.println("DB--->onDBError" + e.toString());
            }

            @Override
            public void onDBSuccess(List<Dog> dogs) {
                User user = new User();
                user.setId(potion);
                user.setName(potion+"--->小明");
                user.setDogs(dogs);


                UserDataManager.getInstance().insertOrReplace(user, new DBCallBack<User>() {
                    @Override
                    public void onDBError(Throwable e) {

                    }

                    @Override
                    public void onDBSuccess(User user) {

                    }
                });
            }
        });



    }

    private long  getNumber(){
        return (int) (Math.random()*10000);
    }

    private void get(){
        Observable<String> cache =  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                Thread.sleep(2000);
                e.onNext("cache");//在操作符 concat 中，只有调用 onComplete 之后才会执行下一个 Observable

              //  e.onComplete();

            }
        });


        Observable<String> network =  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                e.onNext("network");

            }
        });


        cache.concatWith(network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("--->"+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getflamp(){
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        })      .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final String s) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> e) throws Exception {
                                e.onNext("呵呵");
                            }
                        });
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("--->"+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
