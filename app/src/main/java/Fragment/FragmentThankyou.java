package Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.HomeActivity;
import com.pinknblue.R;

/**
 * Created by Param
 */

public class FragmentThankyou extends Fragment implements View.OnClickListener{

    Button btn_continue;
    TextView tv_message;
    String message =   "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thankyou,container,false);

        message    =   getArguments().getString("message");

        initUI(view);

        return view;
    }


    private void initUI(View view) {
        btn_continue         =   (Button)view.findViewById(R.id.btn_continue);
        tv_message         =   (TextView) view.findViewById(R.id.tv_message);
        tv_message.setText(message);

        btn_continue.setOnClickListener(this);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                Intent intent=new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("type","");
                startActivity(intent);
                getActivity().finish();
                break;

        }
    }


}