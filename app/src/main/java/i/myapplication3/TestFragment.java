package i.myapplication3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {
    Activity context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    public static TestFragment newInstance(String message) {
        TestFragment ff = new TestFragment();
        //Supply the construction argument for this fragment
        Bundle b = new Bundle();
        b.putString("mess", message);
        ff.setArguments(b);
        return (ff);
    }

    public void onStart() {
        super.onStart();
        TextView tv = (TextView) context.findViewById(R.id.txt_view);
        //read argument data and show it in the TextVview
        tv.setText(getArguments().getString("mess"));
    }
}
