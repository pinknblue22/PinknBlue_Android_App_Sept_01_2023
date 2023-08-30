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
 * Created by Param
 */

public class FragmentFaq extends Fragment {
    WebView web_view_faq;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_faq,container,false);
        web_view_faq=(WebView)view.findViewById(R.id.web_view_faq);
        String url=Base_URL+"webservices/pages/faqs";
        web_view_faq.getSettings().setJavaScriptEnabled(true);
        web_view_faq.loadUrl(url);
        return view;
    }
}
