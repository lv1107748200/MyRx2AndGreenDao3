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

                break;
        }
    }



    private void set(){
      final   long potion = getNumber();

        List<Dog> dogs = new ArrayList<>();

        for(int i = 0 ; i<4; i++){
            Dog dog = new Dog();
            dog.setName("哈喇子"+potion);
            dog.setTagId(potion);
            dogs.add(dog);
        }




        UserDataManager.getInstance().insertOrReplaceInTx(dogs, new DBCallBack<List<Dog>>() {
            @Override
            public void onDBError(Throwable e) {
                System.out.println("DB--->onDBError" + e.toString());
            }

            @Override
            public void onDBSuccess(List<Dog> dogs) {
                User user = new User();
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

}
