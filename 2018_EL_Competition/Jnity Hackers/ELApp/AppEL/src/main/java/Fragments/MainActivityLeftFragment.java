

/**
 * Created by hxh on 2018/5/17.
 */
package Fragments;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.lenovo.elapp.R;
/**
 * The main scene you can see when open the app.
 * it has involved in the mainActivity.
 * */
public class MainActivityLeftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_left, container, false);
    }
}
