package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.pinknblue.R;

import static uitls.Constant.Base_URL;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by eweb-a1-pc-14 on 1/3/2018.
 */

public class FragmentTerms extends Fragment {
    WebView web_view_term;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_term,container,false);
        web_view_term=(WebView)view.findViewById(R.id.web_view_term);
        String url=Base_URL+"webservices/pages/terms-of-services";
        web_view_term.getSettings().setJavaScriptEnabled(true);
        web_view_term.loadUrl(url);
        return view;
    }
}
