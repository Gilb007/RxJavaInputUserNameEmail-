package vlad.kolomysov.rxjavainputusernameemail;

import android.app.Fragment;
import android.database.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import rx.Observer;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by admin on 08.10.15.
 */
public class PlaceholderFragment extends Fragment
{

    private EditText mUserNameEdit;
    private EditText mEmailEdit;
    private Button mRegisterButton;

    public PlaceholderFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mUserNameEdit = (EditText) rootView.findViewById(R.id.edtUserName);
        mEmailEdit = (EditText) rootView.findViewById(R.id.edtEmail);
        mRegisterButton = (Button) rootView.findViewById(R.id.buttonRegister);

        mRegisterButton.setEnabled(false);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        // return text if it more 4
        rx.Observable<OnTextChangeEvent> usertextname = WidgetObservable.text(mUserNameEdit);
        usertextname.filter(new Func1<OnTextChangeEvent, Boolean>() {
            @Override
            public Boolean call(OnTextChangeEvent onTextChangeEvent) {
                return onTextChangeEvent.text().length() > 4;
            }
        }).subscribe(new Action1<OnTextChangeEvent>() {
            @Override
            public void call(OnTextChangeEvent onTextChangeEvent) {
                Log.d("bool2", "true "+onTextChangeEvent.text());
            }
        });

//       1) UserName should be more than 4 characters in length.
//       2) Email should match a simple regex validaton.
        final Pattern emailPattern = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        //**************-----*****************
        rx.Observable<Boolean> userNameValid = WidgetObservable.text(mUserNameEdit)
                .map(new Func1<OnTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(OnTextChangeEvent onTextChangeEvent) {
                        return onTextChangeEvent.text().length() >4;
                    }
                });


        userNameValid.map(new Func1<Boolean,Integer>() {
            @Override
            public Integer call(Boolean aBoolean) {
                return aBoolean?Color.BLACK:Color.RED;
            }
        }).distinctUntilChanged().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                Log.v("color","color "+integer);
                mUserNameEdit.setTextColor(integer);
            }
        });
        //************************************



        //
        rx.Observable<Boolean> emailValid = WidgetObservable.text(mEmailEdit)
                .map(new Func1<OnTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(OnTextChangeEvent onTextChangeEvent) {
                        return emailPattern.matcher(onTextChangeEvent.text()).matches();
                    }
                });

        emailValid.map(new Func1<Boolean, Integer>()
        {
            @Override
            public Integer call(Boolean aBoolean) {
                return aBoolean?Color.GREEN:Color.BLUE;
            }
        }).distinctUntilChanged().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mEmailEdit.setTextColor(integer);
            }
        });


        rx.Observable<Boolean> registerEnabled = rx.Observable.combineLatest(userNameValid, emailValid, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean, Boolean aBoolean2) {
                return aBoolean && aBoolean2;
            }
        });

        registerEnabled.distinctUntilChanged().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean == true) {
                    mRegisterButton.setEnabled(true);
                }
                else {
                    mRegisterButton.setEnabled(false);
                }
            }
        });






}

}
